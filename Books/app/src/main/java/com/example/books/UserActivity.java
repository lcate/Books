package com.example.books;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    TextView u,e;
    ImageView slika;
    Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        u=findViewById(R.id.useroT);
        e=findViewById(R.id.emailoT);
        slika=findViewById(R.id.slikatA);
        btnlogout=findViewById(R.id.btn_logout);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Logout Alert").setMessage("Are you sure you want to log out?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                LoginManager.getInstance().logOut();
                            }
                        }).setNegativeButton("NO",null).show();
            }

        });



        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        String name = user.getDisplayName();
        String email=user.getEmail();
        e.setText(email);
        u.setText(name);

        if(user.getPhotoUrl()!=null){
            String url=user.getPhotoUrl().toString();
            url=url+"?type=large";
            Picasso.get().load(url).into(slika);
        }

    }
}