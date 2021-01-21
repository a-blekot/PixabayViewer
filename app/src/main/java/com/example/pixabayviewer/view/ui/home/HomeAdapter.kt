package com.example.pixabayviewer.view.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.databinding.HomeRowViewBinding

class HomeAdapter(private val listener: Listener) :
    RecyclerView.Adapter<HomeAdapter.HomeRowViewHolder>() {

    interface Listener {
        fun onSelected(id: Int)
    }

    var dataset = emptyList<Image>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRowViewHolder {
        val binding = HomeRowViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeRowViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: HomeRowViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class HomeRowViewHolder(
        private val binding: HomeRowViewBinding, private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.layout.setOnClickListener(this)
        }

        fun bind(data: Image) {

            with(binding) {
                Glide.with(context)
                    .load(data.previewURL)
                    .error(R.drawable.image_not_found)
                    .into(thumbnail)

                userName.text = data.userName
                layout.tag = data.id
            }
        }

        private val context: Context
            get() = binding.root.context

        override fun onClick(v: View) {
            listener.onSelected(binding.layout.tag as Int)
        }
    }
}