package com.historia.mythsandco.view.navigator

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.historia.mythsandco.R
import com.historia.mythsandco.view.ui.activities.ConnectionActivity
import com.historia.mythsandco.view.ui.activities.MainActivity
import com.historia.mythsandco.view.ui.fragment.connectionActivity.ConnectionFragment

class Navigator(private var fragmentManager : FragmentManager) {
    //Activities
    fun switchToConnectionActivity(context: Context){
        context.startActivity(Intent(context,ConnectionActivity::class.java))
    }

    fun switchToMainActivity(context: Context){
        context.startActivity(Intent(context,MainActivity::class.java))
    }

    //Fragment
    fun displayConnection(){
        fragmentManager
            .beginTransaction()
            .replace(R.id.connection_activity_fragment_container,
                ConnectionFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}