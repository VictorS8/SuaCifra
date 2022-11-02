package br.com.suacifra.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.suacifra.data.database.CifraEntity

@Dao
interface CifraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createOne(cifra: CifraEntity)

    @Query("SELECT * FROM CifraEntity WHERE id = :id")
    fun readOne(id: Long): CifraEntity

     @Query("SELECT * FROM CifraEntity")
     fun readAll() : MutableList<CifraEntity>


}