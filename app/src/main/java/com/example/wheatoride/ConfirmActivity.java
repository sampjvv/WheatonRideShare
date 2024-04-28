package com.example.wheatoride;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.wheatoride.model.ChatroomModel;
import com.example.wheatoride.utils.FirebaseUtil;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfirmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Button confirm = findViewById(R.id.Confirm);
        Button deny = findViewById(R.id.Deny);
        Bundle extras = getIntent().getExtras();

        deny.setOnClickListener((v)-> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        confirm.setOnClickListener((v)-> {
            if(extras.get("userID") != null && extras.get("description") != null){
           FirebaseUtil.confirmPost(extras.get("userID").toString(), extras.get("description").toString());
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });
}
}


