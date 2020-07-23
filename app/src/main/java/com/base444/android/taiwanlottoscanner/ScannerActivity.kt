package com.base444.android.taiwanlottoscanner

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.base444.android.taiwanlottoscanner.adapter.ResultListAdapter
import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.Lotto649
import com.base444.android.taiwanlottoscanner.model.Lotto649OpenedNumber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.text.Text
import kotlinx.android.synthetic.main.activity_scanner.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScannerActivity : AppCompatActivity(), CodeImageAnalyzer.OnResultReturn {

    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null

    private lateinit var cameraExecutor: ExecutorService

    private var numbersList = ArrayList<BaseLotto>()
    private lateinit var adapter: ResultListAdapter
    val db = FirebaseFirestore.getInstance()
    var targetNumber: Lotto649OpenedNumber? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scanner)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
        adapter =
            ResultListAdapter(
                targetNumber,
                numbersList
            )
        val manger = LinearLayoutManager(this)
        manger.orientation = LinearLayoutManager.VERTICAL
        result_list.layoutManager = manger
        result_list.adapter = adapter
        tern_number_edt.doAfterTextChanged {
            if(it.toString().length == 9){
                matchup()
            }
        }

    }

    fun matchup(){
        db.collection("lotto649").document(tern_number_edt.text.toString()).get()
            .addOnSuccessListener { result ->
                targetNumber = result.toObject(Lotto649OpenedNumber::class.java)
                if (targetNumber != null) {
                    term_result_text_view.text = targetNumber!!.getTextFromResult()
                    Log.d("TAG", targetNumber!!.sp_number.toString())
                    adapter.targetNumber = targetNumber
                    adapter.notifyDataSetChanged()
                } else {
                    term_result_text_view.text = "沒有此期別獎號資料"
                    Log.d("TAG", "no result")
                }
            }
            .addOnFailureListener {
                Log.e(TAG,"Cancel")
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height: Int = displayMetrics.heightPixels
        val width: Int = displayMetrics.widthPixels

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder().setTargetResolution(Size(1280, 960))
                .build()
            // Select back camera
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 960))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer)
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider())
                imageAnalyzer!!.setAnalyzer(cameraExecutor, CodeImageAnalyzer(this))
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun startAnalyz(){
        startCamera()
    }
    private fun stopAnalyz() {
        //imageAnalyzer?.clearAnalyzer()
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private val MODE = "mode"

        @JvmStatic
        fun startActivity(context: Context, mode:String){
            val intent = Intent(context, ScannerActivity::class.java)
            intent.putExtra(MODE, mode)
            context.startActivity(intent)
        }
    }

    override fun onResult(visionText: Text) {
        for (block in visionText.textBlocks) {
            for (line in block.lines) {
                var numbers = ArrayList<Int>()
                for (element in line.elements) {
                    var text = element.text
                    if (text.contains(')')){
                        text =  text.substring(text.indexOf(')'))
                    }
                    if (isLotteNumber(element.text) && !numbers.contains(element.text.toInt())){
                        numbers.add(element.text.toInt())
                    }
                }
                if (numbers.size == 6){
                    var lotto = Lotto649(numbers, "", "", false)
                    if (!numbersList.contains(lotto)){
                        numbersList.add(lotto)
                    }                                                                 
                }
                if(line.text.contains('#')){
                    try {
                        var termNumber = line.text.substring(line.text.indexOf('#') + 1, line.text.indexOf('#') + 10)
                        if (TextUtils.isDigitsOnly(termNumber)){
                            if (term_number_toggle.isChecked){
                                if (termNumber != tern_number_edt.text.toString()){
                                    tern_number_edt.setText(termNumber)
                                }
                            }
                        }
                    } catch (e: Exception){
                        Log.e(TAG, e.message)
                    }
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
    private fun isLotteNumber(text: String): Boolean{
        if (text.length == 2) {
            if(text.toIntOrNull() == null){
                return false
            } else if (text.toIntOrNull()!! in 1..49) {
                return true
            }
        }
        return false
    }

}

