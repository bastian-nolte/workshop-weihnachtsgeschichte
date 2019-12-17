package app.codedojo.kata.weihnachtsgeschichte.livecodings

fun main() {
    val pit = Terrier("Pit", "braun", 2)
    println("Der Hund ${pit.name} ist ${pit.alter} Jahr(e) alt.")
    if(pit.isWelpe()) println("Oh wie süss! ${pit.name} ist ein Welpe!")
}

class Terrier(
        override val name: String,
        override val fellfarbe: String,
        override var alter: Int)
    : Hund

interface Hund {
    val name: String
    val fellfarbe: String
    var alter: Int

    fun isWelpe(): Boolean {
        return alter < 3
    }
}
