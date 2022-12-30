package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.annotation.Keep
import androidx.room.*
import com.rootdown.dev.notesapp.root.theme.*

@Keep
@Entity(
    tableName = "cloud_group"
)
data class CloudGroup(
    @PrimaryKey(autoGenerate = true)
    var cloudGroupId: Int = 0,
    val groupName: String = "default",
    val listGroupNames: MutableList<String> = mutableListOf()
){
    companion object {
        val noteColors = listOf(primary, secondary, primaryLight, secondaryLight, primaryText, secondaryText)
    }
}
