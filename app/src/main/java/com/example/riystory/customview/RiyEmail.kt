package com.example.riystory.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Pattern

class RiyEmail : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        // Tambahkan TextWatcher untuk memvalidasi email saat menginput
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun validateEmail(email: String) {
        val pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        val matcher = pattern.matcher(email)

        if (email.isEmpty() || !matcher.matches()) {
            error = "Invalid email address"
        } else {
            error = null
        }
    }

//    fun isValidEmail(): Boolean {
//        val email = text.toString().trim()
//        val pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
//        val matcher = pattern.matcher(email)
//
//        return email.isNotEmpty() && matcher.matches()
//    }
}
