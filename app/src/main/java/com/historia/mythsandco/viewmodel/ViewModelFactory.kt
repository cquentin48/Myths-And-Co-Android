package com.historia.mythsandco.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.historia.mythsandco.errors.exceptions.ViewModelException
import com.historia.mythsandco.viewmodel.connectionActivity.ConnectionViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ConnectionViewModel::class.java)->{
                ConnectionViewModel() as T
            }
            else->{
                throw ViewModelException("Invalid view model exception.\nPlease read guide on how to implement it.")
            }
        }
    }
}