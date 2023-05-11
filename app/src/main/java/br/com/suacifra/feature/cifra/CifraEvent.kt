package br.com.suacifra.feature.cifra

import br.com.suacifra.database.room.models.CifraRoom
import br.com.suacifra.database.room.models.SequenceRoom

sealed interface CifraEvent {
    object SaveCifra: CifraEvent
    data class DeleteCifra(val cifra: CifraRoom): CifraEvent
    data class SetCifraName(val cifraName: String): CifraEvent
    data class SetCifraSingerName(val cifraSingerName: String): CifraEvent
    data class SetCifraTone(val cifraTone: String): CifraEvent
    data class SetCifraSequenceList(val cifraSequenceList: List<SequenceRoom>): CifraEvent

}