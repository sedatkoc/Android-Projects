package fi.jamk.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use LinearManager as a layout manager for recyclerView
        recyclerView.layoutManager=LinearLayoutManager(this)

        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here , https://ptm.fi/data/android_
        val url = "https://ptm.fi/data/android_employees.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            // Get employees from JSON
            response ->
            val employees = response.getJSONArray("employees")
            // Create Employees Adapter with employees data
            recyclerView.adapter= EmployeesAdapter(employees)
        },
                Response.ErrorListener { error ->
                    Log.d("JSON",error.toString())
        })
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

}
