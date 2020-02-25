package com.historia.mythsandco.view.ui.fragment.connectionActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.historia.mythsandco.view.ui.fragment.BaseFragment
import com.historia.mythsandco.R
import com.historia.mythsandco.viewmodel.connectionActivity.ConnectionViewModel
import kotlinx.android.synthetic.main.fragment_connection.*

class ConnectionFragment : BaseFragment<ConnectionViewModel>(){
    override val viewModelClass = ConnectionViewModel::class
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        first_text_view.text = "Bonjour!"
    }

    companion object{
        fun newInstance() = ConnectionFragment()
    }
}