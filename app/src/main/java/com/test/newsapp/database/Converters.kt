package com.test.newsapp.database

import androidx.room.TypeConverter
import com.test.newsapp.data.model.Source

class Converters {
    @TypeConverter
    fun sourceToString(source: Source): String = source.name

    @TypeConverter
    fun stringToSource(name: String): Source =  Source(name = name)
}