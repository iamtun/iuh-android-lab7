package fit.android.lab7.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fit.android.lab7.R;
import fit.android.lab7.activity.MainActivity;
import fit.android.lab7.database.DatabaseHandler;
import fit.android.lab7.model.Tour;

public class TourAdapter extends BaseAdapter {
    private Context context;
    private int layoutItem;
    private List<Tour> tourList;

    public TourAdapter(Context context, int layoutItem, List<Tour> tourList) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.tourList = tourList;
    }
    @Override
    public int getCount() {
        return tourList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        view = LayoutInflater.from(parent.getContext()).inflate(layoutItem, parent, false);

        TextView tvNameTour = view.findViewById(R.id.tvNameTour);
        ImageButton btnDetail = view.findViewById(R.id.btnDetail);
        ImageButton btnDelete = view.findViewById(R.id.btnDelete);
        tvNameTour.setText((index + 1) + ". " + tourList.get(index).getNameTour());

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tour tour = tourList.get(index);

                Intent intent = new Intent(context, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", tour.getId());
                bundle.putString("name", tour.getNameTour());
                bundle.putBoolean("update", true);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tour tour = tourList.get(index);

                Intent intent = new Intent(context, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", tour.getId());
                bundle.putString("name", tour.getNameTour());
                bundle.putBoolean("delete", true);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
        return view;
    }
}
