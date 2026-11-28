package com.example.assgn1_app.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlogin_screen)
        Timber.i("LoginActivity initialized")

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            Timber.d("Login action clicked")
            // Login logic here
        }
    }
}
