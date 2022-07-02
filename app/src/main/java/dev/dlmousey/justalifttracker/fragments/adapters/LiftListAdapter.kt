package dev.dlmousey.justalifttracker.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dlmousey.justalifttracker.R
import dev.dlmousey.justalifttracker.database.LiftTrackerDatabase
import dev.dlmousey.justalifttracker.database.models.Lift

class LiftListAdapter : ListAdapter<Lift, LiftListAdapter.LiftViewHolder>(LiftComparator()) {

    lateinit var longClickListener: LiftListOnLongItemClick

    interface LiftListOnLongItemClick {
        fun doEvent(event: Lift)
    }

    lateinit var database: LiftTrackerDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiftViewHolder {
        return LiftViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LiftViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, longClickListener)
    }

    fun registerLongClickListener(listener: LiftListOnLongItemClick) {
        longClickListener = listener
    }

    class LiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val liftNameView: TextView = itemView.findViewById(R.id.recyclerview_lift_list_nameTextview)

        fun bind(lift: Lift, longClickListener: LiftListOnLongItemClick) {
            liftNameView.text = lift.name

            val listener: View.OnLongClickListener = View.OnLongClickListener {
                longClickListener.doEvent(lift)
                return@OnLongClickListener true
            }

            itemView.setOnLongClickListener(listener)
        }

        companion object {
            fun create(parent: ViewGroup): LiftViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_lift_list_item, parent, false)

                return LiftViewHolder(view)
            }
        }
    }

    class LiftComparator : DiffUtil.ItemCallback<Lift>() {
        override fun areItemsTheSame(oldItem: Lift, newItem: Lift): Boolean {
            return oldItem.liftId == newItem.liftId
        }

        override fun areContentsTheSame(oldItem: Lift, newItem: Lift): Boolean {
            return oldItem.liftId == newItem.liftId && oldItem.name == newItem.name
        }
    }
}