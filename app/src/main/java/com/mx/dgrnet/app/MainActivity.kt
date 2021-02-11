package com.mx.dgrnet.app


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var imageSwitcher : ImageSwitcher
    lateinit var txtEmail : TextView
    var galeria = intArrayOf()


    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = GridLayoutManager(this, 1)
        recyclerView = findViewById(R.id.rv_images)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        imageGalleryAdapter = ImageGalleryAdapter(this, SunsetPhoto.getSunsetPhotos())


        inicializarVariables()
        mostrarCarrusel()
    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = imageGalleryAdapter
    }

    private inner class ImageGalleryAdapter(val context: Context, val sunsetPhotos: Array<SunsetPhoto>)
        : RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {

        private var initializedPicasso = false

        fun initPicasso(context: Context?) {
            Log.i("TAG", "initializedPicasso:$initializedPicasso")
            if (initializedPicasso) {
                return
            }
            try {
                Picasso.setSingletonInstance(Picasso.Builder(context!!).build())
            } catch (e: Exception) {
                Log.e("TAG", "Error:$e")
            }
            initializedPicasso = true
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryAdapter.MyViewHolder {
            val context = parent.context

            if ( !initializedPicasso ) {
                initPicasso(context)
            }

            val inflater = LayoutInflater.from(context)
            val photoView = inflater.inflate(R.layout.item_image, parent, false)
            return MyViewHolder(photoView)
        }

        override fun onBindViewHolder(holder: ImageGalleryAdapter.MyViewHolder, position: Int) {
            val sunsetPhoto = sunsetPhotos[position]
            val imageView = holder.photoImageView

            Picasso.get()
                .load(sunsetPhoto.url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                //.tag(context)
                .into(imageView)
        }

        override fun getItemCount(): Int {
            return sunsetPhotos.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

            var photoImageView: ImageView = itemView.findViewById(R.id.iv_photo)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val sunsetPhoto = sunsetPhotos[position]
                    val intent = Intent(context, SunsetPhotoActivity::class.java).apply {
                        putExtra(SunsetPhotoActivity.EXTRA_SUNSET_PHOTO, sunsetPhoto)
                    }
                    startActivity(intent)
                }
            }
        }

    }

    // Carrusel
    private fun mostrarCarrusel() {

        var posicion : Int = 0
        val DURACION : Int = 9000
        var timer : Timer? = null

        imageSwitcher = findViewById(R.id.imageSwitcher)

        imageSwitcher.setFactory(ViewSwitcher.ViewFactory() {
            val imgView = ImageView(this)
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

    fun inicializarVariables() {
        txtEmail = findViewById(R.id.txtViewEmail)
        galeria = intArrayOf(R.drawable.ubiquiti,
                R.drawable.logo_banner,
                R.drawable.hilook,
                R.drawable.mikro,
                R.drawable.mimosa,
                R.drawable.tenda,
                R.drawable.tplink)
    }
}