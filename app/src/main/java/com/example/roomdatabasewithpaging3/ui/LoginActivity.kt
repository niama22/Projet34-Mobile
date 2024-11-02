package com.example.roomdatabasewithpaging3.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.roomdatabasewithpaging3.R
import com.example.roomdatabasewithpaging3.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
    }

    private fun startAnimations() {
        val welcomeAnimation = AnimationUtils.loadAnimation(this, R.anim.fed_in_welcom)
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.fed_in_text)
        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)

        binding.imageView6.visibility = View.VISIBLE
        binding.imageView6.startAnimation(welcomeAnimation)

        welcomeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                binding.textView.visibility = View.VISIBLE
                binding.textView.startAnimation(textAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        textAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {

                binding.imageView3.visibility = View.VISIBLE
                binding.imageView3.startAnimation(bounceAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                navigateToMainActivity()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}