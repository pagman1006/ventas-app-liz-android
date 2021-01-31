package com.mx.dgrnet.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var imageSwitcher : ImageSwitcher
    var imageList : ArrayList<Drawable> = java.util.ArrayList()
    var imageUrl : ArrayList<URL> = java.util.ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mostrarCarrusel()
    }

    fun mostrarCarrusel() {
        val galeria = intArrayOf(R.drawable.ubiquiti, R.drawable.logo, R.drawable.hilook,
                R.drawable.mikro, R.drawable.mimosa, R.drawable.tenda, R.drawable.tplink)

        var posicion : Int = 0
        val DURACION : Int = 9000
        var timer : Timer? = null

        imageSwitcher = findViewById(R.id.imageSwitcher)

        imageSwitcher.setFactory(ViewSwitcher.ViewFactory() {
            var imgView: ImageView = ImageView(this)
            imgView.scaleType = ImageView.ScaleType.FIT_CENTER

            return@ViewFactory imgView
        })

        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    imageSwitcher.setImageResource(galeria[posicion])
                    posicion++
                    if (posicion === galeria.size) posicion = 0
                }
            }
        }, 1.toLong(), DURACION.toLong())
    }

}