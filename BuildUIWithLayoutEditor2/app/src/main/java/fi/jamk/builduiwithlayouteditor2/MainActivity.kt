package fi.jamk.builduiwithlayouteditor2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val firstnames = arrayListOf("Reneto","Rosangela","Tim","Bartol","Jeannette");
    val lastnames = arrayOf("Ksenia", "Metzli", "Asuncion", "Zemfina", "Giang")
    val jobtitles = arrayOf("District Quality Coordinator","International Intranet Representative","District Intranet Administrator","Dynamic Research Manager","Central Infrastructure Consultant")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showEmployeeData(index=0);
    }
    fun showEmployeeData(index:Int){
        firstnameTextView.text=firstnames[index]
        lastnameTextView.text=lastnames[index]
        jobtitleTextView.text=jobtitles[index]
        Employee_info.text=lastnames[index] + " " + firstnames[index] + " is ...." + getString(R.string.basic_text)

        var id=0
        if (index==0) id=R.drawable.employee1
        else if(index==1) id=R.drawable.employee2
        else if(index==2) id=R.drawable.employee3
        else if(index==3) id=R.drawable.employee4
        else if(index==4) id=R.drawable.employee5
        imageView.setImageResource(id)

    }

    fun numberClicked(view:View){
        val text = (view as TextView).text.toString()
        val int =text.toInt()-1
        showEmployeeData(int)


    }
}
