package com.alpharez.gofish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.findNavController
import com.alpharez.gofish.databinding.FragmentGameBinding
import com.google.accompanist.flowlayout.FlowRow

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var deck = Deck()
    private var playerHand = Hand(false)
    private var cpuHand = Hand(true)
    private var playerBooks = Books()
    private var cpuBooks = Books()
    private var playerCardRequests = mutableListOf<RANK>()
    private var cpuCardRequests = mutableListOf<RANK>()
    private var gameOver = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_game, container, false).apply {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        // Draw Cards
        for(i in 0 .. 6) {
            playerHand.cards += deck.drawCard()
            cpuHand.cards += deck.drawCard()
        }

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    ShowUI()
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Composable
    fun ShowUI() {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            //Column(
                //modifier = Modifier.fillMaxSize(),
                //verticalArrangement = Arrangement.Center,
                //horizontalAlignment = Alignment.CenterHorizontally
            //)  {

            ConstraintLayout {
                val (buttonRow, handColumns) = createRefs()

                Column(modifier = Modifier.constrainAs(handColumns) {
                    top.linkTo(parent.top, margin = 2.dp)
                }
                ) {
                    Text("Player Hand")
                    ShowHand(hand = playerHand)
                    //Divider()
                    Text(text = "Player Books")
                    ShowBooks(books = playerBooks)
                    //Divider()
                    Text(text = "CPU Hand")
                    ShowHand(hand = cpuHand)
                    //Divider()
                    Text(text = "CPU Books")
                    ShowBooks(books = cpuBooks)
                    //Divider()
                    //Text("Notifications")
                }
                // Buttons
                FlowRow(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .wrapContentHeight()
                    .constrainAs(buttonRow) {bottom.linkTo(parent.bottom, margin = 2.dp)},
                    crossAxisSpacing = 1.dp, mainAxisSpacing = 1.dp,

                ) {
                    Button(onClick = { play(RANK.ACE)   }) { Text("A")   }
                    Button(onClick = { play(RANK.TWO)   }) { Text("2")   }
                    Button(onClick = { play(RANK.THREE) }) { Text("3") }
                    Button(onClick = { play(RANK.FOUR)  }) { Text("4")  }
                    Button(onClick = { play(RANK.FIVE)  }) { Text("5")  }
                    Button(onClick = { play(RANK.SIX)   }) { Text("6")   }
                    Button(onClick = { play(RANK.SEVEN) }) { Text("7") }
                    Button(onClick = { play(RANK.EIGHT) }) { Text("8") }
                    Button(onClick = { play(RANK.NINE)  }) { Text("9")  }
                    Button(onClick = { play(RANK.TEN)   }) { Text("10")   }
                    Button(onClick = { play(RANK.JACK)  }) { Text("J")  }
                    Button(onClick = { play(RANK.QUEEN) }) { Text("Q") }
                    Button(onClick = { play(RANK.KING)  }) { Text("K")  }
                }
            }

        }
    }

    /*
    fun onSNACK(view: View){
        //Snackbar(view)
        val snackbar = Snackbar.make(view, "Replace with your own action",
            Snackbar.LENGTH_LONG).setAction("Action", null)
        snackbar.setActionTextColor()
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.Gray)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.Blue)
        textView.textSize = 28f
        snackbar.show()
    }  */

    @Composable
    fun ShowHand(hand : Hand) {
        val hand1 = remember { hand }
        FlowRow(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .wrapContentHeight(),
            crossAxisSpacing = 1.dp, mainAxisSpacing = 1.dp) {
            for (card in hand1.cards) {
                if(hand.cpu)
                    Image(painter = painterResource(id = R.drawable.cardbackred), contentDescription = "card back")
                //Image(painter = painterResource(id = card.imageResource), contentDescription = "card back")
                else
                    Image(painter = painterResource(id = card.imageResource), contentDescription = "playing card")
            }
        }
    }

    @Composable
    fun ShowBooks(books : Books) {
        val books1 = remember { books }
        FlowRow(crossAxisSpacing = 1.dp, mainAxisSpacing = 1.dp) {
            for(card in books1.cards)
                Image(painter = painterResource(id = card.imageResource), contentDescription = "Books")
        }
    }

    private fun play(rank: RANK) {
        var cpuPlayAgain: Boolean
        val numPlayerBooks = playerBooks.cards.size.toString()
        val numCpuBooks = cpuBooks.cards.size.toString()
        playerHand.sort()
        val message: String = if(playerBooks.cards.size > cpuBooks.cards.size)
            "Player Wins with $numPlayerBooks Books to $numCpuBooks CPU Books"
            else "CPU Wins with $numCpuBooks Books to $numPlayerBooks Player Books"
        // check if player has this rank in hand
        if(playerHand.cards.isEmpty() or cpuHand.cards.isEmpty() or deck.isEmpty()) {
            gameOver = true
            //Log.d("GAME", "GAMEOVER SHOULD BE TRUE NOW!!LK!@J")
            val action = GameFragmentDirections.actionGameFragmentToResultFragment(message)
            view?.findNavController()?.navigate(action)
        }
        if(!gameOver) {
            if (playerHand.hasRank(rank)) {
                playerCardRequests.add(rank) // add player guess to list for CPU AI
                val playAgain: Boolean = doTurn(playerHand, cpuHand, rank)
                if (playerHand.cards.isNotEmpty())
                    checkForBooks(playerHand, playerBooks)
                if (!playAgain) { // skip
                    do {
                        cpuPlayAgain = doCpuTurn(cpuHand, playerHand)
                    } while (cpuPlayAgain)
                    if (cpuHand.cards.isNotEmpty())
                        checkForBooks(cpuHand, cpuBooks)
                }
            } else {
                //Log.d("GAME", "GAME OVER@@@!!!")
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(message)
                view?.findNavController()?.navigate(action)
            }
        }
    }

    private fun goFish(hand : Hand, deck : Deck, rank: RANK) : Boolean {
        var goAgain = false
        // TODO report what was asked for
        Toast.makeText(activity, "Requested $rank, Go Fish!", Toast.LENGTH_SHORT).show()
        if (deck.hasCards()) {
            val card = deck.drawCard()
            if(card.rank == rank) {
                goAgain = true
            }
            hand.cards += card
        } // else gameOver = true
        return goAgain
    }

    private fun getCardsOfRank(hand : Hand, rank : RANK) : List<PlayingCard> {
        val matchingCards = mutableListOf<PlayingCard>()
        for(card in hand.cards) {
            if(card.rank == rank) {
                matchingCards += card
            }
        }
        for(card in matchingCards) {
            if(hand.cards.isNotEmpty())
                hand.cards.remove(card)
        }
        return matchingCards
    }

    private fun doTurn(hand : Hand, otherHand : Hand, rank : RANK) : Boolean {
        val goAgain : Boolean
        val cards = getCardsOfRank(otherHand, rank)
        if(cards.isEmpty()) {
            goAgain = goFish(hand, deck, rank)
        } else {
            for(card in cards) hand.cards += card
            goAgain = true
        }
        return goAgain
    }

    private fun doCpuTurn(hand : Hand, otherHand: Hand) : Boolean {
        val goAgain : Boolean
        // only ask for card ranks that are in the hand
        // TODO: Toast what the CPU has taken from player
        // TODO: show on the Toast what turn is which
        return if(hand.cards.isNotEmpty()) {
            // TODO: Do some AI here.  Remember guesses.  Remember what we have taken already.
            // TODO: Remember what the player wants
            val rankRequest = hand.cards.random().rank
            cpuCardRequests.add(rankRequest) // remember previous requests for CPU AI
            goAgain = doTurn(hand, otherHand, rankRequest)
            if(goAgain) {
                Toast.makeText(activity, "CPU has taken a card(s) $rankRequest", Toast.LENGTH_SHORT).show()
            }
            goAgain
        } else false
    }

    private fun checkForBooks(hand: Hand, book: Books) : Int {
        //val books = cards.filter { it.rank > 4}
        val books = hand.cards.groupBy { it.rank }.values.filter { it.size >= 4}
        //print(" $books ")
        if(books.isNotEmpty()) {
            if (books[0].size >= 4) {
                //Log.d("Main", "BOOKS!!!")
                for (card in books[0]) {
                    hand.cards.remove(card)
                }
                book.cards += books[0][0]
                return 1
            }
        }
        return 0
    }
}