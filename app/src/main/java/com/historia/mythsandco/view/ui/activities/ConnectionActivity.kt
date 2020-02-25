package com.historia.mythsandco.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.historia.mythsandco.R
import com.historia.mythsandco.view.navigator.Navigator

class ConnectionActivity : AppCompatActivity() {
    private lateinit var navigator : Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = Navigator(supportFragmentManager)
        navigator.displayConnection()
    }
}
