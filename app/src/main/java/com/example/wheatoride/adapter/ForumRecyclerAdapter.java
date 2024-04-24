package com.example.wheatoride.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheatoride.CreatePostActivity;
import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.ChatActivity;
import com.example.wheatoride.R;
import com.example.wheatoride.model.UserModel;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;

public class ForumRecyclerAdapter extends FirestoreRecyclerAdapter<ForumModel, ForumRecyclerAdapter.ForumModelViewHolder> {

    Context context;

    public ForumRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ForumModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ForumRecyclerAdapter.ForumModelViewHolder holder, int position, @NonNull ForumModel model) {

        FirebaseUtil.getUsernameFromPost(model.getUserId())
                .get().addOnCompleteListener( task -> {
                    if(task.isSuccessful()){
                        ForumModel fm = task.getResult().toObject(ForumModel.class);
                        UserModel otherUserModel = task.getResult().toObject(UserModel.class);
                        assert otherUserModel != null;

                        AndroidUtil.setProfilePic(context, Uri.parse(otherUserModel.getProfilePicUri()),holder.profilePic);
                        holder.usernameText.setText(otherUserModel.getFullName());

                        holder.description.setText(model.getDescription());
                        holder.availableSeats.setText(model.getSeats());
                        holder.location.setText(model.getLocation());
                        holder.createdTimeStamp.setText(DateFormat.getDateInstance().format(model.getCreatedTimestamp().toDate()));


                        holder.itemView.setOnClickListener(v -> {


                        });
                    }
                });

    }

    @NonNull
    @Override
    public ForumRecyclerAdapter.ForumModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_recycler_row,parent,false);
        return new ForumModelViewHolder(view);
    }


    class ForumModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView availableSeats;
        TextView location;
        TextView description;
        TextView createdTimeStamp;
        ImageView profilePic;

        public ForumModelViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description_text);
            availableSeats = itemView.findViewById(R.id.post_seats);
            usernameText = itemView.findViewById(R.id.user_name_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
            createdTimeStamp = itemView.findViewById(R.id.post_time_text);
            location = itemView.findViewById(R.id.location_text);

        }
    }
}
