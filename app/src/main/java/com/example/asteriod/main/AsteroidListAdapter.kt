package com.example.asteriod.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asteriod.models.Asteroid
import com.example.asteriod.R
import com.example.asteriod.bindAsteroidStatusImage
import com.example.asteriod.detail.DetailFragment

class AsteroidListAdapter(data:List<Asteroid> ) : RecyclerView.Adapter<AsteroidListAdapter.AsteroidViewHolder>() {

    var items = listOf<Asteroid>()
        set (value){
            field = value
            notifyDataSetChanged()
        }

    init {
        items = data
    }

    class AsteroidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(asteroid: Asteroid){
            bindAsteroidStatusImage(itemView.findViewById(R.id.Rate_image),asteroid.isPotentiallyHazardous)
            val title : TextView = itemView.findViewById(R.id.Title)
            val date : TextView = itemView.findViewById(R.id.Date)
            title.text = asteroid.codename
            date.text = asteroid.closeApproachDate
            val item:LinearLayout = itemView.findViewById(R.id.item_view)
            item.setOnClickListener(View.OnClickListener {
                itemView.findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(asteroid))
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.i_asteroid_item, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int { return items.size }
}