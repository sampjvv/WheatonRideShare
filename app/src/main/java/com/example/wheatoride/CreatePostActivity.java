package com.example.wheatoride;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wheatoride.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.example.wheatoride.model.ForumModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class CreatePostActivity extends AppCompatActivity {

    EditText text;
    ImageButton backButton;
    Button createPostButton;
    ForumModel forumModel;
    FirebaseAuth mAuth;
    DocumentReference userRef;
    CollectionReference postRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "CreatePostActivity";
    String downloadURL, currentUserID, saveCurrentTime, saveCurrentDate,
    postText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_create_post);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        postRef = db.collection("posts");

        userRef = FirebaseUtil.currentUserDetails();

        text = findViewById(R.id.post_text);
        backButton  = findViewById(R.id.back_btn);
        createPostButton = findViewById(R.id.save_new_post);

        postText = text.getText().toString();

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });


        createPostButton.setOnClickListener(v-> {
            forumModel = new ForumModel(text.toString());
            postText = text.getText().toString();
            savingPostInformationToDatabase();

        });

    }
    void savingPostInformationToDatabase(){
        Map<String,String> postMap = new HashMap<>();
        postMap.put("uid", currentUserID);
        postMap.put("date", saveCurrentDate);
        postMap.put("description", postText);
        postRef.add(postMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID" + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document");
            }
        });

    }






}
