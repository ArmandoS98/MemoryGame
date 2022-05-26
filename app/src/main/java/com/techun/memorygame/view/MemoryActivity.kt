package com.techun.memorygame.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.techun.memorygame.R
import com.techun.memorygame.data.CardItem
import com.techun.memorygame.data.utils.extensions.loadByResource
import com.techun.memorygame.databinding.ActivityMemoryBinding

class MemoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMemoryBinding
    private lateinit var information: List<CardItem>
    private lateinit var cards: List<ImageView>
    private var firstCard: Int? = null
    private var secondCard: Int? = null
    private var enabled: Boolean = false
    private var cardTemp: ImageView? = null
    private var counter = 0.0
    private var maxAttempts = 15
    private var attempts = 0
    private var hits = 0
    private var maxHits = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pbAttempts.max = 100
        binding.imgBackArrow.setOnClickListener(this)
        loadCards()
    }

    private fun loadCards() {
        //Init
        counter = 0.0
        attempts = 0
        hits = 0
        binding.pbAttempts.progress = 0
        binding.tvAttempts.text = getString(R.string.attempts_counter, attempts)

        cards = listOf(
            binding.ivc00, binding.ivc01,
            binding.ivc02, binding.ivc10,
            binding.ivc11, binding.ivc12,
            binding.ivc20, binding.ivc21,
            binding.ivc22, binding.ivc30,
            binding.ivc31, binding.ivc32
        )

        val temp = intent.getParcelableArrayExtra(getString(R.string.id_cards))
        information = temp!!.map { c -> c as CardItem }.shuffled()

        cards.forEachIndexed { index, card ->
            card.isEnabled = true
            card.setBackgroundColor(
                resources.getColor(
                    R.color.md_theme_light_inverseOnSurface,
                    null
                )
            )
            information[index].urlImagen?.let { card.loadByResource(it) }
            Handler().postDelayed({
                card.loadByResource(R.drawable.outline_question_mark_black_24dp)
                card.setBackgroundColor(
                    resources.getColor(
                        R.color.md_theme_light_primaryInverse,
                        null
                    )
                )
            }, 1000)
            card.setOnClickListener {
                if ((!enabled && card.isEnabled) || (attempts < maxAttempts)) {
                    checkCard(index, card)
                }
            }
        }
    }

    private fun checkCard(index: Int, card: ImageView) {
        if (attempts != maxAttempts) {
            card.setBackgroundColor(
                resources.getColor(
                    R.color.md_theme_light_inverseOnSurface,
                    null
                )
            )
            if (cardTemp == null) {
                //First Card Selected
                cardTemp = card
                information[index].urlImagen?.let { card.loadByResource(it) }
                card.isEnabled = false
                firstCard = information[index].id
            } else {
                //Another Card Selected
                enabled = true
                information[index].urlImagen?.let { card.loadByResource(it) }
                card.isEnabled = false
                secondCard = information[index].id

                counter += 6.67
                attempts++
                binding.pbAttempts.progress = counter.toInt()
                binding.tvAttempts.text = getString(R.string.attempts_counter, attempts)

                if (firstCard == secondCard) {
                    cardTemp = null
                    enabled = false
                    cardTemp?.isEnabled = false
                    card.isEnabled = false
                    hits++
                    if ((hits == maxHits)) {
                        //Show dialog Winner
                        MaterialAlertDialogBuilder(this)
                            .setTitle(resources.getString(R.string.title))
                            .setMessage(resources.getString(R.string.supporting_text))
                            .setNegativeButton(resources.getString(R.string.try_again)) { dialog, which ->
                                loadCards()
                                dialog.dismiss()
                            }
                            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                                finish()
                                dialog.dismiss()
                            }.show()
                    }
                } else {
                    Handler().postDelayed({
                        //First card Selected
                        cardTemp?.loadByResource(R.drawable.outline_question_mark_black_24dp)
                        cardTemp?.setBackgroundColor(
                            resources.getColor(
                                R.color.md_theme_light_primaryInverse,
                                null
                            )
                        )
                        cardTemp?.isEnabled = true

                        //Second card Selected
                        card.loadByResource(R.drawable.outline_question_mark_black_24dp)
                        card.setBackgroundColor(
                            resources.getColor(
                                R.color.md_theme_light_primaryInverse,
                                null
                            )
                        )
                        card.isEnabled = true

                        enabled = false
                        cardTemp = null
                    }, 500)
                }
            }
        } else {
            //Game Over
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title_game_over))
                .setMessage(resources.getString(R.string.supporting_text_game_over))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                    finish()
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.try_again)) { dialog, which ->
                    loadCards()
                    dialog.dismiss()
                }.show()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imgBackArrow) {
            finish()
        }
    }
}