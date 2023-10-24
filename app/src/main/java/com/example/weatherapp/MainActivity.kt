package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.weatherapp.Data.WeatherApp
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//893d5711d0a273e7097aa1ed864ca7fa
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        searchCity()
        weatherReport("Chandigarh")
    }

    private fun searchCity(){
        val search = binding.searchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                weatherReport(query!!)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun changeText(conditions: String){
        when(conditions){
            "Thunderstorm", "Drizzle", "Rain" -> {
                val color = ContextCompat.getColor(this,R.color.rainy_temp)
                binding.temperature.setTextColor(color)
                binding.celsius.setTextColor(color)
                binding.weather.setTextColor(color)
                binding.minTemp.setTextColor(color)
                binding.maxTemp.setTextColor(color)
                binding.location.setTextColor(color)
                binding.date.setTextColor(color)
            }
            "Snow", "Blizzard" -> {
                val color = ContextCompat.getColor(this,R.color.snow_temp)
                binding.temperature.setTextColor(color)
                binding.celsius.setTextColor(color)
                binding.weather.setTextColor(color)
                binding.minTemp.setTextColor(color)
                binding.maxTemp.setTextColor(color)
                binding.location.setTextColor(color)
                binding.date.setTextColor(color)
            }
            "Mist", "Smoke", "Haze", "Dust", "Fog", "Tornado", "Clouds" ->{
                val color = ContextCompat.getColor(this,R.color.cloudy_temp)
                binding.temperature.setTextColor(color)
                binding.celsius.setTextColor(color)
                binding.weather.setTextColor(color)
                binding.minTemp.setTextColor(color)
                binding.maxTemp.setTextColor(color)
                binding.location.setTextColor(color)
                binding.date.setTextColor(color)
            }
            "Clear", "Sunny" ->{
                val color = ContextCompat.getColor(this,R.color.sunny_temp)
                binding.temperature.setTextColor(color)
                binding.celsius.setTextColor(color)
                binding.weather.setTextColor(color)
                binding.minTemp.setTextColor(color)
                binding.maxTemp.setTextColor(color)
                binding.location.setTextColor(color)
                binding.date.setTextColor(color)
            }
        }
    }

    private fun changeBackground(conditions: String){
        when(conditions){
            "Thunderstorm", "Drizzle", "Rain" -> {
                binding.backgroundConstraintLayout.setBackgroundResource(R.drawable.background_rainy)
                binding.cardConstraintLayout.setBackgroundResource(R.drawable.rainy_card_layot)
                binding.iconsCard.setBackgroundResource(R.drawable.rainy_card_layot)
                binding.weatherImage.setImageResource(R.drawable.rainy_day_image)
                changeText(conditions)
            }
            "Snow", "Blizzard" -> {
                binding.backgroundConstraintLayout.setBackgroundResource(R.drawable.background_snowy)
                binding.cardConstraintLayout.setBackgroundResource(R.drawable.snowy_card_layout)
                binding.iconsCard.setBackgroundResource(R.drawable.snowy_card_layout)
                binding.weatherImage.setImageResource(R.drawable.snowy_day_image)
                changeText(conditions)
            }
            "Mist", "Smoke", "Haze", "Dust", "Fog", "Tornado", "Clouds" ->{
                binding.backgroundConstraintLayout.setBackgroundResource(R.drawable.background_cloudy)
                binding.cardConstraintLayout.setBackgroundResource(R.drawable.cloudy_card_layout)
                binding.iconsCard.setBackgroundResource(R.drawable.cloudy_card_layout)
                binding.weatherImage.setImageResource(R.drawable.cloudy_day_image)
                changeText(conditions)
            }
            "Clear", "Sunny" ->{
                binding.backgroundConstraintLayout.setBackgroundResource(R.drawable.background_sunny)
                binding.cardConstraintLayout.setBackgroundResource(R.drawable.sunny_card_layout)
                binding.iconsCard.setBackgroundResource(R.drawable.sunny_card_layout)
                binding.weatherImage.setImageResource(R.drawable.sunny_day_image)
                changeText(conditions)
            }
        }
    }

    private fun weatherReport(cityName: String){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiRequest::class.java)

        val response = retrofit.getWeather(cityName,"893d5711d0a273e7097aa1ed864ca7fa","metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val body = response.body()
                if(response.isSuccessful && body != null){
                    val temperature = body.main.temp.toString()
                    val humidity = body.main.humidity
                    val windSpeed = body.wind.speed
                    val pressure = body.main.pressure
                    val sunRise = body.sys.sunrise.toLong()
                    val sunSet = body.sys.sunset.toLong()
                    val condition = body.weather.firstOrNull()?.main?:"Unknown"
                    val visibility = body.visibility
                    val maxTemp = body.main.temp_max
                    val minTemp = body.main.temp_min
                    binding.temperature.text = temperature
                    binding.weather.text = condition
                    binding.humidityValue.text = "$humidity %"
                    binding.windSpeedValue.text = "$windSpeed m/s"
                    binding.pressureValue.text = "$pressure hPa"
                    binding.sunriseValue.text = "${time(sunRise)}"
                    binding.sunsetValue.text = "${time(sunSet)}"
                    binding.visibilityValue.text = "$visibility m"
                    binding.day.text = day(System.currentTimeMillis())
                    binding.date.text = date()
                    binding.location.text = cityName.toUpperCase()
                    binding.maxTemp.text = "Max Temp: $maxTemp"
                    binding.minTemp.text = "Min Temp: $minTemp"
                    changeBackground(condition)

                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                Log.d("TAG",t.toString())
            }

        })
    }

    fun day(timeStamp: Long):String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }

    fun date(): String{
        val sdf = SimpleDateFormat("dd MMMM YYYY",Locale.getDefault())
        return sdf.format((Date()))
    }

    private fun time(timeStamp: Long): String{
        val sdf =SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timeStamp*1000)))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}