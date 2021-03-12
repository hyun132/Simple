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

    val PERMISSION_REQUEST_CODE = 1001
    var NEVER_ASK_AGAIN=false

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_splash
        )

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            when {
                checkAllPermissionsGranted(permissions) -> {
                    // You can use the API that requires the permission.
                    Handler(Looper.myLooper()!!).postDelayed({  //Handler 생성 중에 암시 적으로 Looper를 선택하면 작업이 자동으로 손실 (Handler가 새 작업을 예상하지 않고 종료되는 경우)
                        // , 충돌 (Looper가 활성화되지 않은 스레드에서 처리기가 생성되는 경우) 또는 경쟁 조건이 발생하는 버그가 발생할 수 있습니다.
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }, 3000)

                }
//                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                    finish()
//                }

                else -> {
//                    if (!NEVER_ASK_AGAIN){
//                        onRequestPermissionsResult(PERMISSION_REQUEST_CODE,permissions, intArrayOf(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            ,checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)))
//                    }

                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    Handler(Looper.myLooper()!!).postDelayed({
                        ActivityCompat.requestPermissions(
                            this,
                            permissions,
                            PERMISSION_REQUEST_CODE
                        )
                    }, 3000)

                }
            }
        }

    }

    fun checkAllPermissionsGranted(permissions: Array<String>): Boolean {

        var hasGranted = true
        for (permission in permissions) {
            hasGranted = (hasGranted && ContextCompat.checkSelfPermission(applicationContext, permission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return hasGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() ||
                            PackageManager.PERMISSION_DENIED in grantResults)
                ) {
                    //권한 여러개일 때 하나라도 거절된 권한이 있으면
                    Toast.makeText(this, "서비스 사용을 위해 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
                    finish()
                    return
                } else {
                    //모든 권한 동의 된 경우
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


}