package br.com.suacifra.data.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import br.com.suacifra.data.database.room.models.NoteRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteRoomDao {

    @Upsert
    suspend fun upsertNote(note: NoteRoom)

    @Delete
    suspend fun deleteNote(note: NoteRoom)

    @Query("SELECT * FROM NoteRoom WHERE id=:noteId")
    fun getNoteById(noteId: Int): NoteRoom

    @Query("SELECT * FROM NoteRoom")
    fun getAllNotes(): Flow<List<NoteRoom>>

}
