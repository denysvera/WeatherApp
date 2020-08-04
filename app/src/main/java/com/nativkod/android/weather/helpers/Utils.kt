package com.nativkod.android.weather.helpers

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils{
    companion object{
        private val SECOND_MILLIS = 1000
        private val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private val DAY_MILLIS = 24 * HOUR_MILLIS

        fun formatDate(dateString: String, oldFormat: String, newFormat: String): String{
            val givenFormat = SimpleDateFormat(oldFormat)
            val displayFormat = SimpleDateFormat(newFormat)
            val date = givenFormat.parse(dateString)
            return displayFormat.format(date)
        }

        fun getTimeAgo(time: Long): String? {
            var time = time
            if (time < 1000000000000L) {
                time *= 1000
            }

            val now = System.currentTimeMillis()
            if (time > now || time <= 0) {
                return null
            }


            val diff = now - time

            return if (diff < MINUTE_MILLIS) {
                "just now"
            } else if (diff < 2 * MINUTE_MILLIS) {
                "a minute ago"
            } else if (diff < 50 * MINUTE_MILLIS) {
                (diff / MINUTE_MILLIS).toString() + " minutes ago"
            } else if (diff < 90 * MINUTE_MILLIS) {
                "an hour ago"
            } else if (diff < 24 * HOUR_MILLIS) {
                (diff / HOUR_MILLIS).toString() + " hours ago"
            } else if (diff < 48 * HOUR_MILLIS) {
                "yesterday"
            } else if (diff < 7 * DAY_MILLIS) {
                (diff / DAY_MILLIS).toString() + " days ago"
            }else {
                DateFormat.format("dd MMM, yy", Date(time)).toString()
                //(diff / DAY_MILLIS).toString() + " days ago"
            }
        }
    }
}
const val API_KEY = "fcc325db55bf544451270791066221dd"
const val UNIT = "metric"
const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101