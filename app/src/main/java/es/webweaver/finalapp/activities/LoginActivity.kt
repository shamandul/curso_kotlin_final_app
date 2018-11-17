package es.webweaver.finalapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import es.webweaver.finalapp.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        buttonLogIn.setOnClickListener{
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (isValiEmail(email) && isValiPassword(password)){
                logInByEmail(email, password)
            }else {
                toast( "Please make sure all data is correct.")
            }
        }

        textViewForgotPassword.setOnClickListener {
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        }
        buttonCreateAcount.setOnClickListener {
            goToActivity<SignUpActivity>()
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        }
        editTextEmail.validate {
            editTextEmail.error = if(isValiEmail(it)) null else "Email is not valid"
        }
        editTextPassword.validate {
            editTextEmail.error = if(isValiPassword(it)) null else "Passaword shoul contain 1 lowrcase, 1 uppercase, 1 number, 1 special characte an 4 character lenght at least."
        }

    }
    private fun logInByEmail(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                if (mAuth.currentUser!!.isEmailVerified){
                    toast("User is now logged in")
                }else {
                    toast("User must confirm email first")

                }
            }else{
                toast("An unexpected error occured, please try again")
            }
        }

    }

}
