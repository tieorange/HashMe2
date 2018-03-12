package com.slavicpower.hashme2

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.slavicpower.hashme2.R.drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.content_main.image1
import kotlinx.android.synthetic.main.content_main.image2
import kotlinx.android.synthetic.main.content_main.seekBarTransformation
import kotlinx.android.synthetic.main.content_main.seekBarTransformation2
import kotlinx.android.synthetic.main.content_main.seekBarTransformation2Traperz
import kotlinx.android.synthetic.main.content_main.seekBarTransformation4
import kotlinx.android.synthetic.main.content_main.seekBarTransformation5
import us.technerd.tnimageview.TNImageView
import java.util.Timer
import java.util.TimerTask


class MainActivity : AppCompatActivity() {

    private lateinit var bitmap1: Bitmap
    private lateinit var bitmap2: Bitmap
    private lateinit var selectedImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedImageView = image1
        bitmap1 = BitmapFactory.decodeResource(resources, drawable.ice_cream, getBitmapOptions())
        bitmap2 = BitmapFactory.decodeResource(resources, drawable.ice_cream, getBitmapOptions())

        TNImageView().apply {
            bringToFrontOnTouch(true)
            addListofImageViews(listOf(image1, image2))
        }

        initSeekbars()

        /*InstaDownloader().getPicsByTag("sky") {
            Log.d("Tag", "")
            startSlideShow(it)
        }*/
    }

    private fun startSlideShow(links: List<String?>?) {
        var currentIndex = 0

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkIndexIncrementation()
                    loadImageToBitmapByUrl(links?.get(currentIndex++)!!, image1)

                    checkIndexIncrementation()
                    loadImageToBitmapByUrl2(links[currentIndex++]!!, image2)
                }
            }

            private fun checkIndexIncrementation() {
                if (currentIndex >= links?.size ?: 0)
                    currentIndex = 0
            }
        }, 0, 3000)
    }

    private fun loadImageToBitmapByUrl(
            url: String,
            imageView: ImageView
    ) {
        Picasso.with(this@MainActivity).load(url).into(object : Target {
            override fun onBitmapLoaded(bitmapParam: Bitmap, from: LoadedFrom?) {
                bitmap1 = skewBitmap(
                        bitmapParam,
                        getProgressValue(seekBarTransformation.progress),
                        getProgressValue(seekBarTransformation2.progress)
                )
                imageView.setImageBitmap(bitmap1)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }
        })
    }

    private fun loadImageToBitmapByUrl2(
            url: String,
            imageView: ImageView
    ) {
        Picasso.with(this@MainActivity).load(url).into(object : Target {
            override fun onBitmapLoaded(bitmapParam: Bitmap, from: LoadedFrom?) {
                bitmap2 = skewBitmap(
                        bitmapParam,
                        getProgressValue(seekBarTransformation4.progress),
                        getProgressValue(seekBarTransformation5.progress)
                )
                imageView.setImageBitmap(bitmap2)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }
        })
    }

    val skewDelta = 50
    private fun initSeekbars() {
        seekBarTransformation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewX = getProgressValue(progress)
                skewImageView(image1, skewX, getProgressValue(seekBarTransformation2.progress), bitmap1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekBarTransformation2.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewY = getProgressValue(progress)
                skewImageView(image1, getProgressValue(seekBarTransformation.progress), skewY, bitmap1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekBarTransformation2Traperz.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //                val skewY = getProgressValue(progress)
                deformImageView(progress.toFloat(), image1, bitmap1)
                //                skewImageView(image2, getProgressValue(seekBarTransformation4.progress), skewY, bitmap2)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        // 2nd:
        seekBarTransformation4.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewX = getProgressValue(progress)
                skewImageView(image2, skewX, getProgressValue(seekBarTransformation5.progress), bitmap2)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarTransformation5.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewY = getProgressValue(progress)
                skewImageView(image2, getProgressValue(seekBarTransformation4.progress), skewY, bitmap2)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun getProgressValue(progress: Int) = (progress - skewDelta) / 100.0f

    private fun skewImageView(
            imageView: ImageView,
            xSkew: Float,
            ySkew: Float,
            bitmap: Bitmap
    ) {
        imageView.setImageBitmap(skewBitmap(bitmap, xSkew, ySkew))
    }

    private fun skewBitmap(src: Bitmap, xSkew: Float, ySkew: Float): Bitmap {
        val matrix = Matrix()
        matrix.postSkew(xSkew, ySkew)
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
    }

    private fun getBitmapOptions() = BitmapFactory.Options().apply {
        inJustDecodeBounds = false
        inPreferredConfig = Config.ARGB_8888
        inDither = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun deformImageView(
            deformParam: Float,
            imageView: ImageView,
            bitmap: Bitmap
    ) {
        val bitmapCopy = bitmap.copy(ARGB_8888, true)

        val canvas2 = Canvas(bitmapCopy)
        canvas2.drawColor(Color.WHITE)
        val rectPaint2 = Paint()
        rectPaint2.color = Color.GREEN
        canvas2.drawRect(20f, 20f, 180f, 180f, rectPaint2)
        val matrix2 = Matrix()
        val deform2 = deformParam
        val src2 = floatArrayOf(
                0f,
                0f,
                bitmap.width.toFloat(),
                0f,
                bitmap.width.toFloat(),
                bitmap.height.toFloat(),
                0f,
                bitmap.height.toFloat()
        )
        val dst2 = floatArrayOf(
                0f,
                0f,
                bitmap.width - deform2,
                deform2,
                bitmap.width - deform2,
                bitmap.height - deform2,
                0f,
                bitmap.height.toFloat()
        )
        matrix2.setPolyToPoly(src2, 0, dst2, 0, src2.size shr 1)

        matrix2.postSkew(
                getProgressValue(seekBarTransformation.progress),
                getProgressValue(seekBarTransformation2.progress)
        )

        val bMatrix2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix2, true)

        imageView.setImageBitmap(bMatrix2)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}