package com.example.demo.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.app.base.BaseFragmentWithBinding
import com.example.demo.databinding.FragmentNewsfeedBinding
import com.example.demo.ui.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsfeedFragment : BaseFragmentWithBinding<FragmentNewsfeedBinding>() {

    private val viewModel: NewsViewModel by viewModels()
    private val categories = listOf("Following", "For You", "Football", "Technology", "Lifestyle")

    override fun getViewBinding(inflater: LayoutInflater): FragmentNewsfeedBinding {
        return FragmentNewsfeedBinding.inflate(inflater)
    }

    override fun init() {

        setupCategoryTabs()
        onListenSwipeToRefresh()
        openFragment(
            fragmentManager = requireFragmentManager(),
            ContentFragment::class.java,
            binding.content.id
        )

    }

    override fun initData() {
        // Initialize data if needed
    }

    override fun initAction() {
        // Initialize actions if needed
    }

    private fun setupCategoryTabs() {
        // Add tabs to TabLayout

        categories.forEach { category ->
            binding.categoryTabLayout.addTab(
                binding.categoryTabLayout.newTab().setText(category)
            )
        }

        // Set default selected tab (index 1 = "Cho bạn")
        binding.categoryTabLayout.selectTab(binding.categoryTabLayout.getTabAt(1))

        // Set tab selection listener
        binding.categoryTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    // Open ContentFragment when tab is selected
                    openFragment(
                        fragmentManager = requireFragmentManager(),
                        ContentFragment::class.java,
                        binding.content.id
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselected
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselected
            }
        })
    }

    private fun onListenSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refreshNewsfeed()
        }
    }


}
