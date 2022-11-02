package br.com.suacifra.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Cifras::class, parentColumns = arrayOf("id"), childColumns = arrayOf("cifraId"),
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    )]
)
data class Sequences(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val sequence: String,
    @ColumnInfo(index = true) val cifraId: Int
)
