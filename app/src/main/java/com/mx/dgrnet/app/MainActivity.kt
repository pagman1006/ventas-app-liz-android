package com.mx.dgrnet.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
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
        cargarImagenes()
        cargarCarrusel()
    }

    fun cargarImagenes() {
        val url : String = "https://www.dgrnet.com.mx/images/banner/"
        var urlImage = url + "ubiquiti.png"
        var imageView: ImageView = ImageView(this)
        imageUrl.add(URL(urlImage))

        urlImage = url + "hilook.png"
        imageUrl.add(URL(urlImage))

        urlImage = url + "logo-banner.png"
        imageUrl.add(URL(urlImage))

        urlImage = url + "mikro.png"
        imageUrl.add(URL(urlImage))

        urlImage = url + "mimosa.png"
        imageUrl.add(URL(urlImage))

        urlImage = url + "tenda.png"
        imageUrl.add(URL(urlImage))

        urlImage = url + "tplink.png"
        imageUrl.add(URL(urlImage))

        for ( url in imageUrl ) {
            var img: Deferred<Bitmap?> = GlobalScope.async {
                url.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                // show bitmap on image view when available
                imageList.add(BitmapDrawable(img.await() as Bitmap))
                Log.d("Carga de Imagenes",url.toString())
            }
        }
    }

    fun cargarCarrusel() {
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
                    if ( imageList.size > 0) {
                        imageSwitcher.setImageDrawable(imageList.get(posicion))
                        posicion++
                        if (posicion === imageUrl.size) posicion = 0
                    }

                }
            }
        }, 5.toLong(), DURACION.toLong())
    }

}

// extension function to get bitmap from url
fun URL.toBitmap(): Bitmap?{
    return try {
        BitmapFactory.decodeStream(openStream())
    }catch (e: IOException){
        null
    }
}