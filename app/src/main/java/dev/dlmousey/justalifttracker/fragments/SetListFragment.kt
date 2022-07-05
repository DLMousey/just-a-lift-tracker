package dev.dlmousey.justalifttracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.dlmousey.justalifttracker.LiftTrackerApplication
import dev.dlmousey.justalifttracker.R
import dev.dlmousey.justalifttracker.database.models.Lift
import dev.dlmousey.justalifttracker.database.models.Set
import dev.dlmousey.justalifttracker.fragments.adapters.SetListAdapter
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModel
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModelFactory
import dev.dlmousey.justalifttracker.viewmodels.SetViewModel
import dev.dlmousey.justalifttracker.viewmodels.SetViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.util.*

class SetListFragment : Fragment() {

    private val setViewModel: SetViewModel by viewModels {
        SetViewModelFactory((activity?.application as LiftTrackerApplication).setRepository)
    }

    private val liftViewModel: LiftViewModel by viewModels {
        LiftViewModelFactory((activity?.application as LiftTrackerApplication).liftRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Sets List"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.set_list_recyclerview)
        val adapter = SetListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        setViewModel.allSets.observe(viewLifecycleOwner, Observer { sets ->
            sets?.let { adapter.submitList(it) }
        })

        val formFragment = CreateSetFragment()
        val fab = view.findViewById<Button>(R.id.set_list_add_set_button)
        fab.setOnClickListener {
            formFragment.show(childFragmentManager, "TAG")
            formFragment.setFragmentResultListener("newSet") { requestKey, bundle ->
                val reps: Int = bundle.getInt("reps")
                val weight: Int = bundle.getInt("weight")
                val rpe: Int = bundle.getInt("rpe")
                val setNumber: Int = bundle.getInt("setNumber")
                val timestamp: Long = bundle.getLong("timestamp")
                val liftId: Long = bundle.getLong("liftId")

                lifecycleScope.launch {
                    val altLift = liftViewModel.allLifts.asFlow().first().first { lift -> lift.liftId.equals(liftId) }
                    val set = Set(0, reps, weight, rpe, setNumber, timestamp, altLift)
                    setViewModel.insert(set)
                }
            }
        }

        return view
    }
}