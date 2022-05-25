package com.techun.memorygame.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techun.memorygame.R
import com.techun.memorygame.data.utils.extensions.loadByResource
import com.techun.memorygame.databinding.ActivityMemoryBinding

class MemoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMemoryBinding
    private lateinit var information: List<MemoryItem>
    private lateinit var cards: List<ImageView>
    private var firstCard: Int? = null
    private var secondCard: Int? = null
    private var enabled: Boolean = false
    private var cardTemp: ImageView? = null
    private var counter = 0.0
    private var maxAttempts = 15
    private var attempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pbAttempts.max = 100
        binding.imgBackArrow.setOnClickListener(this)
        loadCards()
    }

    private fun loadCards() {
        cards = listOf(
            binding.ivc00,
            binding.ivc01,
            binding.ivc02,
            binding.ivc10,
            binding.ivc11,
            binding.ivc12,
            binding.ivc20,
            binding.ivc21,
            binding.ivc22,
            binding.ivc30,
            binding.ivc31,
            binding.ivc32
        )

        information = listOf(
            MemoryItem(
                true,
                "ben 10",
                "https://i.cartoonnetwork.com/prismo/props/chars/b10classic_RemixCharacterHead_1015x1068.png",
                0
            ),
            MemoryItem(
                true,
                "ben 10",
                "https://i.cartoonnetwork.com/prismo/props/chars/b10classic_RemixCharacterHead_1015x1068.png",
                0
            ),

            MemoryItem(
                true,
                "Generador Rex",
                "https://static.wikia.nocookie.net/crossover-nexus/images/3/3d/Rex_generator_rex.png/revision/latest?cb=20190811230441",
                1
            ), MemoryItem(
                true,
                "Generador Rex",
                "https://static.wikia.nocookie.net/crossover-nexus/images/3/3d/Rex_generator_rex.png/revision/latest?cb=20190811230441",
                1
            ),

            MemoryItem(
                true,
                "Isla Del Drama",
                "https://img.wattpad.com/49bbb015355dcda6841e2a7baaa58abdd9dc9fc4/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f776174747061642d6d656469612d736572766963652f53746f7279496d6167652f4951593442337655474d395746413d3d2d3931303630323537342e313631633235396166396165383635343734383334333431383530382e706e67",
                2
            ),
            MemoryItem(
                true,
                "Isla Del Drama",
                "https://img.wattpad.com/49bbb015355dcda6841e2a7baaa58abdd9dc9fc4/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f776174747061642d6d656469612d736572766963652f53746f7279496d6167652f4951593442337655474d395746413d3d2d3931303630323537342e313631633235396166396165383635343734383334333431383530382e706e67",
                2
            ),

            MemoryItem(
                true,
                "los padrinos magicos",
                "https://i0.wp.com/cinicosdesinope.com/wp-content/uploads/2016/01/timmy-turner-personajes-padrinos-magicos.png?resize=601%2C601",
                3
            ),
            MemoryItem(
                true,
                "los padrinos magicos",
                "https://i0.wp.com/cinicosdesinope.com/wp-content/uploads/2016/01/timmy-turner-personajes-padrinos-magicos.png?resize=601%2C601",
                3
            ),

            MemoryItem(
                true,
                "bob esponja",
                "https://static.wikia.nocookie.net/doblaje/images/8/8b/Bob-esponja.png/revision/latest?cb=20181119225006&path-prefix=es",
                4
            ),
            MemoryItem(
                true,
                "bob esponja",
                "https://static.wikia.nocookie.net/doblaje/images/8/8b/Bob-esponja.png/revision/latest?cb=20181119225006&path-prefix=es",
                4
            ),

            MemoryItem(
                true,
                "Gravity falls",
                "https://img1.picmix.com/output/stamp/normal/1/1/8/3/263811_78b4e.png",
                5
            ),
            MemoryItem(
                true,
                "Gravity falls",
                "https://img1.picmix.com/output/stamp/normal/1/1/8/3/263811_78b4e.png",
                5
            )
        ).shuffled()

        cards.forEachIndexed { index, card ->
            card.isEnabled = true
            card.setBackgroundColor(resources.getColor(R.color.md_theme_light_primaryInverse))
            card.loadByResource(information[index].urlImagen)
            Handler().postDelayed({
                card.loadByResource(R.drawable.outline_question_mark_black_24dp)
            }, 1000)
            card.setOnClickListener {
                println("Enabled: $enabled, IsEnabled: ${card.isEnabled}")
                if (!enabled && card.isEnabled)
                    checkCard(index, card)
            }
        }
    }

    private fun checkCard(index: Int, card: ImageView) {
        if (attempts != maxAttempts) {
            card.setBackgroundColor(resources.getColor(R.color.md_theme_light_inverseOnSurface))
            if (cardTemp == null) {
                //First Card Selected
                cardTemp = card
                card.loadByResource(information[index].urlImagen)
                card.isEnabled = false
                firstCard = information[index].id
            } else {
                //Another Card Selected
                enabled = true
                card.loadByResource(information[index].urlImagen)
                card.isEnabled = false
                secondCard = information[index].id

                counter += 6.67
                attempts++
                binding.pbAttempts.progress = counter.toInt()
                binding.tvAttempts.text = "$attempts/15"

                if (firstCard == secondCard) {
                    cardTemp = null
                    enabled = false
                    cardTemp?.isEnabled = false
                    card.isEnabled = false
                    if (attempts == maxAttempts) {

                    }
                } else {
                    Handler().postDelayed({
                        cardTemp?.loadByResource(R.drawable.outline_question_mark_black_24dp)
                        cardTemp?.setBackgroundColor(resources.getColor(R.color.md_theme_light_primaryInverse))
                        cardTemp?.isEnabled = true
                        card.loadByResource(R.drawable.outline_question_mark_black_24dp)
                        card.setBackgroundColor(resources.getColor(R.color.md_theme_light_primaryInverse))
                        card.isEnabled = true
                        enabled = false
                        cardTemp = null
                    }, 500)
                }
            }
        } else {
            //
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imgBackArrow) {
            finish()
        }
    }
}