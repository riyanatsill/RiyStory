package com.example.riystory.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.riystory.R
import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAction()
        setDetail()
    }

    private fun setAction() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, StoryActivity::class.java))
        }
    }

    private fun setDetail() {
        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA) as ListStoryItem

        binding.imgDetail.load(story.photoUrl) {
            crossfade(true)
            crossfade(500)
            placeholder(android.R.color.darker_gray)
            error(R.drawable.baseline_broken_image_24)
        }
        binding.txtPenulis.text = story.name
        binding.txtCerita.text = story.description
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}