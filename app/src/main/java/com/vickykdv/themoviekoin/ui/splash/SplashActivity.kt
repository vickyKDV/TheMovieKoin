package com.vickykdv.themovie.ui.splash

import android.os.Bundle
import android.view.WindowManager
import com.vickykdv.themoviekoin.base.BaseActivity
import com.vickykdv.themoviekoin.databinding.ActivitySplashBinding
import com.vickykdv.themoviekoin.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    private val binding  by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun setLayout()=binding.root

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        handlerDelay {
            openActivity(MainActivity::class.java,true)
        }
    }

}