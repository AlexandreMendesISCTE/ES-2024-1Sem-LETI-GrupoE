package com.example;

public class Property {
    private String objectId;
    private String parId;
    private String parNum;
    private String shapeLength;
    private String shapeArea;
    private String geometry;
    private String owner;

    // Getters and Setters
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParNum() {
        return parNum;
    }

    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    public String getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(String shapeLength) {
        this.shapeLength = shapeLength;
    }

    public String getShapeArea() {
        return shapeArea;
    }

    public void setShapeArea(String shapeArea) {
        this.shapeArea = shapeArea;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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
                '}';
    }
}