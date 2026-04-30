package dam.pmdm.spyrothedragon

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.spyrothedragon.databinding.*

/**
 * Actividad principal de la aplicación.
 *
 * Se encarga de:
 * - Gestionar la navegación entre fragments
 * - Controlar la guía interactiva
 * - Manejar animaciones de UI
 */
class MainActivity : AppCompatActivity() {

    /** ViewBinding de la actividad */
    private lateinit var binding: ActivityMainBinding

    /** Controlador de navegación */
    private var navController: NavController? = null

    private var mediaPlayer: MediaPlayer? = null

    private var finalMusicPlayer: MediaPlayer? = null

    // Variable mostrar guia solo una vez
    private lateinit var prefs: android.content.SharedPreferences

    /**
     * Método principal de creación de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Funcion para mostrar la guia solo una vez
        prefs = getSharedPreferences("guide_prefs", MODE_PRIVATE)

        setupNavigation()
        setupGuide()
    }

    /**
     * Configura Navigation Component y menú inferior
     */
    private fun setupNavigation() {

        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment)

        navHostFragment?.let {
            navController = NavHostFragment.findNavController(it)

            NavigationUI.setupWithNavController(binding.navView, navController!!)
            NavigationUI.setupActionBarWithNavController(this, navController!!)
        }

        binding.navView.setOnItemSelectedListener { menuItem ->
            selectedBottomMenu(menuItem)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_characters,
                R.id.navigation_worlds,
                R.id.navigation_collectibles -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }

    /**
     * Configura toda la lógica de la guía interactiva
     */
    private fun setupGuide() {

        // Funcion para mostrar la guia solo una vez
        val hasSeenGuide = prefs.getBoolean("hasSeenGuide", false)
        if (hasSeenGuide) {
            hideGuide()
       }

        val guideWelcomeBinding = GuideWelcomeBinding.bind(binding.guideWelcome.root)
        val guideCharactersBinding = GuideCharactersBinding.bind(binding.guideCharacters.root)
        val guideWorldsBinding = GuideWorldsBinding.bind(binding.guideWorlds.root)
        val guideCollectiblesBinding = GuideCollectiblesBinding.bind(binding.guideCollectibles.root)
        val guideInfoBinding = GuideInfoBinding.bind(binding.guideInfo.root)
        val guideSummaryBinding = GuideSummaryBinding.bind(binding.guideSummary.root)

        // Estado inicial
        binding.guideCharacters.root.visibility = View.GONE
        binding.guideWorlds.root.visibility = View.GONE
        binding.guideCollectibles.root.visibility = View.GONE
        binding.guideInfo.root.visibility = View.GONE
        binding.guideSummary.root.visibility = View.GONE

        /**
         * PANTALLA 1 → WELCOME
         */
        guideWelcomeBinding.btnBegin.setOnClickListener {

            playSound(R.raw.spyro_sound1)

            binding.guideWelcome.root.visibility = View.GONE
            binding.guideCharacters.root.visibility = View.VISIBLE

            if (navController?.currentDestination?.id != R.id.navigation_characters) {
                navController?.navigate(R.id.navigation_characters)
            }

            animateBubble(guideCharactersBinding.bubbleCharacters)
            startArrowAnimation(guideCharactersBinding.arrowGuide)
            positionArrowOverTab(guideCharactersBinding.arrowGuide, 0)
        }

        /**
         * PANTALLA 2 → CHARACTERS
         */
        guideCharactersBinding.btnNext.setOnClickListener {

            playSound(R.raw.spyro_sound3)

            binding.guideCharacters.root.visibility = View.GONE
            binding.guideWorlds.root.visibility = View.VISIBLE

            if (navController?.currentDestination?.id != R.id.navigation_worlds) {
                navController?.navigate(R.id.navigation_worlds)
            }

            animateBubble(guideWorldsBinding.bubbleWorlds)
            startArrowAnimation(guideWorldsBinding.arrowGuide)
            positionArrowOverTab(guideWorldsBinding.arrowGuide, 1)
        }

        /**
         * PANTALLA 3 → WORLDS
         */
        guideWorldsBinding.btnNext.setOnClickListener {

            playSound(R.raw.spyro_sound3)

            binding.guideWorlds.root.visibility = View.GONE
            binding.guideCollectibles.root.visibility = View.VISIBLE

            if (navController?.currentDestination?.id != R.id.navigation_collectibles) {
                navController?.navigate(R.id.navigation_collectibles)
            }

            animateBubble(guideCollectiblesBinding.bubbleCollectibles)
            startArrowAnimation(guideCollectiblesBinding.arrowGuide)
            positionArrowOverTab(guideCollectiblesBinding.arrowGuide, 2)
        }

        /**
         * PANTALLA 4 → INFO (Toolbar)
         */
        guideCollectiblesBinding.btnNext.setOnClickListener {

            playSound(R.raw.spyro_sound3)

            binding.guideCollectibles.root.visibility = View.GONE
            binding.guideInfo.root.visibility = View.VISIBLE

            animateBubble(guideInfoBinding.bubbleInfo)

            binding.root.postDelayed({
                positionArrowToInfoIcon(guideInfoBinding.arrowGuide)
                startArrowAnimation(guideInfoBinding.arrowGuide)
            }, 300)
        }

        /**
         * BOTONES SKIP
         */
        guideWelcomeBinding.btnSkip.setOnClickListener {
            playSound(R.raw.spyro_sound9)
            hideGuide()
        }

        guideCharactersBinding.btnSkip.setOnClickListener {
            playSound(R.raw.spyro_sound9)
            hideGuide()
        }

        guideWorldsBinding.btnSkip.setOnClickListener {
            playSound(R.raw.spyro_sound9)
            hideGuide()
        }

        guideCollectiblesBinding.btnSkip.setOnClickListener {
            playSound(R.raw.spyro_sound9)
            hideGuide()
        }

        guideInfoBinding.btnSkip.setOnClickListener {
            playSound(R.raw.spyro_sound9)
            hideGuide()
        }

        /**
         * FINAL GUÍA
         */
        guideInfoBinding.btnFinish.setOnClickListener {

            binding.guideInfo.root.visibility = View.GONE
            binding.guideSummary.root.visibility = View.VISIBLE
            animateBubble(guideSummaryBinding.bubbleSummary)

            finalMusicPlayer = android.media.MediaPlayer.create(this, R.raw.spyro_song)
            finalMusicPlayer?.isLooping = true // opcional (que se repita)
            finalMusicPlayer?.start()
        }

        guideSummaryBinding.btnStartApp.setOnClickListener {
            finalMusicPlayer?.stop()
            finalMusicPlayer?.release()
            finalMusicPlayer = null

            hideGuide()
        }
    }

