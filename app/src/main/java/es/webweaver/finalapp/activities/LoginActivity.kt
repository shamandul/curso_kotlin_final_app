package es.webweaver.finalapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import es.webweaver.finalapp.R
import es.webweaver.finalapp.goToActivity
import es.webweaver.finalapp.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (mAuth.currentUser === null){
            toast("No")
        } else {
            toast("Si")
            mAuth.signOut()
        }

        buttonLogIn.setOnClickListener{
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (isValiEmailAndPassword(email, password)){
                logInByEmail(email, password)
            }
        }

        textViewForgotPassword.setOnClickListener { goToActivity<ForgotPasswordActivity>() }
        buttonCreateAcount.setOnClickListener { goToActivity<SignUpActivity>() }


    }
    private fun logInByEmail(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                toast("User is now logged in")
            }else{
                toast("An unexpected error occured, please try again")
            }
        }

    }

    private fun isValiEmailAndPassword(email: String, password: String): Boolean{
        return  !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}
