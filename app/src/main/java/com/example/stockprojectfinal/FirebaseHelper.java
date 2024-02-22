package com.example.stockprojectfinal;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of this class is to hold ALL the code to communicate with Firebase.  This class
 * will connect with Firebase auth and Firebase firestore.  Each class that needs to verify
 * authentication OR access data from the database will reference a variable of this class and
 * call a method of this class to handle the task.
 *
 * Essentially this class is like a "gopher" that will go and do whatever the other classes
 * want or need it to do.  This allows us to keep all our other classes clean of the firebase
 * code and also avoid having to update firebase code in many places.
 *
 * This is MUCH more efficient and less error prone.
 *
 * auth docs: https://firebase.google.com/docs/auth/android/password-auth#java_1
 */

public class FirebaseHelper {
    // do I want to maintain uid now that I have mUser?
    private static String uid = null;      // var will be updated for currently signed in user
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;
    private ArrayList<Stock> myStocks;
    // will refer to all Memory objects for authorized user


    public FirebaseHelper() {
        myStocks = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance(); // connects to auth
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            uid = mUser.getUid();
            Log.d("Denna", "user is still logged in when entering app");
            addListener();
        }
        else {
            uid = null;
            Log.d("Denna", "No one is logged in when entering app");
        }

//        db = FirebaseFirestore.getInstance();
//        myStocks = new ArrayList<>();
//        // instantiate arraylist for app use

    }

    // allows other files to access the mAuth variable for logged in user
    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void updateUid(String uid) {
        this.uid = uid;
    }

    // if user logs out this will call the signOut method for auth AND reset the local var for uid
    public void logOutUser() {
        mAuth.signOut();
        this.uid = null;
    }
    public String getUid() {
        return uid;
    }



    public void addListener() {
        Log.d("Denna", "Inside the listener");


        db.collection("users").document(mAuth.getUid()).collection("myStockList")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Denna", "Listen failed", error);
                            return;
                        }


                        myStocks = new ArrayList<>();
                        Log.d("Denna", "Inside onEvent with " + value.size() + " queries");
                        for (QueryDocumentSnapshot doc: value) {
                            Log.d("Denna", doc.toString());
                            if (doc.get("ticker") != null) {
                                myStocks.add(doc.toObject(Stock.class));
                                Log.d("Denna", "myStocks length " + myStocks.size());
                            }
                        }
                    }
                });
    }





    public void addUserToFirestore(String email, String newUID) {
        // Create a new user with their name
        // using a HashMap because we are NOT uploading an object of a class
        // This is an alternative to adding an object, you 'put' key value pairs
        // in the object and then add the object to firestore

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        // Add a new document with a docID = to the authenticated user's UID
        db.collection("users").document(newUID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Denna", email + "'s user account added");
                        addListener();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Denna", "Error adding user account", e);
                    }
                });
    }

    public ArrayList<Stock> getStockArrayList() {
        return myStocks;
    }

    public void addData(Stock m) {
        db.collection("users").document(uid).collection("myStockList")
                .add(m)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // This will set the docID key for the Memory that was just added.
                        db.collection("users").document(uid).collection("myStockList").
                                document(documentReference.getId()).update("docId", documentReference.getId());
                                m.setDocId(documentReference.getId());
                        Log.i("Denna", "just added " + m.getTicker());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Denna", "Error adding document", e);
                    }
                });
    }



    public void deleteData(Stock m, Context c) {
        // delete item w from database
        String docId = m.getDocId();
        Log.d("testing", docId);
        db.collection("users").document(uid).collection("myStockList")
                .document(docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("Denna", m.getTicker() + " successfully deleted");
                        Intent intent = new Intent(c, ViewStocksActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        c.startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Denna", "Error deleting document", e);
                    }
                });
    }


}

