package fi.jamk.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.employee_item.imageView
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

public class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        //get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle !=null){
            val employeeString = bundle!!.getString("employee")
            val employee =JSONObject(employeeString)
            val name =employee["firstName"].toString()+" "+employee["lastName"].toString()
            val otherInfo=employee["email"].toString()+" \n"+employee["phone"].toString() +
            employee["phone"].toString()+"\n"+employee["title"].toString()+"\n"+ employee["department"].toString()

            val imageText= employee["image"]

            GetImageAsyncTask(this).execute(imageText.toString())
            textView.text=name+"\n"+otherInfo
            editText.append("Lorem Ipsum is simply dummy text of the printing " +
                    "and typesetting industry. Lorem Ipsum has been the industry's standard " +
                    "dummy text ever since the 1500s, when an unknown printer took a galley of type " +
                    "and scrambled it to make a type specimen book. It has survived not only five centuries, " +
                    "but also the leap into electronic typesetting, remaining essentially unchanged. ")
            // modify here to display other employee's data too!
            Toast.makeText(this, "Name is $name --> $imageText", Toast.LENGTH_LONG).show()
        }
        // onPostExecute will be called after doInBackground is finished - UI can be modified

    }
}
public class GetImageAsyncTask internal constructor(context: EmployeeActivity)
    : AsyncTask<String, Void, Bitmap?>() {

    // weak reference to the activity
    private val activityReference: WeakReference<EmployeeActivity> = WeakReference(context)

    // AsyncTask own thread
    override fun doInBackground(vararg urls: String?): Bitmap? {
        return try {
            // create a connection
            val connection = URL(urls[0]).openConnection() as HttpURLConnection
            // load bitmap from above connection
            BitmapFactory.decodeStream(connection.inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    override fun onPostExecute(bitmap: Bitmap?) {
        super.onPostExecute(bitmap)

        // get a reference to the activity if it is still there
        val activity = activityReference.get()
        // if activity is destroyed or are finishing, just return (image cannot be displayed)
        if (activity == null || activity.isFinishing) return
        // Activity is still active, we can show the loaded bitmap
        if (bitmap != null) {
            activity.imageView.setImageBitmap(bitmap)
            Toast.makeText(
                activity, "Image loaded from the URL with AsyncTask",
                Toast.LENGTH_LONG
            ).show()
        }
    }}