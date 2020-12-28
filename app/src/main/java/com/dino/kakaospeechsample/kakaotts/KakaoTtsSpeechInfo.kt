package com.dino.kakaospeechsample.kakaotts

data class KakaoTtsSpeechInfo(
    val mode: KakaoTtsSpeechMode = KakaoTtsSpeechMode.NEWTONE_TALK_2,
    val voice: KakaoTtsSpeechVoice = KakaoTtsSpeechVoice.WOMAN_READ_CALM,
    val speed: Double = 1.1,
)
