package dev.dlmousey.justalifttracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dev.dlmousey.justalifttracker.LiftTrackerApplication
import dev.dlmousey.justalifttracker.R
import dev.dlmousey.justalifttracker.viewmodels.SetViewModel
import dev.dlmousey.justalifttracker.viewmodels.SetViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [SetListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetListFragment : Fragment() {

    private val setViewModel: SetViewModel by viewModels {
        SetViewModelFactory((activity?.application as LiftTrackerApplication).setRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Sets List"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_list, container, false)
    }
}