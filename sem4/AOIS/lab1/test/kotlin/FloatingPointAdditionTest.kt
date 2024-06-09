
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test

class FloatingPointAdditionTest {

    @Test
    fun test1() {
        assertEquals(34f.fp, 13f.fp + 21f.fp)
    }

    @Test
    fun test2() {
        assertEquals(34f.fp, 21f.fp + 13f.fp)
    }

    @Test
    fun params() {
        for (i in 1..20) {
            for (j in 1..20) {
                val opX = i / 2f
                val opY = j / 2f

                assertEquals((opX + opY).fp, opX.fp + opY.fp)
            }
        }
    }

}