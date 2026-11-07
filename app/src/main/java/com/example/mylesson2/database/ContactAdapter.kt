package com.example.mylesson2.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mylesson2.databinding.ItemContactLayoutBinding

class MyDiff() : DiffUtil.ItemCallback<ContactModel>() {
    override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem == newItem
    }

}

class ContactAdapter() :
    ListAdapter<ContactModel, ContactAdapter.ContactViewHolder>(MyDiff()) {

    inner class ContactViewHolder(val binding: ItemContactLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(contactModel: ContactModel){
                binding.nameTv.text = contactModel.name
                binding.phonNumberTv.text = contactModel.phoneNumber
                binding.ageTv.text = contactModel.age.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}