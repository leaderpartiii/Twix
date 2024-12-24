package com.example.twix.db

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Parcelize
data class Post(
    val content: String = "",
    val createdAt: String = "",
    var likes: Int = 0,
    var retweets: Int = 0
) : Parcelable {
    constructor(content: String) : this(
        content = content,
        createdAt = getCurrentDate(),
        likes = 0,
        retweets = 0
    )

    companion object {
        fun getCurrentDate(): String {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = formatter.format(date)
            return formattedDate
        }
    }
}
