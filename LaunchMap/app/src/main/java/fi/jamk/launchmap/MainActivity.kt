package fi.jamk.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun showMap(v:View){
        val lat =edtLat.text.toString().toDouble()
        val lng=edtLong.text.toString().toDouble()

        val location = Uri.parse("geo:$lat,$lng")
        val mapIntent =Intent(Intent.ACTION_VIEW,location)

        val activities: List<ResolveInfo> =packageManager.queryIntentActivities(mapIntent,0)
        val isIntentSafe: Boolean =activities.isNotEmpty()

        if (isIntentSafe){
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
        }
    }
}
