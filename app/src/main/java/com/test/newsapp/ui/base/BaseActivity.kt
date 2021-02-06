package com.test.newsapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract  class BaseActivity  : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
    }

    protected abstract fun provideLayout() : Int

}