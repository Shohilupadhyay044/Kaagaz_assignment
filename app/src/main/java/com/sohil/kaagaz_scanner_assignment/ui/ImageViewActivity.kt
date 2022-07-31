package com.sohil.kaagaz_scanner_assignment.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sohil.kaagaz_scanner_assignment.R
import kotlinx.android.synthetic.main.activity_image.*



class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        clickListener()

    }

    private fun clickListener() {
        val image = intent.getStringExtra("image")
        val date = intent.getStringExtra("date")
        Glide.with(this).load(image).into(iv_Image)
        tvName.text = "IMG- ${date}.jpg"

        /**
         * when user have share the path of the image.
         */
        share_button.setOnClickListener {
            val shareBody = image
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(
                Intent.createChooser(sharingIntent, "Share Via")
            )
        }
        /**
         * user have to delete the image.
         */
        delete_button.setOnClickListener {
           showDeleteDialog()
        }
        back_button.setNavigationOnClickListener { onBackPressed() }

    }

    /**
     * Alert dialog for delete the image.
     */
    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Kaagaz Scanner")
            .setMessage("Are you sure to Delete this photo.")
            .setPositiveButton("Delete") { dialog, _ ->
                dialog.dismiss()

                setResult(Activity.RESULT_OK, intent)
                onBackPressed()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create()
        builder.show()
    }
}