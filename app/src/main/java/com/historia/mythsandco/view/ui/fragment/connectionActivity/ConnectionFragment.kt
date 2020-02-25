package com.historia.mythsandco.view.ui.fragment.connectionActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.historia.mythsandco.view.ui.fragment.BaseFragment
import com.historia.mythsandco.R
import com.historia.mythsandco.application.MythsAndCoApp
import com.historia.mythsandco.errors.exceptions.FragmentException
import com.historia.mythsandco.view.model.ConnectionSpinnerModel
import com.historia.mythsandco.view.navigator.Navigator
import com.historia.mythsandco.view.ui.viewholder.ConnectionMethodSpinnerAdapter
import com.historia.mythsandco.viewmodel.connectionActivity.ConnectionViewModel
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_connection.*

class ConnectionFragment : BaseFragment<ConnectionViewModel>(), View.OnClickListener{
    override val viewModelClass = ConnectionViewModel::class
    private lateinit var navigator : Navigator
    private lateinit var connectionMethod: ConnectionType
    private var connectionParameters = ArrayList<String>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigator(fragmentManager!!)
        //It will change with the stocked data
        connectionMethod = ConnectionType.EMAIL

        //We already log in the user
        if(viewModel.isUserAlreadyConnected()){
            navigator.switchToMainActivity(context!!)
        }

        connection_connect_with_email_button.setOnClickListener(this)

        connection_method_spinner.adapter =
            ConnectionMethodSpinnerAdapter(context!!,
                arrayListOf(
                    ConnectionSpinnerModel(
                        "E-mail",R.drawable.ic_auth_service_email
                    ),
                    ConnectionSpinnerModel(
                        "Facebook",R.drawable.ic_auth_service_facebook
                    ),
                    ConnectionSpinnerModel(
                        "Google",R.drawable.ic_auth_service_google
                    ),
                    ConnectionSpinnerModel(
                        "Numéro de téléphone",R.drawable.ic_auth_service_phone
                    )
                )
            )
        connection_method_spinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {/*An item will always be selected*/}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //updateView(position)
            }
        }
    }

    private fun updateView(connectionMethod:Int){
        //Add here
        when(connectionMethod){
            ConnectionType.EMAIL.getConnectionMethod()->{
                updateView(ConnectionType.EMAIL.getConnectionMethod())
            }
            ConnectionType.FACEBOOK.getConnectionMethod()->{
                updateView(ConnectionType.FACEBOOK.getConnectionMethod())
            }
            ConnectionType.GOOGLE.getConnectionMethod()->{
                updateView(ConnectionType.GOOGLE.getConnectionMethod())
            }
            ConnectionType.PHONENUMBER.getConnectionMethod()->{
                updateView(ConnectionType.PHONENUMBER.getConnectionMethod())
            }
            else->{
                throw FragmentException("Invalid connection method")
            }
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.connection_connect_with_email_button->{
                /*FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(connection_email_input.editText!!.text.toString(),connection_password_input.editText!!.text.toString())
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            navigator.switchToMainActivity(context!!)
                        }
                    }*/
                connectionMethod.signIn(connectionParameters,navigator,context!!)
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

    private enum class ConnectionType{
        EMAIL {
            override fun signIn(parameters: List<String>, navigator: Navigator, context:Context) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(parameters[0],parameters[1])
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            navigator.switchToMainActivity(context)
                        }
                    }
            }

            override fun displayLoginScreen() {

            }

            override fun getConnectionMethod(): Int = 0
        },
        FACEBOOK {
            override fun displayLoginScreen() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun signIn(parameters: List<String>, navigator: Navigator, context: Context) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getConnectionMethod(): Int = 1
        },
        GOOGLE {
            override fun displayLoginScreen() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun signIn(parameters: List<String>, navigator: Navigator, context: Context) {
                val signInIntent = MythsAndCoApp.getAppInstance().GoogleSignInOptionsInstance

            }

            override fun getConnectionMethod(): Int = 2
        },
        PHONENUMBER {
            override fun displayLoginScreen() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun signIn(parameters: List<String>, navigator: Navigator, context: Context) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getConnectionMethod(): Int = 3
        };
        abstract fun displayLoginScreen()
        abstract fun signIn(parameters: List<String>, navigator: Navigator, context:Context)

        abstract fun getConnectionMethod():Int
    }

    companion object{
        private const val GOOGLE_SIGN_IN = 1
        @JvmStatic
        fun newInstance() = ConnectionFragment()
    }
}