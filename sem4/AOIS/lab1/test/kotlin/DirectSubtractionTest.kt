import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DirectSubtractionTest {

    @Test
    @DisplayName("13 - 21 = -8")
    fun subtraction_test1() {
        assertEquals((-8).db, 13.db - 21.db)
    }

    @Test
    @DisplayName("13 - (-21) = 34")
    fun subtraction_test2() {
        assertEquals(34.db, 13.db - (-21).db)
    }

    @Test
    @DisplayName("(-13) - 21 = -34")
    fun subtraction_test3() {
        assertEquals((-34).db, (-13).db - 21.db)
    }

    @Test
    @DisplayName("(-13) - (-21) = -34")
    fun subtraction_test4() {
        assertEquals(8.db, (-13).db - (-21).db)
    }

    @Test
    @DisplayName("21 - 13 = 34")
    fun subtraction_test5() {
        assertEquals(8.db, 21.db - 13.db)
    }
    @Test
    @DisplayName("(-21) - 13 = -34")
    fun subtraction_test6() {
        assertEquals((-34).db, (-21).db - 13.db)
    }

    @Test
    @DisplayName("21 - (-13) = 34")
    fun subtraction_test7() {
        assertEquals(34.db, 21.db - (-13).db)
    }

    @Test
    @DisplayName("(-21) - (-13) = -8")
    fun subtraction_test8() {
        assertEquals((-8).db, (-21).db - (-13).db)
    }

}
