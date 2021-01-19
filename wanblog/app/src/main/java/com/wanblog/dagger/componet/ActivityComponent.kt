package com.wanblog.dagger.componet

import androidx.appcompat.app.AppCompatActivity
import com.wanblog.dagger.module.ActivityModule
import com.wanblog.dagger.scope.ActivityScope
import com.wanblog.ui.activity.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun getActivity(): AppCompatActivity

    fun inject(activity: MainActivity)

}