package com.example.stockprojectfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText userEmail_ET;
    TextInputEditText userPwd_ET;

    ConstraintLayout mainLayout;

    boolean isValid=false;

    String email, password;
    // used for communicating with Firebase auth and Firestore db
    public static FirebaseHelper firebaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userEmail_ET = findViewById(R.id.emailText);
        userPwd_ET = findViewById(R.id.passwordText);

        mainLayout = findViewById(R.id.mainLayout);

        if (firebaseHelper == null)
            firebaseHelper = new FirebaseHelper();
    }

    public void switchScreens(View view) {
        if(isValid==true){
            //        EditText editText = findViewById(R.id.enteredName);
//        String name = editText.getText().toString();
            Intent intent = new Intent(this, ViewStocksActivity.class);
//        intent.putExtra("NAME", name);
            startActivity(intent);
        }
    }

    public void signUpLogIn(View v){
        email = userEmail_ET.getText().toString().trim();
        password = userPwd_ET.getText().toString().trim();
        if(email.length()==0){
            snackbar("Please include an email.");
            closeKeyboard();
        }
        else if(password.length()<6){
            snackbar("Password must be at least 6 characters long.");
            closeKeyboard();
        }

        else{
            Log.i("Email Info", email);
            Log.i("Password Info", password);

//            Intent intent = new Intent(this, SelectActionActivity.class);
//            startActivity(intent);

            signUp();
        }
    }


    public void signUpButton(View v){
        email = userEmail_ET.getText().toString().trim();
        password = userPwd_ET.getText().toString().trim();
        if(email.length()==0){
            snackbar("Please include an email.");
            closeKeyboard();
        }
        else if(password.length()<6){
            snackbar("Password must be at least 6 characters long.");
            closeKeyboard();
        }

        else{
            Log.i("Email Info", email);
            Log.i("Password Info", password);

//            Intent intent = new Intent(this, SelectActionActivity.class);
//            startActivity(intent);

            signUp();
        }
    }

    public void logInButton(View v){
        email = userEmail_ET.getText().toString().trim();
        password = userPwd_ET.getText().toString().trim();
        if(email.length()==0){
            snackbar("Please include an email.");
            closeKeyboard();
        }
        else if(password.length()<6){
            snackbar("Password must be at least 6 characters long.");
            closeKeyboard();
        }

        else{
            Log.i("Email Info", email);
            Log.i("Password Info", password);

//            Intent intent = new Intent(this, SelectActionActivity.class);
//            startActivity(intent);

            logIn();
        }
    }



    private void signUp() {
        firebaseHelper.getmAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign up successful, update UI with the currently signed in user's info
                            firebaseHelper.updateUid(firebaseHelper.getmAuth().getUid());
                            firebaseHelper.addUserToFirestore(email, firebaseHelper.getmAuth().getUid());


                            Log.d("Denna", email + " created and logged in");
                            Intent intent = new Intent(SignInActivity.this, ViewStocksActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Denna", "AddOnFailureListener for signUp " + e.getMessage());
                        if (e.getMessage().contains("The email address is already in use")) {
                            snackbar("This email address is already in use");
                        }
                        else if (e.getMessage().contains("The email address is badly formatted")) {
                            snackbar("This email address is badly formatted");
                        }
                        else {
                            snackbar("Account could not be created");
                        }
                    }
                });
    }



    private void logIn() {
        firebaseHelper.getmAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign up successful, update UI with the currently signed in user's info
                            firebaseHelper.updateUid(firebaseHelper.getmAuth().getUid());
                            firebaseHelper.addListener();
                            Intent intent = new Intent(SignInActivity.this, ViewStocksActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.d("Denna", "OnFailureListener for Login " + e.getMessage());
                        if (e.getMessage().contains("INVALID_LOGIN_CREDENTIALS")) {
                            snackbar("Invalid email or password, try again");
                        }
                        else if(e.getMessage().contains("The email address is badly formatted")){
                            snackbar("Poorly formatted email, try again");
                        }
                        else {
                            snackbar("Unknown error, try again");
                        }


                    }
                });
    }




    private void snackbar(String msg){
        Snackbar.make(mainLayout,msg,Snackbar.LENGTH_SHORT)
                .show();
    }

    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI();
    }




    public void updateUI() {
        // if the user is already logged in, then they bypass this opening screen screen
        if (firebaseHelper.getmAuth().getUid() != null) {
            Log.i("Denna",  "UpdateUI - current UID: " + firebaseHelper.getmAuth().getUid());
            Intent intent = new Intent(SignInActivity.this, ViewStocksActivity.class);
            startActivity(intent);
        }
        else {
            Log.i("Denna",  "No one logged in");
        }
    }

}
