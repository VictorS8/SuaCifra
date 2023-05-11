package br.com.suacifra.database.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CifraRoom(
    val name: String,
    val tone: String?,
    val singerName: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
