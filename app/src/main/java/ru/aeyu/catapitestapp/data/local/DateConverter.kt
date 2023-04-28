package ru.aeyu.evoreceipt.data.local

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDateFromLong(timestamp: Long?) = timestamp?.let { Date(it) }

    @TypeConverter
    fun toLongFromDate(date: Date?) = date?.time
}