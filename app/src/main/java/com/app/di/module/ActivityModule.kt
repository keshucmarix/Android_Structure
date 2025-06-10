package com.app.di.module

import android.content.Context
import com.app.ui.base.BaseActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    internal fun provideBaseActivity(@ActivityContext context: Context): BaseActivity {
        return (context as BaseActivity)
    }


    @Provides
    @ActivityScoped
    @Named("placeholder")
    internal fun providePlaceHolder(baseActivity: BaseActivity): Int {
        return baseActivity.findFragmentPlaceHolder()
    }
}