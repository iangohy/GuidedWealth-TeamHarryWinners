package com.iangohy.harrywinnersclientapp;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iangohy.harrywinnersclientapp.model.Client;

/**
 * Utility class for initializing Firebase services and connecting them to the Firebase Emulator
 * Suite if necessary.
 */
public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";
    private static final String CLIENT_COLLECTION_NAME = "client";
    private static final String BANKER_COLLECTION_NAME = "banker";
    private static final String ARTICLES_COLLECTION_NAME = "articles";
    private static final String GOAL_COLLECTION_NAME = "goals";
    private static final String HOLDING_COLLECTION_NAME = "holdings";
    private static final String PREVBANKER_COLLECTION_NAME = "prevBankers";

    /** Use emulators only in debug builds **/
    private static final boolean sUseEmulators = false; // BuildConfig.DEBUG;
    private static FirebaseFirestore FIRESTORE;
    private static String uname;

    public static FirebaseFirestore getFirestore() {
        if (FIRESTORE == null) {
            FIRESTORE = FirebaseFirestore.getInstance();

            // Connect to the Cloud Firestore emulator when appropriate. The host '10.0.2.2' is a
            // special IP address to let the Android emulator connect to 'localhost'.
            if (sUseEmulators) {
                FIRESTORE.useEmulator("10.0.2.2", 8080);
            }
        }

        return FIRESTORE;
    }

    public static CollectionReference getBankerCollection() {
        return getFirestore().collection(BANKER_COLLECTION_NAME);
    }

    public static CollectionReference getUserCollection() {
        return getFirestore().collection(CLIENT_COLLECTION_NAME);
    }

    public static DocumentReference getUserDocument() {
        return getUserCollection().document(uname);
    }

    public static CollectionReference getArticleCollection() {
        return getFirestore().collection(ARTICLES_COLLECTION_NAME);
    }

    public static void setUname(String uname) {
        FirebaseUtil.uname = uname;
    }

    public static String getUname() {
        return FirebaseUtil.uname;
    }

    public static void generateUser() {
        Client user = new Client(uname);
        getUserDocument().set(user)
            .addOnSuccessListener(documentReference ->
                    Log.d(TAG, "Added new user: " + user.toString()))
            .addOnFailureListener(documentReference ->
                    Log.d(TAG, "Error adding user: " + user.toString()));
    }

    public static CollectionReference getGoalCollection() {
        return getUserDocument().collection(GOAL_COLLECTION_NAME);
    }

    public static CollectionReference getHoldingsCollection() {
        return getUserDocument().collection(HOLDING_COLLECTION_NAME);
    }

    public static CollectionReference getPrevBankerCollection() {
        return getUserDocument().collection(PREVBANKER_COLLECTION_NAME);
    }
}
