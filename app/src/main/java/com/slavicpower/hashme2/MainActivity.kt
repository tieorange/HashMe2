package com.slavicpower.hashme2

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.slavicpower.hashme2.R.drawable
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.image1
import kotlinx.android.synthetic.main.content_main.seekBarTransformation
import kotlinx.android.synthetic.main.content_main.seekBarTransformation2
import us.technerd.tnimageview.TNImageView


class MainActivity : AppCompatActivity() {

    private lateinit var bitmap2: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitmap2 = BitmapFactory.decodeResource(resources, drawable.ice_cream, getBitmapOptions())

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        TNImageView().apply {
            makeRotatableScalable(image1)
            bringToFrontOnTouch(true)
        }

        initSeekbars()

        loadFromInsta()
    }

    private fun loadFromInsta() {
        
    }

    private fun initSeekbars() {
        val skewDelta = 50

        seekBarTransformation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewX = (progress - skewDelta /*- 100*/) / 100.0f
                skewImageView(image1, skewX, seekBarTransformation2.progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekBarTransformation2.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val skewY = (progress - skewDelta /*- 100*/) / 100.0f
                skewImageView(image1, seekBarTransformation.progress.toFloat(), skewY)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun skewImageView(imageView: ImageView, xSkew: Float, ySkew: Float) {
        imageView.setImageBitmap(skewBitmap(bitmap2, xSkew, ySkew))
    }

    private fun skewBitmap(src: Bitmap, xSkew: Float, ySkew: Float): Bitmap {
        val matrix = Matrix()
        matrix.postSkew(xSkew, ySkew)

        return Bitmap.createBitmap(
                src,
                0,
                0,
                src.width,
                src.height,
                matrix,
                true
        )
    }

    private fun getBitmapOptions() = BitmapFactory.Options().apply {
        inJustDecodeBounds = false
        inPreferredConfig = Config.RGB_565
        inDither = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun deformImageView(deformParam: Float, imageView: ImageView) {
        val bitmap2 = BitmapFactory.decodeResource(resources, drawable.trump)
        val canvas2 = Canvas(bitmap2.copy(ARGB_8888, true))
        canvas2.drawColor(Color.WHITE)
        val rectPaint2 = Paint()
        rectPaint2.color = Color.GREEN
        canvas2.drawRect(20f, 20f, 180f, 180f, rectPaint2)
        val matrix2 = Matrix()
        val deform2 = deformParam
        val src2 = floatArrayOf(
                0f,
                0f,
                bitmap2.width.toFloat(),
                0f,
                bitmap2.width.toFloat(),
                bitmap2.height.toFloat(),
                0f,
                bitmap2.height.toFloat()
        )
        val dst2 = floatArrayOf(
                0f,
                0f,
                bitmap2.width - deform2,
                deform2,
                bitmap2.width - deform2,
                bitmap2.height - deform2,
                0f,
                bitmap2.height.toFloat()
        )
        matrix2.setPolyToPoly(src2, 0, dst2, 0, src2.size shr 1)
        val bMatrix2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.width, bitmap2.height, matrix2, true)

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
