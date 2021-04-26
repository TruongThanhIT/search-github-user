package com.thanht.presentation.home.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.navArgs
import com.thanht.presentation.R
import com.thanht.presentation.base.BaseFragment
import com.thanht.presentation.common.SimpleDialog
import com.thanht.presentation.databinding.FragmentUserProfileBinding
import com.thanht.presentation.util.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>(
    R.layout.fragment_user_profile,
    UserProfileViewModel::class
) {

    private val args: UserProfileFragmentArgs by navArgs()
    private var simpleDialog: SimpleDialog? = null

    override fun FragmentUserProfileBinding.initViews() {
        viewModel ?: run {
            viewModel = mViewModel
            mViewModel.setUserInfo(args.userInfo)
            initEvents()
        }
        observable()
    }

    private fun observable() {
        mViewModel.errorMsgLive.observe(viewLifecycleOwner) { msg ->
            showErrorDialog(msg)
        }
    }

    private fun initEvents() = with(mBinding) {
        ivBack.setSafeOnClickListener {
            navController.navigateUp()
        }
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