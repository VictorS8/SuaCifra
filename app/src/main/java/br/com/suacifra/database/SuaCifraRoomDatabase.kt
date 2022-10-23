package br.com.suacifra.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.suacifra.models.Cifras
import br.com.suacifra.models.Notes
import br.com.suacifra.utils.Config

@Database(entities = [Cifras::class, Notes::class], version = Config.SUA_CIFRA_DATABASE_VERSION)
abstract class SuaCifraRoomDatabase : RoomDatabase() {
    abstract fun cifrasDao(): CifrasDao
    abstract fun notesDao(): NotesDao
}