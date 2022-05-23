package com.techun.memorygame.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techun.memorygame.R
import com.techun.memorygame.data.utils.extensions.loadByResource
import com.techun.memorygame.databinding.ActivityMemoryBinding


class MemoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMemoryBinding
    private lateinit var information: List<MemoryItem>
    private lateinit var cards: List<ImageView>

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
                "https://static.wikia.nocookie.net/doblaje/images/5/50/Ben_10.jpg/revision/latest?cb=20220313003855&path-prefix=es"
            ),
            MemoryItem(
                true,
                "ben 10",
                "https://static.wikia.nocookie.net/doblaje/images/5/50/Ben_10.jpg/revision/latest?cb=20220313003855&path-prefix=es"
            ),

            MemoryItem(
                true,
                "Generador Rex",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTkiyNdMrR-QK0h66kugnUDX6hQ9QPtkbsKECzniWKs8KoSebnT"
            ), MemoryItem(
                true,
                "Generador Rex",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTkiyNdMrR-QK0h66kugnUDX6hQ9QPtkbsKECzniWKs8KoSebnT"
            ),

            MemoryItem(
                true,
                "Isla Del Drama",
                "https://static.wikia.nocookie.net/doblaje/images/4/44/TotalDramaIsland.jpg/revision/latest?cb=20210211195004&path-prefix=es"
            ),
            MemoryItem(
                true,
                "Isla Del Drama",
                "https://static.wikia.nocookie.net/doblaje/images/4/44/TotalDramaIsland.jpg/revision/latest?cb=20210211195004&path-prefix=es"
            ),

            MemoryItem(
                true,
                "los padrinos magicos",
                "https://static.wikia.nocookie.net/doblaje/images/6/68/Padrinos_m%C3%A1gicos.jpg/revision/latest?cb=20200906215022&path-prefix=es"
            ),
            MemoryItem(
                true,
                "los padrinos magicos",
                "https://static.wikia.nocookie.net/doblaje/images/6/68/Padrinos_m%C3%A1gicos.jpg/revision/latest?cb=20200906215022&path-prefix=es"
            ), MemoryItem(
                true,
                "bob esponja",
                "https://static.wikia.nocookie.net/doblaje/images/4/45/Poster-Bob-Esponja.jpg/revision/latest?cb=20171125060321&path-prefix=es"
            ),
            MemoryItem(
                true,
                "bob esponja",
                "https://static.wikia.nocookie.net/doblaje/images/4/45/Poster-Bob-Esponja.jpg/revision/latest?cb=20171125060321&path-prefix=es"
            ),

            MemoryItem(
                true,
                "Gravity falls",
                "https://static.wikia.nocookie.net/doblaje/images/6/60/Gravity_Falls_Poster.jpg/revision/latest?cb=20200703192506&path-prefix=es"
            ),
            MemoryItem(
                true,
                "Gravity falls",
                "https://static.wikia.nocookie.net/doblaje/images/6/60/Gravity_Falls_Poster.jpg/revision/latest?cb=20200703192506&path-prefix=es"
            )
        ).shuffled()

        cards.forEachIndexed { index, card ->
            card.loadByResource(information[index].urlImagen)
            card.setOnClickListener {
                Toast.makeText(this,"Clicked ${information[index].name}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imgBackArrow) {
            finish()
        }
    }
}