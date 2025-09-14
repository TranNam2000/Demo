package com.example.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.base.BaseFragmentWithBinding
import com.app.base.common.State
import com.example.demo.MainActivity
import com.example.demo.R
import com.example.demo.ui.adapter.NewsAdapter
import com.example.demo.databinding.FragmentContentBinding
import com.example.demo.model.NewsItem
import com.example.demo.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentFragment : BaseFragmentWithBinding<FragmentContentBinding>() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun getViewBinding(inflater: LayoutInflater): FragmentContentBinding {
        return FragmentContentBinding.inflate(inflater)
    }

    override fun init() {

        setupRecyclerView()
        observeViewModel()
        viewModel.loadNewsfeed()
    }

    override fun initData() {
        // Initialize data if needed
    }

    override fun initAction() {
        // Initialize actions if needed
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter { newsItem ->
            openFragment(DetailFragment::class.java, addBackStack = true)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ContentFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.newsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                }

                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    adapter.submitList(state.data, true)
                }

                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.exception
                }

                else -> {
                    // Handle other states if needed
                }
            }
        }
    }

}