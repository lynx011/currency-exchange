package com.example.data_binding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.data_binding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNav,navController)

        binding.bottomNav.setOnItemReselectedListener { item ->
            when(item.itemId){
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
            }
        }

        navController.addOnDestinationChangedListener{_,d,_ ->
            when(d.id){
                R.id.homeFragment -> {
                    supportActionBar?.hide()
                }

            }
        }
    }
}