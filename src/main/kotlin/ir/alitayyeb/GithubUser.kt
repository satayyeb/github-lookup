package ir.alitayyeb.ir.alitayyeb

import com.google.gson.annotations.SerializedName
import java.util.ArrayList
import java.util.Date

data class GithubUser(
    val following: Int,
    val followers: Int,
    @SerializedName("login") val username: String,
    @SerializedName("created_at") val createdAt: Date,
    var repositories: List<Repository> = ArrayList<Repository>()
)