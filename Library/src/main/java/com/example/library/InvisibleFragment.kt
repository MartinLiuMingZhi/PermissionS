package com.example.library

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val deniedList = ArrayList<String>()
            permissions.entries.forEach { entry ->
                if (!entry.value) {
                    deniedList.add(entry.key)
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.invoke(allGranted, deniedList)
        }

    fun requestNow(cb: PermissionCallback, vararg permission: String) {
        callback = cb
        requestPermissionLauncher.launch(arrayOf(*permission))
    }

}