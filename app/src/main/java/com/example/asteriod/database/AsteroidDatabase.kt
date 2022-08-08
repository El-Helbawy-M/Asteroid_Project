package com.example.asteriod.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asteriod.models.Asteroid

@Database(entities = [Asteroid::class], version= 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val databaseDao: DatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE:AsteroidDatabase? = null

        fun getInstance(context: Context) : AsteroidDatabase{

            // for blocking any threads to user this function until the current thread finished
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid_database")      .fallbackToDestructiveMigration()
                        .build()
                }

                return instance

            }

        }
    }
}
