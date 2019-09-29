package com.pj109.xkorey.pjos.ui.login

import androidx.lifecycle.ViewModel;
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigSetUseCase
import com.pj109.xkorey.share.domain.signin.ObserveUserSettingUseCase
import javax.inject.Inject

class LoginBottomLoopMenuViewModel @Inject constructor(
    private val configQueryUseCase: ConfigQueryUseCase,
    private val observable: ObserveUserSettingUseCase,
    private val configSetUseCase: ConfigSetUseCase
) : ViewModel() {

}
