package es.upsa.mimo.mimo18_androidarch.util

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

interface ImageLoader {

    fun loadImageToImageView(
            imageUrl: String,
            imageView: ImageView)

    fun loadImageFittedToImageView(
            imageUrl: String,
            imageView: ImageView)

}

class ImageLoaderImpl(
        context: Context
) : ImageLoader {

    private var picasso: Picasso = Picasso.Builder(context)
            .indicatorsEnabled(false)
            .loggingEnabled(true)
            .build()


    override fun loadImageToImageView(imageUrl: String, imageView: ImageView) {
        picasso
                .load(imageUrl)
                .into(imageView)
    }

    override fun loadImageFittedToImageView(imageUrl: String, imageView: ImageView) {
        picasso
                .load(imageUrl)
                .fit()
                .into(imageView)
    }

}