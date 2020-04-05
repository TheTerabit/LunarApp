package pl.bs.lunarapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    var location: String = "n"
    var algorithm: String = "trig1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun saveSettings(v: View) {
        //super.onBackPressed()
        val intent: Intent = Intent();
        intent.putExtra("algorithm", algorithm)
        intent.putExtra("location", location)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun northClicked(v: View) {
        location = "n"
        Toast.makeText(applicationContext,"On click : ${location}", Toast.LENGTH_SHORT).show()
    }

    fun southClicked(v: View) {
        location = "s"
        Toast.makeText(applicationContext,"On click : ${location}", Toast.LENGTH_SHORT).show()
    }

    fun trig1Clicked(v: View) {
        algorithm = "trig1"
        Toast.makeText(applicationContext,"On click : ${algorithm}", Toast.LENGTH_SHORT).show()
    }

    fun trig2Clicked(v: View) {
        algorithm = "trig2"
        Toast.makeText(applicationContext,"On click : ${algorithm}", Toast.LENGTH_SHORT).show()
    }
    fun simpleClicked(v: View) {
        algorithm = "simple"
        Toast.makeText(applicationContext,"On click : ${algorithm}", Toast.LENGTH_SHORT).show()
    }
    fun conwayClicked(v: View) {
        algorithm = "conway"
        Toast.makeText(applicationContext,"On click : ${algorithm}", Toast.LENGTH_SHORT).show()
    }

}
