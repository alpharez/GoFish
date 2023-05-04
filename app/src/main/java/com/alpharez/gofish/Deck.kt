package com.alpharez.gofish

class Deck {
    private var cards = mutableListOf (
        PlayingCard(RANK.ACE, SUIT.DIAMONDS, R.drawable.acediamonds), PlayingCard(RANK.TWO, SUIT.DIAMONDS, R.drawable.twodiamonds),
        PlayingCard(RANK.THREE, SUIT.DIAMONDS, R.drawable.threediamonds), PlayingCard(RANK.FOUR, SUIT.DIAMONDS, R.drawable.fourdiamonds),
        PlayingCard(RANK.FIVE, SUIT.DIAMONDS, R.drawable.fivediamonds), PlayingCard(RANK.SIX, SUIT.DIAMONDS, R.drawable.sixdiamonds),
        PlayingCard(RANK.SEVEN, SUIT.DIAMONDS, R.drawable.sevendiamonds), PlayingCard(RANK.EIGHT, SUIT.DIAMONDS, R.drawable.eightdiamonds),
        PlayingCard(RANK.NINE, SUIT.DIAMONDS, R.drawable.ninediamonds), PlayingCard(RANK.TEN, SUIT.DIAMONDS, R.drawable.tendiamonds),
        PlayingCard(RANK.JACK, SUIT.DIAMONDS, R.drawable.jackdiamonds), PlayingCard(RANK.QUEEN, SUIT.DIAMONDS, R.drawable.queendiamonds),
        PlayingCard(RANK.KING, SUIT.DIAMONDS, R.drawable.kingdiamonds),
        PlayingCard(RANK.ACE, SUIT.CLUBS, R.drawable.aceclubs), PlayingCard(RANK.TWO, SUIT.CLUBS, R.drawable.twoclubs),
        PlayingCard(RANK.THREE, SUIT.CLUBS, R.drawable.threeclubs), PlayingCard(RANK.FOUR, SUIT.CLUBS, R.drawable.fourclubs),
        PlayingCard(RANK.FIVE, SUIT.CLUBS, R.drawable.fiveclubs), PlayingCard(RANK.SIX, SUIT.CLUBS, R.drawable.sixclubs),
        PlayingCard(RANK.SEVEN, SUIT.CLUBS, R.drawable.sevenclubs), PlayingCard(RANK.EIGHT, SUIT.CLUBS, R.drawable.eightclubs),
        PlayingCard(RANK.NINE, SUIT.CLUBS, R.drawable.nineclubs), PlayingCard(RANK.TEN, SUIT.CLUBS, R.drawable.tenclubs),
        PlayingCard(RANK.JACK, SUIT.CLUBS, R.drawable.jackclubs), PlayingCard(RANK.QUEEN, SUIT.CLUBS, R.drawable.queenclubs),
        PlayingCard(RANK.KING, SUIT.CLUBS, R.drawable.kingclubs),
        PlayingCard(RANK.ACE, SUIT.HEARTS, R.drawable.acehearts), PlayingCard(RANK.TWO, SUIT.HEARTS, R.drawable.twohearts),
        PlayingCard(RANK.THREE, SUIT.HEARTS, R.drawable.threehearts), PlayingCard(RANK.FOUR, SUIT.HEARTS, R.drawable.fourhearts),
        PlayingCard(RANK.FIVE, SUIT.HEARTS, R.drawable.fivehearts), PlayingCard(RANK.SIX, SUIT.HEARTS, R.drawable.sixhearts),
        PlayingCard(RANK.SEVEN, SUIT.HEARTS, R.drawable.sevenhearts), PlayingCard(RANK.EIGHT, SUIT.HEARTS, R.drawable.eighthearts),
        PlayingCard(RANK.NINE, SUIT.HEARTS, R.drawable.ninehearts), PlayingCard(RANK.TEN, SUIT.HEARTS, R.drawable.tenhearts),
        PlayingCard(RANK.JACK, SUIT.HEARTS, R.drawable.jackhearts), PlayingCard(RANK.QUEEN, SUIT.HEARTS, R.drawable.queenhearts),
        PlayingCard(RANK.KING, SUIT.HEARTS, R.drawable.kinghearts),
        PlayingCard(RANK.ACE, SUIT.SPADES, R.drawable.acespades), PlayingCard(RANK.TWO, SUIT.SPADES, R.drawable.twospades),
        PlayingCard(RANK.THREE, SUIT.SPADES, R.drawable.threespades), PlayingCard(RANK.FOUR, SUIT.SPADES, R.drawable.fourspades),
        PlayingCard(RANK.FIVE, SUIT.SPADES, R.drawable.fivespades), PlayingCard(RANK.SIX, SUIT.SPADES, R.drawable.sixspades),
        PlayingCard(RANK.SEVEN, SUIT.SPADES, R.drawable.sevenspades), PlayingCard(RANK.EIGHT, SUIT.SPADES, R.drawable.eightspades),
        PlayingCard(RANK.NINE, SUIT.SPADES, R.drawable.ninespades), PlayingCard(RANK.TEN, SUIT.SPADES, R.drawable.tenspades),
        PlayingCard(RANK.JACK, SUIT.SPADES, R.drawable.jackspades), PlayingCard(RANK.QUEEN, SUIT.SPADES, R.drawable.queenspades),
        PlayingCard(RANK.KING, SUIT.SPADES, R.drawable.kingspades)
    )

    fun drawCard() : PlayingCard {
        val card = cards.random()
        cards.remove(card)
        return card
    }

    fun hasCards() : Boolean {
        return cards.isNotEmpty()
    }

    fun isEmpty() : Boolean {
        return cards.isEmpty()
    }
}