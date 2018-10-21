package com.duzi.gudicafeteria_a.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import java.util.*

class PermissionRequest(context: Context, permissions: List<String>): Observer {
    private val context = context
    private val requestedPermissions = permissions
    private lateinit var onGranted: () -> Unit

    fun setOnGrantedListener(onGranted: () -> Unit) : PermissionRequest {
        this.onGranted = onGranted
        return this
    }

    override fun update(observer: Observable?, data: Any?) {


        PermissionObserver.deleteObserver(this)
    }

    fun run() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onGranted()
            return
        }

        val deniedPermissions = ArrayList<String>()
        for(permission in requestedPermissions) {
            if(PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(context, permission))
                deniedPermissions.add(permission)
        }
        if(deniedPermissions.size <= 0) {
            onGranted()
            return
        }

        PermissionObserver.addObserver(this)

    }
}