package com.example.mylesson2.bottomNavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mylesson2.R
import com.example.mylesson2.databinding.InstagramMainActivityBinding

class InstagramMainActivity : AppCompatActivity() {

    private lateinit var binding: InstagramMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InstagramMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment

        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

        val app = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.addFragment,
            R.id.reelsFragment,
            R.id.profileFragment
        ).build()

        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController, app)
    }
}