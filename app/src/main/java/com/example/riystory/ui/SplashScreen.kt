package com.example.riystory.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.riystory.databinding.ActivitySplashScreenBinding
import com.example.riystory.preference.UserManager

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var preferences: UserManager
    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = UserManager(this)

        setSplash()

    }

    private fun setSplash() {
        Handler().postDelayed({
            checkSession()
        }, splashTimeOut)
    }

    private fun checkSession() {
        if (!preferences.getToken().isNullOrEmpty()) {
            startActivity(Intent(this, StoryActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}