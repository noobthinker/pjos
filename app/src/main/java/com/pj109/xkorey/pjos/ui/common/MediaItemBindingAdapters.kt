package com.pj109.xkorey.pjos.ui.common

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.util.CircularOutlineProvider
import timber.log.Timber

const val SERVICE_ACTION = "android.support.customtabs.action.CustomTabsService"
const val CHROME_PACKAGE = "com.android.chrome"


@BindingAdapter("mediaTagBind")
fun mediaTagBind(recyclerView: RecyclerView, mediaTag: List<Tag>?) {

    recyclerView.adapter = (recyclerView.adapter as? TagAdapter ?: TagAdapter())
        .apply {
            tags = mediaTag ?: emptyList()
        }
}


@BindingAdapter("tagTint")
fun tagTint(textView: TextView, color: Int) {
    (textView.compoundDrawablesRelative[0] as? GradientDrawable)?.setColor(
        tagTintOrDefault(
            color,
            textView.context
        )
    )
}

fun tagTintOrDefault(color: Int, context: Context): Int {
    return if (color != Color.TRANSPARENT) {
        color
    } else {
        ContextCompat.getColor(context, R.color.default_tag_color)
    }
}


@BindingAdapter(value = ["imageUri", "placeholder"], requireAll = false)
fun imageUri(imageView: ImageView, imageUri: Uri?, placeholder: Drawable?) {
    val placeholderDrawable = placeholder ?: AppCompatResources.getDrawable(
        imageView.context, R.drawable.generic_placeholder
    )
    when (imageUri) {
        null -> {
            Glide.with(imageView)
                .load(placeholderDrawable)
                .into(imageView)
        }
        else -> {
            Glide.with(imageView)
                .load(imageUri)
                .apply(RequestOptions().placeholder(placeholderDrawable))
                .into(imageView)
        }
    }
}

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun imageUrl(imageView: ImageView, imageUrl: String?, placeholder: Drawable?) {
    imageUri(imageView, imageUrl?.toUri(), placeholder)
}

@BindingAdapter("clipToCircle")
fun clipToCircle(view: View, clip: Boolean) {
    view.clipToOutline = clip
    view.outlineProvider = if (clip) CircularOutlineProvider else null
}

@BindingAdapter("websiteLink")
fun websiteLink(
    button: Button,
    url: String?
) {
    url ?: return
    button.setOnClickListener {
        val context = it.context
        if (context.isChromeCustomTabsSupported()) {
            CustomTabsIntent.Builder().apply {
                setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }.build().launchUrl(context, Uri.parse(url))
        } else {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent, null)
        }
    }
}

@BindingAdapter("app:errorText")
fun setErrorText(view: TextInputLayout, errorText: String?) {
    view.error = errorText
}


private fun Context.isChromeCustomTabsSupported(): Boolean {
    val serviceIntent = Intent(SERVICE_ACTION)
    serviceIntent.setPackage(CHROME_PACKAGE)
    val resolveInfos = packageManager.queryIntentServices(serviceIntent, 0)
    return !(resolveInfos == null || resolveInfos.isEmpty())
}


@BindingAdapter("fabVisibility")
fun fabVisibility(fab: FloatingActionButton, visible: Boolean) {
    if (visible) fab.show() else fab.hide()
}


