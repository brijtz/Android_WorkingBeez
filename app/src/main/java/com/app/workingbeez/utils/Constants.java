package com.app.workingbeez.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by divyeshshani on 11/09/16.
 */
public class Constants {


    public static final String LOGIN_RESPONSE = "login_response";
    public static final String NAME = "name";
    public static final String CATEGORY_LIST_RESPONSE = "category_list_response";
    public static final String POSTER_REG_DATA = "poster_reg_data";
    public static final String VERSION = "V-1.0.0";
    public static final String PACKAGE_NAME = "com.app.workingbeez";
    public static final String FOLDER_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "Squirrel";

    // === Fragment Tags === //
    public static final String FRAGMENT_CATEGORY = "CategoryFragment";
    public static final String FRAGMENT_JOB_TITLE = "JobTitleFragment";
    public static final String FRAGMENT_CERTIFICATION = "CertificationFragment";
    public static final String FRAGMENT_CORE_SKILL = "CoreSkillFragment";
    public static final String FRAGMENT_EXPERIENCE = "ExperienceFragment";
    public static int TIMEOUT = 100000;

}
