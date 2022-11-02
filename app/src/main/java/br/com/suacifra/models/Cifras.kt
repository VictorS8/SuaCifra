package br.com.suacifra.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cifras(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val tone: String,
    @ColumnInfo val singerName: String,
    @ColumnInfo val chordsSequence: String
)
