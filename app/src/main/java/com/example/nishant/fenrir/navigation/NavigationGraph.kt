package com.example.nishant.fenrir.navigation

import com.example.nishant.fenrir.R

object NavigationGraph {

    object Events {

        val EVENT_LIST = Destination("EVENT_LIST", 0, 0, 0, 0, true)
        val EVENT_INFO = Destination("EVENT_INFO", R.anim.an_slide_down, R.anim.an_stay_and_fade, 0, R.anim.an_slide_up)

        object EventFilter {

            val FILTER_MENU = Destination("FILTER_MENU", 0, 0, 0, 0)
            val FILTER_LIST = Destination("FILTER_LIST", 0, 0, 0, 0)
        }
    }

    object More {

        val MORE = Destination("MORE", 0, 0, 0, 0, true)
    }
}