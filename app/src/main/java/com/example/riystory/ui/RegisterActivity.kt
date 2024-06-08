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
import com.example.riystory.databinding.ActivityRegisterBinding
import com.example.riystory.viewmodel.FactoryVM
import com.example.riystory.viewmodel.RegisterVM
import com.example.riystory.data.Result

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var factory: FactoryVM
    private val viewModel: RegisterVM by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.GONE
        factory = FactoryVM.getInstance(this)

        animation()
        setAction()
    }

    private fun setAction() {
        binding.txtSignup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        val edLoginPassword = binding.edRegisterPassword
        val icShowPass = binding.icShowPass
        binding.icShowPass.setOnClickListener{
            togglePasswordVisibility(edLoginPassword, icShowPass)
        }
    }

    private fun togglePasswordVisibility(edLoginPassword: EditText, icShowPass: ImageView) {
        val isPasswordVisible = edLoginPassword.inputType != (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

        if (isPasswordVisible) {
            edLoginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            icShowPass.setImageResource(R.drawable.baseline_visibility_off_24)
        } else {
            edLoginPassword.inputType = InputType.TYPE_CLASS_TEXT
            icShowPass.setImageResource(R.drawable.baseline_visibility_24)
        }
        edLoginPassword.setSelection(edLoginPassword.text.length)
    }



    private fun register() {
        val name = binding.edRegisterName.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        viewModel.register(name, email, password).observe(this) { response ->
            when (response) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showSuccessDialog()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showErrorDialog()
                }
                else -> {}
            }
        }
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.regis)
        builder.setMessage(R.string.regis2)
        builder.setPositiveButton("NEXT") { _, _ ->
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.regis3)
        builder.setMessage(R.string.regis4)
        builder.setPositiveButton("TRY AGAIN") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun animation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(300)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.inputName, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.inputPass, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.loginSignup, View.ALPHA, 1f).setDuration(300)


        val together = AnimatorSet().apply {
            playTogether(emailEditTextLayout, passwordEditTextLayout, nameEditTextLayout, login, register)
        }

        AnimatorSet().apply {
            playSequentially(title, together)
            start()
        }
    }
}

