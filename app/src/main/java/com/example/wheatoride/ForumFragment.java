package com.example.wheatoride;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheatoride.adapter.ForumRecyclerAdapter;
import com.example.wheatoride.adapter.RecentChatRecyclerAdapter;
import com.example.wheatoride.model.ChatroomModel;
import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class ForumFragment extends Fragment {

    RecyclerView recyclerView;
    ForumRecyclerAdapter adapter;


    public ForumFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forum, container, false);

        return view;
    }
/**
    void setupRecyclerView(){

        Query query = FirebaseUtil.allUserCollectionReference()
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ForumModel> options = new FirestoreRecyclerOptions.Builder<ForumModel>()
                .setQuery(query,ForumModel.class).build();

        adapter = new ForumRecyclerAdapter(options,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
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
**/
    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.notifyDataSetChanged();
    }
}