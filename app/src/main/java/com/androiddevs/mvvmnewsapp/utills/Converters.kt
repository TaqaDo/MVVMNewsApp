package com.androiddevs.mvvmnewsapp.utills

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.data.models.news.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name,name)
    }
}