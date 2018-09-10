package com.example.nishant.fenrir.dagger

import com.example.nishant.fenrir.eventlist.EventListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [AppModule::class])
abstract class AppComponent {

    abstract fun inject(viewModelFactory: EventListViewModelFactory)
}