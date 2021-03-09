package com.example.testathome.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testathome.MainActivity
import com.example.testathome.R
import java.security.Permission
import java.util.*

class SplashActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 1001

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            when {
                checkAllPermissionsGranted(permissions) -> {
                    // You can use the API that requires the permission.
                    Handler().postDelayed({
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }, 3000)

                }
//                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                    Toast.makeText(this, "서비스 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
//                    finish()
//                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    Handler().postDelayed({
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
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