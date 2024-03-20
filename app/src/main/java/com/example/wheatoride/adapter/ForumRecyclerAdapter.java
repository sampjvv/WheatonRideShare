package com.example.wheatoride.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.ChatActivity;
import com.example.wheatoride.R;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ForumRecyclerAdapter extends FirestoreRecyclerAdapter<ForumModel, ForumRecyclerAdapter.ForumModelViewHolder> {

    Context context;

    public ForumRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ForumModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ForumRecyclerAdapter.ForumModelViewHolder holder, int position, @NonNull ForumModel model) {
        holder.usernameText.setText(model.getUsername());

        FirebaseUtil.getOtherProfilePicStorageRef(model.getUserId()).getDownloadUrl()
                .addOnCompleteListener(t -> {
                    if(t.isSuccessful()){
                        Uri uri  = t.getResult();
                        AndroidUtil.setProfilePic(context,uri,holder.profilePic);
                    }
                });

        holder.itemView.setOnClickListener(v -> {

        });

    }

    @NonNull
    @Override
    public ForumRecyclerAdapter.ForumModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forum_post_row,parent,false);
        return new ForumModelViewHolder(view);
    }


    class ForumModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView text;
        ImageView profilePic;

        public ForumModelViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.info_text);
            usernameText = itemView.findViewById(R.id.user_name_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}