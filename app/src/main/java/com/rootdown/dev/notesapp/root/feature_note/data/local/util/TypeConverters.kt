package com.rootdown.dev.notesapp.root.feature_note.data.local.util

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

private const val SEPARATOR = ","
class ListStringConverter {
    @TypeConverter
    fun fromString(value: String?): MutableList<String> {
        val listType = object :
            TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromList(list: MutableList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}