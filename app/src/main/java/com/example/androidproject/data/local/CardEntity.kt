package com.example.androidproject.data.local

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "FlashCards", indices = [Index(
    value = ["english_card", "vietnamese_card"],
    unique = true
)])
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "english_card") val enCard: String?,
    @ColumnInfo(name = "vietnamese_card") val vnCard: String?
)

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM FlashCards")
    suspend fun getAll(): List<FlashCard>

    @Query("SELECT * FROM FlashCards WHERE uid IN (:flashCardIds)")
    suspend fun loadAllByIds(flashCardIds: IntArray): List<FlashCard>

    @Query("SELECT * FROM FlashCards WHERE english_card LIKE :english AND " +
            "vietnamese_card LIKE :vietnamese LIMIT 1")
    suspend fun findByCards(english: String, vietnamese: String): FlashCard

    @Query("SELECT * FROM FlashCards ORDER BY RANDOM() LIMIT :size")
    suspend fun getLesson(size: Int): List<FlashCard>

    @Insert
    suspend fun insertAll(vararg flashCard: FlashCard)

    @Delete
    suspend fun delete(flashCard: FlashCard)
}

@Database(entities = [FlashCard::class], version = 1)
abstract class FlashCardDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao

    companion object {
        @Volatile // Ensures visibility to all threads
        private var INSTANCE: FlashCardDatabase? = null

        fun getDatabase(context: Context): FlashCardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use application context to prevent memory leaks
                    FlashCardDatabase::class.java,
                    "FlashCardDatabase"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}