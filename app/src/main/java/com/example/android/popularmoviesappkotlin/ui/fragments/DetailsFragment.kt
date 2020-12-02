package com.example.android.popularmoviesappkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.list_item.*

class DetailsFragment : Fragment() {

    private var str: String? = ""

    companion object {
        fun newInstance(str: String): DetailsFragment {
            val args = Bundle()
            args.putString("MyString", str)

            val fragment = DetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        str = arguments?.getString("MyString")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTitle.text = str

        val application = requireNotNull(this.activity).application
        val viewModelFactory = DetailsViewModel.DetailsViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
    }


}