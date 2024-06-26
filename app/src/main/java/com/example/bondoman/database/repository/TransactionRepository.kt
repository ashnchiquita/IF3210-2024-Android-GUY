package com.example.bondoman.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.bondoman.api.RetrofitClient
import com.example.bondoman.database.dao.TransactionDao
import com.example.bondoman.database.entity.TransactionEntity
import com.example.bondoman.types.util.Logger
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionRepository(private val transactionDao: TransactionDao) {
    val list: LiveData<List<TransactionEntity>> = transactionDao.getAll()

    companion object {
        private const val TAG = "TransactionRepository"
    }

    fun getByID(id: Int): TransactionEntity? {
        return transactionDao.getById(id)
    }

    suspend fun insert(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    suspend fun update(transaction: TransactionEntity) {
        transactionDao.update(transaction)
    }

    suspend fun delete(transaction: TransactionEntity) {
        transactionDao.delete(transaction)
    }

    suspend fun deleteAll() {
        transactionDao.deleteAll()
    }

    suspend fun postUploadNota(imageReqBody: RequestBody, token: String): List<TransactionEntity> {
        try {
            val authToken = "Bearer $token"
            val response = RetrofitClient.uploadInstance.uploadImage(
                MultipartBody.Part.createFormData(
                    "file", "test", imageReqBody
                ), authToken
            )

            if (!response.isSuccessful) {
                Logger.log(TAG, response.code().toString())
                return emptyList()
            }

            return response.body()!!.items.items.map { item ->
                TransactionEntity(
                    id = 0, title = item.name,
                    // TODO: Category
                    category = "scanned", amount = item.qty * item.price.toInt(),
                    // TODO: Location
                    latitude = null, longitude = null, timestamp = SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
                    ).format(
                        Date()
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())

            return emptyList()
        }
    }
}