package com.softmed.ccm_facility.utils

import android.support.annotation.Nullable
import android.support.v7.util.DiffUtil
import com.softmed.ccm_facility.dom.objects.Referral

class ReferralsDiffCallback(private val oldReferralList : List<Referral>, private val newReferralList : List<Referral>) : DiffUtil.Callback(){

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldReferral  = oldReferralList[oldItemPosition]
        val newReferral = newReferralList[newItemPosition]

        return (oldReferral == newReferral)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldReferralList[oldItemPosition].id === newReferralList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldReferralList.size
    }

    override fun getNewListSize(): Int {
        return newReferralList.size
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}