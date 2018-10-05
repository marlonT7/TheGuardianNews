package com.example.marlon.theguardiannews

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject


class GetNews(val url: String, private val getNewsCallback: GetNewsCallback) : AsyncTask<Void, Void, Void>() {
    private val tag = MainActivity::class.java.simpleName
    private var news: MutableList<New> = mutableListOf()
    private var new = New(headline = "Not found",
            sectionName = "Not found",
            url = "Not found",
            thumbnail = "Not found",
            bodyText = "Not found")

    interface GetNewsCallback {
        //   fun displayMessage(message: String)
        fun displayErrorMessage(errorMessage: String)

        fun getResult(newsList: MutableList<New>)
        fun finished(new: New, newsList: MutableList<New>)
    }

    override fun onProgressUpdate(vararg values: Void?) {
        getNewsCallback.getResult(news)
    }

    // Get the request results
    override fun doInBackground(vararg params: Void?): Void? {
        val sh = HttpHandler()
        // Making a request to url and getting response
        val jsonStr = sh.makeServiceCall(url)
        try {
            val jsonObj = JSONObject(jsonStr)
            // Getting JSON Array node
            val response = jsonObj.getJSONObject("response")
            val results = response.getJSONArray("results")
            // looping through All results
            if (results.length() != 0) {
                for (i in 0 until results.length()) {
                    val result = results.getJSONObject(i)
                    val sectionName = result.getString("sectionName")
                    // Phone node is JSON Object
                    val field = result.getJSONObject("fields")
                    val headline = field.getString("headline")
                    val url = field.getString("shortUrl")
                    val thumbnail = field.optString("thumbnail","Not found")
                    val bodyText = field.getString("bodyText")
                    // tmp hash map for single contact
                    val new = New(sectionName, headline, url, thumbnail, bodyText)
                    news.add(new)
                    publishProgress()
                }
            }
        } catch (e: JSONException) {
            Log.e(tag, "Json parsing error: " + e.message)
            getNewsCallback.displayErrorMessage("Json parsing error: " + e.message)
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        getNewsCallback.finished(new, news)
        // getNewsCallback.displayMessage("The data is fully downloaded")
    }
}

