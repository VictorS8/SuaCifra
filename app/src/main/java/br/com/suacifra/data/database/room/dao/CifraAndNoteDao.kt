package br.com.suacifra.data.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.suacifra.data.database.room.models.CifraAndNoteRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface CifraAndNoteDao {

    @Query("SELECT * FROM CifraAndNoteRoom")
    fun getAllCifraAndNote(): Flow<List<CifraAndNoteRoom>>

    @Query("SELECT * FROM CifraAndNoteRoom WHERE cifraId=:cifraId")
    fun getAllCifrasFromCifraAndNoteByCifraId(cifraId: Int): Flow<List<CifraAndNoteRoom>>

    @Query("SELECT * FROM CifraAndNoteRoom WHERE noteId=:noteId")
    fun getAllNotesFromCifraAndNoteByNoteId(noteId: Int): Flow<List<CifraAndNoteRoom>>

}
