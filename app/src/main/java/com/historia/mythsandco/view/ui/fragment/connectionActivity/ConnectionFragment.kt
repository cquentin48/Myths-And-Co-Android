package com.historia.mythsandco.view.ui.fragment.connectionActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.historia.mythsandco.view.ui.fragment.BaseFragment
import com.historia.mythsandco.R
import com.historia.mythsandco.errors.exceptions.FragmentException
import com.historia.mythsandco.model.phonedata.livedata.PhoneSnackBarMessageContent
import com.historia.mythsandco.view.model.ConnectionSpinnerModel
import com.historia.mythsandco.view.navigator.Navigator
import com.historia.mythsandco.view.ui.viewholder.ConnectionMethodSpinnerAdapter
import com.historia.mythsandco.view.utils.showMessage
import com.historia.mythsandco.viewmodel.connectionActivity.ConnectionViewModel
import kotlinx.android.synthetic.main.fragment_connection.*

class ConnectionFragment : BaseFragment<ConnectionViewModel>(), View.OnClickListener, ConnectionFragmentPattern{
    override val viewModelClass = ConnectionViewModel::class
    private lateinit var navigator : Navigator
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigator(parentFragmentManager)

        //We already log in the user
        if(viewModel.isUserAlreadyConnected()){
            navigator.switchToMainActivity(context!!)
        }

        connection_connect_button.setOnClickListener(this)

        val messageObserver = Observer<PhoneSnackBarMessageContent>{
            view.showMessage(it.message)
        }
        val errorObserver = Observer<PhoneSnackBarMessageContent>{
            view.showMessage(it.message)
        }

        viewModel.ErrorMessage.observe(viewLifecycleOwner,errorObserver)
        viewModel.MessageContent.observe(viewLifecycleOwner,messageObserver)

        initSpinner()
    }

    private fun initSpinner() {
        connection_method_spinner.adapter =
            ConnectionMethodSpinnerAdapter(
                context!!,
                arrayListOf(
                    ConnectionSpinnerModel(
                        "E-mail",
                        R.drawable.ic_auth_service_email,
                        viewModel.signInWithEmail(connection_email_input.editText!!.text.toString(),connection_password_input.editText!!.editableText.toString())
                    ),
                    ConnectionSpinnerModel(
                        "Facebook",
                        R.drawable.ic_auth_service_facebook,
                        viewModel.signInWithFacebook(connection_email_input.editText!!.text.toString(),connection_password_input.editText!!.editableText.toString())
                    ),
                    ConnectionSpinnerModel(
                        "Google",
                        R.drawable.ic_auth_service_google,
                        viewModel.signInWithGoogle(connection_email_input.editText!!.text.toString(),connection_password_input.editText!!.editableText.toString())
                    ),
                    ConnectionSpinnerModel(
                        "Numéro de téléphone",
                        R.drawable.ic_auth_service_phone,
                        viewModel.signInWithPhone()
                    )
                )
            )
        connection_method_spinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {/*An item will always be selected*/
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    updateView(position)
                }
            }
    }

    private fun updateView(connectionMethod:Int){
        //Add here
        when(connectionMethod){
            else->{
                throw FragmentException("Invalid connection method")
            }
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.connection_connect_button->{
                //connectionMethod.signIn(connectionParameters,navigator,context!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java) as GoogleSignInAccount

                //firebaseAuthWithGoogle(account!!)
            }catch (exception : ApiException){
                Log.w("Exception",exception)
            }
        }
    }

    companion object{
        private const val GOOGLE_SIGN_IN = 1

        @JvmStatic
        fun newInstance() = ConnectionFragment()
    }
}