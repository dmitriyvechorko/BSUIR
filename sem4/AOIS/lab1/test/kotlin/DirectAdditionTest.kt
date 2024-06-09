import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DirectAdditionTest {

    @Test
    @DisplayName("13 + 21 = 34")
    fun addition_test1() {
        assertEquals(34.db, 13.db + 21.db)
    }
    @Test
    @DisplayName("13 + (-21) = -8")
    fun addition_test2() {
        assertEquals((-8).db, 13.db + (-21).db)
    }

    @Test
    @DisplayName("(-13) + 21 = 8")
    fun addition_test3() {
        assertEquals(8.db, (-13).db + 21.db)
    }

    @Test
    @DisplayName("(-13) + (-21) = -34")
    fun addition_test4() {
        assertEquals((-34).db, (-13).db + (-21).db)
    }

    @Test
    @DisplayName("21 + 13 = 34")
    fun addition_test5() {
        assertEquals(34.db, 21.db + 13.db)
    }
    @Test
    @DisplayName("(-21) + 13 = -8")
    fun addition_test6() {
        assertEquals((-8).db, (-21).db + 13.db)
    }

    @Test
    @DisplayName("21 + (-13) = 8")
    fun addition_test7() {
        assertEquals(8.db, 21.db + (-13).db)
    }

    @Test
    @DisplayName("(-21) + (-13) = -34")
    fun addition_test8() {
        assertEquals((-34).db, (-21).db + (-13).db)
    }

}
