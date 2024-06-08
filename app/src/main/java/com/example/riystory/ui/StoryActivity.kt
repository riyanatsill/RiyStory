package com.example.riystory.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.riystory.adapter.StoryAdapter
import com.example.riystory.adapter.StoryStateAdapter
import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.databinding.ActivityStoryBinding
import com.example.riystory.preference.UserManager
import com.example.riystory.viewmodel.FactoryVM
import com.example.riystory.viewmodel.StoryVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoryActivity : AppCompatActivity(), StoryAdapter.Listener {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var preferences: UserManager
    private lateinit var factory: FactoryVM
    private val viewModel: StoryVM by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = FactoryVM.getInstance(this)
        preferences = UserManager(this)

        setAction()
        setStory()
    }

    private fun setAction() {
        binding.aboutme.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
        binding.map.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setStory() {
        val token = preferences.getToken() ?: ""
        val userToken = "Bearer $token"

        storyAdapter = StoryAdapter(this)
        binding.rvHome.layoutManager = LinearLayoutManager(this)
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = storyAdapter.withLoadStateFooter(
            footer = StoryStateAdapter {
                storyAdapter.retry()
            }
        )

        GlobalScope.launch(Dispatchers.IO) {
            val storyResponse = viewModel.getStory(userToken)
            withContext(Dispatchers.Main) {
                storyResponse.observe(this@StoryActivity, Observer { storyData ->
                    storyAdapter.submitData(lifecycle, storyData)
                    binding.homeLoading.visibility = View.GONE

                    storyAdapter.notifyDataSetChanged()
                })
            }
        }

    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onListener(story: ListStoryItem) {
        startActivity(
            Intent(this@StoryActivity, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_DATA, story)
        )
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}