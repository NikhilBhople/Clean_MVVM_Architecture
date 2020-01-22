package nikhil.bhople.gojektest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nikhil.bhople.gojektest.data.model.RepoResponse

@Dao
interface RepoDao {
    @Query("SELECT * FROM reporesponse")
    fun getAll(): List<RepoResponse>

    @Query("SELECT * FROM reporesponse WHERE id LIKE :id")
    fun findById(id: Int): RepoResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<RepoResponse>)

    @Query("DELETE FROM reporesponse")
    fun deleteAll()
}