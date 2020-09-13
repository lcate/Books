package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    ArrayList<byte[]> images;
    ArrayList<String> names, authors, years, summaries;
    ArrayList<Float> ratings;
    MyDataBaseHelper myDB;
    CustomAdapter customAdapter;

    Button library,store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        library=findViewById(R.id.booklibrary);
        store=findViewById(R.id.bookstore);
        recyclerView = findViewById(R.id.recycler_view);

        add_button = findViewById(R.id.add_FAbutton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LibraryMapActivity.class));
            }
        });

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StoreMapActivity.class));
            }
        });

        myDB = new MyDataBaseHelper(BookActivity.this);
        names = new ArrayList<>();
        authors = new ArrayList<>();
        ratings = new ArrayList<>();
        years = new ArrayList<>();
        summaries = new ArrayList<>();
        images = new ArrayList<>();

        storeDefaultData();
        storeDataInArrays();
        appendDataToRecyclerView(names, authors, ratings, years, summaries, images);
    }

    //stavanje na podatocite vo recyclerview
    private void appendDataToRecyclerView(ArrayList<String> names, ArrayList<String> brands, ArrayList<Float> displays, ArrayList<String> fcameras, ArrayList<String> rcameras, ArrayList<byte[]> images) {

        customAdapter = new CustomAdapter(BookActivity.this, names, brands, displays, fcameras, rcameras, images);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookActivity.this));

    }

    void storeDefaultData() {
        Resources res = getResources();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Drawable d = res.getDrawable(R.drawable.tokillmockingbird);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] slika_eden = stream.toByteArray();
        names.add("To Kill a Mockingbird");
        authors.add("Harper Lee");
        ratings.add((float) 4.35);
        years.add("1962");
        summaries.add("To Kill a Mockingbird is a novel by Harper Lee. Although it was written in 1960 it is set in the mid-1930s in the small town of Maycomb, Alabama. It is narrated by Scout Finch, a six-year-old tomboy who lives with her lawyer father Atticus and her ten-year-old brother Jem. During the novel Scout, Jem and their friend Dill try to make their reclusive neighbour Boo Radley leave his house. Boo has not been seen in Maycomb since he was a teenager.\n" +
                "\n" +
                "Many residents of Maycomb are racists and during the novel Atticus is asked to defend Tom Robinson, a black man wrongly accused of raping a white woman. Atticus takes on the case even though everyone knows he has little hope of winning. The reader sees the trial develop through the childlike eyes of Scout, as gradually both she and her brother learn some valuable life lessons from their father about tolerance, empathy and understanding.");
        images.add(slika_eden);


        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        d = res.getDrawable(R.drawable.the_lord_of);
        bitmap = ((BitmapDrawable) d).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
        byte[] slika_dva = stream1.toByteArray();
        names.add("The Lord of the Rings");
        authors.add("J.R.R. Tolkien");
        ratings.add((float) 4.4);
        years.add("1949 ");
        summaries.add("At 33, the age of adulthood among hobbits, Frodo Baggins receives a magic Ring of Invisibility from his uncle Bilbo. Frodo, a Christlike figure, learns that the ring has the power to control the entire world and, he discovers, to corrupt its owner. A fellowship of hobbits, elves, dwarfs, and men is formed to destroy the ring by casting it into the volcanic fires of the Crack of Doom, where it was forged. They are opposed on their harrowing mission by the evil Sauron and his Black Riders.\n" +
                "\n" +
                "The Lord of the Rings, together with The Hobbit, is considered by many to be the start of the genre known as high fantasy, and these works have had an enormous influence on that genre as a whole.");
        images.add(slika_dva);

        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        d = res.getDrawable(R.drawable.feature_gatsbycover);
        bitmap = ((BitmapDrawable) d).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
        byte[] slika_tri = stream2.toByteArray();
        names.add("The Great Gatsby");
        authors.add("J.R.R. Tolkien");
        ratings.add((float) 3.33);
        years.add("1925 ");
        summaries.add(" Our narrator, Nick Carraway, moves to the East Coast to work as a bond trader in Manhattan. He rents a small house in West Egg, a nouveau riche town in Long Island. In East Egg, the next town over, where old money people live, Nick reconnects with his cousin Daisy Buchanan, her husband Tom, and meets their friend Jordan Baker.\n" +
                "\n" +
                "Tom takes Nick to meet his mistress, Myrtle Wilson. Myrtle is married to George Wilson, who runs a gas station in a gross and dirty neighborhood in Queens. Tom, Nick, and Myrtle go to Manhattan, where she hosts a small party that ends with Tom punching her in the face.\n" +
                "\n" +
                "Nick meets his next-door neighbor, Jay Gatsby, a very rich man who lives in a giant mansion and throws wildly extravagant parties every weekend, and who is a mysterious person no one knows much about.\n" +
                "\n" +
                "Gatsby takes Nick to lunch and introduces him to his business partner - a gangster named Meyer Wolfshiem.\n" +
                "\n" +
                "Nick starts a relationship with Jordan. Through her, Nick finds out that Gatsby and Daisy were in love five years ago, and that Gatsby would like to see her again.\n" +
                "\n" +
                "Nick arranges for Daisy to come over to his house so that Gatsby can \"accidentally\" drop by. Daisy and Gatsby start having an affair.");
        images.add(slika_tri);

        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        d = res.getDrawable(R.drawable.bokthief);
        bitmap = ((BitmapDrawable) d).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream3);
        byte[] slika_cetiri= stream3.toByteArray();
        names.add("The Book Thief");
        authors.add("Markus Zusak");
        ratings.add((float) 3.8);
        years.add("1938 ");
        summaries.add("In 1938, young orphan Liesel (Sophie Nélisse) arrives at the home of her new foster parents, Hans (Geoffrey Rush) and Rosa (Emily Watson). When Hans, a kindly housepainter, learns that Liesel cannot read, he teaches the child the wonders of the written language. Liesel grows to love books, even rescuing one from a Nazi bonfire. Though Liesel's new family barely scrape by, their situation becomes even more precarious when they secretly shelter a Jewish boy whose father once saved Hans' life.");
        images.add(slika_cetiri);

        ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
        d = res.getDrawable(R.drawable.hobbitcover);
        bitmap = ((BitmapDrawable) d).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream4);
        byte[] slika_pet= stream4.toByteArray();
        names.add("The Hobbit");
        authors.add("J.R.R. Tolkien");
        ratings.add((float) 3.6);
        years.add("2013  ");
        summaries.add("Bilbo Baggins (Martin Freeman) lives a simple life with his fellow hobbits in the shire, until the wizard Gandalf (Ian McKellen) arrives and convinces him to join a group of dwarves on a quest to reclaim the kingdom of Erebor. The journey takes Bilbo on a path through treacherous lands swarming with orcs, goblins and other dangers, not the least of which is an encounter with Gollum (Andy Serkis) and a simple gold ring that is tied to the fate of Middle Earth in ways Bilbo cannot even fathom.");
        images.add(slika_pet);

    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
        } else {
            while (cursor.moveToNext()) {
                names.add(cursor.getString(1));
                authors.add(cursor.getString(2));
                ratings.add(cursor.getFloat(3));
                years.add(cursor.getString(4));
                summaries.add(cursor.getString(5));
                images.add(cursor.getBlob(6));
            }
        }
    }

    //action bar toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iprofile:
                //Intent intent = new Intent(BookActivity.this, MapsActivity.class);
                //startActivity(intent);
                Intent i = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //search filtero
    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<String> newnames = new ArrayList<>();
        ArrayList<String> newauthors = new ArrayList<>();
        ArrayList<Float> newratings = new ArrayList<>();
        ArrayList<String> newyears = new ArrayList<>();
        ArrayList<String> newsummaries = new ArrayList<>();
        ArrayList<byte[]> newimg = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).toLowerCase().contains(newText)) {
                newnames.add(names.get(i));
                newauthors.add(authors.get(i));
                newratings.add(ratings.get(i));
                newyears.add(years.get(i));
                newsummaries.add(summaries.get(i));
                newimg.add(images.get(i));
            }
        }
        appendDataToRecyclerView(newnames, newauthors, newratings, newyears, newsummaries, newimg);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }
//^do tuka action bar toolbar


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(BookActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
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
}