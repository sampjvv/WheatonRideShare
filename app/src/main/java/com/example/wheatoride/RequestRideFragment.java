package com.example.wheatoride;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wheatoride.adapter.ForumRecyclerAdapter;
import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestRideFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestRideFragment extends Fragment {

    RecyclerView recyclerView;
    ForumRecyclerAdapter adapter;
    Button addPostButton;
    FloatingActionButton fb;

    public RequestRideFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_request_ride, container, false);

        addPostButton = view.findViewById(R.id.add_post_button);
        recyclerView = view.findViewById(R.id.forum_recycler_view);

        addPostButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), CreateRidesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        setupForumRecyclerView();


        return view;
    }

    void setupForumRecyclerView(){

        Query query = FirebaseUtil.allRidesCollectionRefrence()
                .orderBy("postTimeStamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ForumModel> options = new FirestoreRecyclerOptions.Builder<ForumModel>()
                .setQuery(query,ForumModel.class).build();

        adapter = new ForumRecyclerAdapter(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
