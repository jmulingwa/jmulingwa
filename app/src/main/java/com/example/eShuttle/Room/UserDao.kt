package com.example.eShuttle.Room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eShuttle.models.User


@Dao
interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: User):Long

    @Query("SELECT * FROM users WHERE id =:userID")
    fun getUser(userID: Int): User?

}