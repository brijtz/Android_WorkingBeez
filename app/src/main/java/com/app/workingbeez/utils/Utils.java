package com.app.workingbeez.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.app.workingbeez.R;
import com.app.workingbeez.ui.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * Created by root on 17/6/16.
 */

public class Utils {

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }


    public static boolean resetExternalStorageMedia(Context context,
                                                    String filePath) {
        try {
            if (Environment.isExternalStorageEmulated())
                return (false);
            Uri uri = Uri.parse("file://" + new File(filePath));
            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);

            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }

        return (true);
    }

    public static void notifyMediaScannerService(Context context,
                                                 String filePath) {

        MediaScannerConnection.scanFile(context, new String[]{filePath},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Debug.i("ExternalStorage", "Scanned " + path + ":");
                        Debug.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public static void setTimerValue(Context c, String value) {
        setPref(c, "Timer", value);
    }

    public static String getTimerValue(Context c) {
        return getPref(c, "Timer", "");
    }

    public static void ResetTimer(Context c) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.remove("Timer");
    }

    public static void setPref(Context c, String pref, String val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String pref, String val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(pref,
                val);
    }

    public static void setPref(Context c, String pref, boolean val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();

    }

    public static boolean getPref(Context c, String pref, boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
                pref, val);
    }

    public static void delPref(Context c, String pref) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.remove(pref);
        e.commit();
    }

    public static void setPref(Context c, String pref, int val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();

    }

    public static int getPref(Context c, String pref, int val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(pref,
                val);
    }

    public static void setPref(Context c, String pref, long val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, val);
        e.commit();
    }

    public static long getPref(Context c, String pref, long val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(pref,
                val);
    }

    public static void setPref(Context c, String file, String pref, String val) {
        SharedPreferences settings = c.getSharedPreferences(file,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor e = settings.edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String file, String pref, String val) {
        return c.getSharedPreferences(file, Context.MODE_PRIVATE).getString(
                pref, val);
    }


    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static void sendExceptionReport(Exception e) {
        e.printStackTrace();

        try {
            // Writer result = new StringWriter();
            // PrintWriter printWriter = new PrintWriter(result);
            // e.printStackTrace(printWriter);
            // String stacktrace = result.toString();
            // new CustomExceptionHandler(c, URLs.URL_STACKTRACE)
            // .sendToServer(stacktrace);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public static int getDeviceWidth(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 480;
    }

    public static int getDeviceHeight(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 800;
    }

    public static void hideKeyBoard(Context c, IBinder iBinder) {
        InputMethodManager imm = (InputMethodManager) c
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(iBinder, 0);
    }

    public static String parseCalendarFormat(Calendar c, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return sdf.format(c.getTime());
    }

    public static String parseTime(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return sdf.format(new Date(time));
    }

    public static Date parseTime(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String parseTime(String time, String fromPattern,
                                   String toPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern,
                Locale.getDefault());
        try {
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(toPattern, Locale.getDefault());
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getDate(String d) {

        return parseTime(d, "dd MMM yyyy hh:mm a", "dd MMM yyyy");
    }

    public static String getTime(String d) {

        return parseTime(d, "dd MMM yyyy hh:mm a", "hh:mm a");
    }

    public static long getTimeMillisecond(String d) {

        String dd = d.replaceAll(Pattern.quote("/Date("), "");

        return Long.parseLong(dd.substring(0, dd.length() - 2));
    }

    public static String nullSafe(String content) {
        if (content == null) {
            return "";
        }

        return content;
    }

    public static String nullSafe(Object content) {
        if (content == null) {
            return "";
        }

        return content.toString();
    }

    public static String nullSafe(String content, String defaultStr) {
        if (content.isEmpty()) {
            return defaultStr;
        }

        return content;
    }

    public static String nullSafeDash(String content) {
        if (content.isEmpty()) {
            return "-";
        }

        return content;
    }

    public static String nullSafe(int content, String defaultStr) {
        if (content == 0) {
            return defaultStr;
        }

        return "" + content;
    }

    public static Typeface getRobotoRegular(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "fonts/Roboto-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Typeface getRobotoLight(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "fonts/Roboto-Thin.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Typeface getRobotoBold(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "fonts/Roboto-Bold.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Typeface getCondensedNormal(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "fonts/RobotoCondensed-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static boolean isGPSProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetworkProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isLocationProviderEnabled(Context context) {
        return (isGPSProviderEnabled(context) || isNetworkProviderEnabled(context));
    }

    public static ArrayList<String> asList(String str) {
        ArrayList<String> items = new ArrayList<String>(Arrays.asList(str
                .split("\\s*,\\s*")));

        return items;
    }

    public static String implode(ArrayList<String> data) {
        try {
            String devices = "";
            for (String iterable_element : data) {
                devices = devices + "," + iterable_element;
            }

            if (devices.length() > 0 && devices.startsWith(",")) {
                devices = devices.substring(1);
            }

            return devices;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getExtenstion(String urlPath) {
        if (urlPath.contains(".")) {
            String extension = urlPath.substring(urlPath.lastIndexOf(".") + 1);
            return extension;
        }

        return "";
    }

    public static byte[] convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static boolean isInternetConnected(Context mContext) {
        boolean outcome = false;

        try {
            if (mContext != null) {
                ConnectivityManager cm = (ConnectivityManager) mContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo[] networkInfos = cm.getAllNetworkInfo();

                for (NetworkInfo tempNetworkInfo : networkInfos) {
                    if (tempNetworkInfo.isConnected()) {
                        outcome = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outcome;
    }

    public static void appendLog(String text) {

        if (Debug.DEBUG) {

            File logFile = new File("sdcard/SqurrelLog.txt");
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.newLine();
                buf.newLine();
                buf.append(text);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static void showDialog(final Activity c, String title, String message) {

        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(c)
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.hint_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.hint_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
        Button ybutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ybutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
    }

    public static void showSingleDialog(final Activity c, String title, String message) {

        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.hint_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
        Button ybutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ybutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
    }

    public static void showSingleDialog(final Activity c, String title, String message, DialogInterface.OnClickListener listner) {

        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.hint_ok, listner).create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
        Button ybutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ybutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
    }

    public static void showYesNoDialog(final Activity c, String title, String message, DialogInterface.OnClickListener listner) {

        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.hint_yes, listner)
                .setNegativeButton(R.string.hint_no, listner).create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
        Button ybutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ybutton.setTextColor(c.getResources().getColor(R.color.colorPrimary));
    }

    public static void printHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(Constants.PACKAGE_NAME,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Debug.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

            Log.e("hashkey_error", e.toString());
        }
    }

    public static void askPermission(Activity c, String permision, int reqCode) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(c,
                permision)) {

        } else {

            ActivityCompat.requestPermissions(c,
                    new String[]{permision},
                    reqCode);
        }
    }

    public static void clearLoginCredetials(Context context) {

//        delPref(context, Constants.LOGIN_RESPONSE);


    }

    public static String getLoginDetails(Context context) {
        return getPref(context, Constants.LOGIN_RESPONSE, "");
    }


    public static String getApiKey(Context context) {
//        if (!getLoginDetails(context).equalsIgnoreCase("")) {
//
//            LoginResponse response = new Gson().fromJson(Utils.getLoginDetails(context), LoginResponse.class);
//            return response.data.apikey;
//
//        } else {
        return "";
//        }
    }

    public static String getToken(Context context) {

//        if (!getLoginDetails(context).equalsIgnoreCase("")) {

//            LoginResponse response = new Gson().fromJson(Utils.getLoginDetails(context), LoginResponse.class);
//            return response.data.token;

//        } else {
        return "123";
//        }
    }

//    public static LoginResponse.Datum getLoginData(Context context){
//
//        LoginResponse data = new Gson().fromJson(getPref(context,Constants.LOGIN_INFO,""),LoginResponse.class);
//        return data.data.get(0);
//    }

    public static String getPath() {
        String path = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else if ((new File("/mnt/emmc")).exists()) {
            path = "/mnt/emmc";
        } else {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return path;
    }

    public static Bitmap rotateBitmap(String src, Bitmap bitmap) {
        try {
            int orientation = getExifOrientation(src);

            if (orientation == 1) {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        try {
            /**
             * if your are targeting only api level >= 5 ExifInterface exif =
             * new ExifInterface(src); orientation =
             * exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
             */
            if (Build.VERSION.SDK_INT >= 5) {
                Class<?> exifClass = Class
                        .forName("android.media.ExifInterface");
                Constructor<?> exifConstructor = exifClass
                        .getConstructor(new Class[]{String.class});
                Object exifInstance = exifConstructor
                        .newInstance(new Object[]{src});
                Method getAttributeInt = exifClass.getMethod("getAttributeInt",
                        new Class[]{String.class, int.class});
                Field tagOrientationField = exifClass
                        .getField("TAG_ORIENTATION");
                String tagOrientation = (String) tagOrientationField.get(null);
                orientation = (Integer) getAttributeInt.invoke(exifInstance,
                        new Object[]{tagOrientation, 1});
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return orientation;
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION},
                null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static long parseLong(EditText editText) {

        return editText.length() > 0 ? Long.parseLong(editText.getText().toString()) : 0;
    }

    public static long parseLong(String str) {

        return str != null && str.length() > 0 ? Long.parseLong(str.toString()) : 0;
    }

    public static float parseFloat(String str) {

        return str != null && str.length() > 0 ? Float.parseFloat(str.toString()) : 0;
    }

    public static String parseString(String str) {

        return str != null && str.length() > 0 ? str.toString() : "";
    }

    public static String parseIntString(String str) {

        return str != null && str.length() > 0 ? str.toString() : "0";
    }

    public static int parseInt(EditText editText) {

        return editText.length() > 0 ? Integer.parseInt(editText.getText().toString()) : 0;
    }

    public static String printCurrency(Object object) {

        return NumberFormat.getCurrencyInstance(new Locale("en", "IN"))
                .format(object);
    }

    public static String correctAmount(String s) {

        if (s.length() > 0) {
            if (s.startsWith("0")) {
                s = s.substring(1, s.length());
                s = correctAmount(s);
            }
        }
        if (s.equalsIgnoreCase("")) {
            s = "0";
        }

        if (s.length() > 0 && !s.equalsIgnoreCase("")) {
            if (Float.parseFloat(s) <= 0) {
                s = "0";
            }
        }

        return s;
    }

    public static String removeAdditionalPoints(String s) {

        if (s.length() > 0) {
            if (s.startsWith(".")) {
                s = "0." + s.replaceAll(Pattern.quote("."), "");
                s = removeAdditionalPoints(s);
            }
        }
        return s;
    }

    public static void clearPosterRegistrationData(BaseActivity activity) {

        delPref(activity,Constants.POSTER_REG_DATA);
    }
}
