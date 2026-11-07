package com.example.mylesson2.bottomNavigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylesson2.R
import com.example.mylesson2.databinding.SplashActivityBinding

class SplashActivity :AppCompatActivity() {

    private lateinit var binding:SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageLogo.postDelayed({
            startActivity(Intent(this,InstagramMainActivity::class.java))
            finish()
        },2000)
    }
}