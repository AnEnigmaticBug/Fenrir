package com.example.nishant.fenrir.navigation

import com.example.nishant.fenrir.R

object NavigationGraph {

    object MainApp {

        object Events {

            object EventFilter {

                val FILTER_MENU = Destination("FILTER_MENU", 0, 0, R.anim.an_fade_in_slow, R.anim.an_fade_out_slow)
                val FILTER_LIST = Destination("FILTER_LIST", R.anim.an_fade_in_slow, R.anim.an_fade_out_slow, 0, 0)
            }
        }
    }
}