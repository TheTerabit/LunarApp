package pl.bs.lunarapp

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import java.time.LocalDate
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_all_in_year.*
import kotlinx.android.synthetic.main.activity_all_in_year.view.*


class AllInYear : AppCompatActivity() {

    var algorithm: String = ""
    var year: Int = 2020
    val lunarCalculator = LunarCalculator()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_in_year)
        algorithm = intent.getStringExtra("algorithm")
        setItemList(getAllFullMoons())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllFullMoons(): ArrayList<String> {
        var currentDate: LocalDate = LocalDate.of(year, 1, 1)
        var lastDate: LocalDate = currentDate.plusYears(1)
        var fullMoons = ArrayList<String>()
        while(currentDate.isBefore(lastDate)) {
            if (lunarCalculator.calculatePhase(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth, algorithm) == 15)
                fullMoons.add(currentDate.dayOfMonth.toString() + "." + currentDate.monthValue.toString() + "." + currentDate.year)
            currentDate = currentDate.plusDays(1)
        }
        return fullMoons
    }

    private fun setItemList(items: ArrayList<String>) {
        val adapter = ArrayAdapter<String>(this,
                R.layout.activity_listview, items)
        listView.setAdapter(adapter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun plusClicked(v: View) {
        textYear.setText((textYear.text.toString().toInt() + 1).toString())
        year = textYear.text.toString().toInt()
        if ((year>1899) && (year <2201))
            setItemList(getAllFullMoons())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun minusClicked(v: View) {
        textYear.setText((textYear.text.toString().toInt() - 1).toString())
        year = textYear.text.toString().toInt()
        if ((year>1899) && (year <2201))
            setItemList(getAllFullMoons())
    }


}
