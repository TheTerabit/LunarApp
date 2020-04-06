package pl.bs.lunarapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SettingsActivity : AppCompatActivity() {

    var location: String = "n"
    var algorithm: String = "trig1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun saveSettings(v: View) {
        val intent = Intent()
        intent.putExtra("algorithm", algorithm)
        intent.putExtra("location", location)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun northClicked(v: View) {
        location = "n"
    }

    fun southClicked(v: View) {
        location = "s"
    }

    fun trig1Clicked(v: View) {
        algorithm = "trig1"
    }

    fun trig2Clicked(v: View) {
        algorithm = "trig2"
    }
    fun simpleClicked(v: View) {
        algorithm = "simple"
    }
    fun conwayClicked(v: View) {
        algorithm = "conway"
    }

}
