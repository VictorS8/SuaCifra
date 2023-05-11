package br.com.suacifra.feature.cifra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.suacifra.database.room.dao.CifraRoomDao
import br.com.suacifra.database.room.models.CifraRoom
import br.com.suacifra.database.room.models.CifraWithSequenceRoom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CifraViewModel(private val dao: CifraRoomDao) : ViewModel() {

    private val _cifras = dao.getAllCifraWithSequence()
    private val _state = MutableStateFlow(CifraState())
    val state = combine(_state, _cifras) { state, cifras ->
        state.copy(
            cifras = cifras,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CifraState())

    fun onEvent(event: CifraEvent) {
        when (event) {
            is CifraEvent.DeleteCifra -> {
                viewModelScope.launch {
                    dao.deleteCifra(event.cifra)
                }
            }

            CifraEvent.SaveCifra -> {
                val name = state.value.name
                val singerName = state.value.singerName
                val tone = state.value.tone
                val sequenceList = state.value.sequenceList

                if (name.isBlank() || singerName.isBlank() || tone.isBlank() || sequenceList.isEmpty()) {
                    return
                }

                val cifra = CifraWithSequenceRoom(
                    CifraRoom(
                        name = name,
                        singerName = singerName,
                        tone = tone
                    ),
                    sequenceList = sequenceList
                )
                viewModelScope.launch {
                    dao.upsertCifra(cifra)
                }
                _state.update {
                    it.copy(
                        name = "",
                        singerName = "",
                        tone = ""
                    )
                }
            }

            is CifraEvent.SetCifraName -> {
                _state.update {
                    it.copy(
                        name = event.cifraName
                    )
                }
            }

            is CifraEvent.SetCifraSequenceList -> {
                _state.update {
                    it.copy(
                        sequenceList = event.cifraSequenceList
                    )
                }
            }

            is CifraEvent.SetCifraSingerName -> {
                _state.update {
                    it.copy(
                        singerName = event.cifraSingerName
                    )
                }
            }

            is CifraEvent.SetCifraTone -> {
                _state.update {
                    it.copy(
                        tone = event.cifraTone
                    )
                }
            }
        }
    }

}