package com.arbonik.project

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_meme.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL


class MemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        setSupportActionBar(toolbar)
        val url: String? = intent.getStringExtra("url")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "MEME"
        MainActivity().parse(meme_image, url)
        save_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                meme_image.setDrawingCacheEnabled(true)
                val b: Bitmap = meme_image.getDrawingCache()
                if (MediaStore.Images.Media.insertImage(contentResolver, b, "title", "desc") != null) {
                    Toast.makeText(applicationContext, "Сохранено", Toast.LENGTH_LONG).show()
                }

            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun saveToInternalStorage(bitmapImage: Bitmap): String? {
        val cw = ContextWrapper(applicationContext)
        val directory: File = cw.getDir("images", Context.MODE_PRIVATE)
        val mypath = File(directory, "meme.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.getAbsolutePath()
    }
    fun getBitmap(url: String?): Bitmap? {
        return try {
            val url_img = URL(url).content as InputStream
            val d = BitmapFactory.decodeStream(url_img)
            url_img.close()
            return d
        } catch (e: Exception) {
            null
        }
    }
}


