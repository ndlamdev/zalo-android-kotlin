package com.lamnguyen.zalo.utils.converters

import androidx.room.TypeConverter
import java.time.Instant

class DataTypeConverter {
    @TypeConverter
    fun fromInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(value) }
    }

    @TypeConverter
    fun instantToTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }
}