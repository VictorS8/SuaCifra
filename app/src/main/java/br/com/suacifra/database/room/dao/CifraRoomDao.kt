package br.com.suacifra.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import br.com.suacifra.database.room.models.CifraRoom
import br.com.suacifra.database.room.models.CifraWithSequenceRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface CifraRoomDao {

    @Upsert
    suspend fun upsertCifra(cifra: CifraRoom)

    @Delete
    suspend fun deleteCifra(cifra: CifraRoom)

    @Query("SELECT * FROM CifraRoom WHERE id=:cifraId")
    fun getCifraById(cifraId: Int): CifraRoom

    @Query("SELECT * FROM CifraRoom")
    fun getAllCifras(): Flow<List<CifraRoom>>

    @Transaction
    @Query("SELECT * FROM CifraRoom WHERE id=:cifraId")
    fun getCifraWithSequenceById(cifraId: Int): CifraWithSequenceRoom

    @Transaction
    @Query("SELECT * FROM CifraRoom")
    fun getAllCifraWithSequence(): Flow<List<CifraWithSequenceRoom>>

}
