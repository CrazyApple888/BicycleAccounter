package ru.nsu.fit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nsu.fit.ui.fragment.HomeScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(HomeScreenFragment::class.java, null, null)
            .commit()
    }
}