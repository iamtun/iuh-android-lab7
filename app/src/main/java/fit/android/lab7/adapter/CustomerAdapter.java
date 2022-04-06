package fit.android.lab7.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fit.android.lab7.R;
import fit.android.lab7.activity.DetailTour;
import fit.android.lab7.model.Customer;

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<Customer> customerList;
    private int layoutItem;

    public CustomerAdapter(Context context, List<Customer> customerList, int layoutItem) {
        this.context = context;
        this.customerList = customerList;
        this.layoutItem = layoutItem;
    }

    @Override
    public int getCount() {
        return customerList.size();
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
        view = LayoutInflater.from(parent.getContext()).
                inflate(layoutItem, parent, false);

        TextView tvCustomer = view.findViewById(R.id.tvCustomer);

        tvCustomer.setText(customerList.get(index).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTour.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id_customer", customerList.get(index).getId());
                bundle.putString("name_customer",customerList.get(index).getName());
                bundle.putInt("id_tour", customerList.get(index).getId_tour());
                bundle.putBoolean("delete", true);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
