package com.duzi.gudicafeteria_a

import android.os.Bundle
import android.widget.Toast
import com.duzi.gudicafeteria_a.base.BaseActivity

class MainActivity : BaseActivity() {

    override val requestedPermissionList: List<String> = listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override val initView: () -> Unit = {
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
    }
}
