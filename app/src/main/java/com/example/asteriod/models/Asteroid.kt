package com.example.asteriod.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName="asteroid_table")
data class Asteroid(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name="Name")
    val codename: String,
    @ColumnInfo(name="CAD")
    val closeApproachDate: String,
    @ColumnInfo(name="Magnitude")
    val absoluteMagnitude: Double,
    @ColumnInfo(name="EDiameter")
    val estimatedDiameter: Double,
    @ColumnInfo(name="Velocity")
    val relativeVelocity: Double,
    @ColumnInfo(name="Distance")
    val distanceFromEarth: Double,
    @ColumnInfo(name="isHazardous")
    val isPotentiallyHazardous: Boolean,
) : Parcelable