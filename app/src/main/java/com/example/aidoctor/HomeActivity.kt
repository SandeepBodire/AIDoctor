package com.example.aidoctor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aidoctor.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().add(R.id.main, HomeFragment()).commit()

    }

    override fun onBackPressed() {

        val currentFragment = supportFragmentManager.findFragmentById(R.id.main)

        if (currentFragment is HomeFragment) {
            super.onBackPressed()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.main, HomeFragment()).commit()
        }
    }
}
