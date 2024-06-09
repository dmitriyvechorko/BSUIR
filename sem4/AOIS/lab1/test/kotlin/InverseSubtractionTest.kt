import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class InverseSubtractionTest {

    @Test
    @DisplayName("13 - 21 = -8")
    fun subtraction_test1() {
        assertEquals((-8).ib, 13.ib - 21.ib)
    }
    @Test
    @DisplayName("13 - (-21) = 34")
    fun subtraction_test2() {
        assertEquals(34.ib, 13.ib - (-21).ib)
    }

    @Test
    @DisplayName("(-13) - 21 = -34")
    fun subtraction_test3() {
        assertEquals((-34).ib, (-13).ib - 21.ib)
    }

    @Test
    @DisplayName("(-13) - (-21) = -34")
    fun subtraction_test4() {
        assertEquals(8.ib, (-13).ib - (-21).ib)
    }

    @Test
    @DisplayName("21 - 13 = 34")
    fun subtraction_test5() {
        assertEquals(8.ib, 21.ib - 13.ib)
    }
    @Test
    @DisplayName("(-21) - 13 = -34")
    fun subtraction_test6() {
        assertEquals((-34).ib, (-21).ib - 13.ib)
    }

    @Test
    @DisplayName("21 - (-13) = 34")
    fun subtraction_test7() {
        assertEquals(34.ib, 21.ib - (-13).ib)
    }

    @Test
    @DisplayName("(-21) - (-13) = -8")
    fun subtraction_test8() {
        assertEquals((-8).ib, (-21).ib - (-13).ib)
    }

}
