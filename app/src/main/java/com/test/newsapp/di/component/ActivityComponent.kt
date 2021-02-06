package com.test.newsapp.di.component


import com.test.newsapp.di.ActivityScope
import com.test.newsapp.di.module.ActivityModule
import com.test.newsapp.ui.bookmark.BookmarkActivity
import com.test.newsapp.ui.main.NewsHomeActivity

import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: NewsHomeActivity)

    fun inject(activity: BookmarkActivity)

}
