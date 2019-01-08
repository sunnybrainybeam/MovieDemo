package com.brainybeam.moviedemo.database

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

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "movieSearch.db"
        private const val TABLE_SEARCH = "SearchTable"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SEARCH_TABLE = ("CREATE TABLE " +
                TABLE_SEARCH + "("
                + COLUMN_TITLE + " TEXT," + COLUMN_TIMESTAMP + " INTEGER" + ")")
        db.execSQL(CREATE_SEARCH_TABLE)
    }

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

    fun addSearchHistory(searchData: SearchData) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, searchData.title)
        values.put(COLUMN_TIMESTAMP, searchData.timestamp)

        insertOrUpdateSearch(searchData.title, values);
    }

    private fun insertOrUpdateSearch(title: String, values: ContentValues) {
        val db = this.writableDatabase
        var searchData: SearchData? = findHistory(title)

        if(searchData==null){
            db.insert(TABLE_SEARCH, null, values)
        }else{
            db.update(TABLE_SEARCH, values, "$COLUMN_TITLE='$title'", null)
        }
        db.close()
    }

    private fun findHistory(title: String): SearchData? {
        val query =
            "SELECT * FROM $TABLE_SEARCH WHERE $COLUMN_TITLE =  \"$title\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var searchData: SearchData? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            searchData?.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            searchData?.timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP))

            cursor.close()
        }

        db.close()
        return searchData
    }

    fun getSearchHistory(limit: Int): ArrayList<SearchData> {
        val query =
            "SELECT * FROM $TABLE_SEARCH ORDER BY $COLUMN_TIMESTAMP DESC LIMIT $limit"

        val searchList = arrayListOf<SearchData>()

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor!=null && cursor.count > 0) {
            for (i in 0 until cursor.count-1) {
                cursor.moveToPosition(i);

                var searchData = SearchData()
                searchData?.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                searchData?.timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP))

                searchList.add(searchData)
            }
            cursor.close()
        }

        db.close()
        return searchList
    }

}
