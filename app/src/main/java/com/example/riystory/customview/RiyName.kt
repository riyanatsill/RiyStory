package com.example.riystory.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class RiyName : AppCompatEditText {

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
        // Tambahkan TextWatcher untuk memvalidasi input nama
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val name = s.toString()
                if (name.isEmpty() || name.length < 4) {
                    error = "Name must not be empty and should have at least 4 characters"
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }
}
