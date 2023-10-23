package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.weatherapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val animation = AnimationUtils.loadAnimation(applicationContext,R.anim.splash_screen)
        binding.constraintWelcomeLayout.startAnimation(animation)

        val holder = Handler(Looper.getMainLooper())
        holder.postDelayed(object : Runnable{
            override fun run() {
                val intent = Intent(this@WelcomeActivity,MainActivity::class.java)
                startActivity(intent)
            }
        },3000)
    }
}
