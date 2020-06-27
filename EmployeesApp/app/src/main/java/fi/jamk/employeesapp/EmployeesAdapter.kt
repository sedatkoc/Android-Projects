package fi.jamk.employeesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.employee_item.view.*
import org.json.JSONArray
import org.json.JSONObject

// Employees Adapter, used in RecyclerView in MainActivity
class EmployeesAdapter(private val employees: JSONArray) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    // Create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        // add other Views here: title, email, ...
        val titleTextView: TextView = view.titleTextView
        val emailTextView: TextView = view.emailTextView
        val phoneTextView: TextView = view.phoneTextView
        val departmentTextView: TextView = view.departmentTextView
        val imageView : ImageView = view.imageView

        init {
            itemView.setOnClickListener{
                // create an explicit intent
                val intent =Intent(view.context, EmployeeActivity::class.java)
                // add selected employee JSON as a string data
                intent.putExtra("employee", employees[adapterPosition].toString())
                // start a new activity
                view.context.startActivity(intent)
              /*  Toast.makeText(
                    view.context,
                    "Employee name is ${nameTextView.text}, adapter position = $adapterPosition",
                    Toast.LENGTH_LONG
                ).show()

               */
            }
        }
    }

    // Return item count in employees
    override fun getItemCount(): Int = employees.length()

    // Bind data to UI View Holder
    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, position: Int) {
        // employee to bind UI
        val employee: JSONObject = employees.getJSONObject(position)
        // employee lastname and firstname
        holder.nameTextView.text = employee["lastName"].toString() + " " + employee["firstName"].toString()
        // title, email, phone, department, image
        holder.titleTextView.text =employee["title"].toString()
        holder.emailTextView.text=employee["email"].toString()
        holder.phoneTextView.text=employee["phone"].toString()
        holder.departmentTextView.text=employee["department"].toString()
        // to get context in Glide, you can use holder.imageView.context
        // place to bind UI

        Glide.with(holder.imageView.context).load(employee["image"].toString()).apply(RequestOptions.circleCropTransform()).into(holder.imageView)


    }


}