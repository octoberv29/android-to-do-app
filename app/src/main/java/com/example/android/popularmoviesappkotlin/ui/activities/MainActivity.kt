package com.example.android.popularmoviesappkotlin.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.ui.fragments.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.frame, ListFragment())
                .addToBackStack("ListFragment")
                .commit()
    }
}