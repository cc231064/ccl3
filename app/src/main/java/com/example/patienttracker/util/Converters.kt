// THIS FILE CURRENTLY DOES NOT WORK. PLEASE IGNORE. I COULDN'T GET IT TO WORK.

package com.example.patienttracker.util

import androidx.room.TypeConverter
import java.util.*

// Class containing type converters for Room database
class Converters {

    // Converts a timestamp (Long) to a Date object
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    // Converts a Date object to a timestamp (Long)
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}