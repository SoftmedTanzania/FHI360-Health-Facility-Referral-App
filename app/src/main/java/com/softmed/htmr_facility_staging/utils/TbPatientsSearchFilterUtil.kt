package com.softmed.htmr_facility_staging.utils

import com.softmed.htmr_facility_staging.dom.objects.TbPatient

class TbPatientsSearchFilterUtil {

    var tbPatients : List<TbPatient> = ArrayList()

    constructor(patients : List<TbPatient>){
        this.tbPatients = patients
    }

    /*
    fun searchByNames(searchQuery : String) : List<TbPatient> {
        val result = tbPatients.filter {

        }
    }

    fun searchByVillage(searchQuery: String) : List<TbPatient> {

    }*/

}