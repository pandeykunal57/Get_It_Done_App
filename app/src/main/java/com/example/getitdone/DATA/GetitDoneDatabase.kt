package com.example.getitdone.DATA

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetitDoneDatabase: RoomDatabase() {

     abstract fun getTaskDao(): TaskDAO

     companion object {
          fun createDataBase(context: Context): GetitDoneDatabase {
               val database = Room.databaseBuilder(
                    context,
                    GetitDoneDatabase::class.java,
                    "get_it_done_database"

               ).build()
               return database
          }

     }
}




