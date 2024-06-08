package com.example.riystory.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.riystory.R
import com.example.riystory.databinding.ActivitySettingBinding
import com.example.riystory.preference.UserManager
import com.example.riystory.viewmodel.FactoryVM

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var preferences: UserManager
    private lateinit var factory: FactoryVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = FactoryVM.getInstance(this)
        preferences = UserManager(this)

        setAction()
    }

    private fun setAction() {
        binding.logout.setOnClickListener {
            dialogLogout()
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, StoryActivity::class.java))
        }
    }
    private fun dialogLogout() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.logout)
            setMessage(R.string.logout2)
            setCancelable(false)
            setPositiveButton("YES") { _, _ ->
                preferences.setToken(null)
                startActivity(Intent(this@SettingActivity, MainActivity::class.java))
                finish()
            }
            setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}