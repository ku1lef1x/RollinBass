package net.app.rollinbass;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoDjsActivity extends AppCompatActivity {

    @BindView(R.id.ivInfoDjs)
    AppCompatImageView ivInfoDjs;
    @BindView(R.id.tvGeneroMusical)
    MaterialTextView tvGeneroMusical;
    @BindView(R.id.tvDescripcion)
    MaterialTextView tvDescripcion;
    @BindView(R.id.ctlInfoDjs)
    CollapsingToolbarLayout ctlInfoDjs;
    @BindView(R.id.buttomPlay)
    FloatingActionButton buttomPlay;
    @BindView(R.id.buttomStop)
    FloatingActionButton buttomStop;
    @BindView(R.id.buttomPause)
    FloatingActionButton butttomPause;

    private Dj mDj;//variable de tipo Dj para recibir datos
    private Links mLinks;//variable de tipo Links para recibir los datos
    private MediaPlayer reproductor;//variable para el reproductor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_djs);
        ButterKnife.bind(this);

        setColorBotons();//configurar color botones
        configurarDatosDj();//configurar datos del dj a mostrar
        createReproductor();//creacion del reproductor
    }

    //metodo onDestroy para liberar el reproductor cuando no se requiera su uso y asi no usar recursos innecesarios
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reproductor != null) reproductor.release();
    }

    //Onclick distintos botones, primero accedemos a FB para obtener los datos del DJ seleccionado y poder poner su url correcta en cada red social, sesion...
    @OnClick({R.id.ivInstagram, R.id.ivYoutube, R.id.ivSoundCloud, R.id.buttomPlay,
            R.id.buttomStop, R.id.buttomPause})
    public void onViewClicked(View view) {
        int id = recibirIntent();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Links");
        ref = ref.child(String.valueOf(id));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLinks = snapshot.getValue(Links.class);
                assert mLinks != null;
                String urlInsta = mLinks.getUrlInstagram();
                String urlSounCloud = mLinks.getUrlSoundCloud();
                String urlYoutube = mLinks.getUrlYoutube();
                switch (view.getId()) {
                    case R.id.ivInstagram:
                        Uri linkIg = Uri.parse(urlInsta);
                        Intent abrirIG = new Intent(Intent.ACTION_VIEW, linkIg);
                        startActivity(abrirIG);
                        break;
                    case R.id.ivYoutube:
                        Uri linkYoutube = Uri.parse(urlYoutube);
                        Intent abrirYoutube = new Intent(Intent.ACTION_VIEW, linkYoutube);
                        startActivity(abrirYoutube);
                        break;
                    case R.id.ivSoundCloud:
                        Uri linkSoundCloud = Uri.parse(urlSounCloud);
                        Intent abrirSoundCloud = new Intent(Intent.ACTION_VIEW, linkSoundCloud);
                        startActivity(abrirSoundCloud);
                        break;
                    case R.id.buttomPlay:
                        if(reproductor==null) {
                            reproductor = new MediaPlayer();
                            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            reproductor.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                            setConfigReproductor2();
                        }
                        else {
                            if (reproductor.isPlaying()) {
                                Toast.makeText(getBaseContext(), getString(R.string.sesion_activa),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                reproductor.start();
                                Toast.makeText(getBaseContext(), getString(R.string.reproducciendo_sesion),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case R.id.buttomStop:
                        if(reproductor.isPlaying()) {
                            reproductor.stop();
                            reproductor.release();
                            reproductor=null;
                            Toast.makeText(getBaseContext(),getString(R.string.sesion_detenida), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getBaseContext(), getString(R.string.no_sesion),
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.buttomPause:
                        if(reproductor.isPlaying()){
                            reproductor.pause();
                            Toast.makeText(getBaseContext(),getString(R.string.sesion_pausada), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getBaseContext(), getString(R.string.no_sesion),
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //metodo para recibir intent con el id del dj seleccionado en la actividad anterior y asi saber a qué dj acceder en FB
    private int recibirIntent() {
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        return extras.getInt("id");
    }

    //metodo para crear el reproductor, se desactiva el boton play hasta que el reproductor este preparado y listo para su uso.
    private void createReproductor() {
        reproductor = new MediaPlayer();
        reproductor.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setConfigReproductor();
        buttomPlay.setEnabled(false);
    }


    //con la informacion del intent configuramos los distintos datos del dj. Con el intent accedemos a la info de ese dj en FB y vamos cogiendo sus distintos datos
    private void configurarDatosDj() {
        int id = recibirIntent();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Djs");
        ref = ref.child(String.valueOf(id));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDj = snapshot.getValue(Dj.class);
                assert mDj != null;
                String generoMusical = mDj.getGeneroMusical();
                String descripcion = mDj.getDescripcion();
                String tittle = "   " + mDj.getAlias();
                String urlFoto = mDj.getUrlFoto();

                //ponemos info en los textView
                tvGeneroMusical.setText(generoMusical);
                tvDescripcion.setText(descripcion);

                //cargamos imagen del DJ
                if (urlFoto != null) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop();
                    Glide.with(getBaseContext())
                            .load(urlFoto)
                            .apply(requestOptions)
                            .into(ivInfoDjs);
                } else {
                    ivInfoDjs.setImageDrawable(ContextCompat.getDrawable(getBaseContext(),
                            R.drawable.ic_logoconfondoblanco));
                }

                //para poner en el titulo de la actionBar el nombre del dj, la info del metodo setIconTittle la ponemos aqui en este caso
                ActionBar actionBar = getSupportActionBar();
                assert actionBar != null;
                actionBar.setIcon(R.drawable.ic_logoconfondoblanco);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setTitle(tittle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("error");
            }
        });
    }

    //para no usar otra api en el layout establecemos aqui el color de los botones, distinto al color acento del style de la app
    private void setColorBotons() {
        buttomPlay.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.color_verdeBoton)));
        butttomPause.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.color_verdeBoton)));
        buttomStop.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.color_verdeBoton)));
    }



    //REPRODUCTOR
    //configuramos el reproductor
    private void setConfigReproductor() {
        int id = recibirIntent();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Links");
        ref = ref.child(String.valueOf(id));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLinks = snapshot.getValue(Links.class);
                assert mLinks != null;
                String urlCancion = mLinks.getUrlCancion();
                try {
                    reproductor.setDataSource(urlCancion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reproductor.prepareAsync();
                Toast.makeText(getBaseContext(),getString(R.string.preparando_sesion),
                        Toast.LENGTH_SHORT).show();
                reproductor.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Toast.makeText(getBaseContext(),getString(R.string.sesion_disponible),
                                Toast.LENGTH_SHORT).show();
                        buttomPlay.setEnabled(true);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //nueva configuracion para cuando se pulse stop y se libere mediaPlayer, con nuevos mensajes y autoreproduccion una vez esta preparado sin necesidar de pulsar play de nuevo
    private void setConfigReproductor2() {
        int id = recibirIntent();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Links");
        ref = ref.child(String.valueOf(id));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLinks = snapshot.getValue(Links.class);
                assert mLinks != null;
                String urlCancion = mLinks.getUrlCancion();
                try {
                    reproductor.setDataSource(urlCancion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reproductor.prepareAsync();
                Toast.makeText(getBaseContext(),getString(R.string.preparando_sesion2),
                        Toast.LENGTH_SHORT).show();
                reproductor.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Toast.makeText(getBaseContext(),getString(R.string.sesion_disponible2),
                                Toast.LENGTH_SHORT).show();
                        mp.start();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}