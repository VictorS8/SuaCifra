package br.com.suacifra.database.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteRoom(
    val title: String,
    val body: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
