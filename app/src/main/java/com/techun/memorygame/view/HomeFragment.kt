package com.techun.memorygame.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.techun.memorygame.R
import com.techun.memorygame.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSecond.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v.apply {
            this?.findNavController()?.navigate(R.id.action_homeFragment_to_memoryFragment)
        }
    }
}