package com.example.demo.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.base.BaseFragmentWithBinding
import com.app.base.common.State
import com.example.demo.databinding.FragmentDetailBinding
import com.example.demo.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragmentWithBinding<FragmentDetailBinding>() {
    
    private val viewModel: DetailViewModel by viewModels()
    
    override fun getViewBinding(inflater: LayoutInflater): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }
    
    override fun init() {
        setupToolbar()
        observeViewModel()
        viewModel.loadDetail()
    }

    
    override fun initData() {
        // Initialize data if needed
    }
    
    override fun initAction() {
        // Initialize actions if needed
    }
    
    
    
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    
    private fun observeViewModel() {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    // Handle loading state if needed
                }
                is State.Success -> {
                    val detailItem = state.data
                    binding.apply {
                        title.text = detailItem.title
                        content.text = detailItem.description
                    }
                }
                is State.Error -> {
                    // Handle error state if needed
                }
                else -> {
                    // Handle other states if needed
                }
            }
        }
    }
}
