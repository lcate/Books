package com.example.books;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {
    EditText name_input, author_input, rating_input, year_input, summary_input;
    Button add_button,takePhoto;
    ImageView imageView;
    byte[] img;

    static int GALLERY_CODE=1;
    static int CAMERA_CODE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        name_input=findViewById(R.id.txtname);
        author_input =findViewById(R.id.txtauthor);
        rating_input =findViewById(R.id.txtrating);
        year_input =findViewById(R.id.txtyear);
        summary_input =findViewById(R.id.txtsummary);
        imageView=findViewById(R.id.imageView);

        takePhoto=findViewById(R.id.takeAPhoto);

        img=imageViewToByte(imageView);



        //button to take pic
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_CODE);
            }
        });

        //add button
        add_button=findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDB=new MyDataBaseHelper(AddActivity.this);
                Toast.makeText(AddActivity.this, name_input.getText().toString(), Toast.LENGTH_SHORT).show();
                myDB.addBook(name_input.getText().toString(), author_input.getText().toString(),
                        Float.valueOf(rating_input.getText().toString()), year_input.getText().toString(),
                        summary_input.getText().toString(), img);
                Intent intent=new Intent(AddActivity.this,BookActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_CODE && resultCode==RESULT_OK && data!=null){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            img=imageViewToByte(imageView);
        }

    }

    public byte[] imageViewToByte(ImageView image){
        Bitmap bitmap=((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
}