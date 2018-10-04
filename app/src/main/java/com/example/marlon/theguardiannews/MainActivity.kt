package com.example.marlon.theguardiannews

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

private const val KEY_SECTION = "section key 1"
private const val KEY_SECTION2 = "section key 2"
private const val KEY_SECTION3 = "section key 3"
private const val KEY_END = "End query key"
const val GENERAL = "https://content.guardianapis.com/search?show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val SPORT = "https://content.guardianapis.com/search?section=sport&show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val POLITICS = "https://content.guardianapis.com/search?section=politics&show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val TECHNOLOGY = "https://content.guardianapis.com/search?section=technology&show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val SCIENCE = "https://content.guardianapis.com/search?section=science&show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val BUSINESS = "https://content.guardianapis.com/search?section=business&show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val QUERY_PART1 = "https://content.guardianapis.com/search?show-fields=headline%2CbodyText%2CshortUrl%2Cthumbnail&page="
const val QUERY_PART2 = "&q="
const val QUERY_END = "&page-size=30&api-key=test"

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // Find our drawer view
        drawerLayout = findViewById(R.id.drawer_layout)
        val navDrawer = findViewById<NavigationView>(R.id.nav_view)
        // Setup drawer view
        setupDrawerContent(navDrawer)
        drawerToggle = setupDrawerToggle()
        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.action_search -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Display the content in a fragment
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        val fragment = NewListFragment()
        val bundle = Bundle()
        when (menuItem.itemId) {
            R.id.general_menu ->
                bundle.putString(KEY_SECTION, GENERAL)
            R.id.sport_menu ->
                bundle.putString(KEY_SECTION, SPORT)
            R.id.politics_menu ->
                bundle.putString(KEY_SECTION, POLITICS)
            R.id.technology_menu ->
                bundle.putString(KEY_SECTION, TECHNOLOGY)
            R.id.science_menu ->
                bundle.putString(KEY_SECTION, SCIENCE)
            R.id.business_menu ->
                bundle.putString(KEY_SECTION, BUSINESS)
            else ->
                bundle.putString(KEY_SECTION, GENERAL)
        }
        bundle.putString(KEY_END, QUERY_END)
        fragment.arguments = bundle
        // Insert the fragment by replacing any existing fragment
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit()
        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        drawerLayout.closeDrawers()
    }

    // Creates search in the menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search a new"
        // Filters data in the fragment and displays
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                search(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                search(query)
                return false
            }
        })
        return true
    }

    override fun onBackPressed() {
        // close search view on back button pressed
        iconified()
        super.onBackPressed()
    }

    private fun iconified() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }

    // Search the word in the charged news
    fun search(query: String) {
        val fragment = NewListFragment()
        val bundle = Bundle()
        bundle.putString(KEY_SECTION, QUERY_PART1)
        bundle.putString(KEY_SECTION2, QUERY_PART2)
        bundle.putString(KEY_SECTION3, query)
        bundle.putString(KEY_END, QUERY_END)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit()
        title = "Search"
    }

}