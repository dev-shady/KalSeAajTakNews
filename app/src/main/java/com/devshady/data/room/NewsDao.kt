package com.devshady.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devshady.domain.NewsPreview
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_feed")
    fun getFeed(): Flow<List<NewsFeedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsFeedEntity>)

    @Query("DELETE FROM news_feed")
    suspend fun deleteAll()
}