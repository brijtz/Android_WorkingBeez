
package com.app.workingbeez.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesListRequest {

    @SerializedName("Token")
    @Expose
    public String token;
    @SerializedName("DeviceID")
    @Expose
    public String deviceID;
    @SerializedName("PageIndex")
    @Expose
    public long pageIndex;

}
