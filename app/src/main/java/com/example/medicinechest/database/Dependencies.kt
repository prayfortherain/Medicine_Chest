package com.example.medicinechest.database

import android.content.Context
import androidx.room.Room
import com.example.medicinechest.HttpRepository

object Dependencies {
    private lateinit var applicationContext: Context

        fun init(context: Context) { //конструктор
            applicationContext = context
        }

        private val appDatabase: AppDatabase by lazy { //ленивая инициализаци
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "aidss.db") //название под которым он сохранит внутри устроиства
                .createFromAsset("med.db") //создает из шаблона
                .build()
        }

        val medicineRepository: MedicineRepository by lazy { MedicineRepository(appDatabase.getDao()) }
        val httpRepository: HttpRepository by lazy { HttpRepository() }
    }