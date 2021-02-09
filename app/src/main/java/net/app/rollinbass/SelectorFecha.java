package net.app.rollinbass;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class SelectorFecha extends DialogFragment {

    public static final String FECHA = "fecha";//constantes fecha y fechaSeleccionada
    public static final String FECHA_SELECCIONADA = "fechaSeleccionada";

    private DatePickerDialog.OnDateSetListener listener;//variable que nos ayuda a seleccionar una fecha

    //sobreescribimos el metodo onCreateDialog();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance(Locale.ROOT);//variable tipo calendario, cogemos localizacion de la raiz
        int anyo = calendario.get(Calendar.YEAR);//variables para año, mes y dia
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        Bundle args = this.getArguments();//Bundle para los argumentos necesarios
        if(args!=null){
            long fecha = args.getLong(FECHA); //añadimos en la variable fecha la fecha obtenida en el args
            calendario.setTimeInMillis(fecha);
        }
        return new DatePickerDialog(getActivity(),listener,anyo,mes,dia);
    }

    //setter para el listener
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
}
