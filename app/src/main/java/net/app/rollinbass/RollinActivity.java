package net.app.rollinbass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RollinActivity extends AppCompatActivity {

    @BindView(R.id.ivFamilia)
    ShapeableImageView ivFamilia;
    @BindView(R.id.ivYoutube)
    ShapeableImageView ivYoutube;
    @BindView(R.id.buttonEventos)
    AppCompatButton buttonEventos;
    @BindView(R.id.buttonLogos)
    AppCompatButton buttonLogos;
    @BindView(R.id.buttonDjs)
    AppCompatButton buttonDjs;
    @BindView(R.id.rlRollinPage)
    RelativeLayout rlRollinPage;
    @BindView(R.id.tvSugerencias)
    MaterialTextView tvSugerencias;

    String linkFoto = "https://i.imgur.com/WLxwNBQ.gif";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rollin);
        ButterKnife.bind(this);

        cargarImagen(linkFoto);//metodo para cargar la imagen
    }

    //OnClick botones
    @OnClick({R.id.ivInstagram, R.id.ivFacebook, R.id.ivYoutube, R.id.ivSoundCloud,
            R.id.buttonLogos, R.id.buttonDjs, R.id.buttonEventos, R.id.tvSugerencias})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivInstagram:
                Uri linkIg = Uri.parse("https://www.instagram.com/rollinbass/?hl=es");
                Intent abrirIG = new Intent(Intent.ACTION_VIEW, linkIg);
                startActivity(abrirIG);
                break;
            case R.id.ivFacebook:
                Uri linkFb = Uri.parse("https://www.facebook.com/Roll-in-Bass-106217554393056");
                Intent abrirFb = new Intent(Intent.ACTION_VIEW, linkFb);
                startActivity(abrirFb);
                break;
            case R.id.ivYoutube:
                Uri linkYt = Uri.parse("https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ");
                Intent abrirYoutube = new Intent(Intent.ACTION_VIEW, linkYt);
                startActivity(abrirYoutube);
                break;
            case R.id.ivSoundCloud:
                Uri linkSc = Uri.parse("https://soundcloud.com/roll_in_bass");
                Intent abrirSc = new Intent(Intent.ACTION_VIEW, linkSc);
                startActivity(abrirSc);
                break;
            case R.id.buttonLogos:
                Intent intentLogos = new Intent(RollinActivity.this, LogosActivity.class);
                startActivity(intentLogos);
                break;
            case R.id.buttonDjs:
                Intent intentDjs = new Intent(RollinActivity.this, DjsActivity.class);
                startActivity(intentDjs);
                break;
            case R.id.buttonEventos:
                Intent intentEventos = new Intent(RollinActivity.this, EventosActivity.class);
                startActivity(intentEventos);
                Toast.makeText(this, R.string.info_video, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvSugerencias:
                Intent intentSugerencias = new Intent(RollinActivity.this, SugerenciasActivity.class);
                startActivity(intentSugerencias);
                break;
        }
    }

    //Glide para cargar imagen de url para ivFamilia
    private void cargarImagen(String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside();
        requestOptions.placeholder(R.drawable.png_logo_rollin_pegatina);//añadimos opcion para que mientras carga la imagen muestre otra por defecto.
        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .apply(requestOptions)
                    .into(ivFamilia);
        } else {
            ivFamilia.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.png_beyota_pegatina));
        }
    }
}