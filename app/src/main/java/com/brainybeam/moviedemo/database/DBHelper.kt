package com.brainybeam.moviedemo.database

/**
 * Created by BrainyBeam on 07-Jan-19.
 * @author BrainyBeam
 *
 * This class handles all SQLite database operations.
 */

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import com.brainybeam.moviedemo.models.SearchData

class DBHelper(
    context: Context, name: String?,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {

    /**
     * Define static variables.
     */
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "movieSearch.db"
        private const val TABLE_SEARCH = "SearchTable"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    /**
     * This method is called when database is created in phone memory.
     * Creates database tables.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SEARCH_TABLE = ("CREATE TABLE " +
                TABLE_SEARCH + "("
                + COLUMN_TITLE + " TEXT," + COLUMN_TIMESTAMP + " INTEGER" + ")")
        db.execSQL(CREATE_SEARCH_TABLE)
    }

    /**
     * This method is called when database version upgrades.
     * If database version is upgraded, delete existing database tables and create new database tables.
     *
     * Upgrade database version if database structure is changed in new version of App.
     */
    override fun onUpgrade(
        db: SQLiteDatabase, oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL(
            "DROP TABLE IF EXISTS "
                    + TABLE_SEARCH
        )
        onCreate(db)
    }

    /**
     * Insert a new entry in Search table.
     */
    fun addSearchHistory(searchData: SearchData) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, searchData.title)
        values.put(COLUMN_TIMESTAMP, searchData.timestamp)

        insertOrUpdateSearch(searchData.title, values)
    }

    /**
     * Delete a row in Search table.
     */
    fun deleteSearchHistory(title: String) {
        val db = this.writableDatabase
        db.delete(TABLE_SEARCH, "$COLUMN_TITLE='$title'", null)
    }

    /**
     * This method insert or update row in Search Table.
     * If search keyword already exist in Search table then entry will be updated.
     * If search keyword doesn't exist, new entry will be created.
     */
    private fun insertOrUpdateSearch(title: String, values: ContentValues) {
        val db = this.writableDatabase
        val searchData: SearchData? = findHistory(title)

        if(searchData==null){
            db.insert(TABLE_SEARCH, null, values)
        }else{
            db.update(TABLE_SEARCH, values, "$COLUMN_TITLE='$title'", null)
        }
    }

    /**
     * Retrieve particular keyword entry from Search table.
     */
    private fun findHistory(title: String): SearchData? {
        val query =
            "SELECT * FROM $TABLE_SEARCH WHERE $COLUMN_TITLE=\'$title'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var searchData: SearchData? = null
        if (cursor.count > 0) {
            cursor.moveToFirst()
            searchData = SearchData()
            searchData.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            searchData.timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP))

            cursor.close()
        }
        return searchData
    }

    /**
     * Retrieve recent limited(10) entries from Search table.
     */
    fun getSearchHistory(limit: Int): ArrayList<SearchData> {
        val query =
            "SELECT * FROM $TABLE_SEARCH ORDER BY $COLUMN_TIMESTAMP DESC LIMIT $limit"
        val searchList = arrayListOf<SearchData>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor!=null && cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                val searchData = SearchData()
                searchData.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                searchData.timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP))

                searchList.add(searchData)
            }
            cursor.close()
        }
        return searchList
    }
}
