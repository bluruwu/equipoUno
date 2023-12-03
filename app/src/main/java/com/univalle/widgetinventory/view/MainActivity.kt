package com.univalle.widgetinventory.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.univalle.widgetinventory.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        colorBarraEstado()
    }

    private fun colorBarraEstado() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.black)
        }
    }
}