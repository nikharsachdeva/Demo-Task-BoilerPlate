package com.demo.app.adapter.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.databinding.RowItemNotificationBinding
import com.demo.app.model.MusicModel
import com.demo.app.utils.loadImageWithGlide
class NotificationAdapter(val onClickListener: OnClickInterface) :
    ListAdapter<MusicModel.Data, NotificationAdapter.NotesViewHolder>(
        ComparatorDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            RowItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val customer = getItem(position)
        customer?.let {
            holder.bind(it, position)
        }

    }

    inner class NotesViewHolder(private val binding: RowItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: MusicModel.Data, position: Int) {

            binding.musicName.text = notification.title.toString()
            binding.musicImg.loadImageWithGlide(notification.imageFile?:"")
            binding.musicPaid.text=notification.paidFree

            binding.musicRootRl.setOnClickListener {
                notifyItemChanged(position)
                onClickListener.onNotificationClicked(notification)
            }

        }
    }

    interface OnClickInterface {
        fun onNotificationClicked(doc: MusicModel.Data)
    }


    class ComparatorDiffUtil : DiffUtil.ItemCallback<MusicModel.Data>() {
        override fun areItemsTheSame(
            oldItem: MusicModel.Data,
            newItem: MusicModel.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MusicModel.Data,
            newItem: MusicModel.Data
        ): Boolean {
            return oldItem == newItem
        }

    }
}