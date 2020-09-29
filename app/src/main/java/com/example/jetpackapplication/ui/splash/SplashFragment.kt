package com.example.jetpackapplication.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.core.base.BaseFragment
import com.example.jetpackapplication.R
import com.example.jetpackapplication.databinding.FragmentSplashBinding
import com.example.jetpackapplication.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel : SplashViewModel by viewModels()


    override fun layoutId() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.actionSPlash.observe(this, {
            when(it){
                SplashActionState.Finish -> {
//                    appNavigation.openLoginScreen()
                }
            }
        })
    }

}