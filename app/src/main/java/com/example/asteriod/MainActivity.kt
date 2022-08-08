package com.example.asteriod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Database
import com.example.asteriod.api.API
import com.example.asteriod.database.AsteroidDatabase
import com.example.asteriod.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    suspend fun getData(){
//        val retro:Retrofit  = Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .build();
//
//        val api = retro.create(API::class.java)
//        Log.d("check",""+api.getAsteriods())
//    }
}
