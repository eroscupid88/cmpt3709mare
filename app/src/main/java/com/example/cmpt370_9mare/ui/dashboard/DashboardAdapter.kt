package com.example.cmpt370_9mare.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.databinding.GroupEventsViewBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */
class DashboardAdapter :
    ListAdapter<DashboardGroupEvents, DashboardAdapter.DashboardViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DashboardGroupEvents>() {
            override fun areItemsTheSame(
                oldItem: DashboardGroupEvents,
                newItem: DashboardGroupEvents
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DashboardGroupEvents,
                newItem: DashboardGroupEvents
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder(
            GroupEventsViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    /**
     * ViewHolder to hold data of DashboardFragment
     */
    class DashboardViewHolder(private var binding: GroupEventsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views on DashboardFragment to the events' data
         */
        fun bind(group: DashboardGroupEvents) {
            val groupEventSAdapter = GroupEventsAdapter {
                val action =
                    DashboardFragmentDirections.actionNavigationDashboardToCreateEventFragment(
                        it.id
                    )
                Navigation.findNavController(itemView).navigate(action)
            }

            binding.dateOfGroup.text = group.date
            binding.groupList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = groupEventSAdapter
            }

            group.events.observe(itemView.context as LifecycleOwner) {
                groupEventSAdapter.submitList(it)
            }
        }
    }
}