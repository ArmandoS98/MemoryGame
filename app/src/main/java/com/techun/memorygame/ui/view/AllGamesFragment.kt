package com.techun.memorygame.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.techun.memorygame.R
import com.techun.memorygame.databinding.FragmentAllGamesBinding
import com.techun.memorygame.domain.model.CardModel
import com.techun.memorygame.ui.view.adapters.GameAdapter
import com.techun.memorygame.ui.viewmodel.GamesViewModel
import com.techun.memorygame.ui.viewmodel.RecentlyGamesViewModel
import com.techun.memorygame.utils.DataState
import com.techun.memorygame.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllGamesFragment : Fragment() {
    private var _binding: FragmentAllGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()
    private val recentlyViewModel: RecentlyGamesViewModel by viewModels()

    @Inject
    lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllGamesBinding.inflate(inflater, container, false)
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
            //Save in Recently
            recentlyViewModel.saveGamed(card)
            val newCards = ArrayList<CardModel>()
            for (c in card.cards!!) {
                for (i in 0..1) {
                    newCards.add(c)
                }
            }
            val bundle = Bundle()
            bundle.putParcelableArray(getString(R.string.id_cards), newCards.toTypedArray())
            findNavController().navigate(R.id.action_nav_all_games_to_nav_game, bundle)
        }

        gameAdapter.setDeleteClickListener {

        }
    }

    private fun initObservers() {
        recentlyViewModel.saveGameState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    //Nothing
                }
                is DataState.Error -> {
                    requireActivity().toast(getString(R.string.msg_something_went_wrong))
                }
                else -> Unit
            }
        }
        viewModel.getGameState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    progressbar(GONE)
                    gameAdapter.submitList(dataState.data)
                }
                is DataState.Error -> {
                    progressbar(GONE)
                    requireActivity().toast(getString(R.string.msg_something_went_wrong))
                }
                else -> Unit
            }
        }
    }

    private fun init() {
        progressbar(VISIBLE)
        viewModel.getAllGames()
    }

    private fun recyclerInit() = binding.rvMemory.apply {
        adapter = gameAdapter
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    private fun progressbar(status: Int) {
        binding.fragmentProgressBar.visibility = status
    }
}