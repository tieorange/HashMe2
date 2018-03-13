package com.slavicpower.hashme2

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.slavicpower.hashme2.R.drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.seekBarTransformation
import kotlinx.android.synthetic.main.content_main.seekBarTransformation2
import kotlinx.android.synthetic.main.content_main.slideShowImageView
import us.technerd.tnimageview.TNImageView
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap
    private lateinit var selectedImageView: ImageView
    private var disposableApiService: Disposable? = null
    private var disposableTimer: Disposable? = null

    private val skewDelta = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedImageView = slideShowImageView
        bitmap = BitmapFactory.decodeResource(resources, drawable.ice_cream, getBitmapOptions())

        TNImageView().apply {
            bringToFrontOnTouch(true)
            makeRotatableScalable(slideShowImageView)
        }

        initSeekbars()

        val tag = "sky"
        loadPictures(tag)
    }

    private fun loadPictures(tag: String) {
        disposableApiService = MyApp.instagramApiService.getPicsByTag(tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { imagesList ->
                            val links: List<String> = imagesList.data.map { dataItem -> dataItem.images.standardResolution.url }
                            startSlideShow(links)
                        },
                        { handleError(it) }
                )
    }

    private fun startSlideShow(links: List<String>) {
        var currentIndex = 0
        val delaySlideshow = 3L
        disposableTimer = Observable.interval(delaySlideshow, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (currentIndex >= links.size) currentIndex = 0

                            loadImage(links[currentIndex++], slideShowImageView)
                        },
                        { handleError(it) })
    }

    private fun handleError(it: Throwable?) {

    }

    private fun loadImage(
            url: String, imageView: ImageView
    ) {
        Picasso.with(this).load(url).into(object : Target {
            override fun onBitmapLoaded(bitmapParam: Bitmap, from: LoadedFrom?) {
                bitmap = skewBitmap(
                        bitmapParam,
                        getProgressValue(seekBarTransformation.progress),
                        getProgressValue(seekBarTransformation2.progress)
                )
                imageView.setImageBitmap(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }
        })
    }

    private fun initSeekbars() {
        seekBarTransformation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewX = getProgressValue(progress)
                skewImageView(slideShowImageView, skewX, getProgressValue(seekBarTransformation2.progress), bitmap)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarTransformation2.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewY = getProgressValue(progress)
                skewImageView(slideShowImageView, getProgressValue(seekBarTransformation.progress), skewY, bitmap)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun getProgressValue(progress: Int) = (progress - skewDelta) / 100.0f

    private fun skewImageView(imageView: ImageView, xSkew: Float, ySkew: Float, bitmap: Bitmap) {
        imageView.setImageBitmap(skewBitmap(bitmap, xSkew, ySkew))
    }

    private fun skewBitmap(src: Bitmap, xSkew: Float, ySkew: Float): Bitmap {
        val xCoordinates = 0
        val yCoordinates = 0
        val matrix = Matrix()
        matrix.postSkew(xSkew, ySkew)
        return Bitmap.createBitmap(src, xCoordinates, yCoordinates, src.width, src.height, matrix, true)
    }

    private fun getBitmapOptions() = BitmapFactory.Options().apply {
        inJustDecodeBounds = false
        inPreferredConfig = Config.ARGB_8888
        inDither = true
    }
}