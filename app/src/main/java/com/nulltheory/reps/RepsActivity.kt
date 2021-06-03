package com.nulltheory.reps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat

lateinit var textRep: TextView
lateinit var textTime: TextView
lateinit var buttonTime: Button
var reps: Int = 0
var startTime: Long = 0
var elapsedTime: Long = 0

class RepsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reps)
        textRep = findViewById(R.id.text_rep)
        textTime = findViewById(R.id.text_time)
        buttonTime = findViewById(R.id.button_time)
        displayReps()

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object: Runnable {
            override fun run() {
                displayTime()
                mainHandler.postDelayed(this, 100)
            }
        })
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

    private fun displayTime() {
        if(startTime == 0L) {
            return
        }

        val elapsed = System.currentTimeMillis() - startTime + elapsedTime
        textTime.text = SimpleDateFormat("mm:ss").format(elapsed)
    }

    fun startTimer(view: View) {
        startTime = System.currentTimeMillis()
        buttonTime.text = getString(R.string.stop)
        buttonTime.setOnClickListener {
            stopTimer(it)
        }
    }

    fun stopTimer(view: View) {
        elapsedTime += (System.currentTimeMillis() - startTime)
        startTime = 0
        buttonTime.text = getString(R.string.start)
        buttonTime.setOnClickListener {
            startTimer(it)
        }
    }

    fun resetTimer(view: View) {
        startTime = 0
        elapsedTime = 0
        textTime.text = getString(R.string.zero_time)
        buttonTime.text = getString(R.string.start)
        buttonTime.setOnClickListener {
            startTimer(it)
        }
    }
}