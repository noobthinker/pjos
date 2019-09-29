package com.pj109.xkorey.pjos.ui.login

import android.net.Uri
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog
import com.pj109.xkorey.model.data.DialogType
import com.pj109.xkorey.model.data.Response
import com.pj109.xkorey.model.enums.EventMthod
import com.pj109.xkorey.model.enums.HttpMethod
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.share.BuildConfig
import com.pj109.xkorey.share.domain.digit.NumberSubtractUseCase
import com.pj109.xkorey.share.domain.http.CaptchaCheckUseCase
import com.pj109.xkorey.share.domain.http.RegisterUseCase
import com.pj109.xkorey.share.domain.prefs.FunctionKeySetUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigSetUseCase
import com.pj109.xkorey.share.domain.signin.ObserveUserSettingUseCase
import com.pj109.xkorey.share.domain.socket.NettyClientUseCase
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import com.pj109.xkorey.share.util.defaultReq
import com.pj109.xkorey.share.util.rndStr
import es.dmoral.markdownview.MarkdownView
import timber.log.Timber
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val observable: ObserveUserSettingUseCase,
    private val signInViewModelDelegate: SignInViewModelDelegate,
    private val configQueryUseCase: ConfigQueryUseCase,
    private val configSetUseCase: ConfigSetUseCase,
    private val numberSubtractUseCase: NumberSubtractUseCase,
    private val captchaCheckUseCase: CaptchaCheckUseCase,
    private val registerUseCase: RegisterUseCase,
    private val keySetUseCase: FunctionKeySetUseCase,
    private val nettyClientUseCase: NettyClientUseCase
) : ViewModel(), SignInViewModelDelegate by signInViewModelDelegate {

    val smsCountDown = MutableLiveData<Long>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    val awesomeDialog=MutableLiveData<Int>()
    val awesomeDialogStatus=MutableLiveData<Int>()


    // 用户输入手机号
    val phone = ObservableField<String>()
    // key 登陆
    val key = ObservableField<String>()
    val loopbarTitle = ObservableField<Int>()
    val anonymous = ObservableField<String>()
    val anonymousError = ObservableField<String>()
    // 按钮txt
    val buttonTxt = ObservableField<Int>()
    val sendTxt = ObservableField<String>()
    val sendBtnVisibility = ObservableField<Int>()
    val agreeCheckTxt = ObservableField<Int>()
    val inviteCode = ObservableField<String>()
    val captcha = ObservableField<String>()
    val passwod = ObservableField<String>()
    val phoneError = ObservableField<String>()
    val passwordError = ObservableField<String>()
    // 短信验证码错误
    val smsCodeError = ObservableField<String>()
    // 验证码错误
    val captchaError = ObservableField<String>()
    val inviteCodeError = ObservableField<String>()
    // 邀请码是否显示
    val inviteEnable = ObservableField<Int>()
    // 验证码url
    val captchaUrl = ObservableField<Uri>()
    val countdownVisibility = ObservableField<Int>()
    val weiboVisibility = ObservableField<Int>()

    // 未发送验证码则显示
    val captchaVisibility = ObservableField<Int>()
    val smsCodeVisible = ObservableField<Int>()
    val countdown = ObservableField<String>()
    val defaultErrorTxt = "请求失败，稍后再试"


    //
    val btnEvt = MutableLiveData<Int>()
    var radKey = ""
    var eventType = EventMthod.Register

    val phoneValidateTxt=ObservableField<String>()

    init {
        _isLoading.postValue(true)
        btnEvt.postValue(0)
        sendBtnVisibility.set(View.VISIBLE)
        buttonTxt.set(R.string.register)
        sendTxt.set("发送")
        agreeCheckTxt.set(R.string.agreement_ok)
        inviteEnable.set(View.GONE)
        smsCodeVisible.set(View.VISIBLE)
        captchaVisibility.set(View.VISIBLE)
        weiboVisibility.set(View.GONE)
        radKey = rndStr()
        changeCaptcha()
        awesomeDialog.postValue(DialogType.none)
        awesomeDialogStatus.postValue(DialogType.none)
        configQueryUseCase.invoke(Pair("user_register", "phone")).observeForever {
            phone.set((it as Result.Success).data)
        }
        configQueryUseCase.invoke(Pair("invite_config", "open")).observeForever {
            val s = (it as Result.Success).data
            if (s.equals("1")) {
                inviteEnable.set(View.VISIBLE)
            } else {
                inviteEnable.set(View.GONE)
            }
        }

        configQueryUseCase.invoke(Pair("user_register", "rand_key")).observeForever {
            key.set((it as Result.Success).data)
        }

        configQueryUseCase.invoke(Pair("user_register", "anonymous")).observeForever {
            anonymous.set((it as Result.Success).data)
        }

        configQueryUseCase.invoke(Pair("validate", "phone")).observeForever {
            when{
                it.succeeded->{
                    phoneValidateTxt.set((it as Result.Success).data)
                }
            }
        }
    }

    // 发送验证码
    fun sendSms() {
        smsCountDown.value?.let {
            if (it > 0) {
                return
            }
        }
        phone.get()?.let {
            var _phone = it
            // 校验手机号
            if (!validatePhone(_phone)) {
                phoneError.set("")
                configSetUseCase.invoke(Triple("user_register", "phone", _phone))
                if (captcha.get().isNullOrBlank()) {
                    captchaError.set("请输入验证码")
                }
                captcha.get()?.let {
                    val t = it
                    // 校验图形验证码
                    val req = defaultReq("captcha/check?r=" + Math.random())
                    req.params?.let {
                        it.put("key", radKey)
                        it.put("code", t)
                    }
                    captchaCheckUseCase.invoke(req).observeForever {
                        when {
                            it.succeeded -> {
                                if ((it as Result.Success).data.code == Response.success) {
                                    // 发送短信
                                    requestSendSmsCode(_phone, t)
                                } else {
                                    captchaError.set("验证码不正确")
                                    changeCaptcha()
                                }
                            }
                        }
                    }
                }
            } else {
                phoneError.set("手机号码格式不正确")
            }
        }
    }

    fun requestSendSmsCode(phone: String, captcha: String) {
        captchaError.set("")
        val req = defaultReq("sms/code")
        req.params?.let {
            it.put("phone", phone)
            it.put("captcha", captcha)
        }
        req.method = HttpMethod.POST
        captchaCheckUseCase.invoke(req).observeForever {
            when {
                it.succeeded -> {
                    if ((it as Result.Success).data.code != Response.success) {
                        _toastMessage.postValue((it as Result.Success).data.msg)
                        // send notice
                        awesomeDialog.postValue(DialogType.error)
                    }else{
                        // 开始倒计时
                        startCount(BuildConfig.captchaTimeOut.toLong())
                        //
                        captchaVisibility.set(View.GONE)
                        //
                        configSetUseCase.invoke(Triple("user_register", "send_sms_time", Date().time.toString()))
                    }
                }
            }
        }
    }

    fun changeCaptcha() {
        configQueryUseCase.invoke(Pair("captcha", "url")).observeForever {
            val url = (it as Result.Success).data
            url?.let {
                captchaUrl.set(Uri.parse(it + "?key=${radKey}&r=" + Math.random()))
            }
        }
    }

    fun startCount(left: Long) {
        numberSubtractUseCase.invoke(Pair(left, smsCountDown))
    }

    fun showAgreementTxt(markdownView: MarkdownView) {
        configQueryUseCase.invoke(Pair("agreement", "content")).observeForever {
            val s = (it as Result.Success).data
            s?.let {
                markdownView.loadFromText(it)
            }
        }

    }

    // 同意用户协议checkbox 点击
    fun agreeCheckClick() {
        when (weiboVisibility.get()) {
            View.GONE -> {
                weiboVisibility.set(View.VISIBLE)
                agreeCheckTxt.set(R.string.agreement_fail)
            }
            else -> {
                weiboVisibility.set(View.GONE)
                agreeCheckTxt.set(R.string.agreement_ok)
            }
        }
    }

    fun keyLog() {
        key.get()?.let {
            configSetUseCase.invoke(Triple("user_register", "rand_key", it))
            Timber.i("rand key login ..")
        }
    }

    //匿名登陆
    fun anonymousLog() {
        if (anonymous.get().isNullOrBlank()) {
            anonymousError.set("请输入key")
        }
        anonymous.get()?.let {
            configSetUseCase.invoke(Triple("user_register", "anonymous", it))
            val req = defaultReq("user/anonymous-register")
            req.method = HttpMethod.POST
            req.params!!.put("nickName", it)
            registerUseCase.invoke(req).observeForever {
                when {
                    it.succeeded -> {
                        val tmp = (it as Result.Success).data
                        if (tmp.code == 200) {
                            tmp.dota?.let {
                                configSetUseCase.invoke(Triple("anonymous", "id", it.id.toString()))
                                configSetUseCase.invoke(Triple("anonymous", "key", it.key))
                                configSetUseCase.invoke(Triple("user", "sign", "0"))
                                keySetUseCase.invoke(it.key)
                                // 切到主页面
                                _toastMessage.postValue("注册成功")
                                signInViewModelDelegate.emitRegisterOk()
                                startNetty()
                            }
                        } else {
                            // show error
                            _toastMessage.postValue(tmp.msg)
                        }
                    }
                    else -> {
                        _toastMessage.postValue(defaultErrorTxt)
                    }
                }
            }
        }
    }

    // 请求注册
    fun connect() {
        if (phone.get().isNullOrEmpty()) {
            phoneError.set("请输入手机号")
            return
        } else {
            if (!validatePhone(phone.get()!!)) {
                phoneError.set("手机号码格式不正确")
                return
            }
        }
        if (passwod.get().isNullOrBlank()) {
            smsCodeError.set("请输入短信验证码")
            return
        }
        var needInviteCode = false
        if (eventType.equals(EventMthod.Register)) {
            // 需要邀请码
            if (inviteEnable.get() == View.VISIBLE) {
                needInviteCode = true
                if (inviteCode.get().isNullOrBlank()) {
                    inviteCodeError.set("请输入邀请码")
                    return
                }
            }
        }
        val req = defaultReq("user/register")
        req.method = HttpMethod.POST
        req.params!!.put("phone", phone.get()!!)
        req.params!!.put("smsCode", passwod.get()!!)
        if (needInviteCode) {
            req.params!!.put("inviteCode", inviteCode.get()!!)
        }
        registerUseCase.invoke(req).observeForever {
            when {
                it.succeeded -> {
                    val tmp = (it as Result.Success).data
                    if (tmp.code == 200) {
                        tmp.dota?.let {
                            configSetUseCase.invoke(Triple("user", "id", it.id.toString()))
                            configSetUseCase.invoke(Triple("user", "key", it.key))
                            keySetUseCase.invoke(it.key)
                            // 切到主页面
                            _toastMessage.postValue("注册成功")
                            configSetUseCase.invoke(Triple("user", "sign", "1"))
                            signInViewModelDelegate.emitRegisterOk()
                            startNetty()
                        }
                    } else {
                        // show error
                        _toastMessage.postValue(tmp.msg)
                        awesomeDialog.postValue(DialogType.error)
                    }
                }
                else -> {
                    _toastMessage.postValue(defaultErrorTxt)
                }
            }
        }

    }


    fun changeCountDown(it: Long) {
        if (it > 0) {
            sendTxt.set(it.toString())
        } else {
            sendTxt.set("发送")
            sendBtnVisibility.set(captchaVisibility.get())
        }
    }

    fun initRegParam() {
        configQueryUseCase.invoke(Pair("user_register", "send_sms_time")).observeForever {
            var s = (it as Result.Success).data
            if ("".equals(s)) {
                s = "0"
            } else {
                smsCodeVisible.set(View.VISIBLE)
            }
            val last = s?.let { Date().time - it.toLong() }
            last?.let {
                Timber.i("left time ${it}")
                if (last > BuildConfig.captchaTimeOut * 1000) {
                    captchaVisibility.set(View.VISIBLE)
                    sendBtnVisibility.set(View.VISIBLE)
                    btnEvt.postValue(0)
                } else {
                    captchaVisibility.set(View.GONE)
                    btnEvt.postValue(1)
                    startCount(BuildConfig.captchaTimeOut - it / 1000)
                }
            }
        }
    }

    // 加载验证码使用说明
    fun showRegCodeNot(markdownView: MarkdownView) {
        configQueryUseCase.invoke(Pair("agreement", "sms_code")).observeForever {
            markdownView.loadFromText((it as Result.Success).data)
        }
    }

    fun startNetty() {
        nettyClientUseCase.execute(BuildConfig.Socket)
    }

    fun validatePhone(phone: String): Boolean {
        var result = false
        if(!phoneValidateTxt.get().isNullOrBlank()){
            val phoneVlid=phoneValidateTxt.get()
            phoneVlid?.let {
                val reg = Pattern.compile(it)
                if (reg.matcher(phone).matches()) {
                    result=true
                }
            }
        }
        return result
    }

    fun login(){
        Timber.i("login method invoke")
    }

}
