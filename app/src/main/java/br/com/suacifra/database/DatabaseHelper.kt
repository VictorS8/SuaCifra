package br.com.suacifra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.suacifra.R
import br.com.suacifra.models.Notes

class DatabaseHelper(
    context: Context
) : SQLiteOpenHelper(context, context.getString(R.string.sqlite_database_name), null, 1) {

    companion object {
        const val NOTES_TABLE = "NOTES_TABLE"
        const val COLUMN_NOTE_TITLE = "NOTE_TITLE"
        const val COLUMN_NOTE_BODY = "NOTE_BODY"
        const val COLUMN_ID = "ID"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement =
            "CREATE TABLE $NOTES_TABLE ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NOTE_TITLE TEXT, $COLUMN_NOTE_BODY TEXT)"

        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addOneNote(noteModel: Notes): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_NOTE_TITLE, noteModel.noteTitle)
        contentValues.put(COLUMN_NOTE_BODY, noteModel.noteBody)

        val insert = database.insert(NOTES_TABLE, null, contentValues)
        val insertErrorNumber: Long = -1
        return insert != insertErrorNumber
    }

    fun getAllNotes() : MutableList<Notes> {
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

}