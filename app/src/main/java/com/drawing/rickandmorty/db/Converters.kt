package com.drawing.rickandmorty.db

import androidx.room.TypeConverter
import com.drawing.rickandmorty.models.Location

class Converters {

    @TypeConverter
    fun fromLocation(location: Location) : String{
        return location.name
    }

    @TypeConverter
    fun toLocation(name: String) : Location{
        return Location(name, name)
    }

}