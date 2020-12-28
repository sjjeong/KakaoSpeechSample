package com.dino.kakaospeechsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dino.kakaospeechsample.databinding.ActivityMainBinding
import com.kakao.sdk.newtoneapi.TextToSpeechClient
import com.kakao.sdk.newtoneapi.TextToSpeechListener
import com.kakao.sdk.newtoneapi.TextToSpeechManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val ttsClient: TextToSpeechClient by lazy {
        TextToSpeechClient.Builder()
            .setListener(object : TextToSpeechListener {
                override fun onFinished() {
                    lifecycleScope.launch {
                        binding.pbPlay.isVisible = false
                    }
                }

                override fun onError(code: Int, message: String?) {
                    lifecycleScope.launch {
                        binding.pbPlay.isVisible = false
                    }
                }
            })
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TextToSpeechManager.getInstance().initializeLibrary(applicationContext)
        binding.btnPlay.setOnClickListener {
            val inputText = binding.etInput.text.toString()
            ttsClient.play(inputText)
            binding.pbPlay.isVisible = true
        }
    }

    override fun onDestroy() {
        TextToSpeechManager.getInstance().finalizeLibrary()
        super.onDestroy()
    }
}
