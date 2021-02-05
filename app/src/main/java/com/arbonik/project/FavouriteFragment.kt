package com.arbonik.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.vseved3.JSONHelper
import java.lang.Exception

class FavouriteFragment : Fragment() {
    var width: Int = 0
    var recyclerView: RecyclerView? = null
    var memes: List<Meme>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, null)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {

                }
            }
        })
        try {
            memes = JSONHelper.importFromJSON(context) as ArrayList<Meme>
            recyclerView?.adapter = RecAdapter(context, width / 2.2, width / 2.2, memes as ArrayList<Meme>)
        } catch (e: Exception) {
        }
        return view
    }

}