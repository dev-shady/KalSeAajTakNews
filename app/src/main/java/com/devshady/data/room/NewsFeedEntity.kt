package com.devshady.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devshady.domain.NewsPreview

@Entity(tableName = "news_feed")
data class NewsFeedEntity (
    @PrimaryKey val id : String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toNewsPreview() = NewsPreview(
        id = id,
        title = title,
        description = description,
        thumbnailUrl = imageUrl
    )
}

