package dev.dlmousey.justalifttracker.fragments.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dlmousey.justalifttracker.LiftTrackerApplication
import dev.dlmousey.justalifttracker.R
import dev.dlmousey.justalifttracker.database.models.Set
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModel
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModelFactory
import java.util.*

class SetListAdapter : ListAdapter<Set, SetListAdapter.SetViewHolder>(SetComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    fun buildGrouping(list: List<Set>) {
        var groupedList = mutableMapOf<Long, List<Set>>()

        list.forEach {
            groupedList.put(it.timestamp, listOf(it))
        }

        Log.d("REBIND", "rebind: ${groupedList.count()}")
    }

    class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val liftNameView: TextView = itemView.findViewById(R.id.recyclerview_set_list_liftNameTextview)
        private val setCountView: TextView = itemView.findViewById(R.id.recyclerview_set_list_setCountTextview)

        fun bind(set: Set) {
//            liftNameView.text = set.lift.name
//            setCountView.text = "1" // @todo - figure out how to find all sets matching this lift and pass it in
            // This will likely have to be an adapter for a list of Sets rather than a single set at a time,
            // bit tricky, might have to do some grouping magic on the view
        }

        companion object {
            fun create(parent: ViewGroup): SetViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_set_list_item, parent, false)

                return SetViewHolder(view)
            }
        }
    }

    class SetComparator : DiffUtil.ItemCallback<Set>() {
        override fun areItemsTheSame(oldItem: Set, newItem: Set): Boolean {
            return oldItem.setId == newItem.setId
        }

        override fun areContentsTheSame(oldItem: Set, newItem: Set): Boolean {
            return oldItem.setId == newItem.setId && oldItem.setNumber == newItem.setNumber
        }
    }
}