package nikhil.bhople.gojektest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nikhil.bhople.gojektest.data.model.RepoResponse

@Database(entities = arrayOf(RepoResponse::class), version = 1)
abstract class RepoDatabase: RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {
        @Volatile private var instance: RepoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RepoDatabase::class.java, "githubRepoDatabase.db")
                .allowMainThreadQueries()
                .build()
    }
}