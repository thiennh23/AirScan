package com.example.airscan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Asset {

    @SerializedName("id")
    public String id;

    @SerializedName("version")
    public String version;

    @SerializedName("createdOn")
    public String createdOn;

    @SerializedName("name")
    public String name;

    @SerializedName("accessPublicRead")
    public String accessPublicRead;

    @SerializedName("parentID")
    public String parentID;

    @SerializedName("realm")
    public String realm;

    @SerializedName("type")
    public String type;

    /*@SerializedName("path")
    public List<String> path;*/

    @SerializedName("attributes")
    public Object attributes;

    //GETTER


    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getName() {
        return name;
    }

    public String getAccessPublicRead() {
        return accessPublicRead;
    }

    public String getParentID() {
        return parentID;
    }

    public String getRealm() {
        return realm;
    }

    public String getType() {
        return type;
    }

    /*public List<String> getPath() {
        return path;
    }*/

    public Object getAttributes() {
        return attributes;
    }

    //SETTER

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessPublicRead(String accessPublicRead) {
        this.accessPublicRead = accessPublicRead;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*public void setPath(List<String> path) {
        this.path = path;
    }*/

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }
}

