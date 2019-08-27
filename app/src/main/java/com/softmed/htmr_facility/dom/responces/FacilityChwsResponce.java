package com.softmed.htmr_facility.dom.responces;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Coze on 02/26/19.
 */
public class FacilityChwsResponce implements Serializable {

    @SerializedName("display")
    private String display;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("teamRole")
    private TeamRole  teamRole;

    @SerializedName("person")
    private Person  person;


    public class TeamRole implements Serializable{

        @SerializedName("identifier")
        private String identifier;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
    }
    public class Person implements Serializable{

        @SerializedName("uuid")
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TeamRole getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(TeamRole teamRole) {
        this.teamRole = teamRole;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
