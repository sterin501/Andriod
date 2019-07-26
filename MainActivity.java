package com.example.readcontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String PhoneNumber = "9222222222";
        String ID = getID(PhoneNumber);
        String data = "content://com.android.contacts/data/" + ID;
        Log.i("POPY", " Got After search  " + data);

        try {

            Context context = this;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("content://com.android.contacts/data/" + ID));
            intent.setComponent(new ComponentName(" com.google.android.apps.tachyon","com.google.android.apps.tachyon.ContactsAudioActionActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Verify it resolves
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean isIntentSafe = activities.size() > 0;
            // Start an activity if it's safe
            if (isIntentSafe) {
                context.startActivity(intent);
                Toast.makeText(context, "Opening Duo", Toast.LENGTH_SHORT).show();
            }


            else{

               Toast.makeText(context, "Failed to open Duo", Toast.LENGTH_SHORT).show();
            }


            Log.i("POPY", "After all ");
        } catch (ActivityNotFoundException e) {

            Log.i("POPY",e.getMessage());
        }


    }





    public  String getID(String PhoneNumber){

        ContentResolver resolver = getContentResolver();
        // Cursor c = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);



        Cursor c =  resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                ContactsContract.CommonDataKinds.Phone.NUMBER+" = ?", new String[]{PhoneNumber},null);
        String id ="";

        while (c.moveToNext()){

            id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            String name =c.getString( c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Log.i("POPY",id+"     "+name);

        }

           return  id;
    }
}
