package com.nulltheory.reps

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat

class RepsActivity : AppCompatActivity() {
    lateinit var mainHandler: Handler
    lateinit var preferences: SharedPreferences
    lateinit var textRep: TextView
    lateinit var textTime: TextView
    lateinit var buttonTime: Button
    var reps: Int = 0
    var startTime: Long = 0
    var elapsedTime: Long = 0

    private val updateDisplayTask = object : Runnable {
        override fun run() {
            var elapsed = elapsedTime

            if(startTime == 0L) {
                buttonTime.text = getString(R.string.start)
            } else {
                elapsed += (System.currentTimeMillis() - startTime)
                buttonTime.text = getString(R.string.stop)
            }

            textTime.text = SimpleDateFormat("mm:ss").format(elapsed)
            textRep.text = reps.toString()

            mainHandler.postDelayed(this, 100)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reps)
        textRep = findViewById(R.id.text_rep)
        textTime = findViewById(R.id.text_time)
        buttonTime = findViewById(R.id.button_time)
        mainHandler = Handler(Looper.getMainLooper())
        preferences = getPreferences(Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        reps = preferences.getInt("reps", 0)
        startTime = preferences.getLong("start_time", 0)
        elapsedTime = preferences.getLong("elapsed_time", 0)
        mainHandler.post(updateDisplayTask)
    }

    override fun onPause() {
        super.onPause()
        with(preferences.edit()) {
            putInt("reps", reps)
            putLong("start_time", startTime)
            putLong("elapsed_time", elapsedTime)
            apply()
        }
        mainHandler.removeCallbacks(updateDisplayTask)
    }

    fun increaseReps(view: View) {
        reps++
    }

    fun resetReps(view: View) {
        reps = 0
    }

    fun toggleTimer(view: View) {
        if(startTime == 0L) {
            startTime = System.currentTimeMillis()
        } else {
            elapsedTime += (System.currentTimeMillis() - startTime)
            startTime = 0
        }
    }

    fun resetTimer(view: View) {
        startTime = 0
        elapsedTime = 0
    }
}