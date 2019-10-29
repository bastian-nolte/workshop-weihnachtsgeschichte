package app.codedojo.codedojo.kata.weihnachtsgeschichte

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


@DisplayName("Die Methoden der Klasse ... sollten folgendes Verhalten implementieren…")
internal class WeihnachtsgeschichteTest {

    @DisplayName("foo() sollte ...")
    @ValueSource(ints = [1, 7, Int.MAX_VALUE])
    @ParameterizedTest(name = "fizzbuzz sollte bei der Übergabe von {0} den String «{0}» zurückgeben.")
    fun fizzbuzz_nmo3o5(foo: Int) {
    }
}
