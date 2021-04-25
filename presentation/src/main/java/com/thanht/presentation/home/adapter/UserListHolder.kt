package com.thanht.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.thanht.presentation.databinding.ItemUserBinding
import com.thanht.presentation.model.UserInfo
import com.thanht.presentation.util.setSafeOnClickListener

class UserListHolder(
    private val binding: ItemUserBinding,
    private val onClick: (UserInfo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: UserInfo) {
        itemView.setSafeOnClickListener {
            onClick.invoke(data)
        }
        binding.apply {
            info = data
            executePendingBindings()
        }
    }
}