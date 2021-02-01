package com.arbonik.project

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vseved3.JSONHelper
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_meme.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.lang.Exception
import java.net.URL


class MemeActivity : AppCompatActivity() {
    var memes: ArrayList<Meme> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        setSupportActionBar(toolbar)
        val id: Int = intent.getIntExtra("id", 0)
        val title: String? = intent.getStringExtra("title")
        val description: String? = intent.getStringExtra("description")
        val url: String? = intent.getStringExtra("url")
        val meme = Meme(id, title, description, url)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "MEME"
        MainActivity().parse(meme_image, meme.url)
        save_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                meme_image.setDrawingCacheEnabled(true)
                val b: Bitmap = meme_image.getDrawingCache()
                if (MediaStore.Images.Media.insertImage(contentResolver, b, "title", "desc") != null) {
                    Toast.makeText(applicationContext, "Сохранено", Toast.LENGTH_LONG).show()
                }

            }
        })
        izbrannoe_button.setOnClickListener(object : View.OnClickListener {
            var isClicked = false
            override fun onClick(v: View?) {
                if (!isClicked) {
                    izbrannoe_button.setImageResource(R.mipmap.ic_star)
                    memes.add(meme)
                    JSONHelper.exportToJSON(applicationContext, memes)
                    try {
                        val asd = JSONHelper.importFromJSON(applicationContext)
                        Log.d("asd", "ff")
                    } catch (e: Exception) {
                        Log.d("asd", "gg")
                    }
                    isClicked = true
                } else {
                    izbrannoe_button.setImageResource(R.mipmap.ic_star_outline)
                    isClicked = false
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}



