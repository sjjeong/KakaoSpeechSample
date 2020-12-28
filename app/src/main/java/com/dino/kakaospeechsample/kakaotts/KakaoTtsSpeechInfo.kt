package com.dino.kakaospeechsample.kakaotts

data class KakaoTtsSpeechInfo(
    val mode: KakaoTtsSpeechMode = KakaoTtsSpeechMode.NEWTONE_TALK_2,
    val voice: KakaoTtsSpeechVoice = KakaoTtsSpeechVoice.WOMAN_READ_CALM,
    val speed: Double = 1.1,
) {

    class Builder() {
        var mode: KakaoTtsSpeechMode = KakaoTtsSpeechMode.NEWTONE_TALK_2
        var voice: KakaoTtsSpeechVoice = KakaoTtsSpeechVoice.WOMAN_READ_CALM
        var speed: Double = 1.1

        fun build() = KakaoTtsSpeechInfo(mode, voice, speed)
    }
}
