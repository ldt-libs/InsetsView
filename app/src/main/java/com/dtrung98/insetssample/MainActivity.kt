package com.dtrung98.insetssample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dtrung98.insetsview.ext.setUpSystemUIVisibility

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setUpSystemUIVisibility(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}