package com.wanblog.dagger.componet

import androidx.appcompat.app.AppCompatActivity
import com.wanblog.dagger.module.ActivityModule
import com.wanblog.dagger.scope.ActivityScope
import com.wanblog.ui.activity.BlogActivity
import com.wanblog.ui.activity.LoginActivity
import com.wanblog.ui.activity.MainActivity
import com.wanblog.ui.activity.SignUpActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun getActivity(): AppCompatActivity

    fun inject(activity: MainActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: SignUpActivity)

    fun inject(activity: BlogActivity)

}