package com.example.asteriod.api

import com.example.asteriod.PictureOfDay
import com.example.asteriod.models.Image
import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY:String="6D9EsIZNrryL7Brnn9k3SzN9AN2ravaK4w2JFcYD"
public interface API {
    @GET("neo/rest/v1/feed?api_key=$API_KEY")
    suspend fun getAsteriods(): JsonElement

    @GET("planetary/apod?api_key=$API_KEY")
    suspend fun getImageOfTheDay(): PictureOfDay
}