package fi.jamk.addmynotes

import android.app.SearchManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast

import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.*
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.row.view.tv_title

class MainActivity : AppCompatActivity() {
   // var listNotes=ArrayList<Note>()
    //var displayList=ArrayList<Note>()

    var listNotes:MutableList<Note> =ArrayList()
    var displayList:MutableList<Note> =ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dbManager =DataBaseHandler(this)
        var data = dbManager.readData()

        for (i in 0..(data.size-1)){
            var myid=data.get(i).id.toInt()
            var t=data.get(i).title
            var det=data.get(i).details
            listNotes.add(Note(myid,t,det))
        }

        listNotes.add(Note(1,"Title 1","Details 1"))
        listNotes.add(Note(2,"Title 2","Details 2 "))
        listNotes.add(Note(3,"Title 3","Details 3"))
        /*    loadquery    */

        displayList.addAll(listNotes)
        var myNotesAdapter=MyNotesAdapter(this, displayList as ArrayList<Note>)
        noteListV.adapter=myNotesAdapter


        //number of tasks from Listview
        val total = noteListV.count
        //actionbar
        val mActionBar =supportActionBar
        if(mActionBar!=null){
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle="You have"+total+" note(s) in list.."
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchItem = menu.findItem(R.id.app_bar_search)

        if (searchItem!=null){
            val searchView =searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()){
                        displayList.clear()
                        val search =newText.toLowerCase()
                       listNotes.forEach {
                           if (it.toString().toLowerCase().contains(search)){
                               displayList.add(it)
                           }

                       }

                    }else{
                        displayList.clear()
                        displayList.addAll(listNotes)

                        noteListV
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.addNote->{
                    startActivity(Intent(this,AddNoteActivity::class.java))
                }
                R.id.action_settings->{
                    Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    inner class MyNotesAdapter : BaseAdapter{
        var listNotesAdapter = ArrayList<Note>()
        var context: Context?=null
        constructor(context: Context,listNotesAdapter:ArrayList<Note>):super(){
            this.listNotesAdapter=listNotesAdapter
            this.context=context
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.row,null)
            var myNote=listNotesAdapter[position]
            myView.tv_title.text=myNote.title
            myView.tv_details.text=myNote.details
            Log.d(myNote.title,myNote.details)

            return myView
        }


        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }


    }

}
