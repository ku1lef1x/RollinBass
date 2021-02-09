package net.app.rollinbass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DjsActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.clDjsActivity)
    CoordinatorLayout clDjsActivity;

    private AdaptadorRecyclerView adaptadorRecyclerView;//variable de tipo adaptador


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_djs);
        ButterKnife.bind(this);

        actionBarIconTittle();
        configs();
        //creacionDjs(); //solo llamar en la creacion de la app o para ejecutar algun cambio en FB
        //creacionLinks(); //solo llamar en la creacion de la app o para ejecutar algun cambio en FB
        configRV();//llamar una vez configurados los djs en FB sino arroja error
    }

    //metodos de la interfaz
    @Override
    public void onItemClick(Dj dj) {
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(60);//numero de ms que queremos que vibre.
        }
        int id = dj.getId();//enviamos en el intent el id del dj para que la siguiente Activity sepa que dj mostrar.
        Intent intent = new Intent(DjsActivity.this, InfoDjsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(Dj dj) { }

    //metodo para añadir icono al titulo de la ActionBar
    private void actionBarIconTittle() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setIcon(R.drawable.ic_logoconfondoblanco);//añadimos el ic a la actionbar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   " + getTitle());
    }

    //configuracion Adaptar y RecyclerView
    public void configs() {
        adaptadorRecyclerView = new AdaptadorRecyclerView(new ArrayList<>(), this);//configuramos el adaptador; necesitamos implementar interfaz OnItemClickListener y sobreescribir sus metodos
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//configuramos la recyclerView
        recyclerView.setAdapter(adaptadorRecyclerView);
    }


    //Creacion djs; solo se ejecuta una vez, para la creación de la tabla Djs en FB
    private void creacionDjs(){
        int id = 0;//id en el bucle for
        HashMap<String,Dj> listaDjs= new  HashMap<>();//hashMap
        DatabaseReference listaDjsRef = FirebaseDatabase.getInstance().getReference("Djs");//Creacion de la ref Djs en FB
        String descripcionAcorn = getString(R.string.acorn_des);
        String descripcionBl4ko = getString(R.string.bl4ko_des);
        String descripcionBlnk = getString(R.string.blnk_des);
        String descripcionCoolbox = getString(R.string.coolbox_des);
        String descripcionElux = getString(R.string.elux_des);
        String descripcionGunpowder = getString(R.string.gunpowder_des);
        String descripcionMinimalDrama = getString(R.string.minimaldrama_des);
        String descripcionRol97 = getString(R.string.rol97_des);
        String descripcionSeteLilak = getString(R.string.setelilak_des);

        //arrays para recorrer con un for e ir creando cada objeto DJ
        String[] nombres = {"Benaya",
                "Daniel",
                "Manuel",
                "Louie",
                "Jesús",
                "Francisco",
                "Jose Manuel",
                "Pablo",
                "Lili y Carlos"};
        String[] apellidos = {"Notario Romero",
                "Hinojosa Tirado",
                "Blanco Moreno",
                "Pinedo",
                "Rodríguez",
                "Ruiz",
                "Luna Pineda",
                "Pablito",
                "Casanova"};
        String[] generoMusical = {"DNB, Neurofunk, Deep, Roller, Techstep",
                "DNB, Neurofunk, Jump Up",
                "Drum & Bass, Techstep, Deep, Neurofunk, Roller",
                "Drum & Bass, Rolling Deep, Jump up Rollers, Deep Rollers, Jungle",
                "Drum & Bass, Roller",
                "Drum & Bass, Rollers, Deep",
                "Drum & Bass, British Underground, Deep Minimal, Liquid, Tech Step, Rolling, Neurofunk",
                "Deep, Rolling, Deep, Funky, Liquid drum & bass",
                "Neurofunk, Drum & Bass, Jump Up"};
        String[] urlFoto = {"https://i.imgur.com/CTfmtjG.jpg",
                "https://i.imgur.com/okrehX3.jpg",
                "https://i.imgur.com/Hknj6P4.jpg",
                "https://i.imgur.com/czXKNEb.jpg",
                "https://i.imgur.com/ygmd4Sb.jpg",
                "https://i.imgur.com/Que00vx.jpg",
                "https://i.imgur.com/nhDbsgu.jpg",
                "https://i.imgur.com/57UKRZ3.jpg",
                "https://i.imgur.com/LFt4p25.jpg"};
        String[] alias = {"ACORN","BL4KO","BLNK","COOLBOX","ELUX","GUNPOWDER","MINIMAL DRAMA",
                "ROLL97","SETE & LILAK"};
        String[] descripcion = {descripcionAcorn,descripcionBl4ko,descripcionBlnk,descripcionCoolbox,
                descripcionElux,descripcionGunpowder,descripcionMinimalDrama,descripcionRol97,
                descripcionSeteLilak};
        String[] urlInstagram = {"https://www.instagram.com/benaya.nr/",
                "https://www.instagram.com/b_l_4_k_o/",
                "https://www.instagram.com/blnk.dnb/",
                "https://www.instagram.com/coolbox_dnb/",
                "https://www.instagram.com/jesus___xule/",
                "https://www.instagram.com/gunpowder_dnb/",
                "https://www.instagram.com/josemlp94/",
                "https://www.instagram.com/roll97_dnb/",
                "https://www.instagram.com/setednb/"};
        String[] urlSoundCloud = {"https://soundcloud.com/acorn-dnb-deep",
                "https://soundcloud.com/bl4ko",
                "https://soundcloud.com/manublnk",
                "https://soundcloud.com/louie-pinedo",
                "https://soundcloud.com/jesus-xule",
                "https://soundcloud.com/francisco-ruiz-288143319",
                "https://soundcloud.com/roll_in_bass",
                "https://soundcloud.com/roll97",
                "https://soundcloud.com/lilakk"};
        String[] urlYoutube = {"https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCI53JhztNF70HzKUYg1_pGw",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCP9pB2ryYvqkRWOOP_bvk8Q"};
        String[] urlCancion ={"https://ia801503.us.archive.org/22/items/acorn-el-origen-series-01-004/ACORN%20-%20El%20Origen%20-%20SERIES%20-%2001_004.mp3",
                "https://ia601407.us.archive.org/11/items/bl-4-ko-el-origen-series-01-002/BL4KO%20-%20El%20Origen%20-%20SERIES%20-%2001_002.mp3",
                "https://ia601503.us.archive.org/17/items/blnk-el-origen-series-01-001/BLNK%20-%20El%20Origen%20-%20SERIES%20-%2001_001.mp3",
                "https://ia601402.us.archive.org/30/items/coolbox-el-origen-series-01-003/COOLBOX%20-%20El%20Origen%20-%20SERIES%20-%2001_003.mp3",
                "https://ia601502.us.archive.org/6/items/elux-el-origen-series-01-008/ELUX%20-%20El%20Origen%20-%20SERIES%20-%2001_008.mp3",
                "https://ia601402.us.archive.org/2/items/gunpowder-el-origen-series-01-007/GUNPOWDER%20-%20El%20Origen%20-%20SERIES%20-%2001_007.mp3",
                "https://ia601508.us.archive.org/22/items/minimal-drama-roll-in-bass-el-origen-series-01-006/MINIMAL%20DRAMA%20-%20Roll%20In%20Bass%20-%20El%20Origen%20-%20SERIES%20-%2001_006.mp3",
                "https://ia601507.us.archive.org/0/items/roll-97-el-origen-series-01-010/ROLL97%20-%20El%20Origen%20-%20SERIES%20-%2001_010.mp3",
                "https://ia601503.us.archive.org/35/items/sete-lilakk-el-origen-series-01-005/SETE%20%26%20LILAKK%20-%20El%20Origen%20-%20SERIES%20-%2001_005.mp3"};


        for (int i=0; i<9; i++){//recorremos los elementos de los arrays con bucle for para ir creando los distintos Djs
            Dj dj = new Dj(id+1,nombres[i],apellidos[i],generoMusical[i],urlFoto[i],alias[i],
                    descripcion[i]);
            id++;
            listaDjs.put(String.valueOf(dj.getId()),dj);//añadimos cada dj al HashMap
        }
        listaDjsRef.setValue(listaDjs);//añadimos la lista HashMap a FB
    }

    //Creacion djs; solo se ejecuta una vez, para la creación de la tabla Djs en FB
    private void creacionLinks(){
        int id = 0;//id en el bucle for
        HashMap<String,Links> listaLinks= new  HashMap<>();//hashMap
        DatabaseReference listaLinksRef = FirebaseDatabase.getInstance().getReference("Links");//Creacion de la ref Djs en FB
        String[] alias = {"ACORN","BL4KO","BLNK","COOLBOX","ELUX","GUNPOWDER","MINIMAL DRAMA",
                "ROLL97","SETE & LILAK"};
        String[] urlInstagram = {
                "https://www.instagram.com/benaya.nr/",
                "https://www.instagram.com/b_l_4_k_o/",
                "https://www.instagram.com/blnk.dnb/",
                "https://www.instagram.com/coolbox_dnb/",
                "https://www.instagram.com/jesus___xule/",
                "https://www.instagram.com/gunpowder_dnb/",
                "https://www.instagram.com/josemlp94/",
                "https://www.instagram.com/roll97_dnb/",
                "https://www.instagram.com/setednb/"};
        String[] urlSoundCloud = {
                "https://soundcloud.com/acorn-dnb-deep",
                "https://soundcloud.com/bl4ko",
                "https://soundcloud.com/manublnk",
                "https://soundcloud.com/louie-pinedo",
                "https://soundcloud.com/jesus-xule",
                "https://soundcloud.com/francisco-ruiz-288143319",
                "https://soundcloud.com/roll_in_bass",
                "https://soundcloud.com/roll97",
                "https://soundcloud.com/lilakk"};
        String[] urlYoutube = {
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCI53JhztNF70HzKUYg1_pGw",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCYoDRQPmeNa9_cBGP7Lz9tQ",
                "https://www.youtube.com/channel/UCP9pB2ryYvqkRWOOP_bvk8Q"};
        String[] urlCancion ={
                "https://ia801503.us.archive.org/22/items/acorn-el-origen-series-01-004/ACORN%20-%20El%20Origen%20-%20SERIES%20-%2001_004.mp3",
                "https://ia601407.us.archive.org/11/items/bl-4-ko-el-origen-series-01-002/BL4KO%20-%20El%20Origen%20-%20SERIES%20-%2001_002.mp3",
                "https://ia601503.us.archive.org/17/items/blnk-el-origen-series-01-001/BLNK%20-%20El%20Origen%20-%20SERIES%20-%2001_001.mp3",
                "https://ia601402.us.archive.org/30/items/coolbox-el-origen-series-01-003/COOLBOX%20-%20El%20Origen%20-%20SERIES%20-%2001_003.mp3",
                "https://ia601502.us.archive.org/6/items/elux-el-origen-series-01-008/ELUX%20-%20El%20Origen%20-%20SERIES%20-%2001_008.mp3",
                "https://ia601402.us.archive.org/2/items/gunpowder-el-origen-series-01-007/GUNPOWDER%20-%20El%20Origen%20-%20SERIES%20-%2001_007.mp3",
                "https://ia601508.us.archive.org/22/items/minimal-drama-roll-in-bass-el-origen-series-01-006/MINIMAL%20DRAMA%20-%20Roll%20In%20Bass%20-%20El%20Origen%20-%20SERIES%20-%2001_006.mp3",
                "https://ia601507.us.archive.org/0/items/roll-97-el-origen-series-01-010/ROLL97%20-%20El%20Origen%20-%20SERIES%20-%2001_010.mp3",
                "https://ia601503.us.archive.org/35/items/sete-lilakk-el-origen-series-01-005/SETE%20%26%20LILAKK%20-%20El%20Origen%20-%20SERIES%20-%2001_005.mp3"};

        for (int i=0; i<9; i++){//recorremos los elementos de los arrays con bucle for para ir creando los distintos grupos de Links para cada id
            Links links = new Links(id+1,alias[i],urlInstagram[i],urlSoundCloud[i],urlYoutube[i], urlCancion[i]);
            id++;
            listaLinks.put(String.valueOf(links.getId()),links);//añadimos cada grupo de links
        }
        listaLinksRef.setValue(listaLinks);//añadimos la lista HashMap a FB
    }

    //configurar ReciclewView para añadir los distintos djs desde la tabla Djs de FB y que asi en caso de haber una modificacion en FB se realice automaticamente en la app.
    private void configRV(){
        for(int i=0; i<9;i++) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Djs");
            ref = ref.child(String.valueOf(i+1));
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Dj dj = snapshot.getValue(Dj.class);
                    adaptadorRecyclerView.add(dj);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(getString(R.string.error));
                }
            });
        }
    }
}