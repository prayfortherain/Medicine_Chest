package com.example.medicinechest

import com.example.medicinechest.database.MedicineListWrapper
import com.example.medicinechest.database.MedicineSerialized
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class HttpRepository {
    suspend fun sendMedicineDataToServer(medicine: String) {

        val httpClient = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { isLenient = true; ignoreUnknownKeys = true })
            }
        }

        val url = "https://malw.ru/api/Medicines"

        try {
            // Отправляем POST-запрос с данными в формате JSON
            val response = httpClient.post<Unit>(url) {
                contentType(ContentType.Application.Json)
                body = medicine
            }

            println("все круто")
        } catch (e: Exception) {
            println("Произошла ошибка при отправке запроса: $e")
        } finally {
            httpClient.close()
        }
    }
}
