package com.example.wheatoride.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheatoride.PostScreenActivity;
import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.R;
import com.example.wheatoride.model.UserModel;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
                        UserModel otherUserModel = task.getResult().toObject(UserModel.class);
                        assert otherUserModel != null;


                        AndroidUtil.setProfilePic(context, Uri.parse(otherUserModel.getProfilePicUri()),holder.profilePic);
                        holder.usernameText.setText(otherUserModel.getFullName());


                        holder.location.setText("location: " + model.getLocation());
                        holder.seats.setText("# of people: " + model.getNumOfSeats());
                        holder.createdTimeStamp.setText(model.getPostTimeStamp());


                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(context, PostScreenActivity.class);
                            AndroidUtil.passUserModelAsIntent(intent,otherUserModel);

                            Bundle bundle = new Bundle();
                            bundle.putString("name", holder.usernameText.getText().toString());
                            bundle.putCharSequence("desc", model.getDescription());
                            bundle.putString("profilepic", otherUserModel.getProfilePicUri());
                            bundle.putString("seats", model.getNumOfSeats());
                            bundle.putString("location", model.getLocation());
                            intent.putExtras(bundle);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        });

                        holder.deletePostButton.setOnClickListener(c -> {
                            FirebaseUtil.deletePost(model.getUserId(),model.getDescription());
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
        TextView createdTimeStamp;
        ImageView profilePic;
        TextView location, seats, deletePostButton, description;

        public ForumModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
            createdTimeStamp = itemView.findViewById(R.id.post_time_text);
            location = itemView.findViewById(R.id.location_text);
            seats = itemView.findViewById(R.id.post_seats_text);
            deletePostButton = itemView.findViewById(R.id.delete_post_button);

        }
    }
}