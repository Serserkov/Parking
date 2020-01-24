package parking
import java.util.Scanner
//create class of parking spots
class ParkingSpots (var isEmpty: Boolean = true, var carNumber: String = "empty", var carColor: String = "empty") {
    //function, that frees up parking spot
    fun leave (num: Int) {
        if (this.isEmpty) println("There is no car in the spot $num.") else {
            this.isEmpty = true
            this.carNumber = "empty"
            this.carColor = "empty"
            println("Spot $num is free.")
        }
    }
}
//function, that fills parking spot
fun park (carNumber: String, carColor: String, spots: Array<ParkingSpots>) {
    var parking = false
    for (i in spots.indices) {
        if (spots[i].isEmpty) {
            spots[i].isEmpty = false
            spots[i].carNumber = carNumber
            spots[i].carColor = carColor
            println("$carColor car parked on the spot ${i + 1}.")
            parking = true
            break
        }
    }
    if (!parking) println("Sorry, parking lot is full.")
}
//function that print status of park
fun status (spots: Array<ParkingSpots>) {
    var isParkEmpty = true
    for (i in spots.indices) {
        if (!spots[i].isEmpty) {
            println("${i + 1} ${spots[i].carNumber} ${spots[i].carColor}")
            isParkEmpty = false
        }
    }
    if (isParkEmpty) println("Parking lot is empty.")
}

fun regByColor (color: String, spots: Array<ParkingSpots>) {
    var sorted = spots.filter { it.carColor.toLowerCase() == color.toLowerCase() }
    if (sorted.size != 0) println(Array<String>(sorted.size) {i -> sorted[i].carNumber}.joinToString(", "))
    else println("No cars with color $color were found.")
}

fun spotByColor (color: String, spots: Array<ParkingSpots>) {
    var numb = mutableListOf<Int>()
    for (i in spots.indices) if (spots[i].carColor.toLowerCase() == color.toLowerCase()) numb.add(i + 1)
    if (numb.size != 0) println(Array<Int>(numb.size) {i -> numb[i]}.joinToString(", "))
    else println("No cars with color $color were found.")
}

fun main() {
    val scan = Scanner(System.`in`)
    var spots = Array<ParkingSpots>(1) { ParkingSpots() }
    var isParkExist = false
    do {
        val input = scan.nextLine().split(" ")
        if (input[0] == "exit") continue
        if (input[0] == "create") {
            spots = Array<ParkingSpots>(input[1].toInt()) { ParkingSpots() } //create array of parking lots
            println("Created a parking lot with ${input[1].toInt()} spots.")
            isParkExist = true
        }
        if (isParkExist) {
            when (input[0]) {
                "leave" -> {
                    val num = input[1].toInt()
                    spots[num - 1].leave(num)
                }
                "park" -> {
                    val carNumber = input[1]
                    val carColor = input[2]
                    park(carNumber, carColor, spots)
                }
                "status" -> status(spots)
                "spot_by_reg" -> {
                    var byReg = false
                    for (i in spots.indices) if (spots[i].carNumber == input[1]) {
                        println(i + 1)
                        byReg = true
                    }
                    if (!byReg) println("No cars with registration number ${input[1]} were found.")
                }
                "reg_by_color" -> regByColor(input[1], spots)
                "spot_by_color" -> spotByColor(input[1], spots)
            }
        } else println("Sorry, parking lot is not created.")
    } while (input[0] != "exit")
}
