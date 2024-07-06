package com.example.getitdone.DATA

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetitDoneDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO

    companion object {
       @Volatile
        private var DATABASE_INSTANCE: GetitDoneDatabase? = null

        fun getdatabase(context: Context): GetitDoneDatabase {

            return DATABASE_INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context,
                    GetitDoneDatabase::class.java,
                    "get_it_done_database"
                ).build()

                DATABASE_INSTANCE=instance
                return instance
            }
        }
    }
}





