package net.app.rollinbass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogosActivity extends AppCompatActivity {

    @BindView(R.id.ivIgRollinBass)
    ShapeableImageView ivIgRollinBass;
    @BindView(R.id.tvIgRollinBass)
    MaterialTextView tvIgRollinBass;
    @BindView(R.id.ivIgGloevolutionsoccer)
    ShapeableImageView ivIgGloevolutionsoccer;
    @BindView(R.id.tvIgGloevolutionsoccer)
    MaterialTextView tvIgGloevolutionsoccer;
    @BindView(R.id.ivIgDrty_dsgn)
    ShapeableImageView ivIgDrty_dsgn;
    @BindView(R.id.tvIgDrty_dsgn)
    MaterialTextView tvIgDrty_dsgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logos);
        ButterKnife.bind(this);


        actionBarIconTittle();
    }

    //OnClick distintos botones para llevar a las redes sociales. Se aplican tanto al pulsar en el icono como en el texto.
    @OnClick({R.id.ivIgRollinBass, R.id.tvIgRollinBass, R.id.ivIgGloevolutionsoccer, R.id.tvIgGloevolutionsoccer, R.id.ivIgDrty_dsgn, R.id.tvIgDrty_dsgn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivIgRollinBass:
            case R.id.tvIgRollinBass:
                Uri linkIgRollin = Uri.parse("https://www.instagram.com/rollinbass/?hl=es");
                Intent abrirIgRollin = new Intent(Intent.ACTION_VIEW, linkIgRollin);
                startActivity(abrirIgRollin);
                break;
            case R.id.ivIgGloevolutionsoccer:
            case R.id.tvIgGloevolutionsoccer:
                Uri linkIgGloevo = Uri.parse("https://www.instagram.com/gloevolutionsoccer/");
                Intent abrirIgGloevo = new Intent(Intent.ACTION_VIEW, linkIgGloevo);
                startActivity(abrirIgGloevo);
                break;
            case R.id.ivIgDrty_dsgn:
            case R.id.tvIgDrty_dsgn:
                Uri linkIgDrty = Uri.parse("https://www.instagram.com/drty.dsgn/");
                Intent abrirIgDrty = new Intent(Intent.ACTION_VIEW, linkIgDrty);
                startActivity(abrirIgDrty);
                break;
        }
    }

    //metodo para añadir icono al titulo de la ActionBar
    private void actionBarIconTittle() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setIcon(R.drawable.ic_logoconfondoblanco);//añadimos el ic a la actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   " + getTitle());
    }

}