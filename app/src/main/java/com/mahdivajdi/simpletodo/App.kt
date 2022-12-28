package com.mahdivajdi.simpletodo

import android.app.Application
import com.mahdivajdi.simpletodo.data.local.AppDatabase

class App : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

}