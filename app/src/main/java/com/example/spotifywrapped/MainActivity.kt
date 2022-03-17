package com.example.spotifywrapped

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.example.spotifywrapped.ui.login.LoginActivity

val CLIENT_ID = "e52d26b096564c6299aea975dac46191"
val CLIENT_SECRET = "ecb6cb7cc5c34b5bb5227f143a989815"
val CALLBACK_URI = "https://localhost/callback/"


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        button?.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}