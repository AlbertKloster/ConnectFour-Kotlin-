package connectfour

class Input {
    fun readDimensions(): Dimensions {
        val string = readln().trim()
        if (string.isEmpty())
            return Dimensions()
        if (!string.matches(Regex("\\d+\\s*[xX]\\s*\\d+")))
            throw RuntimeException("Invalid input")
        val (rowsString, columnsString) = string.split(Regex("\\s*[xX]\\s*"))
        return Dimensions(parseToRows(rowsString), parseToColumns(columnsString))
    }

    private fun parseToRows(rowsString: String): Int {
        val rows = rowsString.toInt()
        if (rows !in 5..9)
            throw RuntimeException("Board rows should be from 5 to 9")
        return rows
    }

    private fun parseToColumns(rowsString: String): Int {
        val columns = rowsString.toInt()
        if (columns !in 5..9)
            throw RuntimeException("Board columns should be from 5 to 9")
        return columns
    }

}