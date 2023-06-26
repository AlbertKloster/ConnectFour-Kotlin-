package connectfour

val input = Input()

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = Player(readln())
    println("Second player's name:")
    val secondPlayer = Player(readln())

    while (true)
    try {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val dimensions = input.readDimensions()
        println("${firstPlayer.name} VS ${secondPlayer.name}")
        println("${dimensions.rows} X ${dimensions.columns} board")
        val board = Board(dimensions)
        val boardHandler = BoardHandler(board)
        boardHandler.printBoard()
        break
    } catch (e: RuntimeException) {
        println(e.message)
    }

}