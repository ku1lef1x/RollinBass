package net.app.rollinbass;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventosActivity extends AppCompatActivity {
    @BindView(R.id.vvYoutube1)
    VideoView vvYoutube1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        ButterKnife.bind(this);

        actionBarIconTittle();
        cargarMeme();


    }

    //Onclick en el VideoView
    @OnClick(R.id.vvYoutube1)
    public void clickEnBoton() {
        if (vvYoutube1.isPlaying()) {
            vvYoutube1.pause();
            Toast.makeText(this, R.string.video_pausado, Toast.LENGTH_SHORT).show();
        } else {
            vvYoutube1.start();
            Toast.makeText(this,R.string.reproducciendo, Toast.LENGTH_SHORT).show();
        }

    }

    //metodo para añadir icono al titulo
    private void actionBarIconTittle() {
        ActionBar actionBar = getSupportActionBar();//variable action bar para poner el icono en ella.
        assert actionBar != null;
        actionBar.setIcon(R.drawable.ic_logoconfondoblanco);//añadimos el ic a la actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   " + getTitle());
    }

    //cargar meme en vv
    private void cargarMeme(){
        if (!vvYoutube1.isPlaying()) {
            String path = "android.resource://" + getPackageName() + "/" + R.raw.meme;
            vvYoutube1.setSoundEffectsEnabled(false);
            vvYoutube1.setVideoURI(Uri.parse(path));
            vvYoutube1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }
    }
}




