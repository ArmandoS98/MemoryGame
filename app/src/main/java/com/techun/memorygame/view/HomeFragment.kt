package com.techun.memorygame.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.techun.memorygame.R
import com.techun.memorygame.data.CardItem
import com.techun.memorygame.data.GameItem
import com.techun.memorygame.databinding.FragmentHomeBinding
import com.techun.memorygame.view.adapters.GameAdapter

class HomeFragment : Fragment(), GameAdapter.OnItemSelected {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val TAG = "HOLIS"

    private lateinit var adapter: GameAdapter
    val rootRef = FirebaseFirestore.getInstance()
    val usersRef = rootRef.collection("Games")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerInit()
    }

    private fun recyclerInit() {
        usersRef.get().addOnSuccessListener { result ->
            val gamesResently = ArrayList<GameItem>()
            for (document in result) {
                val temp = document.toObject(GameItem::class.java)
                gamesResently.add(temp)
            }

            adapter = GameAdapter(gamesResently, this)
            binding.rvMemory.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            binding.rvMemory.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
    }

    override fun onClickListener(card: MutableList<CardItem>?) {
        val newCards = ArrayList<CardItem>()
        for (c in card!!) {
            for (i in 0..1) {
                newCards.add(c)
            }
        }
        val bundle = Bundle()
        bundle.putParcelableArray(getString(R.string.id_cards), newCards.toTypedArray())
        findNavController().navigate(R.id.action_homeFragment_to_nav_game, bundle)
    }
}


