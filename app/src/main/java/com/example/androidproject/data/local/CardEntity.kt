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
import kotlinx.serialization.Serializable


@Entity(tableName = "FlashCards", indices = [Index(
    value = ["english_card", "vietnamese_card"],
    unique = true
)])
@Serializable
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "english_card") val enCard: String?,
    @ColumnInfo(name = "vietnamese_card") val vnCard: String?
)

@Dao
interface FlashCardDao {
    @RawQuery
    fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int

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
                "WHERE english_card = :englishOld " +
                "AND vietnamese_card = :vietnameseOld"
    )
    suspend fun updateFlashCard(
        englishOld: String, vietnameseOld: String,
        englishNew: String, vietnameseNew: String
    )


    @Query(
        "DELETE FROM FlashCards WHERE english_card = :english " +
                "AND vietnamese_card =:vietnamese"
    )
    suspend fun deleteFlashCard(english: String, vietnamese: String)

}
//@Entity(tableName = "FlashCards", indices = [Index(
//    value = ["english_card", "vietnamese_card"],
//    unique = true
//)])
//data class FlashCard(
//    @PrimaryKey(autoGenerate = true) val uid: Int,
//    @ColumnInfo(name = "english_card") val enCard: String?,
//    @ColumnInfo(name = "vietnamese_card") val vnCard: String?
//)
//
//@Dao
//interface FlashCardDao {
//    @Query("SELECT * FROM FlashCards")
//    suspend fun getAll(): List<FlashCard>
//
//    @Query("SELECT * FROM FlashCards WHERE uid IN (:flashCardIds)")
//    suspend fun loadAllByIds(flashCardIds: IntArray): List<FlashCard>
//
//    @Query("SELECT * FROM FlashCards WHERE english_card LIKE :english AND " +
//            "vietnamese_card LIKE :vietnamese LIMIT 1")
//    suspend fun findByCards(english: String, vietnamese: String): FlashCard
//
//    @Query("SELECT * FROM FlashCards ORDER BY RANDOM() LIMIT :size")
//    suspend fun getLesson(size: Int): List<FlashCard>
//
//    @Insert
//    suspend fun insertAll(vararg flashCard: FlashCard)
//
//    @Delete
//    suspend fun delete(flashCard: FlashCard)
//}

//@Database(entities = [FlashCard::class], version = 1)
//abstract class FlashCardDatabase : RoomDatabase() {
//    abstract fun flashCardDao(): FlashCardDao
//
//    companion object {
//        @Volatile // Ensures visibility to all threads
//        private var INSTANCE: FlashCardDatabase? = null
//
//        fun getDatabase(context: Context): FlashCardDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext, // Use application context to prevent memory leaks
//                    FlashCardDatabase::class.java,
//                    "FlashCardDatabase"
//                ).build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
//    }
//
//}