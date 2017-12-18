package apps.softmed.com.hfreferal.dom.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by issy on 11/23/17.
 */

public class Team implements Serializable {

    @SerializedName("identifier")
    private String identifier;

    @SerializedName("display")
    private String display;

    @SerializedName("patients")
    private ArrayList<String> patients;

    @SerializedName("uuid")
    private String UUID;

    public Team() {}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public ArrayList<String> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<String> patients) {
        this.patients = patients;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
