package com.base444.android.taiwanlottoscanner

import android.text.TextUtils
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.base444.android.taiwanlottoscanner.model.SuperLotte638
import com.google.mlkit.vision.text.Text

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.base444.android.taiwanlottoscanner", appContext.packageName)
    }

    @Test
    fun superLottoProcessorTester(){
        var lineText = "7 19 26 3435 38"

        val numbersA = ArrayList<Int>()

        if (lineText.contains('A')){
            numbersA.clear()
        }
        Log.i(LottoTextProcessor.TAG, lineText)
        for (element in lineText.split(" ")) {
            var text = element
            text = text.replace("O", "0")
            if (lineText.contains('A')){
                if (isSupperLotteNumberA(text)){
                    numbersA.add(text.toInt())
                } else if (text.trim().length % 2 == 0){
                    for (index in 0 until text.length - 2 step 2){
                        val subString = text.substring(IntRange(index, index + 2))
                        if (isSupperLotteNumberA(subString) && !numbersA.contains(subString.toInt())){
                            numbersA.add(subString.toInt())
                        }
                    }
                }
            }
        }

        Log.i("TEST", numbersA.toString())

    }
}
