package com.example.nishant.fenrir.dagger

import com.example.nishant.fenrir.events.eventfilter.eventfilterlist.EventFilterListViewModelFactory
import com.example.nishant.fenrir.events.eventfilter.eventfiltermenu.EventFilterMenuViewModelFactory
import com.example.nishant.fenrir.events.eventinfo.EventInfoViewModelFactory
import com.example.nishant.fenrir.events.eventlist.EventListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [AppModule::class])
abstract class AppComponent {

    abstract fun inject(viewModelFactory: EventListViewModelFactory)

    abstract fun inject(viewModelFactory: EventInfoViewModelFactory)

    abstract fun inject(viewModelFactory: EventFilterMenuViewModelFactory)

    abstract fun inject(viewModelFactory: EventFilterListViewModelFactory)
}