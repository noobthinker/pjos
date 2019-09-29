package com.pj109.xkorey.pjos.ui.login

import com.pj109.xkorey.share.domain.digit.NumberSubtractUseCase
import com.pj109.xkorey.share.domain.room.AppConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.signin.ObserveUserSettingUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SignModel {

    @Singleton
    @Provides
    fun provideSignInViewModelDelegate(
        observable: ObserveUserSettingUseCase,
        configQueryUseCase: AppConfigQueryUseCase,
        queryUseCase: ConfigQueryUseCase
    ): SignInViewModelDelegate {
        return RoomSignInViewModelDelegate(observable,configQueryUseCase,queryUseCase)
    }
}