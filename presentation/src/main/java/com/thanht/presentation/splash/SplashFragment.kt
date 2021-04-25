package com.thanht.presentation.splash

import androidx.lifecycle.lifecycleScope
import com.thanht.presentation.MainActivity
import com.thanht.presentation.R
import com.thanht.presentation.base.BaseFragment
import com.thanht.presentation.databinding.FragmentSplashBinding
import com.thanht.presentation.ext.changeStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_TIME = 300L

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    R.layout.fragment_splash,
    SplashViewModel::class
) {

    private val mMainActivity: MainActivity by lazy { activity as MainActivity }

    private val navDispatcher get() = mMainActivity.navDispatcher

    override fun FragmentSplashBinding.initViews() {
        activity.changeStatusBar(
            fullLayout = true,
            color = getColor(R.color.colorPrimary)
        )
        mViewModel.updateState(SplashState.HomeActivity)
        observableData()
    }

    private fun observableData() = with(mViewModel) {
        mViewModel.splashState.observe(viewLifecycleOwner) {
            val splashState = it ?: return@observe
            if (splashState is SplashState.HomeActivity) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() = lifecycleScope.launch {
        delay(DELAY_TIME)
        navDispatcher.navigateToHome()
    }

}
