package com.github.purofle.nmsl.utils.json

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import junit.framework.TestCase.assertEquals
import org.junit.Test

class JsonUtilsTest {
    @Test
    fun jsonTest() {
        data class Dick(val name: String)
        val jsonString = """
            {
                "name": "jb"
            }
        """.trimIndent()
        val result: Dick = jsonString.toJsonObject()
        assertEquals(result.name, "jb")
    }
}