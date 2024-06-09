import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DirectMultiplicationTest {

    @Test
    @DisplayName("13 * 21 = 273")
    fun multiplication_test1() {
        assertEquals(273.db, 13.db * 21.db)
    }
    
    @Test
    @DisplayName("13 * (-21) = -273")
    fun multiplication_test2() {
        assertEquals((-273).db, 13.db * (-21).db)
    }

    @Test
    @DisplayName("(-13) * 21 = -273")
    fun multiplication_test3() {
        assertEquals((-273).db, (-13).db * 21.db)
    }

    @Test
    @DisplayName("(-13) * (-21) = 273")
    fun multiplication_test4() {
        assertEquals(273.db, (-13).db * (-21).db)
    }

    @Test
    @DisplayName("21 * 13 = 273")
    fun multiplication_test5() {
        assertEquals(273.db, 21.db * 13.db)
    }
    @Test
    @DisplayName("(-21) * 13 = -273")
    fun multiplication_test6() {
        assertEquals((-273).db, (-21).db * 13.db)
    }

    @Test
    @DisplayName("21 * (-13) = -273")
    fun multiplication_test7() {
        assertEquals((-273).db, 21.db * (-13).db)
    }

    @Test
    @DisplayName("(-21) * (-13) = 273")
    fun multiplication_test8() {
        assertEquals(273.db, (-21).db * (-13).db)
    }

}
