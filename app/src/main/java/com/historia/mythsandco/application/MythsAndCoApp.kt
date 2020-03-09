package com.historia.mythsandco.application

import android.app.Application
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.historia.mythsandco.R

/**
 * Myths & Co application
 * @author Quentin CHAPEL
 * @property firebaseAuth [FirebaseAuth] Firebase auth instance
 */
class MythsAndCoApp : Application() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var googleSignInOption : GoogleSignInOptions

    private var callbackManager = CallbackManager.Factory.create()

    var FirebaseAuthInstance : FirebaseAuth
    get() = firebaseAuth
    set(value) {}
    var GoogleSignInOptionsInstance : GoogleSignInOptions
    get() = googleSignInOption
    set(value) {}
    var GoogleSignInClientInstance : GoogleSignInClient
    get() = googleSignInClient
    set(value){}
    var CallbackManagerInstance : CallbackManager
    get() = callbackManager
    set(value){}

    override fun onCreate() {
        super.onCreate()
        //Add here all instance creations which should be launched when the app is launched first
        googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOption)
        firebaseAuth = FirebaseAuth.getInstance()
        app = this
    }

    companion object{
        //Add here all variable which should be seen from the whole app
        private var app = MythsAndCoApp()

        /**
         * Return application instance
         * @return [MythsAndCoApp] application instance
         */
        fun getAppInstance() : MythsAndCoApp = app
    }
}