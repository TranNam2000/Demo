package com.example.demo

import android.os.Build
import androidx.core.content.ContextCompat
import com.app.base.BaseActivity
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.ui.fragment.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    
    override fun getViewBinding(inflater: android.view.LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
    
    override fun init() {
        setStatusBarColor()

        openFragment(MainFragment::class.java)
    }
    
    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.red)
            
            // Make status bar icons dark for better visibility on red background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = 
                    window.decorView.systemUiVisibility or android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

}