package hu.bme.aut.android.sleephabitenhancer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.sleephabitenhancer.R
import hu.bme.aut.android.sleephabitenhancer.databinding.AlarmListItemBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm

class AlarmRecyclerViewAdapter :
    ListAdapter<Alarm, AlarmRecyclerViewAdapter.AlarmViewHolder>(itemCallback) {

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                // TODO: list items are not always updated, this method is called with both the parameters
                //  being the updated objects
                Log.d(
                    "Comp: ",
                    "Comparing ${oldItem}\tto\t${newItem} - result: ${oldItem == newItem}"
                )
                return oldItem == newItem
            }
        }
    }

    var itemClickListener: AlarmItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlarmViewHolder(
        AlarmListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarmItem = this.getItem(position)
        holder.alarm = alarmItem

        holder.binding.tvName.text = alarmItem.name
        holder.binding.tvSleepDuration.text =
            holder.itemView.context.getString(
                R.string.sleep_duration,
                alarmItem.alarmDue - alarmItem.reminderDue
            )
        holder.binding.swAlarmEnabled.isChecked = alarmItem.active
        holder.binding.bedtimeWakeupTimes.tvBedtime.text = alarmItem.reminderDue.toString()
        holder.binding.bedtimeWakeupTimes.tvWakeupTime.text = alarmItem.alarmDue.toString()
    }


    inner class AlarmViewHolder(val binding: AlarmListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var alarm: Alarm? = null

        init {
            itemView.setOnClickListener {
                alarm?.let { alarm ->
                    itemClickListener?.onItemClick(alarm)
                }
            }

            itemView.setOnLongClickListener { view ->
                alarm?.let { itemClickListener?.onItemLongClick(adapterPosition, view, it) }
                true
            }

            binding.swAlarmEnabled.setOnClickListener {
                alarm?.let { alarm ->
                    itemClickListener?.onAlarmActiveStateChange(alarm)
                }
            }
        }
    }

    interface AlarmItemClickListener {
        fun onItemClick(alarm: Alarm)
        fun onItemLongClick(position: Int, view: View, alarm: Alarm): Boolean
        fun onAlarmActiveStateChange(alarm: Alarm)
    }
}