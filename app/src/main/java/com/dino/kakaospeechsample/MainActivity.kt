package com.dino.kakaospeechsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dino.kakaospeechsample.databinding.ActivityMainBinding
import com.dino.kakaospeechsample.kakaotts.kakaoTts

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val kakaoTts by kakaoTts(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewListener()
        setupKakaoTtsListener()
    }

    private fun setupViewListener() {
        binding.btnPlay.setOnClickListener {
            val inputText = binding.etInput.text.toString()
            kakaoTts.play(inputText)
            binding.pbPlay.isVisible = true
        }
    }

    private fun setupKakaoTtsListener() {
        kakaoTts.onFinishedListener = {
            binding.pbPlay.isVisible = false
        }

        kakaoTts.onErrorListener = { i: Int, s: String? ->
            binding.pbPlay.isVisible = false
        }
    }

}
