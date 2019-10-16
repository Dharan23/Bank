package com.application.bank.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(binding: View) : RecyclerView.ViewHolder(binding) {
    abstract fun bind(listener: (View, Int) -> Unit)
}