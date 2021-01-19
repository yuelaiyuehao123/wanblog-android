package com.wanblog.dagger.module

import androidx.appcompat.app.AppCompatActivity
import com.wanblog.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun provideActivity(): AppCompatActivity = activity
}
