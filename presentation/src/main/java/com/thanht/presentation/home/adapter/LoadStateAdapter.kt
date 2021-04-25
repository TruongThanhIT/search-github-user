package com.thanht.presentation.home.adapter

import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thanht.presentation.R
import com.thanht.presentation.databinding.ItemUserFooterBinding
import com.thanht.presentation.util.inflate
import com.thanht.presentation.util.viewHolderBinding

class LoadStateAdapter(
    private val pagingDataAdapter: PagingDataAdapter<*, *>
) : LoadStateAdapter<UserFooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        UserFooterViewHolder(parent, pagingDataAdapter)

    override fun displayLoadStateAsItem(loadState: LoadState) =
        !(loadState.endOfPaginationReached && pagingDataAdapter.itemCount < 10)

    override fun onBindViewHolder(holder: UserFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

class UserFooterViewHolder(
    viewGroup: ViewGroup,
    private val pagingDataAdapter: PagingDataAdapter<*, *>
) : RecyclerView.ViewHolder(viewGroup.inflate(R.layout.item_user_footer)) {

    private val mBinding: ItemUserFooterBinding? by viewHolderBinding()

    fun bind(loadState: LoadState) = mBinding?.run {
        val context = root.context
        val count = pagingDataAdapter.itemCount

        when {
            loadState is LoadState.Error -> if (count == 0) {
                progressBar.isVisible = false
                ivEnd.isVisible = false
                tvContentError.isVisible = false
            } else {
                progressBar.isVisible = false
                ivEnd.isInvisible = false
                tvContentError.isVisible = true
                ivEnd.setImageResource(R.drawable.ic_error_block)
                tvContentError.text = context.getString(R.string.title_unexpected_error)
            }

            !loadState.endOfPaginationReached -> {
                progressBar.isVisible = true
                ivEnd.isInvisible = true
                tvContentError.isVisible = true
                tvContentError.text = context.getString(R.string.content_loading_data)
            }

            else -> {
                progressBar.isVisible = true
                ivEnd.isInvisible = true
                tvContentError.isVisible = true
                tvContentError.text = context.getString(R.string.content_loading_data)
            }
        }

        executePendingBindings()
    }
}
