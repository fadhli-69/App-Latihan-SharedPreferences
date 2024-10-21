package com.example.mysharedpreferences

import android.content.Intent
import android.os.Build

fun Intent.getUserModel(): UserModel {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra("User", UserModel::class.java) ?: UserModel() // Provide a default UserModel if null
    } else {
        @Suppress("DEPRECATION")
        this.getParcelableExtra<UserModel>("User") ?: UserModel() // Provide a default UserModel if null
    }
}

fun Intent.getUserModelFromResult(): UserModel? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra(FormUserPreferenceActivity.EXTRA_RESULT, UserModel::class.java)
    } else {
        @Suppress("DEPRECATION")
        this.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT)
    }
}