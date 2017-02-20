
package com.app.workingbeez.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

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
    public Data data;

    public class Data {
    }

}
