
package com.example.medicinechest

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent

class HttpRepository {
    suspend fun sendMedicineDataToServer(medicine: String) {
        val httpClient = HttpClient {}
        try {
            httpClient.post<Unit>("http://37.77.105.18/api/Medicines") {
                body = TextContent(medicine, ContentType.Application.Json)
                Log.d("Send to server", "Success")

            }
        } catch (e: Exception) {
            e.message?.let { Log.i("requesttt", it) }
        } finally {
            httpClient.close()
        }
    }
}