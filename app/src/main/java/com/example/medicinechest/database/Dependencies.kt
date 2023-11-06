package com.example.medicinechest.database

import android.content.Context
import androidx.room.Room

object Dependencies {

        private lateinit var applicationContext: Context

        fun init(context: Context) {
            applicationContext = context
        }

        private val appDatabase: AppDatabase by lazy {
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "db.db")
                .createFromAsset("med.db")
                .build()
        }

        val medicineRepository: MedicineRepository by lazy { MedicineRepository(appDatabase.getDao()) }
    }