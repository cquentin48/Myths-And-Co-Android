package com.historia.mythsandco.application

import android.app.Application

class MythsAndCoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
        //Add here all instance creations which should be launched when the app is launched first
    }

    companion object{
        //Add here all variable which should be seen from the whole app
        private var app = MythsAndCoApp()

        fun getAppInstance() : MythsAndCoApp = app
    }
}