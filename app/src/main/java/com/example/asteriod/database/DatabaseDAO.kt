package com.example.asteriod.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asteriod.models.Asteroid

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: Asteroid)

//    @Query("select * from asteroid_table where CAD = getdate()")
//    suspend fun getAsteroidsOfToday(): LiveData<List<Asteroid>>

    @Query("select * from asteroid_table where id = :key")
    fun getAsteroid(key:Long): LiveData<Asteroid>?

    @Query("select * from asteroid_table order by CAD desc")
    suspend fun getAsteroids():List<Asteroid>?
}