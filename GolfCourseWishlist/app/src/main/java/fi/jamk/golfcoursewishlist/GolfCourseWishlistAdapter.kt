package fi.jamk.golfcoursewishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_places.view.*

class GolfCourseWishlistAdapter(private val places: ArrayList<Place>)
    : RecyclerView.Adapter<GolfCourseWishlistAdapter.ViewHolder>() {

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.placeName
        val imageView: ImageView = view.placeImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater =LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_places,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = places.size

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // place to bind UI
        val place: Place =places.get(position)
        //name
        holder.nameTextView.text=place.name
        //image
        Glide.with(holder.imageView.context).load(place.getImageResourceId(holder.imageView.context)).into(holder.imageView)
    }

}
