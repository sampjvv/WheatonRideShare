package com.example.wheatoride;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheatoride.adapter.ChatRecyclerAdapter;
import com.example.wheatoride.adapter.ForumRecyclerAdapter;
import com.example.wheatoride.model.ChatMessageModel;
import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ForumRecyclerAdapter adapter;
    Button addPostButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        addPostButton = findViewById(R.id.add_post_button);

        RecyclerView recyclerView1 = findViewById(R.id.forum_recycler_view);

        addPostButton.setOnClickListener( v -> {
            Intent intent = new Intent(ForumActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });

        setupForumRecyclerView();


    }


    void setupForumRecyclerView(){

        Query query = FirebaseUtil.allUserCollectionReference()
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ForumModel> options = new FirestoreRecyclerOptions.Builder<ForumModel>()
                .setQuery(query,ForumModel.class).build();

        adapter = new ForumRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!= null)
         adapter.stopListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null)
            adapter.startListening();
    }
}
