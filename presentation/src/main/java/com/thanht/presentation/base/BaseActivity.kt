package com.thanht.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.thanht.presentation.navigation.NavigationDispatcher

abstract class BaseActivity : AppCompatActivity() {
    abstract val viewModel: BaseViewModel
    abstract val navController: NavController
    abstract val navDispatcher: NavigationDispatcher
}
