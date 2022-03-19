package ru.nsu.fit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
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
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            binding.bottomNavMenu,
            navController
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeScreenFragment,
                R.id.customerListFragment,
                R.id.addBicycleFragment,
                R.id.salesListFragment,
                R.id.statisticsFragment -> binding.bottomNavMenu.isGone = false
                else -> binding.bottomNavMenu.isGone = true
            }
        }
    }
}