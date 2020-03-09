package com.historia.mythsandco.viewmodel.connectionActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.historia.mythsandco.application.MythsAndCoApp
import com.historia.mythsandco.errors.exceptions.ViewModelException
import com.historia.mythsandco.model.phonedata.firebase.FirebaseModel
import com.historia.mythsandco.model.phonedata.firebase.FirebaseModelPattern
import com.historia.mythsandco.model.phonedata.livedata.PhoneSnackBarMessageContent

//Todo : include here image for the snackbar
class ConnectionViewModel : ViewModel() {
    private var firebaseModel: FirebaseModelPattern = FirebaseModel()
    private var messageContent = MutableLiveData<PhoneSnackBarMessageContent>()
    private var errorMessage = MutableLiveData<PhoneSnackBarMessageContent>()

    fun signInWithEmail(email: String, password: String) {
        firebaseModel.signInWithEmail(email, password).addOnCompleteListener {
            when{
                it.exception!=null->{
                    errorMessage.postValue(PhoneSnackBarMessageContent(it.exception!!.localizedMessage!!,""))
                }
                it.isSuccessful->{
                    messageContent.postValue(PhoneSnackBarMessageContent("Succès dans la connexion!",""))
                }
                it.isCanceled->{
                    messageContent.postValue(PhoneSnackBarMessageContent("Abandon de la connexion!",""))
                }
            }
        }
    }

    fun signInWithFacebook(facebookLogin: String, facebookPassword: String) {
        try {
            firebaseModel.signInWithFacebook(facebookLogin, facebookPassword)
        } catch (exception: ViewModelException) {
            errorMessage.postValue(PhoneSnackBarMessageContent(exception.localizedMessage!!,""))
        }
    }

    fun signInWithGoogle(googleLogin: String, googlePassword: String) {
        firebaseModel.signInWithGoogle(googleLogin, googlePassword)
    }

    fun signInWithPhone() {

    }

    /**
     * Check if the user is already connected
     * @return [Boolean] true -> user already connected
     * @return [Boolean] false -> user not yet connected
     */
    fun isUserAlreadyConnected(): Boolean =
        MythsAndCoApp.getAppInstance().FirebaseAuthInstance.currentUser != null

    var MessageContent:LiveData<PhoneSnackBarMessageContent> = messageContent
    var ErrorMessage:LiveData<PhoneSnackBarMessageContent> = errorMessage
}