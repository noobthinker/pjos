package com.pj109.xkorey.pjos.util

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog
import com.pj109.xkorey.model.data.DialogType
import com.pj109.xkorey.model.data.DialogVo
import com.pj109.xkorey.pjos.R
import timber.log.Timber

inline fun errorDialog(vo: DialogVo) {
    AwesomeErrorDialog(vo.context)
        .setTitle(vo.title)
        .setMessage(vo.message)
        .setColoredCircle(R.color.dialogErrorBackgroundColor)
        .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
        .setCancelable(true).setButtonText(vo.bttonTxt)
        .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
        .setButtonText(vo.bttonTxt)
        .setErrorButtonClick {
            Timber.i("error button click")
            vo.status.postValue(DialogType.none)
        }
        .show()
}