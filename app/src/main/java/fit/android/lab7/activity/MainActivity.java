package fit.android.lab7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import fit.android.lab7.R;
import fit.android.lab7.adapter.TourAdapter;
import fit.android.lab7.database.DatabaseHandler;
import fit.android.lab7.model.Tour;


public class MainActivity extends AppCompatActivity {
    private ListView lvwTour;
    private List<Tour> tourList;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        lvwTour = findViewById(R.id.lvwTour);
        Button btnSave = findViewById(R.id.btnSave);
        EditText tvTourName = (EditText) findViewById(R.id.editTextTourName);
        reloadData(db, tourList, lvwTour);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTour = tvTourName.getText().toString();
                int id = db.getToursCount() + 1;

                db.addTour(new Tour(id, nameTour));
                reloadData(db, tourList, lvwTour);

                tvTourName.setText("");
            }
        });

        intent = getIntent();

        if(intent != null) {
            String nameTour = intent.getStringExtra("name");
            int idTour = intent.getIntExtra("id", 0);
            Boolean deleted = intent.getBooleanExtra("delete", false);
            Boolean updated = intent.getBooleanExtra("update", false);
            tvTourName.setText(nameTour);
            //===================== DELETE ==================================
            if(deleted) {
                db.deleteTour(new Tour(idTour, nameTour));
                reloadData(db, tourList, lvwTour);
            }

            //--------------------- UPDATE ===================================
            if(updated) {
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nameTour = tvTourName.getText().toString();

                        db.updateTour(new Tour(idTour, nameTour));
                        reloadData(db, tourList, lvwTour);

                        tvTourName.setText("");
                    }
                });
            }
        }

        //Sự kiện update với sự kiện save còn trùng nhau do intent không được cập nhật sau khi update => Sử lý bằng cách tạo fragment
    }

    private void reloadData(DatabaseHandler db,List<Tour> tourList, ListView lvwTour) {
        tourList = db.getAllTours();
        TourAdapter adapter = new TourAdapter(this, R.layout.custom_item_travel, tourList);
        lvwTour.setAdapter(adapter);
    }
}