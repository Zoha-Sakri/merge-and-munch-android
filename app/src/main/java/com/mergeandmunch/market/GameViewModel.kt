package com.mergeandmunch.market

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class GameViewModel {
    var state by mutableStateOf(GameState()); private set
    fun show(screen: String) { state = state.copy(screen = screen, selected = null) }
    fun harvest(plotIndex: Int) {
        val plot = state.plots[plotIndex]
        if (!plot.ready) return
        val nextPlots = state.plots.toMutableList()
        nextPlots[plotIndex] = plot.copy(ready = false)
        val empty = state.board.indexOfFirst { it == null }
        if (empty < 0) { state = state.copy(message = "Your market board is full"); return }
        val nextBoard = state.board.toMutableList()
        nextBoard[empty] = Ingredient(System.currentTimeMillis(), plot.family, 0)
        state = state.copy(board = nextBoard, plots = nextPlots, message = "Harvest added to your market!")
    }
    fun serve(orderIndex: Int) {
        val order = state.orders[orderIndex]
        val found = state.board.indexOfFirst { it?.family == order.family && it.stage >= order.stage }
        if (found < 0) { state = state.copy(message = "Keep farming and merging first!"); return }
        val nextBoard = state.board.toMutableList()
        nextBoard[found] = null
        state = state.copy(board = nextBoard, orders = state.orders.filterIndexed { index, _ -> index != orderIndex }, coins = state.coins + order.reward, stars = state.stars + 1, message = "Order served! +${order.reward} coins")
    }
    fun buyDecoration() {
        if (state.coins < 180) { state = state.copy(message = "You need more coins for that decoration"); return }
        state = state.copy(coins = state.coins - 180, message = "Happy plant added to your farm!")
    }
    fun tap(index: Int) { val selected = state.selected; if (selected == null) { if (state.board[index] != null) state = state.copy(selected = index, message = "Choose its matching ingredient"); return }; if (selected == index) { state = state.copy(selected = null); return }; val source = state.board[selected]; val target = state.board[index]; val next = state.board.toMutableList(); when { source == null -> Unit; target == null -> { next[index] = source; next[selected] = null; state = state.copy(board = next, selected = null, message = "Moved!") }; source.family == target.family && source.stage == target.stage && source.stage < 5 -> { next[selected] = null; next[index] = target.copy(id = System.currentTimeMillis(), stage = target.stage + 1); val reward = 15 * (target.stage + 1); state = state.copy(board = next, coins = state.coins + reward, selected = null, message = "Sweet merge! +$reward coins") }; else -> state = state.copy(selected = null, message = "Those ingredients don't match yet") } }
}
