package com.xuriti.app.test;

import static android.Manifest.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** TestPlugin */
public class TestPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.RequestPermissionsResultListener {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;
  private Activity  activity;
  private   Boolean permissionGranted;
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "test");
    context = flutterPluginBinding.getApplicationContext();
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if(call.method.equals("getData")){

      permissionGranted = ContextCompat.checkSelfPermission(context, permission.READ_SMS)== PackageManager.PERMISSION_GRANTED;
    if(!permissionGranted){
      String[] permissions = new String[10];
      permissions[0] = permission.READ_SMS;
      ActivityCompat.requestPermissions(activity, permissions,123);
    }
    }else {
      result.notImplemented();
    }
  }



  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }

  @Override
  public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    if(requestCode ==123){
      boolean b = grantResults[0] == PackageManager.PERMISSION_GRANTED;
      if(b){
        getUserData();
      }
    }
    return false;
  }
  public String[] getallapps() {
    // get list of all the apps installed
    List<PackageInfo> packList = activity.getPackageManager().getInstalledPackages(0);
    String[] apps = new String[packList.size()];
    for (int i = 0; i < packList.size(); i++) {
      PackageInfo packInfo = packList.get(i);
      apps[i] = packInfo.applicationInfo.loadLabel(activity.getPackageManager()).toString();
    }
    return apps;
    // set all the apps name in list view
//        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, apps));
//        // write total count of apps available.
//        text.setText(packList.size() + " Apps are installed");
  }

  public ArrayList getAllSmsFromProvider() {
    ArrayList<String> lstSms = new ArrayList<String>();
    ArrayList<String> body = new ArrayList<String>();
    Uri smsUri = Uri.parse("content://sms/inbox");
    Cursor cursor = activity.getContentResolver().query(smsUri, null, null, null, null);
    StringBuilder builder = new StringBuilder();
    while (cursor.moveToNext()) {

      //Log.d("_id",cursor.getString(cursor.getColumnIndex()));

      @SuppressLint("Range") String label = cursor.getString(cursor.getColumnIndex("address"));
      @SuppressLint("Range") String bdy =  cursor.getString(cursor.getColumnIndex("body"));
      body.add(bdy);
      lstSms.add(label);
    }



    return lstSms;
  }



  public void getUserData(){
String apps[]= getallapps();


  }
}
