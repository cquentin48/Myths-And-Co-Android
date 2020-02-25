package com.historia.mythsandco.viewmodel.connectionActivity

import androidx.lifecycle.ViewModel
import com.historia.mythsandco.application.MythsAndCoApp

class ConnectionViewModel : ViewModel() {
    /**
     * Check if the user is already connected
     * @return [Boolean] true -> user already connected
     * @return [Boolean] false -> user not yet connected
     */
    fun isUserAlreadyConnected():Boolean = MythsAndCoApp.getAppInstance().FirebaseAuthInstance.currentUser != null
}