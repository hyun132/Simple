package com.example.testathome.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testathome.models.Item

@Database(entities = arrayOf(Item::class), version = 1)
abstract class ItemDatabase:RoomDatabase() {
    abstract fun getDao(): Dao
    companion object{
        private var DB_INSTANCE:ItemDatabase?=null
        fun getDatabase(context: Context):ItemDatabase{
            val tempInstance = DB_INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "item_db"
                ).build()
                DB_INSTANCE=instance
                return instance
            }
        }
    }
}