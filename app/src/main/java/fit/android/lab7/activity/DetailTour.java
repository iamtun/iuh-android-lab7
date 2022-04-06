package fit.android.lab7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fit.android.lab7.R;
import fit.android.lab7.adapter.CustomerAdapter;
import fit.android.lab7.database.DatabaseHandler;
import fit.android.lab7.model.Customer;

public class DetailTour extends AppCompatActivity {
    private List<Customer> customerList;
    private ListView lvwCustomer;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnCancel;
    private EditText editTextNameCustomer;
    private CustomerAdapter adapter;
    int id_tour = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);

        DatabaseHandler db = new DatabaseHandler(this);

        lvwCustomer = findViewById(R.id.lvwListPerson);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnCancel = findViewById(R.id.btnCancel);
        editTextNameCustomer = findViewById(R.id.editTextPerson);

        //Get intent two listTour and listCustomer
        Intent intent = getIntent();
        id_tour = intent.getIntExtra("id_tour", 0);
        Boolean deleted = intent.getBooleanExtra("delete", false);

        Toast.makeText(this, "id_tour:" + id_tour + " deleted: "+ deleted.toString(), Toast.LENGTH_SHORT).show();
        reloadData(db, id_tour);

        if(deleted) {
            int id_customer = intent.getIntExtra("id_customer", 0);
            String name_customer = intent.getStringExtra("name_customer");

            editTextNameCustomer.setText(name_customer);

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailTour.this, "delete click", Toast.LENGTH_SHORT).show();
                    db.deleteCustomer(id_customer);
                    reloadData(db, id_tour);
                    editTextNameCustomer.setText("");
                }
            });
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_customer = 0;
                if(db.getCustomerCount() == 0)
                    id_customer = 1;
                else
                    id_customer = db.getIdLastCustomer() + 1;

                String name_customer = editTextNameCustomer.getText().toString();
                Customer customer = new Customer(id_customer, name_customer, id_tour);
                db.addCustomer(customer);

                reloadData(db, id_tour);
                editTextNameCustomer.setText("");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(DetailTour.this, MainActivity.class);
                startActivity(_intent);
            }
        });
    }

    private void reloadData(DatabaseHandler db, int id_tour) {
        customerList = db.getAllCustomerInTour(id_tour);
        adapter = new CustomerAdapter(this, customerList, R.layout.custom_item_person);
        lvwCustomer.setAdapter(adapter);
    }
}