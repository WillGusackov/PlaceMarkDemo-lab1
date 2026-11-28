package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assgn1_app.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.signin_or_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.developerSignInButton)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //buttons
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val developerSignInButton = findViewById<Button>(R.id.developerSignInButton)

        signUpButton.setOnClickListener {
            Timber.d("Navigating to Sign Up")
            startActivity(
                Intent(this, SignInActivity::class.java)
            )
        }

        loginButton.setOnClickListener {
            Timber.d("Navigating to Login")
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }

        developerSignInButton.setOnClickListener {
            Timber.d("Developer sign in clicked")
            //need more
        }
    }
}