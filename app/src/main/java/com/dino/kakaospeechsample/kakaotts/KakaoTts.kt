package com.dino.kakaospeechsample.kakaotts

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.newtoneapi.TextToSpeechClient
import com.kakao.sdk.newtoneapi.TextToSpeechListener
import com.kakao.sdk.newtoneapi.TextToSpeechManager
import kotlinx.coroutines.launch

fun kakaoTts(
    activity: ComponentActivity,
    kakaoTtsSpeechInfo: KakaoTtsSpeechInfo = KakaoTtsSpeechInfo(),
) =
    lazy { KakaoTts(activity, activity.application, kakaoTtsSpeechInfo) }

fun kakaoTts(
    activity: ComponentActivity,
    builder: KakaoTtsSpeechInfo.Builder.() -> Unit,
): Lazy<KakaoTts> {
    val kakaoTtsSpeechInfo = KakaoTtsSpeechInfo.Builder().apply(builder).build()
    return kakaoTts(activity, kakaoTtsSpeechInfo)
}

fun kakaoTts(
    fragment: Fragment,
    kakaoTtsSpeechInfo: KakaoTtsSpeechInfo = KakaoTtsSpeechInfo(),
) =
    lazy { KakaoTts(fragment, fragment.requireContext(), kakaoTtsSpeechInfo) }

fun kakaoTts(
    fragment: Fragment,
    builder: KakaoTtsSpeechInfo.Builder.() -> Unit,
): Lazy<KakaoTts> {
    val kakaoTtsSpeechInfo = KakaoTtsSpeechInfo.Builder().apply(builder).build()
    return kakaoTts(fragment, kakaoTtsSpeechInfo)
}

class KakaoTts internal constructor(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val kakaoTtsSpeechInfo: KakaoTtsSpeechInfo,
) {

    var onFinishedListener: (() -> Unit)? = null

    var onErrorListener: ((Int, String?) -> Unit)? = null

    private val internalListener = object : TextToSpeechListener {
        override fun onFinished() {
            lifecycleOwner.lifecycleScope.launch {
                onFinishedListener?.invoke()
            }
        }

        override fun onError(code: Int, message: String?) {
            lifecycleOwner.lifecycleScope.launch {
                onErrorListener?.invoke(code, message)
            }
        }
    }

    private val client = TextToSpeechClient.Builder()
        .setListener(internalListener)
        .apply {
            kakaoTtsSpeechInfo.mode.mode.let(this::setSpeechMode)
            kakaoTtsSpeechInfo.voice.voice.let(this::setSpeechVoice)
            kakaoTtsSpeechInfo.speed.let(this::setSpeechSpeed)
        }
        .build()

    var speechText: String
        get() = client.speechText
        set(value) {
            client.speechText = value
        }

    init {
        setupTtsManager()
    }

    private fun setupTtsManager() {
        if (lifecycleOwner.lifecycle.currentState !in listOf(
                Lifecycle.State.INITIALIZED,
                Lifecycle.State.DESTROYED
            )
        ) {
            TextToSpeechManager.getInstance().initializeLibrary(context)
        } else {
            lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    when (event) {
                        Lifecycle.Event.ON_CREATE -> {
                            TextToSpeechManager.getInstance().initializeLibrary(context)
                        }
                    }
                }
            })
        }

        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        client.stop()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        TextToSpeechManager.getInstance().finalizeLibrary()
                    }
                }
            }
        })
    }

    fun play(speechText: String? = null) {
        if (speechText.isNullOrEmpty()) {
            if (client.speechText.isNullOrEmpty()) {
                client.speechText = "변환 할 텍스트를 입력해주세요."
            } else {
                client.play()
            }
        } else {
            client.play(speechText)
        }
    }

    fun stop() {
        if (client.isPlaying) {
            client.stop()
        }
    }

}
