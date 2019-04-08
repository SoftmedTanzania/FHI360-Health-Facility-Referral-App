package com.softmed.ccm_facility.utils

import android.support.annotation.Nullable
import android.support.v7.util.DiffUtil
import com.softmed.ccm_facility.dom.objects.Patient

class PatientsDiffCallback (private val mOldPatientList : List<Patient> , private val mNewPatientList : List<Patient>) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldKid = mOldPatientList[oldItemPosition]
        val newKid = mNewPatientList[newItemPosition]

        return ( oldKid == newKid)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldPatientList[oldItemPosition].id === mNewPatientList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return mOldPatientList.size
    }

    override fun getNewListSize(): Int {
        return mNewPatientList.size
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }


}