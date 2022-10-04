package com.example.manifestchangeexample

import android.content.ComponentName
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    var namesList: Array<AppNameModel> = emptyArray()
    var id: String = ""
    lateinit var btn1: Button
    lateinit var btn2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)

        namesList = readAppNames()
        for (i in namesList.indices) {
            Log.e("MainActivity", "list :${namesList[i]}")
        }

        btn1.setOnClickListener {
            packageManager?.setComponentEnabledSetting(
                ComponentName(
                    applicationContext.packageName,
                    applicationContext.packageName + ".MainActivityAlias1"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            packageManager?.setComponentEnabledSetting(
                ComponentName(
                    applicationContext.packageName,
                    applicationContext.packageName + ".MainActivityAlias2"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
            )
        }

        btn2.setOnClickListener {

            packageManager?.setComponentEnabledSetting(
                ComponentName(
                    applicationContext.packageName,
                    applicationContext.packageName + ".MainActivityAlias1"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
            )

            packageManager?.setComponentEnabledSetting(
                ComponentName(
                    applicationContext.packageName,
                    applicationContext.packageName + ".MainActivityAlias2"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
        }

    }

    private fun readAppNames(): Array<AppNameModel> {
        val inputStream = this.assets.open("app_names.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<AppNameModel>::class.java)
    }
}
