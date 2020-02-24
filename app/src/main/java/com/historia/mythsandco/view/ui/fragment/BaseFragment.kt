package com.historia.mythsandco.view.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.historia.mythsandco.viewmodel.ViewModelFactory
import kotlin.reflect.KClass

/**
 * Base class for the fragment used in the application
 * @property viewModel view model instance for interaction between the view and the model
 */
abstract class BaseFragment<T:ViewModel>: Fragment(){
    var viewModelFactory: ViewModelFactory = ViewModelFactory()

    lateinit var viewModel: T

    abstract val viewModelClass: KClass<T>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this,viewModelFactory).get(viewModelClass.java)
    }

    fun onBackPressed():Boolean{
        return false
    }
}