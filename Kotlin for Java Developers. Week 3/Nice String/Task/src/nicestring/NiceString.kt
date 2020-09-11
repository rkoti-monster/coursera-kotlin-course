package nicestring

fun String.isNice(): Boolean {
    val booleans: List<Boolean> =
            listOf(
                    !(this.contains("bu") || this.contains("ba") || this.contains("be")),
                    this.split("").filter{listOf("a", "e", "i", "o", "u").contains(it)}.size > 2,
                    this.split("").zipWithNext().filter { (f, s) -> f == s}.any()
            ).filter{it}
    return !this.isEmpty() && booleans.size > 1
}