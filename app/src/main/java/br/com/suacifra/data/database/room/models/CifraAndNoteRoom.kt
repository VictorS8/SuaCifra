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
    ), ForeignKey(
        entity = NoteRoom::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("noteId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class CifraAndNoteRoom(
    val cifraId: Int,
    val noteId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
