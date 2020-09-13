package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> names, authors, years, summaries;
    ArrayList<Float> ratings;
    ArrayList<byte[]> images;

    CustomAdapter(Context context,ArrayList<String> names, ArrayList<String> authors,ArrayList<Float> ratings,
            ArrayList<String> years,ArrayList<String> summaries, ArrayList<byte[]> images ){
        this.context=context;
        this.names=names;
        this.authors =authors;
        this.ratings =ratings;
        this.years =years;
        this.summaries =summaries;
        this.images=images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.names_txt.setText(String.valueOf(names.get(position)));
        holder.imgView.setImageBitmap(ByteArrayToBitMap(images.get(position)));
        holder.ratings_txt.setText(String.valueOf(ratings.get(position)));
        holder.authors_txt.setText("By "+String.valueOf(authors.get(position)));
        holder.ratingBar.setRating(ratings.get(position));
        holder.years_txt.setText(years.get(position)+" year");

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, SpecificationsActivity.class);
                i.putExtra("name",String.valueOf(names.get(position)));
                i.putExtra("author",String.valueOf(authors.get(position)));
                i.putExtra("rating",String.valueOf(ratings.get(position)));
                i.putExtra("year",String.valueOf(years.get(position)));
                i.putExtra("summary",String.valueOf(summaries.get(position)));
                i.putExtra("img",images.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView names_txt,authors_txt,ratings_txt,years_txt;
        ImageView imgView;
        RatingBar ratingBar;


        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            names_txt=itemView.findViewById(R.id.name_txt1);
            imgView=itemView.findViewById(R.id.image);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            authors_txt=itemView.findViewById(R.id.author_txt1);
            ratings_txt=itemView.findViewById(R.id.rating_txt1);
            years_txt=itemView.findViewById(R.id.year_txt1);

            mainLayout=itemView.findViewById(R.id.mainLayout);

        }
    }

    public Bitmap ByteArrayToBitMap(byte[] bajtarej){
        Bitmap bmp= BitmapFactory.decodeByteArray(bajtarej,0,bajtarej.length);
        return bmp;
    }
}
