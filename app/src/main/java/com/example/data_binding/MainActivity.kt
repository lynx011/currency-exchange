package com.example.data_binding

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.data_binding.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNav,navController)

        val sharedPreferences = this.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)

        binding.bottomNav.setOnItemReselectedListener { item ->
            when(item.itemId){
                R.id.currenciesFragment -> {
                    navController.navigate(R.id.currenciesFragment)
                }
                R.id.converterFragment -> {
                    navController.popBackStack()
                    navController.navigate(R.id.converterFragment)
                }
            }
        }

        navController.addOnDestinationChangedListener{_,d,_ ->
            when(d.id){
                R.id.currenciesFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNav.isVisible = true
                }

                R.id.searchCodeFragment -> {
                    binding.bottomNav.isVisible = false
                }

                R.id.converterFragment -> {
                    binding.bottomNav.isVisible = true
                }

                R.id.quantityFragment -> {
                    binding.bottomNav.isVisible = false
                }

            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val destination = findNavController(R.id.nav_host_fragment).currentDestination?.id
        val navController = findNavController(R.id.nav_host_fragment)
        if (destination == R.id.currenciesFragment){
            if (backPressed + 2000 > System.currentTimeMillis()){
                super.onBackPressed()
            }else{
                Toast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT).show()
                backPressed = System.currentTimeMillis()
            }
        }else{
            navController.popBackStack()
        }
    }
}