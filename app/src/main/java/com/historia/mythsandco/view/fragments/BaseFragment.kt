package com.historia.mythsandco.view.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.historia.mythsandco.application.MythsAndCoApp
import kotlin.reflect.KClass

abstract class BaseFragment<T:ViewModel>: Fragment() {
    //var viewModelFactory: ViewModelFactory = ViewModelFactory()

    lateinit var viewModel: T

    abstract val viewModelClass: KClass<T>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(MythsAndCoApp.getAppInstance()).create(viewModelClass.java)
    }
}