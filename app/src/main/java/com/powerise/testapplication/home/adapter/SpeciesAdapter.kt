package com.powerise.testapplication.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.powerise.testapplication.R
import com.powerise.testapplication.databinding.RowProgressBinding
import com.powerise.testapplication.databinding.RowSpeciesBinding
import com.powerise.testapplication.home.core.IPageLoader
import com.powerise.testapplication.home.models.SpeciesModel
import com.powerise.testapplication.home.viewmodel.RowSpeciesViewModel

class SpeciesAdapter(
    context: Context,
    private val speciesList: ArrayList<SpeciesModel>,
    private val pageLoad: IPageLoader
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val loading = 1
    private val item = 2
    private var isLoading = false
    private var isEndPagination = false


    override fun onCreateViewHolder(viewGroup: ViewGroup, itemView: Int): RecyclerView.ViewHolder {
        return if (itemView == item) {
            val binding = DataBindingUtil.inflate<RowSpeciesBinding>(inflater, R.layout.row_species, viewGroup, false)
            return SpeciesViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<RowProgressBinding>(inflater, R.layout.row_progress, viewGroup, false)
            return ProgressViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (!isEndPagination) (speciesList.size + 1) else speciesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == (speciesList.size) && isLoading && !isEndPagination) loading else item
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is SpeciesViewHolder) {
            if (position == (speciesList.size - 1) && !isLoading && !isEndPagination) {
                isLoading = true
                pageLoad.loadNextPage()
            }

            if (position <= (speciesList.size - 1)) {
                val speciesViewModel = RowSpeciesViewModel(
                    currentState = "Current State : " + if (speciesList[position].isExtinct) "Active" else "Extinct",
                    name = "Name : " + speciesList[position].name,
                    classification = "Classification : " + speciesList[position].classification,
                    designation = "Designation : " + speciesList[position].designation,
                    onClickListener = View.OnClickListener {
                        speciesList[position].isExtinct = !speciesList[position].isExtinct
                        notifyItemChanged(position)
                    }
                )
                viewHolder.setModel(speciesViewModel)
            }

        }
    }

    fun addSpecies(speciesList: ArrayList<SpeciesModel>) {
        val lastPosition = this.speciesList.size
        isLoading = false
        this.speciesList.addAll(speciesList)
        notifyItemRangeInserted(lastPosition, speciesList.size)
    }

    fun noElementFound() {
        notifyItemRemoved(speciesList.size + 1)
        isLoading = false
        isEndPagination = true
    }

    fun clearAll() {
        speciesList.clear()
        notifyDataSetChanged()
    }

    inner class SpeciesViewHolder(private val binding: RowSpeciesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setModel(model: RowSpeciesViewModel) {
            binding.model = model
        }
    }

    inner class ProgressViewHolder(binding: RowProgressBinding) : RecyclerView.ViewHolder(binding.root)
}