package woojin.projects.starbucksapplication

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

fun readData(context: Context): Home? {

    return try {
        val inputStream = context.resources.assets.open("home.json")
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        val gson = Gson()
        gson.fromJson(String(buffer), Home::class.java)
    } catch (e: IOException) {
        null
    }
}