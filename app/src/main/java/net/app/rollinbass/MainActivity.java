package net.app.rollinbass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvCreateNewAccount)
    MaterialTextView tvCreateNewAccount;
    @BindView(R.id.buttonLogin)
    AppCompatButton buttonLogin;
    @BindView(R.id.etPass)
    TextInputEditText etPass;
    @BindView(R.id.etUser)
    TextInputEditText etUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        actionBarIconNoTittle();
    }

    //Onclick en textView con intent para pasar del textview Registro a la activity Registro
    @OnClick(R.id.tvCreateNewAccount)
    public void clickTextViewRegistro() {
        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(intent);
        Toast.makeText(this, R.string.pagina_registro, Toast.LENGTH_SHORT).show();
    }

    //Onclick en el boton login con intent para pasar del boton login a la rollinActivity
    //se comprueba que los campos no esten vacios, que el usuario exista en FB y que la contraseña sea correcta.
    @OnClick(R.id.buttonLogin)
    public void clickLoginButton() {
        String user = etUser.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (user.isEmpty() || pass.isEmpty()){
                    if(user.isEmpty()) etUser.setError(getString(R.string.error_campo_vacio));
                    if(pass.isEmpty())etPass.setError(getString(R.string.error_campo_vacio));
                }else{
                    if (snapshot.hasChild(user)) {
                        Usuario usuario = snapshot.child(user).getValue(Usuario.class);
                        if (usuario.getPass().equals(pass)) {
                            Intent intent = new Intent(MainActivity.this, RollinActivity.class);
                            startActivity(intent);
                            Toast.makeText(getBaseContext(), R.string.bienvenida, Toast.LENGTH_SHORT).show();
                            //para que mantenga los datos recordados:
                            etUser.setText(user);
                            etPass.setText(pass);
                        }else {
                            etPass.setText("");
                            etPass.setError(getString(R.string.error_pass));
                            Toast.makeText(getBaseContext(),R.string.error_pass,Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        etUser.setError(getString(R.string.error_usuario_no_existente));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(getString(R.string.error));
            }
        });
    }

    //metodo para eliminar titulo de la actionBar y añadir icono.
    private void actionBarIconNoTittle() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setIcon(R.drawable.ic_logoconfondoblanco);//añadimos el ic a la actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   " + getTitle());
    }
}