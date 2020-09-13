package com.example.books;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SpecificationsActivity extends AppCompatActivity {
    TextView name_input, author_input, rating_input, year_input, summaary_input;
    String name, author, rating, year, summary;
    ImageView image;
    byte[] img;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifications);

        image=findViewById(R.id.image);
        name_input=findViewById(R.id.name_txt1);
        author_input =findViewById(R.id.author_txt1);
        rating_input =findViewById(R.id.rating_txt1);
        year_input =findViewById(R.id.year_txt1);
        summaary_input =findViewById(R.id.summary_txt1);
        ratingBar=findViewById(R.id.ratingBar);


        getAndSetIntentData();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("author") &&
                getIntent().hasExtra("rating") && getIntent().hasExtra("year") && getIntent().hasExtra("summary")){
            //Getting data from Intent
            name=getIntent().getStringExtra("name");
            author =getIntent().getStringExtra("author");
            rating =getIntent().getStringExtra("rating");
            year =getIntent().getStringExtra("year");
            summary =getIntent().getStringExtra("summary");
            img=getIntent().getByteArrayExtra("img");

            //Setting intent data
            name_input.setText(name);
            author_input.setText(author);
            rating_input.setText(rating);
            year_input.setText(year+" year");
            summaary_input.setText(summary);
            image.setImageBitmap(ByteArrayToBitMap(img));
            ratingBar.setRating(Float.parseFloat(rating));
        }else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap ByteArrayToBitMap(byte[] bajtarej){
        Bitmap bmp= BitmapFactory.decodeByteArray(bajtarej,0,bajtarej.length);
        return bmp;
    }
}