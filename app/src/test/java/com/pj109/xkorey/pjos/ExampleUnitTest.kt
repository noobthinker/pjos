package com.pj109.xkorey.pjos

import com.pj109.xkorey.model.enums.MeidaType
import com.pj109.xkorey.model.room.MediaTags
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun enmu_test(){
        print(enumValueOf<MeidaType>("image"))
    }
}
