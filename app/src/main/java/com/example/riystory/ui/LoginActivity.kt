package com.example.riystory.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.riystory.R
import com.example.riystory.databinding.ActivityLoginBinding
import com.example.riystory.preference.UserManager
import com.example.riystory.viewmodel.FactoryVM
import com.example.riystory.viewmodel.LoginVM
import com.example.riystory.data.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var factory: FactoryVM
    private val viewModel: LoginVM by viewModels {
        factory
    }
    private lateinit var preferences: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.GONE
        factory = FactoryVM.getInstance(this)
        preferences = UserManager(this)

        animation()
        setAction()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setAction() {
        binding.txtSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
        val edLoginPassword = binding.edLoginPassword
        val icShowPass = binding.icShowPass
        binding.icShowPass.setOnClickListener{
            togglePasswordVisibility(edLoginPassword, icShowPass)
        }
    }
    private fun togglePasswordVisibility(edLoginPassword: EditText, icShowPass: ImageView) {
        val inputType = if (edLoginPassword.inputType == (InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT)) {
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        }
        edLoginPassword.inputType = inputType
        icShowPass.setImageResource(if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24)
        edLoginPassword.setSelection(edLoginPassword.text.length)
    }

    private fun login() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()

        viewModel.login(email, password).observe(this) { response ->
            if (response != null) {
                when (response) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = response.data
                        preferences.setToken(data.loginResult.token)
                        val intent = Intent(this@LoginActivity, StoryActivity::class.java)
                        intent.putExtra(StoryActivity.EXTRA_DATA, data.loginResult.token)
                        showSuccessDialog()
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showErrorDialog()
                    }
                }
            }
        }
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.login)
        builder.setMessage(R.string.login2)
        builder.setPositiveButton("NEXT") { _, _ ->
            val intent = Intent(this, StoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.login3)
        builder.setMessage(R.string.login4)
        builder.setPositiveButton("TRY AGAIN") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun animation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.inputPass, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.loginSignup, View.ALPHA, 1f).setDuration(300)


        val together = AnimatorSet().apply {
            playTogether(emailEditTextLayout, passwordEditTextLayout, login, register)
        }

        AnimatorSet().apply {
            playSequentially(title, together)
            start()
        }
    }
}