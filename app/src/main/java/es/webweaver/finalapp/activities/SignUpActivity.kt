package es.webweaver.finalapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import es.webweaver.finalapp.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        buttonGoLogIn.setOnClickListener{
            goToActivity<LoginActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)


        }

        buttonSingUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (isValiEmailAndPassword(email, password)   ){
                signUpByEmail(email, password)
            } else {
                toast( "Please fill all data and confirm password is correct.")
            }
        }

        editTextEmail.validate {
            editTextEmail.error = if(isValiEmail(it)) null else "Email is not valid"
        }
        editTextPassword.validate {
            editTextEmail.error = if(isValiPassword(it)) null else "Passaword shoul contain 1 lowrcase, 1 uppercase, 1 number, 1 special characte an 4 character lenght at least."
        }
        editTextConfirmPassword.validate {
            editTextConfirmPassword.error = if(isvValiConfirmPassword(editTextPassword.text.toString(), it)) null else "Confirm Password does not match with Password "
        }
    }



    private fun signUpByEmail(email: String, password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    toast("An email has been sent to you. Please, confirm before sign in.")
                    goToActivity<LoginActivity> {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                }else{
                    // If sign in fails, display a message to the user.
                    toast("An unexpected error occurred, please try again.")
                }

                // ...
            }
    }
    private fun isValiEmailAndPassword(email: String, password: String):Boolean{
        return !email.isNullOrEmpty() && !password.isNullOrEmpty() &&
                password == editTextConfirmPassword.text.toString()

    }


}
