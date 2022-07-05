package dev.dlmousey.justalifttracker.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets_table")
data class Set(
    @PrimaryKey(autoGenerate = true) val setId: Long,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "rpe") val rpe: Int,
    @ColumnInfo(name = "set_number") val setNumber: Int,
    @ColumnInfo(name = "date") val timestamp: Long,
    @Embedded val lift: Lift
)