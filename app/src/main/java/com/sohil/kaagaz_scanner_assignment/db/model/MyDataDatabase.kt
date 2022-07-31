package com.sohil.kaagaz_scanner_assignment.db.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity

/**
 * This is Database abstract class which is used for a Database of room with @Database annotation
 */
@Database(entities = [MyDataEntity::class,MultipleImageEntity::class], version = 1)
abstract class MyDataDatabase : RoomDatabase() {

    abstract fun getMyDao(): MyDataDao

    companion object {

        private var INSTANCE: MyDataDatabase? = null

        fun getDatabase(context: Context): MyDataDatabase {
            if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataDatabase::class.java,
                    "my_database"
                )

                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()

                return INSTANCE!!
            } else {
                return INSTANCE!!
            }
        }

    }

}