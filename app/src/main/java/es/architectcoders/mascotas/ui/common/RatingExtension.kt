package es.architectcoders.mascotas.ui.common


fun String.ratingCountToInt(): Int {
    return this.replace("(", "").replace(")", "").toInt()
}

fun Int.intToRating(): String {
    return "($this)"
}