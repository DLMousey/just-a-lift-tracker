package dev.dlmousey.justalifttracker.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "lifts_table")
data class Lift(
    @PrimaryKey(autoGenerate = true) val liftId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val type: String
) : Parcelable