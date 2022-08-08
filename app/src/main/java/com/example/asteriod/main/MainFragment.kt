package com.example.asteriod.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteriod.models.Asteroid
import com.example.asteriod.R
import com.example.asteriod.database.AsteroidDatabase
import com.example.asteriod.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import java.net.URI

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

//    by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewModel: MainViewModel = MainViewModel(this.activity!!.application)
        var adapter = AsteroidListAdapter(emptyList())

    viewModel.asteroids.observe(this.viewLifecycleOwner, Observer {
            adapter.items = it
            binding.progressBarViews.isVisible = false
        })
        viewModel.imageOfDay.observe(this.viewLifecycleOwner, Observer {
            binding.activityMainImageOfTheDay.isVisible = true
            binding.progressBarImage.isVisible = false
            Picasso.with(this.activity).load(Uri.parse(it.url)).into(binding.activityMainImageOfTheDay)
            binding.title.text = it.title
        })
        binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        binding.asteroidRecycler.adapter = adapter
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(this.activity)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

}
