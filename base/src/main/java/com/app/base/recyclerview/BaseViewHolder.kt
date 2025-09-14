package com.app.base.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.app.base.utils.click

open class BaseViewHolder<T : Any>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    private var _itemData: T? = null
    val itemData get() = _itemData

    open fun bind(itemData: T?) {
        this._itemData = itemData
    }

    open fun onItemClickListener( callBack : ()-> Unit){
        itemView.click {
            callBack.invoke()
        }
    }

    open fun onLongClickListener( callBack : ()-> Unit){
        itemView.setOnLongClickListener{
            callBack.invoke()
            true
        }
    }

}
