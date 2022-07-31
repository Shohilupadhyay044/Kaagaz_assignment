package com.sohil.kaagaz_scanner_assignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sohil.kaagaz_scanner_assignment.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        intentActivity()
    }

    /**
     * Pass this Activity to MainActivity after some delay
     * */

    private fun intentActivity() {
        Handler().postDelayed({
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
        }, 4000)

    }

}