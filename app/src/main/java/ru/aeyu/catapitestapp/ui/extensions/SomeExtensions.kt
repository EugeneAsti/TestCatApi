package ru.aeyu.catapitestapp.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import ru.aeyu.catapitestapp.R
import java.nio.charset.StandardCharsets


fun ImageView.getImageFromRemote(
    context: Context, url:
    String, progressBarWidget: ProgressBar
) {
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                this@getImageFromRemote.background = AppCompatResources.getDrawable(context, R.drawable.onerrloadimage)
                progressBarWidget.isVisible = false
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBarWidget.isVisible = false
                return false
            }
        })
        .into(this)
}

fun ImageView.getImageFromRemoteWithCompress(
    context: Context, url:
    String, progressBarWidget: ProgressBar
) {
    val myOptions = RequestOptions()
        .fitCenter() // or centerCrop
        .override(150, 150)

    Glide.with(context)
        .asBitmap()
        .apply(myOptions)
        .load(url)
        .listener(object : RequestListener<Bitmap> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBarWidget.isVisible = false
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBarWidget.isVisible = false
                return false
            }
        })
        .into(this)
}


private val HEX_ARRAY = "0123456789ABCDEF".toByteArray(StandardCharsets.US_ASCII)
fun ByteArray.bytesToHex(): String {
    val hexChars = ByteArray(this.size * 2)
    for (j in this.indices) {
        val v = this[j].toInt() and 0xFF
        hexChars[j * 2] = HEX_ARRAY[v ushr 4]
        hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
    }
    return String(hexChars, StandardCharsets.UTF_8)
}