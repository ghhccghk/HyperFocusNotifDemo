package com.ghhccghk.hyperfocusnotifdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes

class Preferences(context: Context) {
    private val preferencesView: View =
        LayoutInflater.from(context).inflate(R.layout.items_md3_preferences, null)
    val preferencesTitle: TextView = preferencesView.findViewById(R.id.switchTitle)
    val preferencesSummary: TextView = preferencesView.findViewById(R.id.switchSummary)

    fun getView(): View = preferencesView

    fun setTitle(title: String) {
        preferencesTitle.text = title
    }

    fun setSummary(summary: String?) {
        if (summary.isNullOrBlank()) {
            preferencesSummary.visibility = View.GONE
        } else {
            preferencesSummary.visibility = View.VISIBLE
            preferencesSummary.text = summary
        }
    }

    fun setSummary(@StringRes resId: Int) {
        setSummary(preferencesView.context.getString(resId))
    }
}