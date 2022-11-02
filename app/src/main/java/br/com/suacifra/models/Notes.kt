package br.com.suacifra.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey val id: Int?,
    @ColumnInfo val noteTitle: String,
    @ColumnInfo val noteBody: String
)
