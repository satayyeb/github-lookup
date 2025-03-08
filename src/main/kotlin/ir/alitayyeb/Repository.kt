package ir.alitayyeb.ir.alitayyeb

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Repository(
    val name: String,
    val description: String?,
    val private: Boolean,
    @SerializedName("html_url") val url: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("stargazers_count") val stars: Int,
)
