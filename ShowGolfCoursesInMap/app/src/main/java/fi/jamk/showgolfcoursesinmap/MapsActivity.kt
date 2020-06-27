package fi.jamk.showgolfcoursesinmap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.custom_info_window.view.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    internal inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        private val contents: View = layoutInflater.inflate(R.layout.custom_info_window, null)
        override fun getInfoContents(marker: Marker): View {
            val titleTextView = contents.titleTextView
            val addressTextView = contents.addressTextView
            val phoneTextView = contents.phoneTextView
            val emailTextView = contents.emailTextView
            val webTextView = contents.webTextView

          titleTextView.text = marker.title.toString()
            // get data from Tag list
            if (marker.tag is List<*>){
                val list =marker.tag as List<*>
                addressTextView.text=list[0].toString()
                phoneTextView.text=list[1].toString()
                emailTextView.text=list[2].toString()
                webTextView.text=list[3].toString()
            }
            return contents
        }
        override fun getInfoWindow(marker: Marker?): View? {
            return null
        }
    }
    private val TAG : String?=null
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        
        loadJSonInfo()

    }
    private fun loadJSonInfo(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://ptm.fi/materials/golfcourses/golf_courses.json"

        var courseTypes = mapOf(
                "?" to BitmapDescriptorFactory.HUE_VIOLET,
                "Etu" to BitmapDescriptorFactory.HUE_BLUE,
                "Kulta" to BitmapDescriptorFactory.HUE_GREEN,
                "Kulta/Etu" to BitmapDescriptorFactory.HUE_YELLOW
        )
        // Create request and listeners
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    // Get courses from JSON
                    val golfCourses = response.getJSONArray("courses")
                    Log.d("JSON",golfCourses.toString())
                    for (i in 0 until golfCourses.length()) {
                        // get course data
                        val course = golfCourses.getJSONObject(i)
                        val lat = course["lat"].toString().toDouble()
                        val lng = course["lng"].toString().toDouble()
                        val coord = LatLng(lat, lng)
                        val type = course["type"].toString()
                        val title = course["course"].toString()
                        val address = course["address"].toString()
                        val phone = course["phone"].toString()
                        val email = course["email"].toString()
                        val web_url = course["web"].toString()

                        val list = listOf(address,phone,email,web_url)

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(65.5, 26.0),5.0F))
                        if (courseTypes.containsKey(type)){
                            var m =mMap.addMarker(
                                    MarkerOptions().position(coord).title(title).icon(BitmapDescriptorFactory.defaultMarker(courseTypes.getOrDefault(type,BitmapDescriptorFactory.HUE_RED)))

                            )
                            m.setTag(list)

                        }

                    }
                },
                Response.ErrorListener { error ->
                    Log.d("JSON",error.toString())
                }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

    }
}
