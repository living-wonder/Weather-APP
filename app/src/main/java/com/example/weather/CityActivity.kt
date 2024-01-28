package com.example.weather

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.weather.api.ApiClient
import com.example.weather.api.ApiInterface
import com.example.weather.model.Weather
import com.example.weather.model.Coordinates
import retrofit2.Call
import retrofit2.Response

class CityActivity : AppCompatActivity() {
    private lateinit var location:EditText
    private lateinit var summit:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        location=findViewById(R.id.et_location)
        summit=findViewById(R.id.bt_summit)

        summit.setOnClickListener {
            val value = location.text.toString().trim()
            Log.d("value","${value}")
            if(value.isEmpty()){
              Toast.makeText(this,"Location Cannot be Empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val retrofit = ApiClient.getinstance().create(ApiInterface::class.java)
            val apikey ="0c6549c4278af8725f50370ee8e5f0eb"
            val data = retrofit?.getCoordinate(value,apikey )

            data?.enqueue(object:retrofit2.Callback<Coordinates>{
                override fun onResponse(call: Call<Coordinates>, response: Response<Coordinates>) {
                     if(response.isSuccessful){
                       val apidata = response.body()
                         val weatherList =apidata?.weather
                       val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                         val editor = sharedPref.edit()

                         if (weatherList != null && weatherList.isNotEmpty()) {
                             val firstWeather = weatherList[0]
                             editor.putString("location", apidata?.name)
                             editor.putString("main", firstWeather.main)
                             editor.putString("status", firstWeather.description)
                             editor.putFloat("mintemp", apidata?.main?.temp_min ?: 0f)
                             editor.putFloat("maxtemp", apidata?.main?.temp_max ?: 0f)
                             editor.putFloat("temp", apidata?.main?.temp ?: 0f)
                             editor.putLong("sunrise", apidata?.sys?.sunrise ?: 0)
                             editor.putLong("sunset", apidata?.sys?.sunset ?: 0)
                             editor.putFloat("wind", apidata?.wind?.speed ?: 0f)
                             editor.putInt("pressure", apidata?.main?.pressure ?: 0)
                             editor.putInt("humidity", apidata?.main?.humidity ?: 0)
                             editor.apply()
                         }

                         Log.d("Data","${apidata?.coord?.lat},${apidata?.coord?.lon}")
                       startActivity(Intent(this@CityActivity,MainActivity::class.java))
                   }else{
                       Toast.makeText(this@CityActivity,response.message().toString(),Toast.LENGTH_SHORT).show()
                       Log.e("API Error", "Code: ${response.code()}, Message: ${response.message()}${response.errorBody()}")
                   }
                }

                override fun onFailure(call: Call<Coordinates>, t: Throwable) {
                    Log.d("Error","${t.cause}.${t.localizedMessage},${t.message}")

                    Toast.makeText(this@CityActivity,t.message,Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}