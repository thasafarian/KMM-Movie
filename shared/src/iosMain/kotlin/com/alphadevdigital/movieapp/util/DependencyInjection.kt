package com.alphadevdigital.movieapp.util

import com.alphadevdigital.movieapp.di.getSharedModules
import org.koin.core.context.startKoin

fun initModule() {
    startKoin {
        modules(getSharedModules())
    }
}
