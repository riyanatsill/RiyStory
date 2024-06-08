package com.example.riystory.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.riystory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAction()
        animation()
    }

    private fun setAction() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun animation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.txtWelcomeTit, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.txtWelcomeDesc, View.ALPHA, 1f).setDuration(500)
        val ors = ObjectAnimator.ofFloat(binding.tvRegis, View.ALPHA, 1f).setDuration(500)


        val together = AnimatorSet().apply {
            playTogether(login, signup, ors)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}