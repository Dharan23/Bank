package com.application.bank.ui.statement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.bank.R
import com.application.bank.data.network.response.Statement
import com.application.bank.databinding.LayoutEmptyBinding
import com.application.bank.databinding.LayoutStatementBinding
import com.application.bank.ui.base.BaseViewHolder

private const val VIEW_TYPE_NORMAL = 1
private const val VIEW_TYPE_EMPTY = 0

class StatementAdapter(private val listener: (View, Int) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var statementList = ArrayList<Statement>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding: LayoutStatementBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_statement,
                    parent, false
                )
                return StatementViewHolder(binding)
            }
            else -> {
                val binding: LayoutEmptyBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context)
                    , R.layout.layout_empty,
                    parent, false
                )
                return EmptyViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        if (statementList.isNotEmpty())
            return statementList.size
        return 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listener)
    }

    override fun getItemViewType(position: Int): Int {
        if (statementList.isNullOrEmpty()) {
            return VIEW_TYPE_EMPTY
        }
        return VIEW_TYPE_NORMAL
    }

    fun updateStatementList(statements: List<Statement>) {
        statementList.clear()
        statementList.addAll(statements)
        notifyDataSetChanged()
    }

    inner class StatementViewHolder(private val binding: LayoutStatementBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(
            listener: (View, Int) -> Unit
        ) {
            val statement = statementList[adapterPosition]
            binding.statement = statement
            binding.executePendingBindings()
            binding.statementCard.setOnClickListener {
                listener(binding.root, adapterPosition)
            }
        }
    }

    inner class EmptyViewHolder(private val binding: LayoutEmptyBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(
            listener: (View, Int) -> Unit
        ) {
        }
    }
}