
package com.andela.art.models;

import com.google.gson.annotations.SerializedName;

/**
 * Incident report model.
 */
public class IncidentModel {

    @SerializedName("incident_description")
    private String mIncidentDescription;
    @SerializedName("incident_location")
    private String mIncidentLocation;
    @SerializedName("injuries_sustained")
    private String mInjuriesSustained;
    @SerializedName("loss_of_property")
    private String mLossOfProperty;
    @SerializedName("police_abstract_obtained")
    private String mPoliceAbstractObtained;
    @SerializedName("witnesses")
    private String mWitnesses;

    /**
     * Description getter.
     * @return String - description
     */
    public String getIncidentDescription() {
        return mIncidentDescription;
    }

    /**
     * Description setter.
     * @param incidentDescription - description of incident
     */
    public void setIncidentDescription(String incidentDescription) {
        mIncidentDescription = incidentDescription;
    }

    /**
     * Location getter.
     * @return String - location
     */
    public String getIncidentLocation() {
        return mIncidentLocation;
    }

    /**
     * Location setter.
     * @param incidentLocation - location of incident
     */
    public void setIncidentLocation(String incidentLocation) {
        mIncidentLocation = incidentLocation;
    }

    /**
     * InjuriesSustained getter.
     * @return String - injury sustained on incident
     */
    public String getInjuriesSustained() {
        return mInjuriesSustained;
    }

    /**
     * InjuriesSustained setter.
     * @param injuriesSustained - injury sustained on incident
     */
    public void setInjuriesSustained(String injuriesSustained) {
        mInjuriesSustained = injuriesSustained;
    }

    /**
     * Loss of property getter.
     * @return String -lossOf Property
     */
    public String getLossOfProperty() {
        return mLossOfProperty;
    }

    /**
     * Lost property setter.
     * @param lossOfProperty - loss Of Property
     */
    public void setLossOfProperty(String lossOfProperty) {
        mLossOfProperty = lossOfProperty;
    }

    /**
     * Police abstract getter.
     * @return String - Police Abstract
     */
    public String getPoliceAbstractObtained() {
        return mPoliceAbstractObtained;
    }

    /**
     * Police abstract setter.
     * @param policeAbstractObtained -police Abstract Obtiained
     */
    public void setPoliceAbstractObtained(String policeAbstractObtained) {
        mPoliceAbstractObtained = policeAbstractObtained;
    }

    /**
     *  witness getter.
     * @return String - Witness
     */
    public String getWitnesses() {
        return mWitnesses;
    }

    /**
     * Witness setter.
     * @param witnesses - witness names
     */
    public void setWitnesses(String witnesses) {
        mWitnesses = witnesses;
    }

}
