package com.dino.kakaospeechsample.kakaotts

import com.kakao.sdk.newtoneapi.TextToSpeechClient

enum class KakaoTtsSpeechVoice(val voice: String) {
    // 여성 차분한 낭독체
    WOMAN_READ_CALM(TextToSpeechClient.VOICE_WOMAN_READ_CALM),

    // 남성 차분한 낭독체
    MAN_READ_CALM(TextToSpeechClient.VOICE_MAN_READ_CALM),

    // 여성 밝은 대화체
    WOMAN_DIALOG_BRIGHT(TextToSpeechClient.VOICE_WOMAN_DIALOG_BRIGHT),

    // 남성 밝은 대화체
    MAN_DIALOG_BRIGHT(TextToSpeechClient.VOICE_MAN_DIALOG_BRIGHT),
}
