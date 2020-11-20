package com.dariushm2.thescore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.db.model.TeamEntity

@Database(entities = [TeamEntity::class, PlayerEntity::class], version = 1, exportSchema = false)
abstract class NbaDatabase : RoomDatabase() {

    abstract fun getNbaDao(): NbaDao



    companion object {

        private const val NBA_DATABASE = "NBA_DATABASE"
        private var INSTANCE: NbaDatabase? = null

        fun getDatabase(context: Context): NbaDatabase {
            if (INSTANCE == null) {
                synchronized(NbaDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, NbaDatabase::class.java, NBA_DATABASE)
                                //.allowMainThreadQueries()
                                .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}