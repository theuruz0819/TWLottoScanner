package com.base444.android.taiwanlottoscanner

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition


class CodeImageAnalyzer(var onResult: OnResultReturn) : ImageAnalysis.Analyzer {

    interface OnResultReturn{
        fun onResult(text: Text)
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.e("CodeImageAnalyzer", "analyze")
        val mediaImage = imageProxy.image
        Log.e("CodeImageAnalyzer", mediaImage?.width.toString() + " " + mediaImage?.height.toString())
        if (mediaImage != null) {
            val inputimage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val recognizer = TextRecognition.getClient()
            val result = recognizer.process(inputimage)
                .addOnSuccessListener { visionText ->
                    onResult.onResult(visionText);
                    val resultText = visionText.text
                    imageProxy.close()
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                    imageProxy.close()
                }

            // below are barcode
            //barcode(image, imageProxy)
        }
    }

    private fun barcode(
        image: InputImage,
        imageProxy: ImageProxy
    ) {
        val scanner = BarcodeScanning.getClient()
        val result = scanner.process(image)
            .addOnSuccessListener { barcodes ->
                Log.e("CodeImageAnalyzer", "OnSuccessListener")
                imageProxy.close()
                for (barcode in barcodes) {
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    val rawValue = barcode.rawValue
                    val valueType = barcode.valueType

                    Log.e("CodeImageAnalyzer", rawValue)
                    // See API reference for complete list of supported types
                    when (valueType) {
                        Barcode.TYPE_WIFI -> {
                            val ssid = barcode.wifi!!.ssid
                            val password = barcode.wifi!!.password
                            val type = barcode.wifi!!.encryptionType
                        }
                        Barcode.TYPE_URL -> {
                            val title = barcode.url!!.title
                            val url = barcode.url!!.url
                        }
                    }
                }
            }
            .addOnFailureListener {
                imageProxy.close()
            }
    }
}