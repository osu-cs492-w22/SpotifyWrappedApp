package com.example.spotifywrapped.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifywrapped.R
import com.example.spotifywrapped.data.SpotifyItem
import com.example.spotifywrapped.data.SpotifyItems
import com.example.spotifywrapped.data.SpotifyResultsRepository


class SpotifyAdapter(private val onResultItemClicked: (SpotifyItem) -> Unit)
    : RecyclerView.Adapter<SpotifyAdapter.ViewHolder>() {

    var resultList = listOf<SpotifyItem>()

    fun updateResultList(newResultList: List<SpotifyItem>?) {
        Log.d("Spotify", "$newResultList")
        resultList = newResultList ?: listOf()

        notifyDataSetChanged()
    }

    override fun getItemCount() = resultList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spotify_result_item, parent, false)
        return ViewHolder(view, onResultItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resultList[position])
    }

    class ViewHolder(view: View, val onResultItemClicked: (SpotifyItem) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private var currentForecastPeriod: SpotifyItem? = null
        private val nameTV: TextView = itemView.findViewById(R.id.tv_item_name)
        private val iconIV: ImageView = itemView.findViewById(R.id.spotify_icon)

        val ctx = itemView.context

        init {
            itemView.setOnClickListener {
                currentForecastPeriod?.let(onResultItemClicked)
            }
        }

        fun bind(spotifyItem: SpotifyItem) {
            nameTV.text = ctx.getString(R.string.result_name, spotifyItem.name)


            if (spotifyItem.images != null) {
                val url = spotifyItem.images[1].url
                Log.d("Spotify", "$url")

                Glide.with(ctx)
                    .load(url)
                    .dontAnimate()
                    .placeholder(R.drawable.profile_icon)
                    .error(R.drawable.profile_icon)
                    .into(iconIV)

            }
        }
    }
}