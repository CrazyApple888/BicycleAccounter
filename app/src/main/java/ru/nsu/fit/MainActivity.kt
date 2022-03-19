package ru.nsu.fit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.nsu.fit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNavMenu,
            navHostFragment.navController
        )
    }
}