
package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var currentImage : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
        val n = findViewById<Button>(R.id.button2) as Button
        n.setOnClickListener {
            loadmeme()
        }
        val k = findViewById<Button>(R.id.button) as Button
        k.setOnClickListener {view->
            sharememe(view)
        }
    }
    fun loadmeme(){
        val g = findViewById<ProgressBar>(R.id.progressbar) as ProgressBar
        g.visibility = View.VISIBLE
// Instantiate the RequestQueue.
        val r = findViewById<ImageView>(R.id.imageView)
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"
// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
              currentImage = response.getString("url")
                Glide.with(this).load(currentImage).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        g.visibility = View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        g.visibility = View.GONE
                        return false

                    }
                }).into(r)

            },
            {  })
// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }
    fun sharememe(view: View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey! check out this meme $currentImage")
        val F = Intent.createChooser(intent, "share this meme..")
        startActivity(F)
    }

}