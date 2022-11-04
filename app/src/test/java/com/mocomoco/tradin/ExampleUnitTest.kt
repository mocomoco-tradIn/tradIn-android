package com.mocomoco.tradin

import com.mocomoco.tradin.util.LoginRegex.checkNickname
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
    fun regex_test() {
        assertEquals(true, "".checkNickname())
        assertEquals(false, "asd!sadf".checkNickname())
        assertEquals(false, "!asdf".checkNickname())
        assertEquals(false, "asdf!".checkNickname())
        assertEquals(false, "a!sd!f".checkNickname())
        assertEquals(true, "a".checkNickname())
        assertEquals(true, "aas".checkNickname())
        assertEquals(true, "aas".checkNickname())
    }
}