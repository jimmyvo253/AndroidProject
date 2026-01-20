package com.example.androidproject.data.local

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable


@Entity(tableName = "FlashCards", indices = [Index(
    value = ["english_card", "vietnamese_card"],
    unique = true
)])
@Serializable
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "english_card") val enCard: String?,
    @ColumnInfo(name = "vietnamese_card") val vnCard: String?,
    @ColumnInfo(name = "audio_file")  val audioFile: String?
)

@Dao
interface FlashCardDao {
    @RawQuery
    fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int


    @Query("SELECT * FROM FlashCards")
    fun getAllFlow(): Flow<List<FlashCard>>

    @Query("SELECT * FROM FlashCards")
    suspend fun getAll(): List<FlashCard>

    //@Query("SELECT * FROM FlashCards LIMIT :size")
    @Query("SELECT * FROM FlashCards ORDER BY RANDOM() LIMIT :size")
    suspend fun getLesson(size: Int): List<FlashCard>

    @Query(
        "SELECT * FROM FlashCards WHERE english_card LIKE :english AND " +
                "vietnamese_card LIKE :vietnamese LIMIT 1"
    )
    suspend fun findByCards(english: String, vietnamese: String): FlashCard?

    @Insert
    suspend fun insertAll(vararg flashCard: FlashCard)

    @Query(
        "UPDATE FlashCards SET english_card = :englishNew " +
                ", vietnamese_card =:vietnameseNew " +
                ", audio_file = :audioFile " +
                "WHERE english_card = :englishOld " +
                "AND vietnamese_card = :vietnameseOld"
    )
    suspend fun updateFlashCard(
        englishOld: String, vietnameseOld: String,
        englishNew: String, vietnameseNew: String,
        audioFile: String?
    )


    @Query(
        "DELETE FROM FlashCards WHERE english_card = :english " +
                "AND vietnamese_card =:vietnamese"
    )
    suspend fun deleteFlashCard(english: String, vietnamese: String)

    @Query(
        "SELECT * FROM FlashCards WHERE english_card LIKE :english"
    )
    suspend fun searchEnglish(english: String): List<FlashCard>

    @Query(
        "SELECT * FROM FlashCards WHERE vietnamese_card LIKE :vietnamese"
    )
    suspend fun searchVietnamese(vietnamese: String): List<FlashCard>

    @Query("SELECT * FROM FlashCards WHERE english_card LIKE :english " +
            "AND vietnamese_card LIKE :vietnamese")
    suspend fun searchBoth(english: String, vietnamese: String): List<FlashCard>

}