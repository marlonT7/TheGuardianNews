package com.example.marlon.theguardiannews

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject


class GetNews(val url: String, private val getNewsCallback: GetNewsCallback) : AsyncTask<Void, Void, Void>() {
    private val tag = MainActivity::class.java.simpleName
    private var news: MutableList<New> = mutableListOf()

    interface GetNewsCallback {
        fun displayMessage(message: String)
        fun displayErrorMessage(errorMessage: String)
        fun finished(newsList: MutableList<New>)
    }

    // Get the request results
    override fun doInBackground(vararg params: Void?): Void? {
        val sh = HttpHandler()
        // Making a request to url and getting response
        val jsonStr = sh.makeServiceCall(url)
        if (jsonStr != null) {
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
                        // field node is JSON Object
                        val field = result.getJSONObject("fields")
                        val headline = field.getString("headline")
                        val url = field.getString("shortUrl")
                        val thumbnail = field.optString("thumbnail", "Not found")
                        val bodyText = field.getString("bodyText")
                        // tmp hash map for single new
                        val new = New(sectionName, headline, url, thumbnail, bodyText)
                        news.add(new)
                    }
                }

            } catch (e: JSONException) {
                Log.e(tag, "Json parsing error: " + e.message)
                getNewsCallback.displayErrorMessage("Json parsing error: " + e.message)
            }
        }
        return null
    }
    // Send data to the fragment
    override fun onPostExecute(result: Void?) {
        getNewsCallback.finished(news)
        getNewsCallback.displayMessage("The data is fully downloaded")
    }
}

