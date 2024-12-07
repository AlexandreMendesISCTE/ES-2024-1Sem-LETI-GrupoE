package com.example;

/**
 * Represents a property with various attributes such as object ID, parcel details,
 * geometry, owner information, and location details (freguesia, municipio, ilha).
 */
public class Property {
    // Private fields to store property details
    private String objectId;     // Unique identifier for the property
    private String parId;        // Parcel ID
    private String parNum;       // Parcel number
    private String shapeLength;  // Boundary length of the property
    private String shapeArea;    // Area of the property
    private String geometry;     // Geometry in WKT (Well-Known Text) format
    private String owner;        // Owner of the property
    private String freguesia;    // Freguesia (parish) where the property is located
    private String municipio;    // Municipality where the property is located
    private String ilha;         // Island where the property is located

    /**
     * Constructs a Property object with all the required attributes.
     *
     * @param objectId    Unique identifier for the property.
     * @param parId       Parcel ID of the property.
     * @param parNum      Parcel number of the property.
     * @param shapeLength Length of the property boundary.
     * @param shapeArea   Area of the property.
     * @param geometry    Geometry of the property in WKT format.
     * @param owner       Owner of the property.
     * @param freguesia   Freguesia (parish) where the property is located.
     * @param municipio   Municipality where the property is located.
     * @param ilha        Island where the property is located.
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

    // Getter and setter methods for each field

    /**
     * Gets the object ID of the property.
     *
     * @return The object ID.
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the object ID of the property.
     *
     * @param objectId The new object ID.
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Gets the parcel ID of the property.
     *
     * @return The parcel ID.
     */
    public String getParId() {
        return parId;
    }

    /**
     * Sets the parcel ID of the property.
     *
     * @param parId The new parcel ID.
     */
    public void setParId(String parId) {
        this.parId = parId;
    }

    /**
     * Gets the parcel number of the property.
     *
     * @return The parcel number.
     */
    public String getParNum() {
        return parNum;
    }

    /**
     * Sets the parcel number of the property.
     *
     * @param parNum The new parcel number.
     */
    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    /**
     * Gets the length of the property boundary.
     *
     * @return The boundary length.
     */
    public String getShapeLength() {
        return shapeLength;
    }

    /**
     * Sets the length of the property boundary.
     *
     * @param shapeLength The new boundary length.
     */
    public void setShapeLength(String shapeLength) {
        this.shapeLength = shapeLength;
    }

    /**
     * Gets the area of the property.
     *
     * @return The area.
     */
    public String getShapeArea() {
        return shapeArea;
    }

    /**
     * Sets the area of the property.
     *
     * @param shapeArea The new area.
     */
    public void setShapeArea(String shapeArea) {
        this.shapeArea = shapeArea;
    }

    /**
     * Gets the geometry of the property in WKT format.
     *
     * @return The geometry in WKT format.
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Sets the geometry of the property in WKT format.
     *
     * @param geometry The new geometry in WKT format.
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets the owner of the property.
     *
     * @return The owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the property.
     *
     * @param owner The new owner.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the freguesia (parish) where the property is located.
     *
     * @return The freguesia.
     */
    public String getFreguesia() {
        return freguesia;
    }

    /**
     * Sets the freguesia (parish) where the property is located.
     *
     * @param freguesia The new freguesia.
     */
    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    /**
     * Gets the municipality where the property is located.
     *
     * @return The municipality.
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Sets the municipality where the property is located.
     *
     * @param municipio The new municipality.
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Gets the island where the property is located.
     *
     * @return The island.
     */
    public String getIlha() {
        return ilha;
    }

    /**
     * Sets the island where the property is located.
     *
     * @param ilha The new island.
     */
    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    /**
     * Returns a string representation of the property, including all its details.
     *
     * @return A string representing the property.
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
