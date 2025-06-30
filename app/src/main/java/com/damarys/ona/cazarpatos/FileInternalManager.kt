package com.damarys.ona.cazarpatos

import android.app.Activity
import android.content.Context

class FileInternalManager(val actividad: Activity) : FileHandler {
    override fun SaveInformation(datosAGrabar:Pair<String,String>) {
        val texto = datosAGrabar.first + System.lineSeparator() + datosAGrabar.second
        actividad.openFileOutput("fichero.txt", Context.MODE_PRIVATE).bufferedWriter().use { fos->
            fos.write(texto)
        }
    }
    override fun ReadInformation(): Pair<String, String> {
        return try {
            val datoLeido = actividad.openFileInput("fichero.txt").bufferedReader().use {
                it.readText()
            }
            val textArray = datoLeido.split(System.lineSeparator())
            if (textArray.size >= 2) {
                val email = textArray[0]
                val clave = textArray[1]
                email to clave
            } else {
                "" to ""
            }
        } catch (e: Exception) {
            "" to ""
        }
    }
}