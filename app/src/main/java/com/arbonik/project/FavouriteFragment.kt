package com.arbonik.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vseved3.JSONHelper
import java.lang.Exception

class FavouriteFragment : Fragment() {
    var width: Int = 0
    var recyclerView: RecyclerView? = null
    var memes: List<Meme>? = null
    val mergeAdapter = MergeAdapter()
    var recAdapter: RecAdapter? = null
    var mFragmentManager: FragmentManager? = null
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
            if (memes?.size!! > 0) {
                if (recyclerView?.adapter == null) {
                    mergeAdapter.addAdapter(TopAdapter())
                    recAdapter = RecAdapter(
                        context,
                        width / 2.2,
                        width / 2.2,
                        memes as ArrayList<Meme>
                    )
                    mergeAdapter.addAdapter(recAdapter!!)
                    recyclerView?.adapter = mergeAdapter
                } else {
                    Toast.makeText(context, "asd", Toast.LENGTH_LONG).show()
                }
            } else {
                val nthFragment = NothingFragment()
                mFragmentManager?.beginTransaction()?.replace(R.id.fragment_viewer, nthFragment)?.commit()
            }
        } catch (e: Exception) {
            val nthFragment = NothingFragment()
            mFragmentManager?.beginTransaction()?.replace(R.id.fragment_viewer, nthFragment)?.commit()
        }
        return view
    }

}