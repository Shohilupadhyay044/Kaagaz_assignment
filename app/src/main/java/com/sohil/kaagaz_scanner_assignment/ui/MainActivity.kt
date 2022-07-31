package com.sohil.kaagaz_scanner_assignment.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.sohil.kaagaz_scanner_assignment.R
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataDatabase
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity
import com.sohil.kaagaz_scanner_assignment.db.viewModel.MyViewModel
import com.sohil.kaagaz_scanner_assignment.db.viewModel.ViewModelFactory
import com.sohil.kaagaz_scanner_assignment.repository.MyRepository

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE = 10
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecuter: ExecutorService
    private lateinit var myViewModel: MyViewModel
    private var myDataEntity: MyDataEntity? = null
    private var multipleImageEntity: MultipleImageEntity? = null
    private var m_text = 0


    companion object {
        /**
         * This function is used to check camera permissions is granted or not.
         */
        fun isPermissions(context: Context) = arrayOf(Manifest.permission.CAMERA).all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickListener()

        /**
         * Request camera-related permissions
         */
        if (!isPermissions(this)) {

            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CODE)
        }

        /**
         * If permissions have already been granted, it will be proceed to mainActivity
         */
        else {
            openCamera()
        }
    }

    /**
     * This function is for set the click on ImageButton like switch camera, captureImage, Gallery.
     */
    private fun clickListener() {

        camera_capture_button.setOnClickListener {
            takePhoto()
        }
        /**
         * capture multiple image in one go.
         */
        camera_switch_button.setOnClickListener {
            showDialog()

        }
        /**
         * launch imageRecyclerActivity on click of photo_view button.
         */
        photo_view_button.setOnClickListener {
            val intent = Intent(this, ImageGalleryActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * This function is to for dialogBox to set the number to image to capture in one go.
     */
    fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Set Number Of Photos")
        val input = EditText(this)
        input.setHint("Enter Number")
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        builder.setPositiveButton("Set", DialogInterface.OnClickListener { dialog, which ->
            m_text = input.text.toString().toInt()

            val handler = Handler()
            handler.post(object : Runnable {
                private var k = 0
                override fun run() {
                    count1.visibility = View.VISIBLE
                    val tvText = findViewById<TextView>(R.id.count1)
                    tvText.text = "$k"
                    k++
                    if (k <= m_text) {
                        handler.postDelayed(this, 2000)

                    }
                    if (k == m_text + 1) {
                        count1.visibility = View.GONE
                    }
                }
            })
            Handler().postDelayed({
                for (i in 0..m_text) {
                    takePhoto2()
                    Toast.makeText(this, "Burst mode start", Toast.LENGTH_LONG).show()
                }
            }, 2000)

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

        })
        builder.show()
    }

    /**
     * when user all permission then it open the camera.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    private fun openCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()


            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(view_finder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.d("Nipun", "failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            /**
             * If permissions is granted, it will be proceed to open camera.
             */
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                openCamera()
            } else {
                Toast.makeText(this, "Required permissions", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * when user click destroy the activity then shut the camera.
     */
    override fun onDestroy() {
        cameraExecuter = Executors.newSingleThreadExecutor()
        super.onDestroy()
        cameraExecuter.shutdown()
    }

    /**
     * This function is for capture photos .
     */
    private fun takePhoto() {
        val sdf = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
        val timestamp = Timestamp(System.currentTimeMillis())
        val date = Date()
        println(Timestamp(date.time))
        println(timestamp.time)
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            getExternalFilesDir(Environment.DIRECTORY_DCIM),
            "${sdf.format(timestamp)}.jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        Handler().postDelayed({
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exception: ImageCaptureException) {
                        Log.d("Nipun", "Image not Saved")
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedPath = Uri.fromFile(photoFile)
                        findViewById<ImageButton>(R.id.photo_view_button).visibility = View.VISIBLE
                        findViewById<ImageButton>(R.id.photo_view_button).setImageURI(savedPath)
                    }
                }
            )
        }, 2000)
        val MyDataDao by lazy {
            val roomDatabase = MyDataDatabase.getDatabase(this)
            roomDatabase.getMyDao()
        }
        val repository by lazy {
            MyRepository(MyDataDao)
        }
        val factory = ViewModelFactory(repository)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
        val savedPath = Uri.fromFile(photoFile)

        CoroutineScope(Dispatchers.IO).launch {
            myDataEntity?.let { myViewModel.addImage(it) }
            var myEntity = MyDataEntity(savedPath.toString(), sdf.format(timestamp))
            myViewModel.addImage(myEntity)
        }

    }

    private fun takePhoto2() {
        val sdf = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
        val timestamp = Timestamp(System.currentTimeMillis())
        val date = Date()
        println(Timestamp(date.time))
        println(timestamp.time)
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            getExternalFilesDir(Environment.DIRECTORY_DCIM),
            "${sdf.format(timestamp)}.jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        Handler().postDelayed({
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exception: ImageCaptureException) {
                        Log.d("Nipun", "Image not Saved")
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedPath = Uri.fromFile(photoFile)
                        findViewById<ImageButton>(R.id.photo_view_button).visibility = View.VISIBLE
                        findViewById<ImageButton>(R.id.photo_view_button).setImageURI(savedPath)
                    }
                }
            )
        }, 2000)
        val MyDataDao by lazy {
            val roomDatabase = MyDataDatabase.getDatabase(this)
            roomDatabase.getMyDao()
        }
        val repository by lazy {
            MyRepository(MyDataDao)
        }
        val factory = ViewModelFactory(repository)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
        val savedPath = Uri.fromFile(photoFile)

        CoroutineScope(Dispatchers.IO).launch {
            multipleImageEntity?.let { myViewModel.addMultipleImage(it) }
            var myEntity = MultipleImageEntity(savedPath.toString(), sdf.format(timestamp))
            myViewModel.addMultipleImage(myEntity)
        }
    }

}