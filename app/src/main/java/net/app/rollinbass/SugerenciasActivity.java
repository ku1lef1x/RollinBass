package net.app.rollinbass;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SugerenciasActivity extends AppCompatActivity {

    @BindView(R.id.buttonEnviar)
    AppCompatButton buttonEnviar;
    @BindView(R.id.etTitulo)
    TextInputEditText etTitulo;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etSugerencia)
    EditText etSugerencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencias);
        ButterKnife.bind(this);
        actionBarIconNoTittle();
    }

    @OnClick(R.id.buttonEnviar)
    public void onClickButonEnviar() {
        DatabaseReference listaSugerenciaRef = FirebaseDatabase.getInstance().getReference("Sugerencias");//Creacion de la ref Djs en FB
        String titulo = etTitulo.getText().toString().trim();
        String mail = etEmail.getText().toString().trim();
        String mensaje = etSugerencia.getText().toString().trim();
        if (titulo.isEmpty() || mail.isEmpty() || mensaje.isEmpty()) {
            if (titulo.isEmpty()) etTitulo.setError(getString(R.string.error_campo_vacio));
            if (mensaje.isEmpty()) etSugerencia.setError(getString(R.string.error_campo_vacio));
        }
        if (mail.isEmpty()) {
            etEmail.setError(getString(R.string.error_campo_vacio));
        } else {
            Pattern pattern = Pattern.compile("[-\\w.]+@[.\\w]+\\.\\w+");
            Matcher matcher = pattern.matcher(mail);
            if (!matcher.matches()) {
                etEmail.setError(getString(R.string.error_mail));
            } else {
                listaSugerenciaRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(titulo)) {
                            Toast.makeText(getBaseContext(), getString(R.string.sugerencia_existente)
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            Sugerencia sugerencia = new Sugerencia(titulo, mail, mensaje);
                            listaSugerenciaRef.child(String.valueOf(titulo)).setValue(sugerencia);
                            Toast.makeText(getBaseContext(), getString(R.string.sugerencia_enviada), Toast.LENGTH_SHORT).show();
                            etTitulo.setText("");
                            etEmail.setText("");
                            etSugerencia.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Error");
                    }
                });

            }
        }
    }

    private void actionBarIconNoTittle() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setIcon(R.drawable.ic_logoconfondoblanco);//añadimos el ic a la actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   " + getTitle());
    }
}