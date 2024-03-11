package com.example.bondoman.ui.hub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bondoman.R
import com.example.bondoman.databinding.ActivityHubBinding
import com.example.bondoman.database.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class HubActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHubBinding
    lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database
        database = AppDatabase.getInstance(this)

        // Initialize header
        binding.header.navBackButton.visibility = View.GONE

        // Initialize navbar and fragments
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.hub_nav_transaction,
                R.id.hub_nav_scan,
                R.id.hub_nav_stats,
                R.id.hub_nav_settings,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}