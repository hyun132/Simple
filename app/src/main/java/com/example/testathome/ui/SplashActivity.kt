package com.example.testathome.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.testathome.R
import com.example.testathome.databinding.ActivityMainBinding
import com.example.testathome.databinding.ActivitySplashBinding
import com.google.android.material.snackbar.Snackbar

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_splash
        )

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)


    }


}