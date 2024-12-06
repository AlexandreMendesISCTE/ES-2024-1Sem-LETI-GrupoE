package com.example;

/**
 * The Property class represents a property with various attributes such as object ID, parcel ID,
 * geometry, owner, and location details.
 */
public class Property {
    // Private fields for storing property details
    private String objectId;
    private String parId;
    private String parNum;
    private String shapeLength;
    private String shapeArea;
    private String geometry;
    private String owner;
    private String freguesia;
    private String municipio;
    private String ilha;

    /**
     * Constructor to initialize all fields of the Property class.
     *
     * @param objectId Unique identifier for the property.
     * @param parId Parcel ID of the property.
     * @param parNum Parcel number of the property.
     * @param shapeLength Length of the property boundary.
     * @param shapeArea Area of the property.
     * @param geometry Geometry of the property in WKT format.
     * @param owner Owner of the property.
     * @param freguesia Freguesia (parish) where the property is located.
     * @param municipio Municipality where the property is located.
     * @param ilha Island where the property is located.
     */
    public Property(String objectId, String parId, String parNum, String shapeLength, String shapeArea,
                    String geometry, String owner, String freguesia, String municipio, String ilha) {
        this.objectId = objectId;
        this.parId = parId;
        this.parNum = parNum;
        this.shapeLength = shapeLength;
        this.shapeArea = shapeArea;
        this.geometry = geometry;
        this.owner = owner;
        this.freguesia = freguesia;
        this.municipio = municipio;
        this.ilha = ilha;
    }

    // Getter and setter methods for all fields

    /**
     * Gets the object ID of the property.
     *
     * @return The object ID of the property.
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the object ID of the property.
     *
     * @param objectId The new object ID of the property.
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Gets the parcel ID of the property.
     *
     * @return The parcel ID of the property.
     */
    public String getParId() {
        return parId;
    }

    /**
     * Sets the parcel ID of the property.
     *
     * @param parId The new parcel ID of the property.
     */
    public void setParId(String parId) {
        this.parId = parId;
    }

    /**
     * Gets the parcel number of the property.
     *
     * @return The parcel number of the property.
     */
    public String getParNum() {
        return parNum;
    }

    /**
     * Sets the parcel number of the property.
     *
     * @param parNum The new parcel number of the property.
     */
    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    /**
     * Gets the length of the property boundary.
     *
     * @return The length of the property boundary.
     */
    public String getShapeLength() {
        return shapeLength;
    }

    /**
     * Sets the length of the property boundary.
     *
     * @param shapeLength The new length of the property boundary.
     */
    public void setShapeLength(String shapeLength) {
        this.shapeLength = shapeLength;
    }

    /**
     * Gets the area of the property.
     *
     * @return The area of the property.
     */
    public String getShapeArea() {
        return shapeArea;
    }

    /**
     * Sets the area of the property.
     *
     * @param shapeArea The new area of the property.
     */
    public void setShapeArea(String shapeArea) {
        this.shapeArea = shapeArea;
    }

    /**
     * Gets the geometry of the property in WKT format.
     *
     * @return The geometry of the property.
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Sets the geometry of the property in WKT format.
     *
     * @param geometry The new geometry of the property.
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets the owner of the property.
     *
     * @return The owner of the property.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the property.
     *
     * @param owner The new owner of the property.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the freguesia (parish) where the property is located.
     *
     * @return The freguesia where the property is located.
     */
    public String getFreguesia() {
        return freguesia;
    }

    /**
     * Sets the freguesia (parish) where the property is located.
     *
     * @param freguesia The new freguesia where the property is located.
     */
    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    /**
     * Gets the municipality where the property is located.
     *
     * @return The municipality where the property is located.
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Sets the municipality where the property is located.
     *
     * @param municipio The new municipality where the property is located.
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Gets the island where the property is located.
     *
     * @return The island where the property is located.
     */
    public String getIlha() {
        return ilha;
    }

    /**
     * Sets the island where the property is located.
     *
     * @param ilha The new island where the property is located.
     */
    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    /**
     * Overridden toString method for representing the property details as a String.
     *
     * @return A string representation of the property details.
     */
    @Override
    public String toString() {
        return "Property{" +
                "objectId='" + objectId + '\'' +
                ", parId='" + parId + '\'' +
                ", parNum='" + parNum + '\'' +
                ", shapeLength='" + shapeLength + '\'' +
                ", shapeArea='" + shapeArea + '\'' +
                ", geometry='" + geometry + '\'' +
                ", owner='" + owner + '\'' +
                ", freguesia='" + freguesia + '\'' +
                ", municipio='" + municipio + '\'' +
                ", ilha='" + ilha + '\'' +
                '}';
    }
}
