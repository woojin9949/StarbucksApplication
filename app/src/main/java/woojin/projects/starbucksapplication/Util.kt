package woojin.projects.starbucksapplication

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.IOException

//제네릭 함수로 변환하여 공용사용
fun <T> readData(context: Context, fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = context.resources.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        val gson = Gson()
        gson.fromJson(String(buffer), classT)

    } catch (e: IOException) {
        null
    }
}