package com.techun.memorygame.view

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.techun.memorygame.MemoryItem
import com.techun.memorygame.R
import com.techun.memorygame.databinding.ActivityMainBinding
import com.techun.memorygame.databinding.ActivityMemoryBinding
import com.techun.memorygame.view.adapters.CardAdapter

class MemoryActivity : AppCompatActivity(), CardAdapter.OnItemSelected {
    private lateinit var binding: ActivityMemoryBinding
    private lateinit var cards: List<MemoryItem>
    private lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerInit()
    }

    private fun recyclerInit() {
        cards = listOf(
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
        )

        adapter = CardAdapter(cards.shuffled(), this)
        binding.rvMemory.layoutManager =
            GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        binding.rvMemory.adapter = adapter
    }


    override fun onClickListener(
        imageFront: ImageView,
        imageBack: ImageView,
        position: String,
        adapterPosition: Int
    ) {

    }
}