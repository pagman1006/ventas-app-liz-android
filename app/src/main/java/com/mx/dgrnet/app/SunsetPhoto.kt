package com.mx.dgrnet.app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SunsetPhoto(val url: String) : Parcelable {

    companion object {
        fun getSunsetPhotos(): Array<SunsetPhoto> {
            return arrayOf(SunsetPhoto("https://www.dgrnet.com.mx/images/productos/lbem523.jpg"),
                SunsetPhoto("https://www.dgrnet.com.mx/images/productos/tc-con.jpg"),
                SunsetPhoto("https://www.dgrnet.com.mx/images/productos/deco_X60_3.webp"),
                SunsetPhoto("https://www.dgrnet.com.mx/images/productos/deco_x20_2.webp"),
                SunsetPhoto("https://www.dgrnet.com.mx/images/productos/deco_P9_3.jpg"),
                SunsetPhoto("https://www.dgrnet.com.mx/images/productos/archer_ax50.webp"))
        }
    }
}