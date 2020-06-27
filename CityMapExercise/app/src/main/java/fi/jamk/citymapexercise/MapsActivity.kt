package fi.jamk.citymapexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(marker: Marker?): Boolean {
        Toast.makeText(this,marker!!.title, Toast.LENGTH_LONG).show()
        return true
    }

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
     * we just add a marker near Istanbul, Turkey.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in kongsberg and move the camera
        val kongsberg = LatLng(59.6689, 9.6502)
        mMap.addMarker(MarkerOptions().position(kongsberg).title("Marker in Kongsberg"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kongsberg,14.0F))
        mMap.uiSettings.isZoomControlsEnabled=true

        val istanbul = LatLng(41.0082,28.9784)
        mMap.addMarker(MarkerOptions().position(istanbul).title("Marker in Istanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istanbul,10F))
        mMap.uiSettings.isZoomControlsEnabled=true

        val ct = LatLng(-33.9249,18.4241)
        mMap.addMarker(MarkerOptions().position(ct).title("Marker in Cape Town"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ct,14F))
        mMap.uiSettings.isZoomControlsEnabled=true

        val venice = LatLng(45.4408,12.3155)
        mMap.addMarker(MarkerOptions().position(venice).title("Marker in Venice"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venice,14F))
        mMap.uiSettings.isZoomControlsEnabled=true

        val helsinki = LatLng(60.1699,24.9384)
        mMap.addMarker(MarkerOptions().position(helsinki).title("Marker in Helsinki"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(helsinki,14F))
        mMap.uiSettings.isZoomControlsEnabled=true
        //click listener
        mMap.setOnMarkerClickListener(this)
    }
}
