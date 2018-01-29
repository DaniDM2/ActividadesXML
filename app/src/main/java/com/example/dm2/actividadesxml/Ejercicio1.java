package com.example.dm2.actividadesxml;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio1 extends AppCompatActivity {

    private ListView lista;
    private  List<Noticia> noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);

        lista=(ListView) findViewById(R.id.listaNoticias);
    }

    public void cargar(View v){
        CargarXMLTask task= new CargarXMLTask();
        task.execute();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Noticia item= (Noticia) lista.getItemAtPosition(position);
                Uri uri = Uri.parse(item.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    public class CargarXMLTask extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            RssParserSax saxParser=new RssParserSax("http://www.europapress.es/rss/rss.aspx");
            noticias=saxParser.parse();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //Tratamnos la lista de noticias metiendolos en la lista
            Adaptador ad=new Adaptador(this, (ArrayList<Noticia>) noticias); //CIUDADO CON EL PARSEO
            lista=(ListView)findViewById(R.id.listaNoticias);
            lista.setAdapter(ad);
        }
    }

    public class Adaptador extends ArrayAdapter<Noticia> {

        ArrayList<Noticia> websArr=null;

        public Adaptador(CargarXMLTask context, @NonNull ArrayList<Noticia> webs) {
            super(context, R.layout.vistaweb,webs);
            websArr=webs;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getContext());
            View item=inflater.inflate(R.layout.vistaweb,null);

            //CAMBIANOS EL TEXTO
            TextView txt=(TextView) item.findViewById(R.id.titular);
            txt.setText(websArr.get(position).getTitulo());

            return (item);
        }
    }

}