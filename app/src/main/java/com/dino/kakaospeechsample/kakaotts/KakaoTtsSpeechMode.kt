package com.dino.kakaospeechsample.kakaotts

import com.kakao.sdk.newtoneapi.TextToSpeechClient

enum class KakaoTtsSpeechMode(val mode: String) {
    NEWTONE_TALK_1(TextToSpeechClient.NEWTONE_TALK_1),
    NEWTONE_TALK_2(TextToSpeechClient.NEWTONE_TALK_2)
}
