package com.sohil.kaagaz_scanner_assignment.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sohil.kaagaz_scanner_assignment.R
import kotlinx.android.synthetic.main.activity_image_recycler_view.*


class ImageGalleryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_recycler_view)
        /**
         * when use have to show multiple taken photos in one go.
         */
        burstImage.setOnClickListener {
            clickListener()
        }
        /**
         * when use have to show single taken photo in one go.
         */
        singleImage.setOnClickListener {
            val intent = Intent(this, SingleTakeAlbumActivity::class.java)
            startActivity(intent)
        }
        toolbar_top.setNavigationOnClickListener { onBackPressed() }
    }
    /**
     * clickListener show single taken photo in one go.
     */
    fun clickListener() {
        val intent = Intent(this, AlbumActivity::class.java)
        startActivity(intent)
    }

}