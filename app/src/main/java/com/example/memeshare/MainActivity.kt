package com.example.memeshare

import android.app.DownloadManager
import android.content.Intent
import android.graphics.DrawFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme(){
        progessBar.visibility= View.VISIBLE
        val url="https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response->
           currentImageUrl=response.getString("url")

                Glide.with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progessBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progessBar.visibility=View.GONE
                        return false
                    }
                }).into(memeImageView)
        },
            Response.ErrorListener {

            })
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareMeme(view: android.view.View) {
    val intent =Intent(Intent.ACTION_SEND)
        intent.type="text/plane"
        intent.putExtra(Intent.EXTRA_TEXT,"hey, checkout this cool meme $currentImageUrl")
    val chooser=Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)

    }
    fun nextMeme(view: android.view.View) {
        loadMeme()
    }
}