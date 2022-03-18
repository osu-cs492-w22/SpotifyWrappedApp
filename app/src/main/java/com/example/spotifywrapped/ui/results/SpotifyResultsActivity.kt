package com.example.spotifywrapped.ui.results
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifywrapped.data.SpotifyItem

import android.widget.Button
import com.example.spotifywrapped.R
import com.example.spotifywrapped.data.utils.SessionManager

class SpotifyResultsActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var spotifyResultsAdapter: SpotifyResultsAdapter
    private val resultsViewModel: SpotifyResultsViewModel by viewModels()

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
        spotifyResultsAdapter = SpotifyResultsAdapter(::onSpotifyItemClick)

        spotifyListRV.layoutManager = LinearLayoutManager(this)
        spotifyListRV.setHasFixedSize(true)
        spotifyListRV.adapter = spotifyResultsAdapter

        topArtistsBtn = findViewById(R.id.top_artists)
        topSongsBtn = findViewById(R.id.top_songs)

        shortTermBtn = findViewById(R.id.short_term)
        mediumTermBtn = findViewById(R.id.medium_term)
        longTermBtn = findViewById(R.id.long_term)

        sessionManager = SessionManager(this)

        resultsViewModel.spotifyResults.observe(this) { spotifyResults ->
            if (spotifyResults != null) {
                Log.d("Spotify", "${spotifyResults.items}")
                spotifyResultsAdapter.updateResultList(spotifyResults.items)
            }
        }

        topArtistsBtn.setOnClickListener() {
            Log.d("Spotify", "ARTISTS CLICKED")
            sessionManager.setType("artists")
            val range = sessionManager.getRange()
            resultsViewModel.loadResults("artists", range!!)
        }

        topSongsBtn.setOnClickListener() {
            Log.d("Spotify", "SONGS CLICKED")
            sessionManager.setType("tracks")
            val range = sessionManager.getRange()
            resultsViewModel.loadResults("tracks", range!!)
        }

        shortTermBtn.setOnClickListener() {
            Log.d("Spotify", "SHORT CLICKED")
            sessionManager.setRange("short_term")
            val type = sessionManager.getType()
            resultsViewModel.loadResults(type!!, "short_term")
        }

        mediumTermBtn.setOnClickListener() {
            Log.d("Spotify", "MEDIUM CLICKED")
            sessionManager.setRange("medium_term")
            val type = sessionManager.getType()
            resultsViewModel.loadResults(type!!, "medium_term")
        }

        longTermBtn.setOnClickListener() {
            Log.d("Spotify", "LONG CLICKED")
            sessionManager.setRange("long_term")
            val type = sessionManager.getType()
            resultsViewModel.loadResults(type!!, "long_term")
        }


        sessionManager.setType("artists")
        sessionManager.setRange("medium_term")

        val type = sessionManager.getType()
        val range = sessionManager.getRange()

        resultsViewModel.loadResults(type!!, range!!)
    }

    private fun onSpotifyItemClick(spotifyItem: SpotifyItem) {

        Log.d("CLICK","here")

        val webUri = spotifyItem.external_urls.spotify
        Log.d("web-uri",webUri)

        val webIntent: Intent = Uri.parse(webUri).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }

        startActivity(webIntent)

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
            R.id.action_launch -> {
                launchResults()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun shareResults() {

        val text = getString(R.string.share_text)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))

    }

    private fun launchResults(){

        var webUri: String? = null
        resultsViewModel.spotifyResults.observe(this) { spotifyResults ->
            if (spotifyResults != null) {
                webUri = spotifyResults.items[0].external_urls.spotify
            }
        }

        val webIntent: Intent = Uri.parse(webUri).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }

        startActivity(webIntent)
    }

}