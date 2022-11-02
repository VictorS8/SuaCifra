package br.com.suacifra.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.suacifra.data.database.dao.*
import br.com.suacifra.utils.Config

@Database(entities = [CifraEntity::class], version = Config.SUA_CIFRA_DATABASE_VERSION)
abstract class RoomSuaCifraDatabase : RoomDatabase() {

    abstract fun cifraDao() : CifraDao

    companion object {
        @Volatile
        private var INSTANCE: RoomSuaCifraDatabase? = null

        fun getDatabase(context: Context): RoomSuaCifraDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomSuaCifraDatabase::class.java,
                    Config.SUA_CIFRA_ROOM_DATABASE
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}