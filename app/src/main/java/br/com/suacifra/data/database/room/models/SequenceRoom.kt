package br.com.suacifra.data.database.room.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CifraRoom::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("cifraId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class SequenceRoom(
    val chords: String,
    val cifraId: Int?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
