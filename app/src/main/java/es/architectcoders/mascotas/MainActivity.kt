package es.architectcoders.mascotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import es.architectcoders.mascotas.R.menu.bottomappbar_menu_primary
import es.architectcoders.mascotas.R.menu.bottomappbar_menu_secondary
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottomBar)

        val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                bottomBar.toggleFabAlignment()
                bottomBar.replaceMenu(
                    if(currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
                        bottomappbar_menu_secondary
                    else
                        bottomappbar_menu_primary
                )

                fab?.setImageDrawable(
                    if(currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) getDrawable(R.drawable.ic_shopping_cart_white)
                    else getDrawable(R.drawable.ic_add_white)
                )
                fab?.show()
            }
        }


        toggle_fab_alignment_button.setOnClickListener {
            fabButtonBar.hide(addVisibilityChanged)
            invalidateOptionsMenu()

            when(screen_label.text) {
                getString(R.string.primary_screen_text) -> {
                    screen_label.text = getString(R.string.secondary_sceen_text)
                    toggle_fab_alignment_button.text = getString(R.string.go_to_main)
                }
                getString(R.string.secondary_sceen_text) -> {
                    screen_label.text = getString(R.string.primary_screen_text)
                    toggle_fab_alignment_button.text = getString(R.string.go_to_detail)
                }
            }
        }

        fabButtonBar.setOnClickListener {
            // TODO
            displayMaterialSnackBar()
        }
    }

    private fun BottomAppBar.toggleFabAlignment() {
        currentFabAlignmentMode = fabAlignmentMode
        fabAlignmentMode = currentFabAlignmentMode.xor(1)
    }

    private fun displayMaterialSnackBar() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(bottomappbar_menu_primary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_search -> openSearchFragment()
            android.R.id.home -> openNavigationFragment()
        }
        return true
    }

    private fun openSearchFragment() {
        val bottomNavDrawerFragment = BottomSearchFragment()
        bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
    }
    private fun openNavigationFragment() {
        val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
        bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
    }
}
