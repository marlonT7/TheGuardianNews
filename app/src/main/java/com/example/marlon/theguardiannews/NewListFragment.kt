package com.example.marlon.theguardiannews

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

// the fragment initialization parameters
private const val KEY_SECTION = "section key 1"
private const val KEY_SECTION2 = "section key 2"
private const val KEY_SECTION3 = "section key 3"
private const val KEY_END = "End query key"
private const val ARG_PARAM1 = "new"

class NewListFragment : Fragment(), NewsListAdapter.SelectedNew, GetNews.GetNewsCallback {
    override fun onFinished(new: New, newsList: MutableList<New>) {
        if (news[0].headline == "loading"){
            news.removeAt(0)
            news.add(new)
            viewAdapter.notifyDataSetChanged()
        } else {
            updateData(newsList)
        }

    }

    override fun getResult(newsList: MutableList<New>) {
       updateData(newsList)
    }

    fun updateData(newsList: MutableList<New>){
        news=newsList
        viewAdapter.setData(news)
    }

    override fun displayErrorMessage(errorMessage: String) {
        this.activity?.runOnUiThread {
            Toast.makeText(this.activity,
                    errorMessage,
                    Toast.LENGTH_LONG).show()
        }
    }

//    override fun displayMessage(message: String) {
//
//        Toast.makeText(this.activity,
//                message,
//                Toast.LENGTH_LONG).show()
//    }

    override fun openNew(new: New) {
        val bundle = Bundle()
        bundle.putParcelable(ARG_PARAM1, new)
        val intent = Intent(context, NewActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    // Url first part to request data to the API
    private var urlQueryPart1: String? = null
    //  Result request  page
    private var page: Int = 0
    // Url second part to request data to the API
    private var urlQueryPart2: String? = null
    // Word to search
    private var urlQueryPart3: String? = null
    // End of url parts to request data
    private var urlQueryEnd: String? = null
    // Final url to send to request
    private lateinit var urlQuery: String
    private var news: MutableList<New> = mutableListOf(New(headline = "loading",
            url = "loading",
            bodyText = "loading",
            thumbnail = "loading",
            sectionName = "loading"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlQueryPart1 = it.getString(KEY_SECTION)
            urlQueryPart2 = it.getString(KEY_SECTION2)
            urlQueryPart3 = it.getString(KEY_SECTION3)
            urlQueryEnd = it.getString(KEY_END)
        }
        createUrl()
        GetNews(urlQuery, this).execute()
    }

    private fun createUrl() {
        page++
        urlQuery = if (urlQueryPart3 == null || urlQueryPart3 == "") {
            urlQueryPart1 + page + urlQueryEnd
        } else {
            urlQueryPart1 + page + urlQueryPart2 + urlQueryPart3 + urlQueryEnd
        }
    }

    private lateinit var viewManager: LinearLayoutManager

    private lateinit var viewAdapter: NewsListAdapter

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_list, container, false)
        viewManager = LinearLayoutManager(this.context)
        // Sets data to the recycler view
        viewAdapter = NewsListAdapter(news, this)
        // Divides the data in categories and send to the corresponding view page
        recyclerView = view.findViewById<RecyclerView>(R.id.news_list_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        return view
    }
}
