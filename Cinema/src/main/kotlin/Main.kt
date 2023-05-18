import java.text.DecimalFormat

fun main() {
    println("Enter the number of rows:")
    val numRows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val numSeats = readLine()!!.toInt()

    val seatingArrangement = Array(numRows) { CharArray(numSeats) { 'S' } }

    var purchasedTickets = 0
    var currentIncome = 0
    val totalIncome = calculateTotalIncome(numRows, numSeats)

    while (true) {
        printMenu()
        val choice = readLine()!!.toInt()

        when (choice) {
            1 -> showSeats(seatingArrangement)
            2 -> {
                val ticketInfo = buyTicket(seatingArrangement)
                if (ticketInfo != null) {
                    val (ticketPrice, ticketCount) = ticketInfo
                    purchasedTickets += ticketCount
                    currentIncome += ticketPrice
                }
            }
            3 -> showStatistics(numRows, numSeats, totalIncome, purchasedTickets, currentIncome)
            0 -> break
            else -> println("Invalid choice. Please try again.")
        }
    }
}

fun printMenu() {
    println("\n1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun showSeats(seatingArrangement: Array<CharArray>) {
    println("\nCinema:")
    print("  ")
    for (seatNum in 1..seatingArrangement[0].size) {
        print("$seatNum ")
    }
    println()

    for (rowNum in seatingArrangement.indices) {
        print(rowNum + 1)
        for (seatNum in seatingArrangement[rowNum].indices) {
            print(" ${seatingArrangement[rowNum][seatNum]}")
        }
        println()
    }
}

fun buyTicket(seatingArrangement: Array<CharArray>): Pair<Int, Int>? {
    var rowNum: Int
    var seatNum: Int
    while (true) {
        println("\nEnter a row number:")
        rowNum = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        seatNum = readLine()!!.toInt()

        if (rowNum < 1 || rowNum > seatingArrangement.size || seatNum < 1 || seatNum > seatingArrangement[0].size) {
            println("Wrong input!")
        } else {
            val seatChar = seatingArrangement[rowNum - 1][seatNum - 1]
            if (seatChar == 'B') {
                println("That ticket has already been purchased!")
            } else {
                break
            }
        }
    }

    seatingArrangement[rowNum - 1][seatNum - 1] = 'B'
    val ticketPrice = calculateTicketPrice(seatingArrangement.size, seatingArrangement[0].size, rowNum)
    println("Ticket price: \$$ticketPrice")

    return Pair(ticketPrice, 1) // return ticket price and count (always 1 in this case)
}

fun calculateTicketPrice(numRows: Int, numSeats: Int, rowNum: Int): Int {
    val totalSeats = numRows * numSeats
    return if (totalSeats <= 60 || rowNum <= numRows / 2) {
        10
    } else {
        8
    }
}

fun calculateTotalIncome(numRows: Int, numSeats: Int): Int {
    val totalSeats = numRows * numSeats
    return if (totalSeats <= 60) {
        totalSeats * 10
    } else {
        val frontHalf = numRows / 2
        val backHalf = numRows - frontHalf
        (frontHalf * numSeats * 10) + (backHalf * numSeats * 8)
    }
}

fun showStatistics(
    numRows: Int,
    numSeats: Int,
    totalIncome: Int,
    purchasedTickets: Int,
    currentIncome: Int
) {
    val percentage = purchasedTickets.toDouble() / (numRows * numSeats) * 100
    val formatPercentage = DecimalFormat("0.00").format(percentage)

    println("\nNumber of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}