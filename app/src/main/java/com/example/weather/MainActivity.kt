package com.example.weather

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private lateinit var location: TextView
    private lateinit var updateat: TextView
    private lateinit var temperature: TextView
    private lateinit var mintemp: TextView
    private lateinit var maxtemp: TextView
    private lateinit var sunset: TextView
    private lateinit var sunrise: TextView
    private lateinit var pressure: TextView
    private lateinit var humidity: TextView
    private lateinit var wind: TextView
    private lateinit var weather:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        location = findViewById(R.id.tv_address)
        updateat = findViewById(R.id.tv_updatedAt)
        temperature = findViewById(R.id.tv_temperature)
        mintemp = findViewById(R.id.tv_tempMin)
        maxtemp = findViewById(R.id.tv_tempMax)
        sunset = findViewById(R.id.tv_sunsetTime)
        sunrise = findViewById(R.id.tv_sunriseTime)
        pressure = findViewById(R.id.tv_pressureTime)
        humidity = findViewById(R.id.tv_humidityTime)
        wind = findViewById(R.id.tv_windTime)
        weather =findViewById(R.id.tv_status)
        val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        val locationgot = sharedPref.getString("location", null)
        val maingot = sharedPref.getString("main", null)
        val statusgot = sharedPref.getString("status", null)
        val tempgot = sharedPref.getFloat("temp", 0f)
        val mintempgot = sharedPref.getFloat("mintemp", 0f)
        val maxtempgot = sharedPref.getFloat("maxtemp", 0f)
        val sunrisegot = sharedPref.getLong("sunrise", 0)
        val sunsetgot = sharedPref.getLong("sunset", 0)
        val windgot = sharedPref.getFloat("wind", 0f)
        val pressuregot = sharedPref.getInt("pressure", 0)
        val humiditygot = sharedPref.getInt("humidity", 0)
        //actual
        val tempinC=tempgot-273.15
        val minTempinC=mintempgot-273.15
        val maxTempinC=maxtempgot-273.15
        val sunrisetime=convertTimestampToDateTime(sunrisegot)
        val sunsettime=convertTimestampToDateTime(sunsetgot)
        val pressureinkpa=pressuregot*0.1
        location.text=locationgot
        updateat.text=statusgot
        weather.text=maingot
        temperature.text=String.format("%.3f", tempinC)
        mintemp.text=String.format("%.3f", minTempinC)
        maxtemp.text=String.format("%.3f", maxTempinC)
        sunrise.text=sunrisetime
        sunset.text=sunsettime
        wind.text=windgot.toString()
        humidity.text=humiditygot.toString()
        pressure.text=String.format("%.3f", pressureinkpa)

    }
    fun convertTimestampToDateTime(timestamp: Long): String {
        val date = Date(timestamp * 1000) // Convert seconds to milliseconds
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)
    }


}