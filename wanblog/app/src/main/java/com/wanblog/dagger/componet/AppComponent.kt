package com.wanblog.dagger.componet

import android.content.Context
import com.wanblog.dagger.module.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getContext(): Context
}
