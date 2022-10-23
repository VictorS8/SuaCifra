package br.com.suacifra.database

import androidx.room.*
import br.com.suacifra.models.Notes

@Dao
interface NotesDao {
    @Insert
    fun createOneNote(note: Notes)

    @Query("SELECT * FROM Notes")
    fun readAllNotes() : MutableList<Notes>

    @Query("SELECT * FROM Notes WHERE id = (:notesId)")
    fun readOneNote(notesId: Int) : Notes

    @Update
    fun updateOneNote(note: Notes)

    @Delete
    fun deleteOneNote(note: Notes)
}