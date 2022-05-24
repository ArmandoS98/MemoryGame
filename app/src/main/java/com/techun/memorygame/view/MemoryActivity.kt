package com.techun.memorygame.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techun.memorygame.R
import com.techun.memorygame.data.utils.extensions.loadByResource
import com.techun.memorygame.databinding.ActivityMemoryBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MemoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMemoryBinding
    private lateinit var information: List<MemoryItem>
    private lateinit var cards: List<ImageView>
    private var firstCard: Int? = null
    private var secondCard: Int? = null
    private var enabled: Boolean = false
    private var cardTemp: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadCards()
        binding.imgBackArrow.setOnClickListener(this)
    }

    private fun loadCards() {
        cards = listOf(
            binding.ivc00,
            binding.ivc01,
            binding.ivc02,
            binding.ivc03,
            binding.ivc10,
            binding.ivc11,
            binding.ivc12,
            binding.ivc13,
            binding.ivc20,
            binding.ivc21,
            binding.ivc22,
            binding.ivc23
        )
        information = listOf(
            MemoryItem(
                true,
                "ben 10",
                "https://static.wikia.nocookie.net/doblaje/images/5/50/Ben_10.jpg/revision/latest?cb=20220313003855&path-prefix=es",
                0
            ),
            MemoryItem(
                true,
                "ben 10",
                "https://static.wikia.nocookie.net/doblaje/images/5/50/Ben_10.jpg/revision/latest?cb=20220313003855&path-prefix=es",
                0
            ),

            MemoryItem(
                true,
                "Generador Rex",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTkiyNdMrR-QK0h66kugnUDX6hQ9QPtkbsKECzniWKs8KoSebnT",
                1
            ), MemoryItem(
                true,
                "Generador Rex",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTkiyNdMrR-QK0h66kugnUDX6hQ9QPtkbsKECzniWKs8KoSebnT",
                1
            ),

            MemoryItem(
                true,
                "Isla Del Drama",
                "https://static.wikia.nocookie.net/doblaje/images/4/44/TotalDramaIsland.jpg/revision/latest?cb=20210211195004&path-prefix=es",
                2
            ),
            MemoryItem(
                true,
                "Isla Del Drama",
                "https://static.wikia.nocookie.net/doblaje/images/4/44/TotalDramaIsland.jpg/revision/latest?cb=20210211195004&path-prefix=es",
                2
            ),

            MemoryItem(
                true,
                "los padrinos magicos",
                "https://static.wikia.nocookie.net/doblaje/images/6/68/Padrinos_m%C3%A1gicos.jpg/revision/latest?cb=20200906215022&path-prefix=es",
                3
            ),
            MemoryItem(
                true,
                "los padrinos magicos",
                "https://static.wikia.nocookie.net/doblaje/images/6/68/Padrinos_m%C3%A1gicos.jpg/revision/latest?cb=20200906215022&path-prefix=es",
                3
            ),

            MemoryItem(
                true,
                "bob esponja",
                "https://static.wikia.nocookie.net/doblaje/images/4/45/Poster-Bob-Esponja.jpg/revision/latest?cb=20171125060321&path-prefix=es",
                4
            ),
            MemoryItem(
                true,
                "bob esponja",
                "https://static.wikia.nocookie.net/doblaje/images/4/45/Poster-Bob-Esponja.jpg/revision/latest?cb=20171125060321&path-prefix=es",
                4
            ),

            MemoryItem(
                true,
                "Gravity falls",
                "https://static.wikia.nocookie.net/doblaje/images/6/60/Gravity_Falls_Poster.jpg/revision/latest?cb=20200703192506&path-prefix=es",
                5
            ),
            MemoryItem(
                true,
                "Gravity falls",
                "https://static.wikia.nocookie.net/doblaje/images/6/60/Gravity_Falls_Poster.jpg/revision/latest?cb=20200703192506&path-prefix=es",
                5
            )
        ).shuffled()

        cards.forEachIndexed { index, card ->
            card.isEnabled = true
            card.loadByResource(information[index].urlImagen)
            Handler().postDelayed({
                card.setImageDrawable(getDrawable(R.drawable.outline_question_mark_black_24dp))
            }, 1000)
            card.setOnClickListener {
                if (!enabled)
                    checkCard(index, card)
            }
        }
    }

    private fun checkCard(index: Int, card: ImageView) {
        if (cardTemp == null) {
            //First Card Selected
            cardTemp = card
            card.loadByResource(information[index].urlImagen)
            card.isEnabled = true
            firstCard = information[index].id
        } else {
            //Another Card Selected
            enabled = true
            card.loadByResource(information[index].urlImagen)
            card.isEnabled = true
            secondCard = information[index].id
            if (firstCard == secondCard) {
                cardTemp = null
                enabled = false
                //Update counters & Validates cards
            } else {
                Handler().postDelayed({
                    cardTemp?.setImageDrawable(getDrawable(R.drawable.outline_question_mark_black_24dp))
                    cardTemp?.isEnabled = true
                    card.setImageDrawable(getDrawable(R.drawable.outline_question_mark_black_24dp))
                    card.isEnabled = true
                    enabled = false
                    cardTemp = null
                }, 500)
            }
        }
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.imgBackArrow) {
            finish()
        }
    }
}