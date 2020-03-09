package com.historia.mythsandco.model.phonedata.firebase

import android.app.Activity
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.historia.mythsandco.R
import com.historia.mythsandco.application.MythsAndCoApp
import com.historia.mythsandco.errors.exceptions.FragmentException
import com.historia.mythsandco.errors.exceptions.ViewModelException
import com.historia.mythsandco.view.ui.fragment.connectionActivity.ConnectionFragmentPattern
import java.util.concurrent.TimeUnit

class FirebaseModel : FirebaseModelPattern {
    private lateinit var credential : PhoneAuthCredential
    override fun signInWithEmail(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email,password)
    }

    override fun signInWithFacebook(facebookLogin: String, facebookPassword: String) {
        LoginManager.getInstance().registerCallback(MythsAndCoApp.getAppInstance().CallbackManagerInstance, object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                result!!.accessToken
            }

            override fun onCancel() {
                //May change it to a "Do you want to cancel login through facebook?"
                throw ViewModelException(MythsAndCoApp.getAppInstance().getString(R.string.cancelled_facebook_login))
            }

            override fun onError(error: FacebookException?) {
                throw ViewModelException(error!!.localizedMessage)
            }

        })
    }

    override fun signInWithGoogle(googleLogin: String, googlePassword: String): GoogleSignInOptions.Builder? {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(MythsAndCoApp.getAppInstance().getString(R.string.default_web_client_id))
    }

    override fun sendSMSCode(phoneNumber: String, connectionFragment: ConnectionFragmentPattern) {
        FirebaseAuth.getInstance()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            (connectionFragment as Fragment).activity as Activity,
            object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    credential = phoneAuthCredential
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    throw FragmentException(exception.localizedMessage)
                }

            }
        )
    }

    override fun signInWithPhoneCredential(phoneCredential: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithCredential(credential)
    }
}