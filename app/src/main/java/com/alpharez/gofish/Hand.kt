package com.alpharez.gofish

import androidx.compose.runtime.mutableStateListOf

class Hand(cpu : Boolean) {
    var cards = mutableStateListOf<PlayingCard>()
    val cpu = cpu

    fun hasRank(rank : RANK) : Boolean {
        for(card in cards) {
            if(card.rank == rank) return true
        }
        return false
    }

    fun sort() {
        cards.sortBy { it.rank }
    }
}
