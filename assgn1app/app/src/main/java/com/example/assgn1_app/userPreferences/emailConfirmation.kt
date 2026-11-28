package com.example.assgn1_app.userPreferences

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import com.example.assgn1_app.main.MainActivity
import timber.log.Timber

class emailConfirmation : AppCompatActivity() {

    private lateinit var codeInput: EditText
    private lateinit var verifyButton: Button
    private lateinit var resendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.email_confirmation)

        Timber.d("emailConfirmation Activity created")

        codeInput = findViewById(R.id.codeInput)
        verifyButton = findViewById(R.id.verifyButton)
        resendButton = findViewById(R.id.resendButton)

        verifyButton.setOnClickListener {
            val code = codeInput.text.toString().trim()
            if (code.length < 6) {
                codeInput.error = "Enter a valid 6-digit code"
                Timber.w("Invalid verification code attempted")
                return@setOnClickListener
            }

            Timber.d("Verifying code: $code")
            Toast.makeText(this, "Email Verified Successfully!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        resendButton.setOnClickListener {
            Timber.d("Resend verification code requested")
            Toast.makeText(this, "Verification code resent!", Toast.LENGTH_SHORT).show()
        }
    }
}
