package com.wanblog.dagger.module

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wanblog.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(val fragment: Fragment) {

    @Provides
    @FragmentScope
    fun provideActivity(): AppCompatActivity = (fragment.activity as AppCompatActivity?)!!

}
