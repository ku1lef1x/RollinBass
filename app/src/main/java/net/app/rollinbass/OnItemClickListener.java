package net.app.rollinbass;

//interfaz creada para pulsacion larga o corta en la recycler view, en esta versión de la app solo se usara la pulsacion corta.
public interface OnItemClickListener {

    void onItemClick(Dj dj); //click en el item
    void onLongItemClick (Dj dj); //long click en el item
}
