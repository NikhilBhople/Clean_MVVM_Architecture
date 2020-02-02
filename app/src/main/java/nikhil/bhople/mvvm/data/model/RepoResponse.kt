package nikhil.bhople.mvvm.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RepoResponse(
    @PrimaryKey var id: Int,

    @ColumnInfo(name = "update_time")
    var updateTime: Long,

    @ColumnInfo(name = "author")
    val author: String,
    @SerializedName("avatar")
    val avatar: String,
    @ColumnInfo(name = "currentPeriodStars")
    val currentPeriodStars: Int,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "forks")
    val forks: Int,
    @ColumnInfo(name = "language",defaultValue = "")
    val language: String,
    @ColumnInfo(name = "languageColor", defaultValue = "")
    val languageColor: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "stars")
    val stars: Int,
    @ColumnInfo(name = "url")
    val url: String
)