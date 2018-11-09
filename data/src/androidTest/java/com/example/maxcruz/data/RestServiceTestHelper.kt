package com.example.maxcruz.data

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Class used to load the content from JSON
 */
object RestServiceTestHelper {

    /**
     * Return the content for the given InputStream.
     *
     * @param inputStream Stream to read.
     */
    private fun convertStreamToString(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = null
        while ({ line = reader.readLine(); line }() != null) {
            sb.append(line).append("\n")
        }
        reader.close()
        return sb.toString()
    }

    /**
     * Read the given file and return his content.
     *
     * @param context  test instrumentation context
     * @param filePath path relative to the asset directory
     */
    fun getStringFromFile(context: Context, filePath: String): String {
        val stream = context.resources.assets.open(filePath)
        val ret = convertStreamToString(stream)
        stream.close()
        return ret
    }
}
