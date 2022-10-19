package br.com.suacifra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.suacifra.R
import br.com.suacifra.models.Cifras
import br.com.suacifra.models.Notes

class DatabaseHelper(
    context: Context
) : SQLiteOpenHelper(context, context.getString(R.string.sqlite_database_name), null, 1) {

    companion object {
        const val NOTES_TABLE = "NOTES_TABLE"
        const val NOTE_ID_COLUMN = "ID"
        const val NOTE_TITLE_COLUMN = "NOTE_TITLE"
        const val NOTE_BODY_COLUMN = "NOTE_BODY"

        const val CIFRAS_TABLE = "CIFRAS_TABLE"
        const val CIFRA_ID_COLUMN = "ID"
        const val CIFRA_NAME_COLUMN = "CIFRA_NAME"
        const val CIFRA_TONE_COLUMN = "CIFRA_TONE"
        const val CIFRA_SINGER_NAME_COLUMN = "CIFRA_SINGER_NAME"
        const val CIFRA_CHORDS_SEQUENCE_COLUMN = "CIFRA_CHORDS_SEQUENCE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createNotesTableStatement =
            "CREATE TABLE $NOTES_TABLE ($NOTE_ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $NOTE_TITLE_COLUMN TEXT, $NOTE_BODY_COLUMN TEXT)"
        val createCifrasTableStatement =
            "CREATE TABLE $CIFRAS_TABLE ($CIFRA_ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $CIFRA_NAME_COLUMN TEXT, $CIFRA_TONE_COLUMN TEXT, $CIFRA_SINGER_NAME_COLUMN TEXT, $CIFRA_CHORDS_SEQUENCE_COLUMN TEXT)"

        db?.execSQL(createNotesTableStatement)
        db?.execSQL(createCifrasTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addOneNote(noteModel: Notes): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NOTE_TITLE_COLUMN, noteModel.noteTitle)
        contentValues.put(NOTE_BODY_COLUMN, noteModel.noteBody)

        val insert = database.insert(NOTES_TABLE, null, contentValues)
        val insertErrorNumber: Long = -1
        return insert != insertErrorNumber
    }

    fun addOneCifra(cifraModel: Cifras): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(CIFRA_NAME_COLUMN, cifraModel.name)
        contentValues.put(CIFRA_TONE_COLUMN, cifraModel.tone)
        contentValues.put(CIFRA_SINGER_NAME_COLUMN, cifraModel.singerName)
        contentValues.put(CIFRA_CHORDS_SEQUENCE_COLUMN, cifraModel.chordsSequence)

        val insert = database.insert(CIFRAS_TABLE, null, contentValues)
        val insertErrorNumber: Long = -1
        return insert != insertErrorNumber
    }

    fun getAllNotes(): MutableList<Notes> {
        val returnMutableList = mutableListOf<Notes>()

        val queryString = "SELECT * FROM $NOTES_TABLE"
        val database: SQLiteDatabase = this.readableDatabase
        val cursor = database.rawQuery(queryString, null)

        if (cursor.moveToFirst()) {
            do {
                val noteId = cursor.getInt(0)
                val noteTitle = cursor.getString(1)
                val noteBody = cursor.getString(2)

                val newNote = Notes(noteId, noteTitle, noteBody)
                returnMutableList.add(newNote)
            } while (cursor.moveToNext())
        } else {
            return mutableListOf()
        }

        cursor.close()
        database.close()
        return returnMutableList
    }

    fun getAllCifras(): MutableList<Cifras> {
        val returnMutableList = mutableListOf<Cifras>()

        val queryString = "SELECT * FROM $CIFRAS_TABLE"
        val database: SQLiteDatabase = this.readableDatabase
        val cursor = database.rawQuery(queryString, null)

        if (cursor.moveToFirst()) {
            do {
                val cifraId = cursor.getInt(0)
                val cifraName = cursor.getString(1)
                val cifraTone = cursor.getString(2)
                val cifraSingerName = cursor.getString(3)
                val cifraChordsSequence = cursor.getString(4)

                val newCifra = Cifras(cifraId, cifraName, cifraTone, cifraSingerName, cifraChordsSequence)
                returnMutableList.add(newCifra)
            } while (cursor.moveToNext())
        } else {
            return mutableListOf()
        }

        cursor.close()
        database.close()
        return returnMutableList
    }

    fun deleteOneNote(noteModel: Notes): Boolean {
        val database = this.writableDatabase
        val queryString = "DELETE FROM $NOTES_TABLE WHERE $NOTE_ID_COLUMN = ${noteModel.id}"

        val cursor = database.rawQuery(queryString, null)
        return if (!cursor.moveToFirst()) {
            cursor.close()
            database.close()
            true
        } else {
            cursor.close()
            database.close()
            false
        }
    }

    fun deleteOneCifra(cifraModel: Cifras): Boolean {
        val database = this.writableDatabase
        val queryString = "DELETE FROM $CIFRAS_TABLE WHERE $CIFRA_ID_COLUMN = ${cifraModel.id}"

        val cursor = database.rawQuery(queryString, null)
        return if (!cursor.moveToFirst()) {
            cursor.close()
            database.close()
            true
        } else {
            cursor.close()
            database.close()
            false
        }
    }

    fun updateOneNote(noteModel: Notes): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NOTE_TITLE_COLUMN, noteModel.noteTitle)
        contentValues.put(NOTE_BODY_COLUMN, noteModel.noteBody)

        return database.update(NOTES_TABLE, contentValues, "${noteModel.id}", null)
    }

    fun updateOneCifra(cifraModel: Cifras): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(CIFRA_NAME_COLUMN, cifraModel.name)
        contentValues.put(CIFRA_TONE_COLUMN, cifraModel.tone)
        contentValues.put(CIFRA_SINGER_NAME_COLUMN, cifraModel.singerName)
        contentValues.put(CIFRA_CHORDS_SEQUENCE_COLUMN, cifraModel.chordsSequence)

        return database.update(CIFRAS_TABLE, contentValues, "${cifraModel.id}", null)
    }

}
