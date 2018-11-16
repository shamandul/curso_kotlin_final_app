package es.webweaver.finalapp

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern


fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT)  =Toast.makeText(this, message, duration).show()
fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT)  =Toast.makeText(this, resourceId, duration).show()


fun ViewGroup.inflate(layoutId: Int)= LayoutInflater.from(context).inflate(layoutId, this,false)!!

//fun ImageView.loadByURL(url: String)= Picasso.get().load(url).into(this)
//fun ImageView.loadByResource(resource: Int)= Picasso.get().load(resource).fit().into(this)

inline fun <reified T : Activity>Activity.goToActivity(noinline init: Intent.()-> Unit= {}){
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)

}


/*
fun Activity.goToActivityResult(action:String, requestCode: Int, init: Intent.() -> Unit ={}){
    val intent = Intent(action)
    intent.init()
    startActivityForResult(intent, requestCode)
}*/

fun EditText.validate(validation: (String)-> Unit){
    this.addTextChangedListener(object: TextWatcher {

        override fun afterTextChanged(editeble: Editable) {
            validation(editeble.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })
}


fun Activity.isValiEmail(email: String): Boolean{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}
fun Activity.isValiPassword(password: String): Boolean{
    // Nesesitamos tener ->  1 Num    / 1 Minuscula / 1 Mayuscula/ 1 Especial / Min caracteres 4
    val passwordPatterns = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(passwordPatterns)
    return pattern.matcher(password).matches()

}
fun Activity.isvValiConfirmPassword(password:String, confirmPassword: String): Boolean{
    return password == confirmPassword
}