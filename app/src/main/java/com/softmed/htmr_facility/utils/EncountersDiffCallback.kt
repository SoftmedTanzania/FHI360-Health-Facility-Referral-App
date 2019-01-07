package com.softmed.htmr_facility.utils

import android.support.annotation.Nullable
import android.support.v7.util.DiffUtil
import com.softmed.htmr_facility.dom.objects.TbEncounters

class EncountersDiffCallback (private val mOldEncounters : List<TbEncounters>, private val mNewEncoutners : List<TbEncounters>) : DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return mOldEncounters.size
    }

    override fun getNewListSize(): Int {
        return mNewEncoutners.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEncounter = mOldEncounters[oldItemPosition]
        val newEncounter = mNewEncoutners[newItemPosition]
        return (oldEncounter == newEncounter)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEncounters[oldItemPosition].id === mNewEncoutners[newItemPosition].id
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}