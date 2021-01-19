package com.wanblog.dagger.componet

import androidx.appcompat.app.AppCompatActivity
import com.wanblog.dagger.module.FragmentModule
import com.wanblog.dagger.scope.FragmentScope
import com.wanblog.ui.fragment.HomeFragment
import com.wanblog.ui.fragment.MeFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun getActivity(): AppCompatActivity

    fun inject(fragment: HomeFragment)

    fun inject(fragment: MeFragment)

}