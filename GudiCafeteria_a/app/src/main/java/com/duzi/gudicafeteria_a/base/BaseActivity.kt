package com.duzi.gudicafeteria_a.base

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    abstract val initView: () -> Unit
    abstract val requestedPermissionList: List<String>
    abstract val layoutResID: Int
    protected val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)

        if(isCheckRuntimePermission() && requestedPermissionList.isNotEmpty() &&
                !checkPermission(requestedPermissionList)) {
            requestPermission()
        } else {
            initView()
        }
    }

    private fun isCheckRuntimePermission(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    private fun checkPermission(requestedPermissionList: List<String>): Boolean {
        for(permission in requestedPermissionList)
            if(PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, permission))
                return false

        return true
    }

    fun requestPermission() {
        val deniedPermissionList = ArrayList<String>()
        for(permission in requestedPermissionList) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                deniedPermissionList.add(permission)
        }

        if(deniedPermissionList.size > 0) {
            AlertDialog.Builder(this)
                    .setMessage("추가 퍼미션이 필요합니다")
                    .setOnCancelListener { fireDenied() }
                    .setNegativeButton("거부") { _, _ -> fireDenied() }
                    .setPositiveButton("설정") { _, _ ->
                        ActivityCompat.requestPermissions(this, deniedPermissionList.toTypedArray(), REQ_REQUEST)
                    }
                    .show()
            return
        }

        ActivityCompat.requestPermissions(this, requestedPermissionList.toTypedArray(), REQ_REQUEST)
    }

    private fun fireDenied() {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQ_REQUEST) {
            initView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    companion object {
        private val REQ_REQUEST = 99
    }
}