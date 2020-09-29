package com.example.jetpackapplication.navigation

import com.example.core.navigationComponent.BaseNavigatorImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorImpl @Inject constructor() : BaseNavigatorImpl(), AppNavigation