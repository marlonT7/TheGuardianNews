package com.example.marlon.theguardiannews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


// the fragment initialization parameters
private const val ARG_PARAM1 = "new"

class NewFragment : Fragment() {

    private var new: New? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            new = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new, container, false)
        val headline = view.findViewById<TextView>(R.id.headline_view)
        headline.text = new?.headline
        val photo = view.findViewById<ImageView>(R.id.photo_view)
        Picasso.get().load(new?.thumbnail).fit().centerCrop()
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(photo)
        val body = view.findViewById<TextView>(R.id.body_view)
        body.text = new?.bodyText
        val section = view.findViewById<TextView>(R.id.section_view)
        section.text = new?.sectionName
        val url = view.findViewById<TextView>(R.id.url_view)
        url.text = new?.url
        url.setOnClickListener { openWebSite() }
        return view

    }

    // Open the web site in an intent
    private fun openWebSite() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(new?.url)
        startActivity(intent)
    }
}
