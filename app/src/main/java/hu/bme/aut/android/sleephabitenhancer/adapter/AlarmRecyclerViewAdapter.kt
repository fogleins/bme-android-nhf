package hu.bme.aut.android.sleephabitenhancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.sleephabitenhancer.R
import hu.bme.aut.android.sleephabitenhancer.databinding.AlarmListItemBinding
import hu.bme.aut.android.sleephabitenhancer.model.Alarm
import kotlin.random.Random

class AlarmRecyclerViewAdapter :
    ListAdapter<Alarm, AlarmRecyclerViewAdapter.AlarmViewHolder>(itemCallback) {

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
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
        // TODO: sleep duration
        holder.binding.tvSleepDuration.text =
            holder.itemView.context.getString(R.string.sleep_duration, Random.nextInt(5, 12))
        // TODO: checkbox events
        holder.binding.swAlarmEnabled.isChecked = alarmItem.active
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

            binding.swAlarmEnabled.setOnClickListener {
                alarm?.let { alarm ->
                    itemClickListener?.onAlarmActiveStateChange(alarm)
                }
            }
        }
    }

    interface AlarmItemClickListener {
        fun onItemClick(alarm: Alarm)
        fun onAlarmActiveStateChange(alarm: Alarm)
    }
}