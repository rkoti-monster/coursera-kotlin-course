package taxipark

import kotlin.math.floor

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filter{driver -> this.trips.none{trip -> trip.driver == driver}}.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter{passenger -> this.trips.count { it.passengers.contains(passenger) } >= minTrips}.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter{passenger -> this.trips.count { it.passengers.contains(passenger) && it.driver == driver } > 1}.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter{passenger -> this.trips.count{it.discount != null && it.passengers.contains(passenger)} >
                this.trips.count{it.discount == null && it.passengers.contains(passenger)}}.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return this.trips.groupBy {
        it.duration - it.duration % 10 .. it.duration + (9 - it.duration % 10)
    }.mapValues { e -> e.value.size }.maxBy { e -> e.value}?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val totalCost = this.trips.map{it.cost}.sum()
    val eightyPercentCost  = totalCost * 0.8
    val twentyPercentDrivers = floor(this.allDrivers.size * 0.2).toInt()
    val topTwentyPercentDriversContrib = this.trips.groupBy{it.driver}.mapValues {
        k -> k.value.sumByDouble{it.cost}
    }.entries.sortedByDescending { e -> e.value }.take(twentyPercentDrivers).sumByDouble { e -> e.value }
    return this.trips.isNotEmpty() && topTwentyPercentDriversContrib >= eightyPercentCost
}