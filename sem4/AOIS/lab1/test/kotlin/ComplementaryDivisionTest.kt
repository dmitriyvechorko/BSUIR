import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ComplementaryDivisionTest {

    @Test
    @DisplayName("21 / 13 = 1.15")
    fun division_test1() {
        assertEquals(0.cb, (21.cb / 13.cb).first)
        assertEquals("01111", (21.cb / 13.cb).second)
    }
    
    @Test
    @DisplayName("21 / (-13) = -1.15")
    fun division_test2() {
        assertEquals(0.cb, (21.cb / (-13).cb).first)
        assertEquals("01111", (21.cb / (-13).cb).second)
    }

    @Test
    @DisplayName("(-21) / 13 = -1.15")
    fun division_test3() {
        assertEquals(0.cb, ((-21).cb / 13.cb).first)
        assertEquals("01111", ((-21).cb / 13.cb).second)
    }

    @Test
    @DisplayName("(-21) / (-13) = 1.15")
    fun division_test4() {
        assertEquals(0.cb, ((-21).cb / (-13).cb).first)
        assertEquals("01111", ((-21).cb / (-13).cb).second)
    }

    @Test
    @DisplayName("13 / 21 = 0.62")
    fun division_test5() {
        assertEquals(2.cb, (13.cb / 21.cb).first)
        assertEquals("00011", (13.cb / 21.cb).second)
    }
    @Test
    @DisplayName("(-13) / 21 = -0.62")
    fun division_test6() {
        assertEquals((-2).cb, ((-13).cb / 21.cb).first)
        assertEquals("00011", ((-13).cb / 21.cb).second)
    }

    @Test
    @DisplayName("13 / (-21) = -0.62")
    fun division_test7() {
        assertEquals((-2).cb, (13.cb / (-21).cb).first)
        assertEquals("00011", (13.cb / (-21).cb).second)
    }

    @Test
    @DisplayName("(-13) / (-21) = 0.62")
    fun division_test8() {
        assertEquals(2.cb, ((-13).cb / (-21).cb).first)
        assertEquals("00011", ((-13).cb / (-21).cb).second)
    }

}
