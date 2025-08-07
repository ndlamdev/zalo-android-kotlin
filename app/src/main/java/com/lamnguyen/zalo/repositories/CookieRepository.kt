package com.lamnguyen.zalo.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lamnguyen.zalo.entities.Cookie

@Dao
interface CookieRepository {
    @Query(
        """
        SELECT * 
        FROM cookies
        WHERE url = :url
        
    """
    )
    fun findAllByUrl(url: String): List<Cookie>

    @Insert
    fun insert(cookie: Cookie)

    @Query("DELETE FROM cookies WHERE url = :url AND name = :name")
    fun deleteAllByUrlAndName(url: String, name: String)
}