package com.example.asteriod

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteriod.api.API
import com.example.asteriod.api.parseAsteroidsJsonResult
import com.example.asteriod.database.AsteroidDatabase
import com.example.asteriod.models.Asteroid
import com.example.asteriod.tools.ConnectionChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorkerTester(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    val retro = Retrofit.Builder().baseUrl("https://api.nasa.gov/") .addConverterFactory(
        GsonConverterFactory.create()).build()
    val asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val imageOfDay: MutableLiveData<PictureOfDay> = MutableLiveData()
    val dataSource = AsteroidDatabase.getInstance(appContext).databaseDao

    companion object {
        const val WORK_NAME = "WorkerTester"
    }

    override suspend fun doWork(): Result {
//            val connectionCheck = async { ConnectionChecker().isOnline(appContext) }
//            if(connectionCheck.await()) {
            try{
                var asteroidsData = getDataFromApi()
                Log.d("check", "" + asteroids.value)
                setAsteroidsToDatabase(asteroidsData)

            }
            catch (e:Exception){
                return Result.failure()
            }
            return Result.success()
    }

    private suspend fun getDataFromApi():List<Asteroid> {
        try {
            val api = retro.create(API::class.java)
            var rawData = api.getAsteriods()
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