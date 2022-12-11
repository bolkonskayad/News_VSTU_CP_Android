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
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.navigation.NavigationBackPressed
import com.chibisova.vstu.navigation.NavigationNewDetails
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * Фрагмент, который содержит контейнер для других фрагментов и нижнее навигационное меню
 */
class TabFragment : Fragment(), NavigationBackPressed, NavigationNewDetails, BottomBarVisible {

    companion object {
        private const val LABEL_New_FEED = "fragment_New_feed"
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

    override fun startNewDetailsScreen(news: News) {
        when (navControllerTab.currentDestination?.label) {
            LABEL_New_FEED -> {
                val action =
                    NewsFeedFragmentDirections.actionNewFeedFragmentToNewDetailsFragment(news)
                navControllerTab.navigate(action)
            }
            LABEL_PROFILE -> {
                val action =
                    ProfileFragmentDirections.actionProfileFragmentToNewDetailsFragment(news)
                navControllerTab.navigate(action)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearFragmentContentComponent()
    }

}