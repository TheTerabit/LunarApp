package pl.bs.lunarapp

import android.app.Activity
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.util.*
import android.content.Intent




class MainActivity : AppCompatActivity() {

    var algorithm: String = "trig2"
    var location: String = "n"
    val lunarCalculator: LunarCalculator = LunarCalculator()
    var phase: Int = 0
    var year: Int = 2019
    var month: Int = 3
    var day: Int = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getCurrentDate()
        phase = lunarCalculator.calculatePhase(year, month, day, algorithm)
        //Toast.makeText(this, "Dziś jest $phase faza księżyca", Toast.LENGTH_LONG).show()
        Log.d("phase", "This is $phase phase")
        setMoonPhaseImage(phase)
        setPercentage(phase)
        setPreviousEmpty(phase)
        setNextFull(phase)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate() {
        var currentDate: LocalDate = LocalDate.now()
        this.year = currentDate.year
        this.month = currentDate.monthValue
        this.day = currentDate.dayOfMonth
    }

    private fun setMoonPhaseImage(phase: Int) {
        //R.drawable.n10
        //this.imageView.setImageResource(getResources().getIdentifier(location.toLowerCase() + phase.toString() + ".jpg", "drawable", getPackageName())
        //Toast.makeText(this, "Obrazek " + location.toLowerCase() + phase.toString(), Toast.LENGTH_LONG).show()
        val id = R.mipmap::class.java.getField(location.toLowerCase() + phase.toString()).getInt(null)
        this.imageView.setImageResource(id)
    }

    private fun setPercentage(phase: Int) {
        var percantage: Int = (((phase.toDouble()/30)) * 100).toInt()
        this.textPercentage.setText("Cykl księżyca: " + percantage + "%")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setPreviousEmpty(phase: Int) {

        var currentDate: LocalDate = LocalDate.now()
        var phase = this.phase
        while(phase!=0) {
            Log.d("prevEmpty", "This is my previous empty");
            currentDate = currentDate.minusDays(1)
            phase = lunarCalculator.calculatePhase(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth, algorithm)
        }
        this.textPreviousEmpty.setText("Poprzedni nów: " + currentDate.dayOfMonth + "." + currentDate.monthValue + "." + currentDate.year + " r.")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNextFull(phase: Int) {

        var currentDate: LocalDate = LocalDate.now()
        var phase = this.phase
        while(phase!=15) {
            Log.d("nextFull", "This is my nextFull")
            currentDate = currentDate.plusDays(1)
            phase = lunarCalculator.calculatePhase(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth, algorithm)
        }
        this.textNextFull.setText("Następna pełnia: " + currentDate.dayOfMonth + "." + currentDate.monthValue + "." + currentDate.year + " r.")
    }

    fun openSettings(v: View) {
        val intent = Intent(this, Settings::class.java)
        startActivityForResult(intent, 1)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    algorithm = data?.getStringExtra("algorithm")
                    location = data?.getStringExtra("location")
                    Toast.makeText(this, "Zapisano ustawienia: $location, $algorithm", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun openAllFullInYear(v: View) {
        val intent = Intent(this, AllInYear::class.java)
        intent.putExtra("algorithm", algorithm);
        startActivity(intent)
    }


}
