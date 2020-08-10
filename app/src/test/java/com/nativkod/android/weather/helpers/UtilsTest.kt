package com.nativkod.android.weather.helpers

import org.junit.Assert.*
import org.junit.Test

class UtilsTest{

    @Test
    fun testDateFormatter(){
        val newD = Utils.formatDate("2020-08-01 03:00:00","yyyy-MM-dd HH:mm:ss","EEEE")
        assert(newD.equals("Saturday"))
        val newT = Utils.formatDate("2020-08-01 03:00:00","yyyy-MM-dd HH:mm:ss","HH:mm")
        assert(newT.equals("03:00"))
    }

}