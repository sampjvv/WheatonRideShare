package com.example.wheatoride;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePostActivity extends AppCompatActivity {

    EditText text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_create_post);

        text = findViewById(R.id.post_info_text);
    }
}
