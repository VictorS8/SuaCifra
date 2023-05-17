package br.com.suacifra.data.database.room.models

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

data class CifraWithSequenceRoom(
    @Embedded
    val cifra: CifraRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "cifraId"
    )
    val sequenceList: List<SequenceRoom>?
)
