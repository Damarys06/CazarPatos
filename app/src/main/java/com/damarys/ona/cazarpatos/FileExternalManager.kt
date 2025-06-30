package com.damarys.ona.cazarpatos

import android.app.Activity
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileExternalManager(val actividad: Activity) : FileHandler{
    fun isExternalStorageWritable(): Boolean{
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    override fun SaveInformation(datosAGrabar:Pair<String,String>) {
        if(isExternalStorageWritable()) {
            FileOutputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    SHAREDINFO_FILENAME
                )
            ).bufferedWriter().use { outputStream->
                outputStream.write(datosAGrabar.first)
                outputStream.write(System.lineSeparator())
                outputStream.write(datosAGrabar.second)
            }
        }
    }
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    override fun ReadInformation(): Pair<String, String> {
        val file = File(actividad.getExternalFilesDir(null), SHAREDINFO_FILENAME)

        if (isExternalStorageReadable() && file.exists()) {
            FileInputStream(file).bufferedReader().use {
                val datoLeido = it.readText()
                val textArray = datoLeido.split(System.lineSeparator())

                val email = textArray.getOrNull(0) ?: ""
                val clave = textArray.getOrNull(1) ?: ""
                return (email to clave)
            }
        }
        return ("" to "")
    }
}