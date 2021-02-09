package net.app.rollinbass;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistroActivity extends AppCompatActivity implements DatePickerDialog
        .OnDateSetListener {

    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.etUser)
    TextInputEditText etUser;
    @BindView(R.id.etPass)
    TextInputEditText etPass;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etFechaNacimiento)
    TextInputEditText etFechaNacimiento;
    @BindView(R.id.buttonRegister)
    AppCompatButton buttonRegister;

    private Calendar calendar;//variable tipo calendar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        actionBarIconTittle();
        configCalendar();//llamamos metodo para obtener la configuracion del calendario
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    /*onCLick en registerButton. Se comprueba que los campos no esten vacios, que el mail siga el patron indicado y que el usuario no exista en FB*/
    @OnClick(R.id.buttonRegister)
    public void clickRegisterButton() {
        DatabaseReference listaUsuariosRef = FirebaseDatabase.getInstance().getReference("Usuarios");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String user = etUser.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String nombreCompleto = etNombre.getText().toString().trim();
        String mail = etEmail.getText().toString().trim();
        long fechaNac = calendar.getTimeInMillis();
        Usuario usuario = new Usuario(user, pass, nombreCompleto, mail, fechaNac);

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (user.isEmpty() || pass.isEmpty() || nombreCompleto.isEmpty()
                        || mail.isEmpty()) {
                    if (user.isEmpty()) etUser.setError(getString(R.string.error_campo_vacio));
                    if (pass.isEmpty()) etPass.setError(getString(R.string.error_campo_vacio));
                    if (nombreCompleto.isEmpty())
                        etNombre.setError(getString(R.string.error_campo_vacio));
                    if (mail.isEmpty()) etEmail.setError(getString(R.string.error_campo_vacio));
                } else {
                    Pattern pattern = Pattern.compile("[-\\w.]+@[.\\w]+\\.\\w+");
                    Matcher matcher = pattern.matcher(mail);
                    if(matcher.matches()){
                        if (snapshot.child("Usuarios").hasChild(usuario.getUser())) {
                            etUser.setError(getString(R.string.error_usuario_existente));
                        } else {
                            listaUsuariosRef.child(usuario.getUser()).setValue(usuario);
                            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                            startActivity(intent);
                            //toast
                            Toast.makeText(getBaseContext(), R.string.registro_exito, Toast.LENGTH_LONG).show();
                        }
                    }else etEmail.setError(getString(R.string.error_mail));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(), getString(R.string.error),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //metodo para añadir icono al titulo de la ActionBar
    private void actionBarIconTittle() {
        ActionBar actionBar = getSupportActionBar();//variable action bar para poner el icono en ella.
        assert actionBar != null;
        actionBar.setIcon(R.drawable.ic_logoconfondoblanco); //añadimos el ic a la actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   " + getTitle());
    }

    /*
     *** CALENDARIO ***
     */
    //onClick en etFechaNacimiento
    @OnClick(R.id.etFechaNacimiento)
    public void clickEtFechaNacimiento() {
        long date = 0L;
        SelectorFecha selectorFecha = new SelectorFecha();
        selectorFecha.setListener((DatePickerDialog.OnDateSetListener) RegistroActivity.this);
        Bundle args = new Bundle();
        args.putLong(SelectorFecha.FECHA, date);
        selectorFecha.show(getSupportFragmentManager(), SelectorFecha.FECHA_SELECCIONADA);
    }

    //la interfaz DatePickerDialog requiere sobreescribir el metodo onDateSet();
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        etFechaNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                .format(calendar.getTimeInMillis()));
    }

    //metodo configCalendar para configurar el calendario con el patron deseado.
    private void configCalendar() {
        calendar = Calendar.getInstance(Locale.ROOT); //damos una localizacion estandar
        etFechaNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(
                System.currentTimeMillis()));
    }
    /*
     *  ***FIN CALENDARIO***
     * */
}