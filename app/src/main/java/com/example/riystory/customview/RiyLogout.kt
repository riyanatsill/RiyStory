package com.example.riystory.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.riystory.R

class RiyLogout : AppCompatButton {

    private lateinit var first: Drawable
    private lateinit var second: Drawable

    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        first = ContextCompat.getDrawable(context, R.drawable.button_bg) as Drawable
        second = ContextCompat.getDrawable(context, R.drawable.button_bg_dis) as Drawable

        // Atur latar belakang awalnya ke enabledBackground
        background = second

        // Tambahkan listener untuk mengubah latar belakang tombol saat ditekan
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    background = first
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    background = second
                }
            }
            false
        }
    }
}