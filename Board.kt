package connectfour

class Board(val dimensions: Dimensions, val discs: MutableList<Disc> = mutableListOf()) {
    fun add(disc: Disc) {
        discs.add(disc)
    }
}