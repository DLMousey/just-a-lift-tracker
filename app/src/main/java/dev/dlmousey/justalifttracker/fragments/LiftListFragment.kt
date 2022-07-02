package dev.dlmousey.justalifttracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.dlmousey.justalifttracker.LiftTrackerApplication
import dev.dlmousey.justalifttracker.R
import dev.dlmousey.justalifttracker.database.models.Lift
import dev.dlmousey.justalifttracker.fragments.adapters.LiftListAdapter
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModel
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModelFactory

class LiftListFragment : Fragment(), LiftListAdapter.LiftListOnLongItemClick {

    private val liftViewModel: LiftViewModel by viewModels {
        LiftViewModelFactory((activity?.application as LiftTrackerApplication).liftRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Lifts List"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lift_list, container, false)
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.lift_list_recyclerview)
        val adapter = LiftListAdapter()
        adapter.registerLongClickListener(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        liftViewModel.allLifts.observe(viewLifecycleOwner, Observer { lifts ->
            lifts?.let { adapter.submitList(it) }
        })

        val formFragment = CreateLiftFragment()
        val fab = view.findViewById<Button>(R.id.lift_list_add_lift_button)
        fab.setOnClickListener {
            formFragment.show(childFragmentManager, "TAG")
            formFragment.setFragmentResultListener("newLift") { requestKey, bundle ->
                val name = bundle.getString("name")
                val type = bundle.getString("type")
                val lift = Lift(0, name!!, type!!)
                liftViewModel.insert(lift)
            }
        }

        return view
    }

    override fun doEvent(event: Lift) {
        var formFragment = CreateLiftFragment.newInstance(event.name, event.type)
        formFragment.show(childFragmentManager, "TAG")
        formFragment.setFragmentResultListener("newLift") { requestKey, bundle ->
            val name = bundle.getString("name")
            val type = bundle.getString("type")
            Log.d("LiftListItemFragmentDOEVENT", "doEvent: received new data for lift: ${event.liftId}, now called $name of type $type")
        }
    }


}