package com.neowise.spic.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.neowise.spic.R
import java.io.IOException
import java.util.*

fun timeTask(delay: Long, block: () -> Unit) {
    val task = object : TimerTask() {
        override fun run() {
            block()
        }
    }
    Timer().schedule(task, delay)
}

fun Int.monthName(): String {
    return Month.values()[this-1].text
}

fun Context.sendEmail(email: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
    intent.type = "*/*"
    startActivity(intent)
}

fun Context.phoneCall(number: String) {
    val uri = "tel:+" + number.onlyNumbers()
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(uri))
    startActivity(intent)
}

private fun String.onlyNumbers(): String {
    return filter { it.isDigit() }
}

fun loadImage(context: Context, id: Int, imageView: ImageView) {
    val firebase = FirebaseStorage.getInstance()
    val storageReference = firebase.reference
    try {
        storageReference.child("avatars/$id").downloadUrl.addOnSuccessListener {
            println("image:$id")
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.placeholder)
                .into(imageView)
        }
    }
    catch (_: IOException) { }
}

fun loadImage(context: Context, url: String, imageView: ImageView) {
    Log.d("PICASSO", url)
    if (url.isNotEmpty()) {
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(imageView)
    }
    else {
        Picasso.get().load(R.drawable.placeholder).into(imageView)
    }
}