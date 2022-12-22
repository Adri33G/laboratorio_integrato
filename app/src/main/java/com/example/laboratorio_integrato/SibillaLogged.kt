package com.example.laboratorio_integrato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SibillaLogged : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sibilla_logged)
    }
    private lateinit var question: EditText
    private lateinit var button: Button
    private var firsLetter = 0
    private var lastLetter = 0
    private var letters = arrayOf("kqwyx", "huv", "ers", "mt", "lo", "ag", "ijn", "cf", "dz", "pb");
    private var numbers = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
}