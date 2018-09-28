package com.example.nishant.fenrir.dagger

import com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfilterlist.EventFilterListViewModelFactory
import com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfiltermenu.EventFilterMenuViewModelFactory
import com.example.nishant.fenrir.screens.mainapp.events.eventinfo.EventInfoViewModelFactory
import com.example.nishant.fenrir.screens.mainapp.events.eventlist.EventListViewModelFactory
import com.example.nishant.fenrir.screens.wallet.items.ItemsViewModelFactory
import com.example.nishant.fenrir.screens.wallet.stalls.StallsViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [AppModule::class])
abstract class AppComponent {

    abstract fun inject(viewModelFactory: EventListViewModelFactory)

    abstract fun inject(viewModelFactory: EventInfoViewModelFactory)

    abstract fun inject(viewModelFactory: EventFilterMenuViewModelFactory)

    abstract fun inject(viewModelFactory: EventFilterListViewModelFactory)

    abstract fun inject(viewModelFactory: StallsViewModelFactory)

    abstract fun inject(viewModelFactory: ItemsViewModelFactory)
}