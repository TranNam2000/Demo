package com.example.demo.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.app.base.recyclerview.BaseRecyclerAdapter
import com.app.base.recyclerview.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.demo.R
import com.example.demo.databinding.ItemNewsBinding
import com.example.demo.model.NewsItem
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private val onItemClick: (NewsItem) -> Unit
) : BaseRecyclerAdapter<NewsItem, NewsAdapter.NewsViewHolder>() {

    override fun getItemLayoutResource(viewType: Int): Int {
        return R.layout.item_news
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(getViewHolderDataBinding(parent, viewType))
    }

    inner class NewsViewHolder(
        private val binding: ViewDataBinding
    ) : BaseViewHolder<NewsItem>(binding) {

        override fun bind(itemData: NewsItem?) {
            super.bind(itemData)
            if (binding is ItemNewsBinding) {
                itemData?.let { newsItem ->
                    // Set title
                    binding.title.text = newsItem.title

                    // Set source and time
                    binding.source.text = newsItem.publisher?.name
                    binding.publishDate.text = formatTimeAgo(newsItem.published_date)

                    // Set main image
                    val imageUrl = newsItem.avatar?.href ?: newsItem.images?.firstOrNull()?.href
                    if (imageUrl == null) {
                        binding.imageView.visibility = View.GONE
                    } else {
                        binding.imageView.visibility = View.VISIBLE
                    }

                    Glide.with(binding.imageView.context)
                        .load(imageUrl)
                        .apply(
                            RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .error(android.R.drawable.ic_menu_report_image)
                        )
                        .into(binding.imageView)

                    // Set time overlay for videos
                    if (newsItem.content_type == "video" && newsItem.content?.duration != null) {
                        binding.timeOverlay.visibility = View.VISIBLE
                        binding.timeOverlay.text = formatDuration(newsItem.content.duration)
                    } else {
                        binding.timeOverlay.visibility = View.GONE
                    }

                    // Show grid layout for gallery items
                    if (newsItem.content_type == "gallery" && (newsItem.images?.size ?: 0) >= 3) {
                        binding.gridLayout.visibility = View.VISIBLE

                        // Load grid images
                        newsItem.images?.let { images ->
                            if (images.isNotEmpty()) {
                                binding.gridImage1.visibility = View.VISIBLE
                                Glide.with(binding.gridImage1.context)
                                    .load(images[0].href)
                                    .apply(RequestOptions().centerCrop())
                                    .into(binding.gridImage1)
                            } else {
                                binding.gridImage1.visibility = View.GONE
                            }
                            if (images.size >= 2) {
                                binding.gridImage2.visibility = View.VISIBLE
                                Glide.with(binding.gridImage2.context)
                                    .load(images[1].href)
                                    .apply(RequestOptions().centerCrop())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(binding.gridImage2)
                            } else {
                                binding.gridImage2.visibility = View.GONE
                            }
                            if (images.size >= 3) {
                                binding.gridImage3.visibility = View.VISIBLE

                                Glide.with(binding.gridImage3.context)
                                    .load(images[2].href)
                                    .apply(RequestOptions().centerCrop())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(binding.gridImage3)
                            } else {
                                binding.gridImage3.visibility = View.GONE
                            }
                        }
                    } else {
                        binding.gridLayout.visibility = View.GONE
                        binding.imageView.visibility = View.VISIBLE
                    }
                    if (position == itemCount - 1) {
                        binding.divider.visibility = View.GONE
                    }
                    onItemClickListener {
                        onItemClick(newsItem)
                    }
                }
            }
        }

        private fun formatTimeAgo(dateString: String?): String {
            return try {
                if (dateString == null) return ""
                val inputFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                val now = Date()
                val diff = now.time - (date?.time ?: 0)

                when {
                    diff < 24 * 60 * 60 * 1000 -> "Hôm nay"
                    diff < 7 * 24 * 60 * 60 * 1000 -> "1 tuần"
                    diff < 30 * 24 * 60 * 60 * 1000 -> "1 tháng"
                    else -> "1 năm"
                }
            } catch (e: Exception) {
                "1 tháng"
            }
        }

        @SuppressLint("DefaultLocale")
        private fun formatDuration(seconds: Int): String {

            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60

            return if (hours > 0) {
                String.format("%d:%02d:%02d", hours, minutes, secs)
            } else {
                String.format("%d:%02d", minutes, secs)
            }
        }
    }
}



