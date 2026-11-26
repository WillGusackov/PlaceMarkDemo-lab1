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

        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("MainActivity initialized & Timber DebugTree planted")

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewMarksButton = findViewById<Button>(R.id.viewMarksButton)
        val addMarkButton = findViewById<Button>(R.id.addMarkButton)
        val viewEventsButton = findViewById<Button>(R.id.viewEventsButton)

        viewMarksButton.setOnClickListener {
            Timber.d("Navigating to MarkListActivity")
            startActivity(Intent(this, MarkListActivity::class.java))
        }

        addMarkButton.setOnClickListener {
            Timber.d("Navigating to AddEditActivity")
            startActivity(Intent(this, AddEditActivity::class.java))
        }

        viewEventsButton.setOnClickListener {
            Timber.d("Navigating to EventListActivity")
            startActivity(Intent(this, EventListActivity::class.java))
        }
    }
}
