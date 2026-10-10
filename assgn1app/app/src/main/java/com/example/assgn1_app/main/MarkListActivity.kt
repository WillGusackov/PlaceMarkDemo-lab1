package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assgn1_app.R
import com.example.assgn1_app.placemark.AppData
import com.example.assgn1_app.placemark.PlacedMark
import com.example.assgn1_app.placemark.PlacedMarkAdapter

class MarkListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlacedMarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mark_list)

        recyclerView =
            findViewById(R.id.marksRecyclerView)

        adapter = PlacedMarkAdapter(
            marks = AppData.placeMarks.findAll(),

            onEdit = { mark ->
                editMark(mark)
            },

            onDelete = { mark ->
                deleteMark(mark)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {

            adapter.updateMarks(
                AppData.placeMarks.findAll()
            )
        }
    }

    private fun editMark(mark: PlacedMark) {

        val intent =
            Intent(this, AddEditActivity::class.java)

        intent.putExtra("id", mark.id)

        startActivity(intent)
    }

    private fun deleteMark(mark: PlacedMark) {

        AppData.placeMarks.delete(mark.id)

        adapter.updateMarks(
            AppData.placeMarks.findAll()
        )
    }
}
