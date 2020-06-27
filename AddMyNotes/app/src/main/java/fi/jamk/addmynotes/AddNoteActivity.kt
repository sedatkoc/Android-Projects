package fi.jamk.addmynotes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    val dbTable ="Notes"
    var id =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        val context =this
       btn_insert.setOnClickListener({
           if(et_title.text.toString().length>0 && et_details.text.toString().length>0){
                var note=Note(et_title.text.toString(),et_details.text.toString())
               var db=DataBaseHandler(context)
               db.insertData(note)
           }else{
               Toast.makeText(context,"Please fill the all data",Toast.LENGTH_SHORT).show()
           }
       })
    }
    fun addFunc(view:View){
        var dbManager=DataBaseHandler(this)
        var values = ContentValues()
        values.put("Title",et_title.text.toString())
        values.put("Details",et_details.text.toString())

        if(id==0){
            val ID =dbManager.insert(values)
            if(ID>0){
                Toast.makeText(this,"Note is added", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Error adding note",Toast.LENGTH_SHORT).show()

            }
        }else{
            var selectionArgs = arrayOf(id.toString())
            val ID =dbManager.update(values,"ID=?",selectionArgs)
            if(ID>0){
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Error adding note",Toast.LENGTH_SHORT).show()

            }
        }
    }
}
