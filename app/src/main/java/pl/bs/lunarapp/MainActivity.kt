package pl.bs.lunarapp

import android.app.Activity
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
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
        setMoonPhaseImage()
        setPercentage()
        setPreviousEmpty()
        setNextFull()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate() {
        var currentDate: LocalDate = LocalDate.now()
        this.year = currentDate.year
        this.month = currentDate.monthValue
        this.day = currentDate.dayOfMonth
    }

    private fun setMoonPhaseImage() {
        val id = R.mipmap::class.java.getField(location.toLowerCase() + phase.toString()).getInt(null)
        this.imageView.setImageResource(id)
    }

    private fun setPercentage() {
        var percantage: Int = (((phase.toDouble()/30)) * 100).toInt()
        this.textPercentage.setText("Cykl księżyca: " + percantage + "%")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setPreviousEmpty() {
        var currentDate: LocalDate = LocalDate.now()
        var phase = this.phase
        while(phase!=0) {
            currentDate = currentDate.minusDays(1)
            phase = lunarCalculator.calculatePhase(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth, algorithm)
        }
        this.textPreviousEmpty.setText("Poprzedni nów: " + currentDate.dayOfMonth + "." + currentDate.monthValue + "." + currentDate.year + " r.")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNextFull() {
        var currentDate: LocalDate = LocalDate.now()
        var phase = this.phase
        while(phase!=15) {
            currentDate = currentDate.plusDays(1)
            phase = lunarCalculator.calculatePhase(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth, algorithm)
        }
        this.textNextFull.setText("Następna pełnia: " + currentDate.dayOfMonth + "." + currentDate.monthValue + "." + currentDate.year + " r.")
    }

    fun openSettings(v: View) {
        val intent = Intent(this, SettingsActivity::class.java)
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

    fun openFullMoonsInYear(v: View) {
        val intent = Intent(this, FullMoonsInYearActivity::class.java)
        intent.putExtra("algorithm", algorithm);
        startActivity(intent)
    }

}
