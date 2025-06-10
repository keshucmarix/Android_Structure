package com.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.data.model.User
import com.app.databinding.ItemLayoutBinding
import com.bumptech.glide.Glide

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var users: MutableList<User> = mutableListOf()

    inner class ViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        users[position].apply {
            holder.binding.textViewUserName.text = name
            holder.binding.textViewUserEmail.text = email
            Glide.with(holder.binding.root.context)
                .load(avatar)
                .into(holder.binding.imageViewAvatar)
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun addData(users: MutableList<User>) {
        this.users = users
        notifyItemRangeChanged(0, users.size.minus(1))
    }

}