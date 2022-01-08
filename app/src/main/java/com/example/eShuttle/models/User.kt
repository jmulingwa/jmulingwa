package com.example.eShuttle.models

import androidx.room.Entity
import androidx.room.PrimaryKey


//We use rooms Annotation @Entity inorder to save our article
// in a database
@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int ?,
    val firstName: String?,
    val lastName: String?,
    val username: String?,
    val email: String?,
    val idNumber: String?,
    val gender: String?,
    val phone: String?,
    val password:String?,
    val confirm: String?,
    val status: String?,
    val dob: String?,

    )