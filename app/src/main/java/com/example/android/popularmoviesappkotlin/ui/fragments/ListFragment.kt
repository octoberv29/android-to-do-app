package com.example.android.popularmoviesappkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.ui.adapters.MyAdapter
import com.example.android.popularmoviesappkotlin.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), MyAdapter.OnRecyclerViewItemClick {

    private lateinit var adapter: MyAdapter
    private val sampleDataSet : Array<String> = arrayOf("one", "two", "three", "four")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MyAdapter(sampleDataSet, this)
        rvList.layoutManager = LinearLayoutManager(activity)
        rvList.setHasFixedSize(true)
        rvList.adapter = adapter

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ListViewModel.ListViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onClick(position: Int) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame, DetailsFragment.newInstance(sampleDataSet[position]))
            ?.addToBackStack("DetailsFragment")
            ?.commit()
    }

}