package com.example.jetpackapplication

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.core.base.BaseActivity
import com.example.jetpackapplication.databinding.ActivityMainBinding
import com.example.jetpackapplication.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var appNavigation: AppNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        appNavigation.bind(navHostFragment.navController)

        CoroutineScope(Dispatchers.Main + Job()).launch {

        }
    }

    override fun layoutId() = R.layout.activity_main

//    private fun getOriginalBitmapAsync(): Deferred<Bitmap> =
//        // 2
//        GlobalScope.async(Dispatchers.IO) {
//            // 3
//            URL(tutorial.url).openStream().use {
//                return@async BitmapFactory.decodeStream(it)
//            }
//        }
}