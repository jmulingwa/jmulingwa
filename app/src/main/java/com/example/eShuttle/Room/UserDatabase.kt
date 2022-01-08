package com.example.eShuttle.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eShuttle.models.User


@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    abstract  fun getUserDao():UserDao

    @Volatile // other threads can see it
    private var INSTANCE: UserDatabase? =null
    private val LOCK = Any()

    operator  fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK){
        INSTANCE ?: createDatabase(context).also { INSTANCE = it}
    }

    //Room  db Migrations
//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    }
    private fun createDatabase(context: Context)=
        Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "users.db"
        )
            .build()
//            .addMigrations(MIGRATION_1_2)
}

