package com.thanht.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.thanht.presentation.databinding.ItemUserBinding
import com.thanht.presentation.model.UserInfo

class UserListAdapter(
    private val onItemClickListener: (UserInfo) -> Unit = {}
) : PagingDataAdapter<UserInfo, UserListHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        val userInfo = getItem(position) ?: return
        holder.bindData(userInfo)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserInfo>() {
            override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
