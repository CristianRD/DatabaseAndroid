package com.example.lds.databaseandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText et1, et2, et3, et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.activity_main, menu);
        return true;
    }

    public void alta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administrar", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id = et1.getText().toString();
        String concepto = et2.getText().toString();
        String descripcion = et3.getText().toString();
        String precio = et4.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("concepto", concepto);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        bd.insert("productos", null, registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        Toast.makeText(this, "Se cargaron los datos del producto",
                Toast.LENGTH_SHORT).show();
    }

    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administrar", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id = et1.getText().toString();
        Cursor fila = bd.rawQuery(
                "select concepto,descripcion,precio from productos where id=" + id, null);
        if (fila.moveToFirst()) {
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            et4.setText(fila.getString(2));
        } else
            Toast.makeText(this, "No existe un producto con dicho Id",
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administrar", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id = et1.getText().toString();
        int cant = bd.delete("productos", "id=" + id, null);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borro el producto con dicho Id",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe producto con dicho Id",
                    Toast.LENGTH_SHORT).show();
    }

    public void modificacion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administrar", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id = et1.getText().toString();
        String concepto = et2.getText().toString();
        String descripcion = et3.getText().toString();
        String precio = et4.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("concepto", concepto);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        int cant = bd.update("productos", registro, "id=" + id, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe producto con dicho Id",
                    Toast.LENGTH_SHORT).show();
    }
}


