package br.com.suacifra.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.suacifra.models.Sequences

@Dao
interface SequencesDao {

    @Insert
    fun createOneSequence(sequence: Sequences)

    @Query("SELECT * FROM Sequences WHERE cifraId = :cifraId")
    fun readAllSequencesFromCifraId(cifraId: Int): MutableList<Sequences>

    @Update
    fun updateOneSequence(sequence: Sequences)

    @Delete
    fun deleteOneSequence(sequence: Sequences)

}