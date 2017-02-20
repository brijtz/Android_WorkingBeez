
package com.app.workingbeez.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesListResponse {

    @SerializedName("ReturnCode")
    @Expose
    public String returnCode;
    @SerializedName("ReturnMsg")
    @Expose
    public String returnMsg;
    @SerializedName("ReturnValue")
    @Expose
    public String returnValue;
    @SerializedName("Data")
    @Expose
    public List<Datum> data = null;

public class Datum {

    @SerializedName("ID")
    @Expose
    public long iD;
    @SerializedName("Name")
    @Expose
    public String name;

}

}
