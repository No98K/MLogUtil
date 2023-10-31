package com.hj.logoutput

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.hj.logoutput.databinding.ActivityMainBinding
import com.hj.logoutput.util.LogUtil
import android.Manifest

class MainActivity : AppCompatActivity() {
    // 定义权限请求码
    private val READ_WRITE_PERMISSION_REQUEST_CODE = 1


    private lateinit var binding: ActivityMainBinding

    private var num = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        checkPermissions()

        binding.btnRequestOne.setOnClickListener {
//            num++
//            LogUtil.e("存储日志" + num, true)
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 已经有权限，可以执行文件读写操作
            LogUtil.e("拥有权限")
        } else {
            // 请求权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                READ_WRITE_PERMISSION_REQUEST_CODE
            )
        }

    }

    // 处理权限请求的结果
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_WRITE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                // 用户授予了读写权限，可以进行文件操作
                LogUtil.e("拥有权限")
            } else {
                // 用户拒绝了权限请求，可以提示用户或执行相应的操作
                LogUtil.e("没有权限")
            }
        }
    }

}