    /**
     * Animación de flecha (fade + movimiento)
     */
    private fun startArrowAnimation(view: View) {

        view.animate().cancel()
        view.clearAnimation()

        val fade = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.3f).apply {
            duration = 500
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
        }

        val move = ObjectAnimator.ofFloat(view, "translationY", 0f, 20f).apply {
            duration = 500
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
        }

        fade.start()
        move.start()
    }

    /**
     * Animación de aparición de bocadillos
     */
    private fun animateBubble(view: View) {
        view.apply {
            alpha = 0f
            scaleX = 0.8f
            scaleY = 0.8f
            translationY = 100f

            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationY(0f)
                .setDuration(400)
                .start()
        }
    }

    /**
     * Oculta completamente la guía
     */
    private fun hideGuide() {

        binding.guideWelcome.root.visibility = View.GONE
        binding.guideCharacters.root.visibility = View.GONE
        binding.guideWorlds.root.visibility = View.GONE
        binding.guideCollectibles.root.visibility = View.GONE
        binding.guideInfo.root.visibility = View.GONE
        binding.guideSummary.root.visibility = View.GONE

        // Funcion mostrar la guia solo una vez
        prefs.edit().putBoolean("hasSeenGuide", true).apply()
    }

    /**
     * Posiciona la flecha sobre el icono INFO del toolbar
     */
    private fun positionArrowToInfoIcon(arrow: View) {
        val decorView = window.decorView.rootView

        decorView.post {
            val possibleViews = ArrayList<View>()
            decorView.findViewsWithText(
                possibleViews,
                "Info",
                View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION
            )

            if (possibleViews.isNotEmpty()) {
                val item = possibleViews[0]

                val location = IntArray(2)
                item.getLocationOnScreen(location)

                val arrowLocation = IntArray(2)
                arrow.getLocationOnScreen(arrowLocation)

                val itemX = location[0] + item.width / 2
                val itemY = location[1]

                val arrowX = arrowLocation[0]
                val arrowY = arrowLocation[1]

                arrow.translationX = (itemX - arrowX - arrow.width / 2).toFloat()
                arrow.translationY = (itemY - arrowY + item.height).toFloat()
            }
        }
    }

    /**
     * Posiciona la flecha sobre un tab del BottomNavigation
     */
    private fun positionArrowOverTab(arrow: View, tabIndex: Int) {
        binding.navView.post {

            val menuView = binding.navView.getChildAt(0) as? ViewGroup ?: return@post
            val item = menuView.getChildAt(tabIndex)

            val location = IntArray(2)
            item.getLocationOnScreen(location)

            val itemX = location[0] + item.width / 2
            val itemY = location[1]

            val arrowLocation = IntArray(2)
            arrow.getLocationOnScreen(arrowLocation)

            val arrowX = arrowLocation[0]
            val arrowY = arrowLocation[1]

            arrow.translationX = (itemX - arrowX - arrow.width / 2).toFloat()
            arrow.translationY = (itemY - arrowY - arrow.height).toFloat()
        }
    }

    /**
     * Maneja la navegación del menú inferior
     */
    private fun selectedBottomMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_characters -> navController?.navigate(R.id.navigation_characters)
            R.id.nav_worlds -> navController?.navigate(R.id.navigation_worlds)
            else -> navController?.navigate(R.id.navigation_collectibles)
        }
        return true
    }

    /**
     * Infla el menú superior (toolbar)
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    /**
     * Maneja clics del menú superior
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_info) {
            showInfoDialog()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Muestra el diálogo de información
     */
    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_about)
            .setMessage(R.string.text_about)
            .setPositiveButton(R.string.accept, null)
            .show()
    }

    private fun playSound(soundResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer?.start()
    }
}
