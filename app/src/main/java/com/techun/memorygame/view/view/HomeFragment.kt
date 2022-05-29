package com.techun.memorygame.view.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.techun.memorygame.R
import com.techun.memorygame.databinding.FragmentHomeBinding
import com.techun.memorygame.domain.model.CardModel
import com.techun.memorygame.utils.DataStates
import com.techun.memorygame.view.view.adapters.GameAdapter
import com.techun.memorygame.view.viewmodel.GamesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()

    @Inject
    lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        recyclerInit()
        initObservers()
        initListener()
    }

    private fun initListener() {
        gameAdapter.setItemClickListener { card ->
            val newCards = ArrayList<CardModel>()
            for (c in card.cards!!) {
                for (i in 0..1) {
                    newCards.add(c)
                }
            }
            val bundle = Bundle()
            bundle.putParcelableArray(getString(R.string.id_cards), newCards.toTypedArray())
            findNavController().navigate(R.id.action_homeFragment_to_nav_game, bundle)
        }

        gameAdapter.setDeleteClickListener {

        }
    }

    private fun initObservers() {
        viewModel.getGameState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataStates.Success -> {
                    gameAdapter.submitList(dataState.data)
                }
                is DataStates.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Ooops, algo salio mal, intanta de nuevo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    private fun init() {
        viewModel.getAllGames()
    }

    private fun recyclerInit() = binding.rvMemory.apply {
        adapter = gameAdapter
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }
}


