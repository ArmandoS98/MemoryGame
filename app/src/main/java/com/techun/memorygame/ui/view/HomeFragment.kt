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
import com.techun.memorygame.databinding.FragmentHomeBinding
import com.techun.memorygame.domain.model.CardModel
import com.techun.memorygame.ui.view.adapters.GameAdapter
import com.techun.memorygame.ui.viewmodel.GamesViewModel
import com.techun.memorygame.ui.viewmodel.RecentlyGamesViewModel
import com.techun.memorygame.utils.DataState
import com.techun.memorygame.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val recentlyViewModel: RecentlyGamesViewModel by viewModels()

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

        binding.tvCleckHereForMoreGames.setOnClickListener(this)
    }

    private fun initObservers() {
        recentlyViewModel.getGameState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    progressbar(GONE)
                    val recentlyGames = dataState.data
                    if (recentlyGames.isNotEmpty())
                        gameAdapter.submitList(dataState.data)
                    else {
                        binding.tvNothing.visibility = VISIBLE
                        binding.lavNothing.visibility = VISIBLE
                        binding.tvCleckHereForMoreGames.visibility = VISIBLE
                    }
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
        recentlyViewModel.getAllGames()
    }

    private fun recyclerInit() = binding.rvFavoriteGames.apply {
        adapter = gameAdapter
        layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    private fun progressbar(status: Int) {
        binding.fragmentProgressBar.visibility = status
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvCleckHereForMoreGames -> {
                findNavController().navigate(R.id.action_nav_recently_to_nav_all_games)
            }
        }
    }

}


