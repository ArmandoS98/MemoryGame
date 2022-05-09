package com.techun.memorygame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.techun.memorygame.MemoryItem
import com.techun.memorygame.databinding.FragmentMemoryBinding
import com.techun.memorygame.view.adapters.CardAdapter

class MemoryFragment : Fragment(), CardAdapter.OnItemSelected {
    private var _binding: FragmentMemoryBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: CardAdapter
    private lateinit var cards: List<MemoryItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerInit()
    }

    private fun recyclerInit() {

        cards = listOf(
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ), MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ), MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            ),
            MemoryItem(
                0,
                "ben 10",
                "https://studiosol-a.akamaihd.net/uploadfile/letras/fotos/3/2/c/a/32ca5aa2fbedc83c04be9810c33c3811.jpg"
            )
        )

        adapter = CardAdapter(cards, this)
        binding.rvMemory.layoutManager =
            GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        binding.rvMemory.adapter = adapter
    }

    override fun onClickListener(position: Int) {
        println("Holis")
    }
}