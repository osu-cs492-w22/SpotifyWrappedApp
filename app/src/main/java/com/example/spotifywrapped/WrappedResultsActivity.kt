package com.example.spotifywrapped

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class WrappedResultsActivity : AppCompatActivity() {

    private val apiBaseUrl = "https://api.spotify.com"
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrapped_results)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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


    fun doRepoSearch(q: String){
        val url = "$apiBaseUrl/v1/me/top/q=$q"

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<WeatherResults> =
            moshi.adapter(WeatherResults::class.java)

        val req = StringRequest(
            Request.Method.GET,
            url,
            {
                val results = jsonAdapter.fromJson(it)
                Log.d(tag, results.toString())
                repoListAdapter.updateRepoList(results?.list)

                //searchResultsListRV.visibility = View.INVISIBLE
                //Log.d(tag,it)
            },
            {
                Log.d(tag, "Error fetching from $url: ${it.message}")

            }
        )

        requestQueue.add(req)
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


    private fun playMusic(){

        val intent = Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER)
        startActivity(intent)
    }


}