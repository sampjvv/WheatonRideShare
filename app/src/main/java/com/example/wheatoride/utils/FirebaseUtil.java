package com.example.wheatoride.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wheatoride.model.ForumModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class FirebaseUtil {



    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn(){
        return currentUserId() != null;
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static CollectionReference allRidesCollectionRefrence() {
        return FirebaseFirestore.getInstance().collection("rides");
    }

    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }
public static void deleteChatroomModel(String chatroomID){

        CollectionReference chatroomsCollection = FirebaseFirestore.getInstance().collection("chatrooms");

        chatroomsCollection.whereEqualTo("chatroomId", chatroomID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            document.getReference().delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("DeleteChatroom", "Chatroom document with ID " + chatroomID + " deleted successfully");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("DeleteChatroom", "Error deleting chatroom document with ID " + chatroomID, e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DeleteChatroom", "Error getting chatroom documents", e);
                    }
                });
    }

    public static void deletePost(String userId, String description){

        Task<QuerySnapshot> task  = allPostsCollectionReference().whereEqualTo("userId",userId).whereEqualTo("description",description).get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete (@NonNull Task < QuerySnapshot > task) {
                        if(task.getResult().getDocuments().size() == 0) {
                            deleteRide(userId, description);
                        }else{
                            if (currentUserDetails().getId().equals(userId)) {
                                task.getResult().getDocuments().get(0).getReference().delete();
                                System.out.println("deleted");
                            } else {
                                System.out.println("NOT deleted: " + currentUserDetails().getId() + " " + userId);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        deleteRide(userId,description);
                        Log.e("FirebaseUtil", "Error getting offer post documents", e);
                    }
                });
            }
        });




    }
    public static void confirmPost(String userId, String description){
        Task<QuerySnapshot> task  = allPostsCollectionReference().whereEqualTo("userId",userId).whereEqualTo("description",description).get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                        {
                            @Override
                            public void onComplete (@NonNull Task < QuerySnapshot > task) {
                                if(task.getResult().getDocuments().size() == 0){

                                    confirmRide(userId,description);


                                }else{

                                    if(task.getResult().getDocuments().get(0).getString("isConfirmed").equals("true") && currentUserDetails().getId().equals(userId)){
                                        task.getResult().getDocuments().get(0).getReference().delete();
                                    }else{
                                        ForumModel trueForumModel = new ForumModel(task.getResult().getDocuments().get(0).getData());
                                        trueForumModel.setIsConfirmed("true");
                                        Map<String, Object> information = task.getResult().getDocuments().get(0).getData();
                                        trueForumModel.setUserId(information.get("userId").toString());
                                        task.getResult().getDocuments().get(0).getReference().set(trueForumModel);
                                    }

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                deleteRide(userId,description);
                                Log.e("FirebaseUtil", "Error getting offer post documents", e);
                            }
                        });
            }
        });


    }
    public static void confirmRide(String userId, String description){

        Task<QuerySnapshot> task  = allRidesCollectionRefrence().whereEqualTo("userId",userId).whereEqualTo("description",description).get();
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().getDocuments().get(0).getString("isConfirmed").equals("true") && currentUserDetails().getId().equals(userId)){
                    task.getResult().getDocuments().get(0).getReference().delete();
                }else{
                    ForumModel trueForumModel = new ForumModel(task.getResult().getDocuments().get(0).getData());
                    trueForumModel.setIsConfirmed("true");
                    Map<String, Object> information = task.getResult().getDocuments().get(0).getData();
                    trueForumModel.setUserId(information.get("userId").toString());
                    task.getResult().getDocuments().get(0).getReference().set(trueForumModel);
                }

                }

        });
    }

    public static void deleteRide(String userId, String description){

        Task<QuerySnapshot> task  = allRidesCollectionRefrence().whereEqualTo("userId",userId).whereEqualTo("description",description).get();
            task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (currentUserDetails().getId().equals(userId)) {
                        task.getResult().getDocuments().get(0).getReference().delete();
                        System.out.println("deleted");
                    } else {
                        System.out.println("NOT deleted: " + currentUserDetails().getId() + " " + userId);
                    }
                }
            });
    }



    public static String getChatroomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }

    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public static CollectionReference allPostsCollectionReference(){
        return FirebaseFirestore.getInstance().collection("posts");
    }

    public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(FirebaseUtil.currentUserId())){
            return allUserCollectionReference().document(userIds.get(1));
        }else{
            return allUserCollectionReference().document(userIds.get(0));
        }
    }

    public static DocumentReference getUsernameFromPost(String userId){
        return allUserCollectionReference().document(userId);
    }


    @SuppressLint("SimpleDateFormat")
    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("hh:mm").format(timestamp.toDate());
    }

    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public static StorageReference  getCurrentProfilePicStorageRef(){
        System.out.println("profilePicUri " + FirebaseStorage.getInstance().getReference().child("profilePicUri")
                .child(FirebaseUtil.currentUserId()));
        return FirebaseStorage.getInstance().getReference().child("profilePicUri")
                .child(FirebaseUtil.currentUserId());
    }

    public static StorageReference  getOtherProfilePicStorageRef(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profilePicUri")
                .child(otherUserId);
    }


}










