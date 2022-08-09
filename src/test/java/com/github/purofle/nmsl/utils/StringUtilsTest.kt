package com.github.purofle.nmsl.utils

import com.github.purofle.nmsl.utils.StringUtils.formatTime
import org.junit.Test
import kotlin.test.assertEquals

internal class StringUtilsTest {

    @Test
    fun formatTime() {
        assertEquals("2020-10-27T16:31:08+00:00".formatTime().toString(), "Wed Oct 28 00:31:08 CST 2020")
    }
}