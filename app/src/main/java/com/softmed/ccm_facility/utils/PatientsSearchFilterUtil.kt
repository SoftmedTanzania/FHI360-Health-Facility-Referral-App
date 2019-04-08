package com.softmed.ccm_facility.utils

import com.softmed.ccm_facility.dom.objects.Patient
import kotlin.collections.ArrayList

class PatientsSearchFilterUtil {

    var patientList : List<Patient>  = ArrayList()

    constructor(patients : List<Patient> ){
        this.patientList = patientList
    }

    fun searchByNames (nameQuery: String) : List<Patient> {
        val res =  patientList.filter {
            it.patientFirstName.contains(nameQuery, ignoreCase = true) ||
                    it.patientSurname.contains(nameQuery, ignoreCase = true)
        }
        return res
    }

    fun searchByVillage (villageQuery : String ) : List<Patient> {
        val res =  patientList.filter {
            it.village.startsWith(villageQuery, ignoreCase = true)
        }
        return res
    }

}