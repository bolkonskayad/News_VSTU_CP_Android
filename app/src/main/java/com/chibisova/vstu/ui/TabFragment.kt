package com.chibisova.vstu.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.managers.BottomBarVisible
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.navigation.NavigationBackPressed
import com.chibisova.vstu.navigation.NavigationMemeDetails
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment(), NavigationBackPressed, NavigationMemeDetails, BottomBarVisible {

    companion object {
        private const val LABEL_MEME_FEED = "fragment_meme_feed"
        private const val LABEL_PROFILE = "fragment_profile"
    }

    private lateinit var navControllerTab: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.getFragmentContentComponentOrCreateIfNull(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControllerTab =
            Navigation.findNavController(view.findViewById(R.id.nav_host_fragment_content))
        NavigationUI.setupWithNavController(bottom_navigation_view, navControllerTab);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_feed, menu)
    }

    override fun showBottomNavigationBar() {
        bottom_navigation_view.visibility = View.VISIBLE
    }

    override fun hideBottomNavigationBar() {
        bottom_navigation_view.visibility = View.GONE
    }

    override fun back() {
        navControllerTab.popBackStack()
    }

    override fun startMemeDetailsScreen(meme: Meme) {
        when (navControllerTab.currentDestination?.label) {
            LABEL_MEME_FEED -> {
                val action =
                    MemeFeedFragmentDirections.actionMemeFeedFragmentToMemeDetailsFragment(meme)
                navControllerTab.navigate(action)
            }
            LABEL_PROFILE -> {
                val action =
                    ProfileFragmentDirections.actionProfileFragmentToMemeDetailsFragment(meme)
                navControllerTab.navigate(action)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearFragmentContentComponent()
    }

}