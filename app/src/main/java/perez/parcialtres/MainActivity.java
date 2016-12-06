package perez.parcialtres;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity{
    private EditText txtnombre;
    private EditText txtprecio;
    private Button btnregistrar;
    private Button btnbuscar;
    private EditText txtbuscar;
    private Producto producto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtprecio = (EditText) findViewById(R.id.txtprecio);
        btnregistrar = (Button) findViewById(R.id.btnregistrar);
        txtbuscar = (EditText) findViewById(R.id.txtbuscar);
        btnbuscar = (Button) findViewById(R.id.btnbuscar);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference productoRefCC = database.getReference("Producto");

                productoRefCC.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(txtbuscar.getText().toString().trim())) {
                            dataSnapshot.child(txtbuscar.getText().toString().trim());
                            Toast.makeText(getApplicationContext(), "Si existe producto ",
                                    Toast.LENGTH_SHORT).show();

                            producto = dataSnapshot.child(txtbuscar.getText().toString().trim()).getValue(Producto.class);

                            txtnombre.setText(producto.getNombre);
                            txtprecio.setText(producto.getPrecio);
                        } else {

                            Toast.makeText(getApplicationContext(), "No existe producto",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Failed to read value." + error.toException());
                    }


                });


            }
        });
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                producto = new Producto();
                insertarProducto(producto);

            }
        });

    }

    public void insertarProducto(Producto producto) {

        Producto.setNombre(txtnombre.getText().toString().trim());
        Producto.setPrecio(txtprecio.getText().toString().trim());

        Bundle bundle = new Bundle();
        bundle.putString("Nombres", Producto.getNombre());
        bundle.putString("Precios", Producto.getPrecio());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productoRef = database.getReference("pacientes").child(producto.getNombre().trim());
        productoRef.setValue(Producto);
        System.out.println("Registro de paciente exitoso");
    }
}

