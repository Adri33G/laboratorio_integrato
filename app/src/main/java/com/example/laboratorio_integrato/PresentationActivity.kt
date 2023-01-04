package com.example.laboratorio_integrato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class PresentationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation)

        val timer = object: CountDownTimer(3000, 1000){

            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                openAct()
            }
        }

        timer.start()
    }
    fun openAct(){
        val intent = Intent(this,LoginOrRegister::class.java)
        startActivity(intent)
        finish()
    }

}