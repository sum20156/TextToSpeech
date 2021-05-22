package com.sumanmsoft.mytransmate

import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val et = findViewById<EditText>(R.id.editText)

        if(!Python.isStarted()){
            Python.start(AndroidPlatform(this))
        }

        findViewById<Button>(R.id.btnSpeak).setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
                    return@setOnClickListener
                }
            }

            val fileName = "audio.wav"
            val py = Python.getInstance()
            val pyObj = py.getModule("script")
            val obj = pyObj.callAttr("main", filesDir.path, fileName,et.text.toString())
            val mp = MediaPlayer()

            try {
                mp.setDataSource(filesDir.path + File.separator.toString() + fileName)
                mp.prepare()
                mp.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}