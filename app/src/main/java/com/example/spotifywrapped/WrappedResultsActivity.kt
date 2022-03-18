package com.example.spotifywrapped
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.se.omapi.Session
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.spotifywrapped.data.SpotifyItem
import com.example.spotifywrapped.data.SpotifyItems
import com.example.spotifywrapped.ui.SpotifyAdapter
import com.example.spotifywrapped.ui.SpotifyViewModel

import android.view.View
import android.widget.Button
import com.example.spotifywrapped.data.SessionManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class WrappedResultsActivity : AppCompatActivity() {

    private val apiBaseUrl = "https://api.spotify.com"
    private val tag = "MainActivity"

    private lateinit var spotifyAdapter: SpotifyAdapter
    private val viewModel: SpotifyViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    private lateinit var spotifyListRV: RecyclerView

    private lateinit var topArtistsBtn: Button
    private lateinit var topSongsBtn: Button
    private lateinit var shortTermBtn: Button
    private lateinit var mediumTermBtn: Button
    private lateinit var longTermBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_wrapped_results)

        spotifyListRV = findViewById(R.id.rv_wrapped_list)
        spotifyAdapter = SpotifyAdapter(::onSpotifyItemClick)

        spotifyListRV.layoutManager = LinearLayoutManager(this)
        spotifyListRV.setHasFixedSize(true)
        spotifyListRV.adapter = spotifyAdapter


        topArtistsBtn = findViewById(R.id.top_artists)
        topSongsBtn = findViewById(R.id.top_songs)

        shortTermBtn = findViewById(R.id.short_term)
        mediumTermBtn = findViewById(R.id.medium_term)
        longTermBtn = findViewById(R.id.long_term)

        sessionManager = SessionManager(this)

        viewModel.spotifyResults.observe(this) { spotifyResults ->
            if (spotifyResults != null) {
                Log.d("Spotify", "${spotifyResults.items}")
                spotifyAdapter.updateResultList(spotifyResults.items)
            }
        }

        topArtistsBtn.setOnClickListener() {
            Log.d("Spotify", "ARTISTS CLICKED")
            sessionManager.setType("artists")
            val range = sessionManager.getRange()
            viewModel.loadResults("artists", range!!)
        }

        topSongsBtn.setOnClickListener() {
            Log.d("Spotify", "SONGS CLICKED")
            sessionManager.setType("tracks")
            val range = sessionManager.getRange()
            viewModel.loadResults("tracks", range!!)
        }

        shortTermBtn.setOnClickListener() {
            Log.d("Spotify", "SHORT CLICKED")
            sessionManager.setRange("short_term")
            val type = sessionManager.getType()
            viewModel.loadResults(type!!, "short_term")
        }

        mediumTermBtn.setOnClickListener() {
            Log.d("Spotify", "MEDIUM CLICKED")
            sessionManager.setRange("medium_term")
            val type = sessionManager.getType()
            viewModel.loadResults(type!!, "medium_term")
        }

        longTermBtn.setOnClickListener() {
            Log.d("Spotify", "LONG CLICKED")
            sessionManager.setRange("long_term")
            val type = sessionManager.getType()
            viewModel.loadResults(type!!, "long_term")
        }

        sessionManager.setType("artists")
        sessionManager.setRange("medium_term")

        val type = sessionManager.getType()
        val range = sessionManager.getRange()


        viewModel.loadResults(type!!, range!!)
    }

    private fun onSpotifyItemClick(spotifyItem: SpotifyItem) {

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share_results,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_share -> {
                shareResults()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//    fun doRepoSearch(q: String){
//        val url = "$apiBaseUrl/v1/me/top/q=$q"
//
//        val moshi = Moshi.Builder()
//            .addLast(KotlinJsonAdapterFactory())
//            .build()
//
//        val jsonAdapter: JsonAdapter<SpotifyItems> =
//            moshi.adapter(SpotifyItems::class.java)
//
//        val req = StringRequest(
//            Request.Method.GET,
//            url,
//            {
//                val results = jsonAdapter.fromJson(it)
//                Log.d(tag, results.toString())
//                repoListAdapter.updateRepoList(results?.list)
//
//                //searchResultsListRV.visibility = View.INVISIBLE
//                //Log.d(tag,it)
//            },
//            {
//                Log.d(tag, "Error fetching from $url: ${it.message}")
//
//            }
//        )
//
//        //requestQueue.add(req)
//    }

    private fun shareResults() {

        val text = getString(R.string.share_text)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))

    }


    private fun playMusic(){

        val intent = Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER)
        startActivity(intent)
    }


}