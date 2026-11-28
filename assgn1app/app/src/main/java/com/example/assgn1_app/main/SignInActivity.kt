package com.example.assgn1_app.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import timber.log.Timber

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_screen)
        Timber.i("SignInActivity initialized")

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            Timber.d("Sign up action clicked")
            //sign up logic needs more later
        }
    }
}
