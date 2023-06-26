package connectfour

class BoardHandler(val board: Board) {
    fun printBoard() {
        (1..board.dimensions.columns).forEach { print(" $it") }
        for (row in 1..board.dimensions.rows) {
            print("\n║")
            for (column in 1..board.dimensions.columns) {
                print(" ║")
            }
        }
        print("\n╚")
        print("═╩".repeat(board.dimensions.columns - 1))
        println("═╝")

    }
}