import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeproj.ItemsViewModel
import com.example.pokeproj.PokeInfo
import com.example.pokeproj.R

class CustomAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        val imageURL = ItemsViewModel.image

        // Use Glide to load the image into the ImageView
        Glide.with(holder.imageView.context)
            .load(imageURL)
            .into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text

        // Set an OnClickListener to the entire view or any part of it you want to be clickable.
        holder.itemView.setOnClickListener {
            // Create an intent to open the second activity
            val intent = Intent(holder.itemView.context, PokeInfo::class.java)

            intent.putExtra("PokeId", position) // A mettre l'id du pokemon
            // Start the new activity
            holder.itemView.context.startActivity(intent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
