package com.techun.memorygame.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.techun.memorygame.R
import com.techun.memorygame.databinding.FragmentCreateBinding
import com.techun.memorygame.databinding.FragmentHomeBinding
import com.techun.memorygame.ui.viewmodel.AuthViewModel
import com.techun.memorygame.utils.DataState
import com.techun.memorygame.utils.goToActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    val TAG = "HOLIS"

    val rootRef = FirebaseFirestore.getInstance()
    val usersRef = rootRef.collection("Games")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListener()


        // Create a new user with a first and last name

        /* val test = GameItem(
             "Spy x Family",
             "data:image/jpeg;base64,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",
             "Spy × Family es una serie de manga y anime escrita e ilustrada por Tatsuya Endō. Comenzó su serialización quincenalmente de forma gratuita en la aplicación y sitio web Shōnen Jump+ de Shūeisha el 25 de marzo de 2019 y hasta el momento se recopila en nueve volúmenes tankōbon",
             mutableListOf(
                 CardItem(
                     "Anya",
                     "https://static.wikia.nocookie.net/spy-x-family/images/c/ca/Anya_Forger_Anime_Perfil.png/revision/latest?cb=20211111135926&path-prefix=es",
                     0
                 ),
                 CardItem(
                     "Twilight",
                     "https://static.wikia.nocookie.net/spy-x-family/images/e/e7/Loid_Forger_Anime_2_Perfil.png/revision/latest?cb=20211111135946&path-prefix=es",
                     1
                 ),
                 CardItem(
                     "Yor",
                     "https://static.wikia.nocookie.net/spy-x-family/images/b/bd/Yor_Briar_Anime_2_Perfil.png/revision/latest?cb=20211111140041&path-prefix=es",
                     2
                 ),
                 CardItem(
                     "Franky",
                     "https://static.wikia.nocookie.net/spy-x-family/images/6/6e/Franky_Franklin_Perfil_Anime_.png/revision/latest?cb=20220420141020&path-prefix=es",
                     3
                 ),
                 CardItem(
                     "n/a",
                     "https://i.blogs.es/fe5b8e/spy-x-family_2/1366_2000.jpg",
                     4
                 ),
                 CardItem(
                     "n/a",
                     "https://as01.epimg.net/meristation/imagenes/2022/05/20/noticias/1653038867_718692_1653041278_noticia_normal.jpg",
                     5
                 )
             )
         )
         usersRef.add(test)
             .addOnSuccessListener { documentReference ->
                 Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
             }
             .addOnFailureListener { e ->
                 Log.w(TAG, "Error adding document", e)
             }*/
    }

    private fun initListener() {
        binding.btnmLogout.setOnClickListener(this)
    }

    private fun initObservers() {
        viewModel.logOutState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    requireActivity().goToActivity<LoginActivity>()
                }
                is DataState.Error -> {
//                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnmLogout) {
            viewModel.logOut()
        }
    }
}