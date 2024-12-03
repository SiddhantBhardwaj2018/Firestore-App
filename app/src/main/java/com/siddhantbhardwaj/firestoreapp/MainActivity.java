package com.siddhantbhardwaj.firestoreapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private Button saveBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button readBtn;
    private TextView textView;
    private EditText nameET;
    private EditText emailET;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference friendsRef = db.collection("Users").document("GlXc2AclQSjqZHwa6cYZ");

    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        textView = findViewById(R.id.text);
        saveBtn = findViewById(R.id.SaveBTN);
        deleteBtn = findViewById(R.id.deleteBTN);
        updateBtn = findViewById(R.id.updateBTN);
        readBtn = findViewById(R.id.readBTN);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToNewDocument();
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllDocumentsInCollection();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDocuments();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
            }
        });

    }

    private void deleteAll() {
        friendsRef.delete();
    }

    private void saveDataToNewDocument(){
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        Friend friend = new Friend(name,email);
        collectionReference.add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String document = documentReference.getId();

            }
        });

    }

    private void getAllDocumentsInCollection(){
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String data = "";
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Friend friend = snapshot.toObject(Friend.class);
                    data += "Name: " + friend.name + " Email: " + friend.email + "\n";
                }
                textView.setText(data);
            }
        });
    }

    public void updateDocuments(){
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        friendsRef.update("name",name);
        friendsRef.update("email",email);
    }



}