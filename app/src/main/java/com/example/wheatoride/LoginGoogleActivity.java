package com.example.wheatoride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wheatoride.model.UserModel;
import com.example.wheatoride.utils.FirebaseUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginGoogleActivity extends AppCompatActivity {

    Button googleBtn;
    ProgressBar progressBar;

    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;

    UserModel userModel;

    // Declare an instance of ActivityResultLauncher
    ActivityResultLauncher<Intent> signInResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google);

        googleBtn = findViewById(R.id.google_auth_btn);
        progressBar = findViewById(R.id.login_progress_bar);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        progressBar.setVisibility(View.GONE);

        googleBtn.setOnClickListener(view -> {
            System.out.println("Clicked");
            googleSignIn();
            progressBar.setVisibility(View.VISIBLE);
        });

        // Initialize the ActivityResultLauncher instance
        signInResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            progressBar.setVisibility(View.GONE);
                            ExecutorService executor = Executors.newSingleThreadExecutor();
                            executor.execute(() -> firebaseAuth(account.getIdToken()));
                        } catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void googleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        signInResultLauncher.launch(intent);
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.print("Success");
                        FirebaseUser user = auth.getCurrentUser();

                        HashMap<String, Object> map = new HashMap<>();
                        assert user != null;

                        map.put("id", user.getUid());

                        userModel = new UserModel(user.getEmail(), user.getDisplayName(),
                                Objects.requireNonNull(Objects.requireNonNull(user.getPhotoUrl()).toString()), Timestamp.now(),
                                FirebaseUtil.currentUserId());

                        database.getReference().child("users").child(user.getUid()).setValue(map);
                        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(LoginGoogleActivity.this, "login successful", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(LoginGoogleActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(Throwable::printStackTrace);

                    } else {
                        Toast.makeText(LoginGoogleActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }
}
