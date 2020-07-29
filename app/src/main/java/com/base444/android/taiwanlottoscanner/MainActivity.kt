package com.base444.android.taiwanlottoscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.MODE_638
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.MODE_649
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lotto_649_btn.setOnClickListener{
            ScannerActivity.startActivity(this, MODE_649)
        }
        lotto_super_638_btn.setOnClickListener {
            ScannerActivity.startActivity(this, MODE_638)
        }

    }
}
