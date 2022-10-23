package br.com.suacifra.database

import android.content.Context
import androidx.room.Room
import br.com.suacifra.utils.Config

object RoomDatabaseBuilderSingleton {
    fun databaseBuild(applicationContext: Context):
            SuaCifraRoomDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SuaCifraRoomDatabase::class.java, Config.SUA_CIFRA_DATABASE
        ).build()
    }
}
