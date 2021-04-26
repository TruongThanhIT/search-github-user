package com.thanht.presentation.home

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.thanht.presentation.R
import com.thanht.presentation.base.BaseFragment
import com.thanht.presentation.common.SimpleDialog
import com.thanht.presentation.databinding.FragmentHomeBinding
import com.thanht.presentation.ext.changeStatusBar
import com.thanht.presentation.ext.hideSoftKeyboard
import com.thanht.presentation.ext.showSoftKeyboard
import com.thanht.presentation.home.adapter.LoadStateAdapter
import com.thanht.presentation.home.adapter.UserItemDecorator
import com.thanht.presentation.home.adapter.UserListAdapter
import com.thanht.presentation.model.UserInfo
import com.thanht.presentation.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class
) {
    private lateinit var adapter: UserListAdapter
    private lateinit var textWatcher: TextWatcher
    private var simpleDialog: SimpleDialog? = null

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun FragmentHomeBinding.initViews() {
        viewModel ?: run {
            viewModel = mViewModel
            initUI()
            initEvents()
            activity.changeStatusBar(
                fullLayout = false,
                color = getColor(R.color.colorPrimaryDark)
            )
        }
        observable()
    }

    override fun onResume() {
        super.onResume()
        mBinding.edtSearch.apply {
            addTextChangedListener(textWatcher)
            requestFocus()
            showSoftKeyboard()
        }
    }

    override fun onPause() {
        mBinding.edtSearch.apply {
            removeTextChangedListener(textWatcher)
            clearFocus()
            hideSoftKeyboard()
        }
        super.onPause()
    }

    private fun observable() = mViewModel.run {
        searchHandler.searchUserResultLive.observe(viewLifecycleOwner) { result ->
            lifecycleScope.launch {
                adapter.submitData(result)
            }
        }

        searchHandler.errorMsgLive.observe(viewLifecycleOwner) { msg ->
            showErrorDialog(msg)
        }
    }

    private fun initEvents() = mViewModel.run {
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                queryWeather(s)
            }
        }
    }

    private fun initUI() {
        adapter = UserListAdapter {
            itemsOnclick(it)
        }
        mBinding.rvInfo.apply {
            adapter =
                this@HomeFragment.adapter.withLoadStateFooter(LoadStateAdapter(this@HomeFragment.adapter))
            addItemDecoration(UserItemDecorator(context))
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun itemsOnclick(userInfo: UserInfo) {
        val action = HomeFragmentDirections.toUserProfileFragment(userInfo)
        navController.navigate(action)
    }

    private fun showErrorDialog(msg: String?) {
        val content = if (msg.isNullOrBlank()) getString(R.string.content_unexpected_error) else msg
        simpleDialog = SimpleDialog.create(requireContext(), viewLifecycleOwner)
            .setTitle(getString(R.string.title_unexpected_error))
            .setContent(content)
            .setPositiveLabel(getString(R.string.title_close))
            .also {
                it.show()
            }
    }
}