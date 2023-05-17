package br.com.suacifra.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.suacifra.data.database.room.dao.CifraAndNoteDao
import br.com.suacifra.data.database.room.dao.CifraRoomDao
import br.com.suacifra.data.database.room.dao.NoteRoomDao
import br.com.suacifra.data.database.room.models.CifraAndNoteRoom
import br.com.suacifra.data.database.room.models.CifraRoom
import br.com.suacifra.data.database.room.models.NoteRoom
import br.com.suacifra.utils.Config

@Database(
    entities = [
        CifraRoom::class,
        NoteRoom::class,
        CifraAndNoteRoom::class
    ],
    version = Config.SUA_CIFRA_DATABASE_VERSION
)
abstract class SuaCifraDatabaseRoom : RoomDatabase() {

    abstract val cifraDao: CifraRoomDao
    abstract val noteDao: NoteRoomDao
    abstract val cifraAndNoteDao: CifraAndNoteDao

}
