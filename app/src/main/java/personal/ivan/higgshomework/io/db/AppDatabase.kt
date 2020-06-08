package personal.ivan.higgshomework.io.db

import androidx.room.*
import personal.ivan.higgshomework.io.model.GitHubUserDetails

// region Database

@Database(entities = [GitHubUserDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gitHubUserDetailsDao(): GitHubUserDetailsDao
}

// endregion

// region DAO

@Dao
interface GitHubUserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: GitHubUserDetails)

    @Query("SELECT * FROM GitHubUserDetails WHERE username IN (:username)")
    suspend fun load(username: String): GitHubUserDetails
}

// endregion