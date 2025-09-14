package com.example.demo.ui.fragment

import android.view.LayoutInflater
import com.app.base.BaseFragmentWithBinding
import com.example.demo.R
import com.example.demo.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragmentWithBinding<FragmentMainBinding>() {


    private val newsfeedFragment by lazy { NewsfeedFragment() }
    private val accountFragment by lazy { AccountFragment() }

    override fun getViewBinding(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun init() {
        setupBottomNavigation()
        showFragment(newsfeedFragment, R.id.fragment_container_view_tag)
    }

    override fun initData() {
        // Initialize data if needed
    }

    override fun initAction() {
        // Initialize actions if needed
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newsfeedFragment -> {
                    showFragment(newsfeedFragment, R.id.fragment_container_view_tag)
                    true
                }

                R.id.accountFragment -> {
                    showFragment(accountFragment, R.id.fragment_container_view_tag)
                    true
                }

                else -> false
            }
        }
    }


}