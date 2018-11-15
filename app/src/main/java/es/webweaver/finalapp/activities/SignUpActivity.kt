package es.webweaver.finalapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import es.webweaver.finalapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        buttonGoLogIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        buttonSingUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (isValiEmailAndPassword(email, password)   ){
                signUpByEmail(email, password)
            } else {
                Toast.makeText(this, "Please fill all data and confirm password is correct.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signUpByEmail(email: String, password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "An email has been sent to you. Please, confirm before sign in.", Toast.LENGTH_SHORT).show()
                    val user = mAuth.currentUser
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "An unexpected error occurred, please try again.", Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }

    private fun isValiEmailAndPassword(email: String, password: String):Boolean{
        return !email.isNullOrEmpty() && !password.isNullOrEmpty() &&
                password === editTextConfirmPassword.text.toString()

    }
}
