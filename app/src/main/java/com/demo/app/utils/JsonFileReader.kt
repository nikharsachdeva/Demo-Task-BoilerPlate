package com.demo.app.utils

import android.content.Context
import com.demo.app.model.DataModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object JsonFileReader {

    suspend fun readJsonFile(context: Context, fileName: String): DataModel? {
        return withContext(Dispatchers.IO) {
            val json = readJsonFromAssets(context, fileName)
            Gson().fromJson(json, DataModel::class.java)
        }
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines -> lines.forEach { stringBuilder.append(it) } }
        return stringBuilder.toString()
    }
}
