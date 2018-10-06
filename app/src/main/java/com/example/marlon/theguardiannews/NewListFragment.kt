package com.example.marlon.theguardiannews

import android.content.Intent
import android.os.AsyncTask
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
    // Updates the data when async task is finished
    override fun finished(newsList: MutableList<New>) {
        if (newsList.size==0&&(news[0]==newLoading||news[0]==newNotFound)) {
            // Remove the default value and set Not found if the result don't has data
            news.removeAt(0)
            news.add(newNotFound)
            viewAdapter.notifyDataSetChanged()
        } else {
            updateData(newsList)
        }
    }

    // Send the new data to the adapter
    private fun updateData(newsList: MutableList<New>) {
        if (page == 1) {
            news = newsList
        } else {
            news.addAll(newsList)
        }
        viewAdapter.setData(news)
    }

    // Displays in background errors
    override fun displayErrorMessage(errorMessage: String) {
        this.activity?.runOnUiThread {
            Toast.makeText(this.activity,
                    errorMessage,
                    Toast.LENGTH_LONG).show()
        }
    }
    // Display async task messages
    override fun displayMessage(message: String) {
        Toast.makeText(this.activity,
                message,
                Toast.LENGTH_LONG).show()
    }

    // Open the new in other activity
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
    private lateinit var getNews: GetNews
    private val newNotFound = New(headline = "Not found",
            sectionName = "Not found",
            url = "Not found",
            thumbnail = "Not found",
            bodyText = "Not found")
    private val newLoading = New(headline = "loading",
            url = "loading",
            bodyText = "loading",
            thumbnail = "loading",
            sectionName = "loading")

    private var news: MutableList<New> = mutableListOf(newLoading)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlQueryPart1 = it.getString(KEY_SECTION)
            urlQueryPart2 = it.getString(KEY_SECTION2)
            urlQueryPart3 = it.getString(KEY_SECTION3)
            urlQueryEnd = it.getString(KEY_END)
        }
        // Request data to the API y an async task
        getNews = GetNews(createUrl(), this)
        getNews.execute()
    }

    // Increase in 1 the page and formats the url to request to the API
    private fun createUrl(): String {
        // change the page in the url
        page++
        urlQuery = if (urlQueryPart3 == null || urlQueryPart3 == "") {
            urlQueryPart1 + page + urlQueryEnd
        } else {
            urlQueryPart1 + page + urlQueryPart2 + urlQueryPart3 + urlQueryEnd
        }
        return urlQuery
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
            // specify an viewAdapter
            adapter = viewAdapter
            // Add the pagination, loads more data when cant scroll down
            addOnScrollListener(OnScrollListener(this@NewListFragment))
        }
        return view
    }

    override fun onDestroy() {
        // Cancel the async task when the fragment destroys
        if (!getNews.isCancelled) {
            getNews.cancel(true)
        }
        super.onDestroy()
    }

    class OnScrollListener(private val context: NewListFragment) : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!(recyclerView!!.canScrollVertically(1))) {
                // If the task has finished, run the task with a new url
                if ((context.getNews.status==(AsyncTask.Status.FINISHED))) {
                    context.getNews.cancel(true)
                    context.getNews= GetNews(context.createUrl(),context)
                    context.getNews.execute()
                }

            }
        }
    }
}



