package com.example.asteriod.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.asteriod.PictureOfDay
import com.example.asteriod.models.Asteroid
import com.example.asteriod.api.API
import com.example.asteriod.api.parseAsteroidsJsonResult
import com.example.asteriod.database.AsteroidDatabase
import com.example.asteriod.database.DatabaseDAO
import com.example.asteriod.models.Image
import com.example.asteriod.tools.ConnectionChecker
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(val application: Application) : ViewModel() {

    val retro = Retrofit.Builder().baseUrl("https://api.nasa.gov/") .addConverterFactory(
        GsonConverterFactory.create()).build()
    val asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val imageOfDay: MutableLiveData<PictureOfDay> = MutableLiveData()
    val dataSource = AsteroidDatabase.getInstance(application).databaseDao
    init{
        viewModelScope.launch {
            // Api level
            val connectionCheck = async { ConnectionChecker().isOnline(application) }
            if(connectionCheck.await()) {
                var asteroidsData = async {
                    getAsteroidsFromApi()
                }
                val image = async { getImageOfTheDay() }
                imageOfDay.value = image.await()

                Log.d("check",""+asteroids.value)
                setAsteroidsToDatabase(asteroidsData.await())
            }
            else{
                asteroids.postValue(dataSource.getAsteroids())
            }
            // Data base level

        }
    }

    private suspend fun getImageOfTheDay():PictureOfDay{
        val api = retro.create(API::class.java)
        return api.getImageOfTheDay()
    }

    private suspend fun getAsteroidsFromApi():List<Asteroid>{
        try {
            val api = retro.create(API::class.java)
            var rawData = api.getAsteriods()//
            var data = parseAsteroidsJsonResult(JSONObject(rawData.toString()))
            return data
        } catch (e: Exception) {
            Log.d("check", "${e.toString()}:${e.message}")
        }
        return listOf()
    }

    private suspend fun setAsteroidsToDatabase(data:List<Asteroid>){
        asteroids.value = data
        for (x: Asteroid in data){
            dataSource.insertAsteroid(x)
        }

    }
}

