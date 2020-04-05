package pl.bs.lunarapp

import java.util.*

class LunarCalculator {

    fun calculatePhase(year: Int, month: Int, day: Int, algorithm: String): Int {
        if (algorithm.equals("simple"))
            return simple(year, month, day)
        else if (algorithm.equals("julday"))
            return julday(year, month, day)
        else if (algorithm.equals("conway"))
            return conway(year, month, day)
        else if (algorithm.equals("trig2"))
            return trig2(year, month, day)
        else
            return trig1(year, month, day)
    }

    fun simple(year: Int, month: Int, day: Int): Int {

        val lp = 2551443;
        val now = Date(year,month-1,day,20,35,0);
        val new_moon = Date(1970, 0, 7, 20, 35, 0);
        val phase: Long = ((now.getTime() - new_moon.getTime())/1000) % lp;
        return Math.abs((Math.floor((phase /(24*3600)).toDouble())).toInt())
    }

    private fun conway(year: Int, month: Int, day: Int): Int {
        var r = year % 100.toDouble()
        r %= 19
        if (r > 9) { r -= 19}
        r = ((r * 11) % 30) + month + day;
        if (month<3){r += 2;}
        if(year<2000)
            r = r - 4
        else
            r = r - 8.3
        //r -= (if(year<2000)  4 else 8.3) as Double
        r = Math.floor(r+0.5)%30
        return (if(r < 0)  r+30 else r).toInt()
    }

    private fun trig1(year: Int, month: Int, day: Int): Int {
        val thisJD = julday(year,month,day);
        val degToRad = 3.14159265 / 180;
        val K0:Double
        val T:Double
        val T2:Double
        val T3:Double
        val J0:Double
        val F0:Double
        val M0:Double
        val M1:Double
        val B1:Double
        var oldJ:Double = 0.0
        K0 = Math.floor((year-1900)*12.3685);
        T = (year-1899.5) / 100;
        T2 = T*T; T3 = T*T*T;
        J0 = 2415020 + 29*K0;
        F0 = 0.0001178*T2 - 0.000000155*T3 + (0.75933 + 0.53058868*K0) - (0.000837*T + 0.000335*T2);
        M0 = 360*(GetFrac(K0*0.08084821133)) + 359.2242 - 0.0000333*T2 - 0.00000347*T3;
        M1 = 360*(GetFrac(K0*0.07171366128)) + 306.0253 + 0.0107306*T2 + 0.00001236*T3;
        B1 = 360*(GetFrac(K0*0.08519585128)) + 21.2964 - (0.0016528*T2) - (0.00000239*T3);
        var phase:Double = 0.0;
        var jday:Double = 0.0;
        while (jday < thisJD) {
            var F = F0 + 1.530588*phase;
            val M5 = (M0 + phase*29.10535608)*degToRad;
            val M6 = (M1 + phase*385.81691806)*degToRad;
            val B6 = (B1 + phase*390.67050646)*degToRad;
            F -= 0.4068*Math.sin(M6) + (0.1734 - 0.000393*T)*Math.sin(M5);
            F += 0.0161*Math.sin(2*M6) + 0.0104*Math.sin(2*B6);
            F -= 0.0074*Math.sin(M5 - M6) - 0.0051*Math.sin(M5 + M6);
            F += 0.0021*Math.sin(2*M5) + 0.0010*Math.sin(2*B6-M6);
            F += 0.5 / 1440;
            oldJ=jday;
            jday = J0 + 28*phase + Math.floor(F);
            phase++;
        }
        return ((thisJD-oldJ)%30).toInt()
    }

    private fun GetFrac(fr: Double): Double {
        return (fr - Math.floor(fr))
    }

    private fun trig2(year: Int, month: Int, day: Int): Int {
        val n = Math.floor(12.37 * (year -1900 + ((1.0 * month - 0.5)/12.0)));
        val RAD = 3.14159265/180.0;
        val t = n / 1236.85;
        val t2 = t * t;
        val as1 = 359.2242 + 29.105356 * n
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2;
        var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2;
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * as1) - 0.4068 * Math.sin(RAD * am);
        val i = (if(xtra > 0.0)  Math.floor(xtra) else  Math.ceil(xtra - 1.0));
        val j1 = julday(year,month,day);
        val jd = (2415020 + 28 * n) + i;
        return ((j1-jd + 30)%30).toInt()
    }

    private fun julday(year: Int, month: Int, day: Int): Int {
        var year: Int = year
        if (year < 0) { year+=1; }
        var jy = year;
        var jm = month +1;
        if (month <= 2) {jy--;	jm += 12;	}
        var jul = Math.floor(365.25 *jy) + Math.floor(30.6001 * jm) + day + 1720995;
        if (day+31*(month+12*year) >= (15+31*(10+12*1582))) {
            val ja = Math.floor(0.01 * jy);
            jul = jul + 2 - ja + Math.floor(0.25 * ja);
        }
        return jul.toInt()
    }

}