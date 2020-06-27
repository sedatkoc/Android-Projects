package fi.jamk.addmynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

var dbName="AddMyNotes"
var dbTable="Notes"
var colID="ID"
var colTitle="Title"
var colDetails="Details"
var sqlDB:SQLiteDatabase?=null
val createTable="CREATE TABLE "+ dbTable + " (" +
        colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        colTitle + " VARCHAR(256), "+
        colDetails + " TEXT);"
class DataBaseHandler (var context: Context): SQLiteOpenHelper(context, dbName,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
        Toast.makeText(this.context,"Database created..",Toast.LENGTH_SHORT)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun readData():MutableList<Note>{
        var list : MutableList<Note> =ArrayList()
        val db = this.readableDatabase
        val query ="Select * from "+ dbTable
        val result = db.rawQuery(query,null)
        if (result.moveToFirst()){
            do {
                var note =Note()
                    note.id=result.getString(0).toInt()
                    note.title = result.getString(1).toString()
                    note.details = result.getString(2).toString()
                list.add(note)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun insertData(note:Note){
        val db =this.writableDatabase
        var cv = ContentValues()
        cv.put(colTitle,note.title)
        cv.put(colDetails,note.details)
        var result=db.insert(dbTable,null,cv)
        if(result==-1.toLong()){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
        }
    }
    fun insert(values:ContentValues):Long{
        val ID = sqlDB!!.insert(dbTable,"",values)
        return ID
    }
    fun delete(selection:String, selectionArgs:Array<String>):Int{
        val count = sqlDB!!.delete(dbTable,selection,selectionArgs)
        return count
    }
    fun update(values:ContentValues,selection:String, selectionArgs:Array<String>):Int{
        val count =sqlDB!!.update(dbTable,values,selection,selectionArgs)
        return count
    }
}