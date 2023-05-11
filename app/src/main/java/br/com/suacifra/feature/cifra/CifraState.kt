package br.com.suacifra.feature.cifra

import br.com.suacifra.database.room.models.CifraWithSequenceRoom
import br.com.suacifra.database.room.models.SequenceRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CifraState(
    val cifras: List<CifraWithSequenceRoom> = emptyList(),
    val name: String = "",
    val singerName: String = "",
    val tone: String = "",
    val sequenceList: List<SequenceRoom> = emptyList()
)
