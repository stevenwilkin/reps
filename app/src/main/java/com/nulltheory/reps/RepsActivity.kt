package com.nulltheory.reps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

lateinit var textRep: TextView
var reps: Int = 0

class RepsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reps)
        textRep = findViewById(R.id.text_rep)
        displayReps()
    }

    private fun displayReps() {
        textRep.text = reps.toString()
    }

    fun increaseReps(view: View) {
        reps++
        displayReps()
    }

    fun resetReps(view: View) {
        reps = 0
        displayReps()
    }
}