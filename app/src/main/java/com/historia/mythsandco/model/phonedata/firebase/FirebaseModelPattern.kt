package com.historia.mythsandco.model.phonedata.firebase

import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.historia.mythsandco.view.ui.fragment.connectionActivity.ConnectionFragmentPattern

interface FirebaseModelPattern {
    /**
     * Sign in with email
     * @param email email account
     * @param password password account
     */
    fun signInWithEmail(email:String, password:String) : Task<AuthResult>

    fun signInWithFacebook(facebookLogin:String, facebookPassword:String)

    fun signInWithGoogle(googleLogin:String, googlePassword:String): GoogleSignInOptions.Builder?

    //Check later
    fun sendSMSCode(phoneNumber:String, connectionFragment: ConnectionFragmentPattern)

    fun signInWithPhoneCredential(phoneCredential: String): Task<AuthResult>
}