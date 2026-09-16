package com.mergeandmunch.market

data class Ingredient(val id: Long, val family: Family, val stage: Int)
enum class Family(val emoji: String) { TOMATO("🍅"), CHEESE("🧀"), BREAD("🍞"), LETTUCE("🥬"), FRIES("🍟"), JUICE("🍊") }
data class Order(val name: String, val family: Family, val stage: Int, val reward: Int)
data class FarmPlot(val family: Family, val ready: Boolean = true)
data class GameState(
	val board: List<Ingredient?> = starterBoard(),
	val coins: Int = 1240,
	val stars: Int = 18,
	val selected: Int? = null,
	val message: String = "Tap two matching ingredients to merge!",
	val screen: String = "farm",
	val plots: List<FarmPlot> = listOf(FarmPlot(Family.TOMATO), FarmPlot(Family.LETTUCE), FarmPlot(Family.JUICE)),
	val orders: List<Order> = listOf(Order("Garden sauce", Family.TOMATO, 1, 65), Order("Cheese bites", Family.CHEESE, 0, 65))
)
fun starterBoard(): List<Ingredient?> { val data = listOf(Family.TOMATO, Family.CHEESE, Family.TOMATO, null, Family.BREAD, Family.LETTUCE, Family.CHEESE, Family.BREAD, Family.JUICE, Family.FRIES, Family.FRIES, null, Family.LETTUCE, Family.JUICE, null, Family.TOMATO, Family.CHEESE, null, Family.BREAD, null, Family.TOMATO, Family.FRIES, null, Family.JUICE, null); return data.mapIndexed { index, family -> family?.let { Ingredient(index.toLong(), it, 0) } } }
