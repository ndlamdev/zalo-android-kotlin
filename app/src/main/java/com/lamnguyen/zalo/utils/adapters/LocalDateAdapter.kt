package com.lamnguyen.zalo.utils.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : JsonAdapter<LocalDate>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE // hoặc "yyyy-MM-dd"

    override fun fromJson(reader: JsonReader): LocalDate? {
        return LocalDate.parse(reader.nextString(), formatter)
    }


    override fun toJson(jsonWriter: JsonWriter, date: LocalDate?) {
        jsonWriter.value(date?.format(formatter));
    }
}