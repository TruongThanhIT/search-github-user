package com.thanht.presentation.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.thanht.presentation.R
import javax.inject.Inject

class NavigationDispatcherImpl @Inject constructor(
    private val navController: NavController
) : NavigationDispatcher {

    override fun goBack() {
        navController.navigateUp()
    }

    override fun navigateToHome() {
        val navOptions = navOptions {
            launchSingleTop = true
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
            }
            popUpTo(R.id.splashFragment) { inclusive = true }
        }
        navController.navigate(R.id.homeFragment, null, navOptions)
    }

    override fun navigateToUserProfile() {
        val navOptions = navOptions {
            launchSingleTop = true
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
            }
            popUpTo(R.id.homeFragment) { inclusive = false }
        }
        navController.navigate(
            R.id.userProfileFragment,
            bundleOf(),
            navOptions
        )
    }
}
