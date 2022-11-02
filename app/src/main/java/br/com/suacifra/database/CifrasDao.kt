package br.com.suacifra.database

import androidx.room.*
import br.com.suacifra.models.Cifras

@Dao
interface CifrasDao {
    @Insert
    fun createOneCifra(cifra: Cifras)

    @Query("SELECT * FROM Cifras")
    fun readAllCifras(): MutableList<Cifras>

    @Query("SELECT * FROM Cifras WHERE id = :id")
    fun readOneCifra(id: Int): Cifras

    @Query("SELECT chordsSequence FROM Cifras WHERE id = :id")
    fun readOneCifraChordsSequenceById(id: Int): String?

    @Update
    fun updateOneCifra(cifra: Cifras)

    @Query("UPDATE Cifras SET name = :name, tone = :tone, singerName = :singerName, chordsSequence = :chordsSequence WHERE id = :id")
    fun updateOneCifraById(
        id: Int,
        name: String,
        tone: String,
        singerName: String,
        chordsSequence: String
    )

    @Query("UPDATE Cifras SET chordsSequence = :chordsSequence WHERE id = :id")
    fun updateOneCifraChordsSequenceById(id: Int, chordsSequence: String)

    @Delete
    fun deleteOneCifra(cifra: Cifras)

    @Query("DELETE FROM Cifras WHERE id = :cifraId")
    fun deleteOneCifraById(cifraId: Int)

}
