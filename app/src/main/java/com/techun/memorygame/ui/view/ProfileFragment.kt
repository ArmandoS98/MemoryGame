package com.techun.memorygame.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.techun.memorygame.R
import com.techun.memorygame.databinding.FragmentCreateBinding
import com.techun.memorygame.domain.model.CardModel
import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.ui.viewmodel.AuthViewModel
import com.techun.memorygame.ui.viewmodel.GamesViewModel
import com.techun.memorygame.utils.*
import com.techun.memorygame.utils.Constants.IMAGE_USER_DEFAULT
import com.techun.memorygame.utils.Constants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private val gameViewModel: GamesViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.civProfile.loadByResource(IMAGE_USER_DEFAULT)
        binding.tvUserName.text = USER_NAME
        initObservers()
        initListener()
    }

    private fun initListener() {
        binding.btnmLogout.setOnClickListener(this)
    }

    private fun initObservers() {
        viewModel.logOutState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    requireActivity().goToActivity<LoginActivity>(finish = true)
                }
                is DataState.Error -> {
                    requireActivity().toast(getString(R.string.msg_something_went_wrong))
                }
                else -> Unit
            }
        }

        gameViewModel.saveGameState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    requireActivity().toast("OK")
                }
                is DataState.Error -> {
                    requireActivity().toast(getString(R.string.msg_something_went_wrong))
                }
                else -> Unit
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnmLogout) {
            viewModel.logOut()

            /*val test = GameModel(
                "",
                "Zelda",
                "https://www.zeldadungeon.net/wp-content/uploads/2019/04/npc.jpg",
                "A list of characters from The Legend of Zelda series.",
                mutableListOf(
                    CardModel(
                        "Anya",
                        "https://oyster.ignimgs.com/mediawiki/apis.ign.com/the-legend-of-zelda-ocarina-of-time-3d/8/81/Young_link_and_adult_link_by_legend_tony980-d4ebyg1.png",
                        0
                    ),
                    CardModel(
                        "Twilight",
                        "https://www.giantbomb.com/a/uploads/scale_medium/46/462814/3222928-2156159156-BotW_.png",
                        1
                    ),
                    CardModel(
                        "Yor",
                        "https://static.tvtropes.org/pmwiki/pub/images/rsz_1rsz_5botw_expansion_pass_artwork.png",
                        2
                    ),
                    CardModel(
                        "Franky",
                        "https://www.denofgeek.com/wp-content/uploads/2016/02/zelda-linebeck.png",
                        3
                    ),
                    CardModel(
                        "n/a",
                        "https://cdn.themis-media.com/media/global/images/library/deriv/1281/1281805.png",
                        4
                    ),
                    CardModel(
                        "n/a",
                        "https://static.tvtropes.org/pmwiki/pub/images/link_oox.png",
                        5
                    )
                )
            )
            gameViewModel.saveGamed(test)*/
        }
    }
}