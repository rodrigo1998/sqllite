package com.rodrigo_oliveira.sqllite;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rodrigo_oliveira.sqllite.persist.PrevisaoDAO;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/**
 * Created by Rodrigo Oliveira on 13/04/18.
 * ra 816114365
 */
public class MainActivity extends AppCompatActivity {

    private PrevisaoDAO previsaoDAO;
    private ListView previsoesListView;
    private List<Previsao> previsoes;
    private ArrayAdapter<Previsao> adapter;
    private EditText cidadeEditText;
    private TextView descricaoTextView;
    private TextView minTextView;
    private TextView maxTextView;
    private TextView humidityTextView;
    private SimpleDateFormat sdf =
            new SimpleDateFormat("EEEE", new Locale("pt", "BR"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidadeEditText = (EditText)
                findViewById(R.id.cidadeEditText);
        previsoesListView = (ListView)
                findViewById(R.id.previsoesListView);

        previsaoDAO = new PrevisaoDAO(this);
        previsoes = previsaoDAO.buscarTodos();
        adapter = new ArrayAdapter<Previsao>(this, -1, previsoes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)
                        MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


/**
 * Created by Rodrigo Oliveira on 13/04/18.
 * ra 816114365
 */
            }

            @Override
            protected void onResume() {
                
                super.onResume();
                mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
                Utils.printStatus(mStatusView, mStatusAllView);
                setContentView(R.layout.activity_main);
                cidadeEditText = (EditText)
                        findViewById(R.id.cidadeEditText);
                previsoesListView = (ListView)
                        findViewById(R.id.previsoesListView);

                previsaoDAO = new PrevisaoDAO(this);

                previsoes = previsaoDAO.buscarTodos();
                adapter = new ArrayAdapter<Previsao>(this, -1, previsoes) {
                    @NonNull
                    @Override

                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater)
                                MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.previsao, parent, false);

                        ImageView iconeImageView =
                                (ImageView) view.findViewById(R.id.iconeImageView);
                        TextView descricaoTextView =
                                (TextView) view.findViewById(R.id.descricaoTextView);
                        TextView maxTextView =
                                (TextView) view.findViewById(R.id.maxTextView);
                        TextView minTextView =
                                (TextView) view.findViewById(R.id.minTextView);

                        TextView humidityTextView =
                                (TextView) view.findViewById(R.id.humidityTextView);

                        TextView nomeCidadeTextView =
                                (TextView) view.findViewById(R.id.nomeCidadeTextView);

                        Button apagarButton = (Button)
                                view.findViewById(R.id.apagarButton);
                        Previsao caraEscolhido = getItem(position);
                        apagarButton.setTag(caraEscolhido);

                        apagarButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Previsao previsaoAApagar = (Previsao) view.getTag();
                                previsaoDAO.apagar(previsaoAApagar);
                                previsoes = previsaoDAO.buscarTodos();
                                adapter.notifyDataSetChanged();
                                //previsoesListView.invalidate();
                                //preprevisoes.remove(previsaoAApagar);
                            }
                        });


                        //usar Picasso para carregar a figura
                        String urlFigura = "http://openweathermap.org/img/w/"
                                + caraEscolhido.getIcone() + ".png";

                        Picasso.with(MainActivity.this).load(urlFigura).into(iconeImageView);

                        descricaoTextView.setText(caraEscolhido.getDiaDaSemana() + " : " + caraEscolhido.getDescricao());
                        maxTextView.setText(Double.toString(caraEscolhido.getMax()));
                        minTextView.setText(Double.toString(caraEscolhido.getMin()));
                        humidityTextView.setText(Integer.toString(caraEscolhido.getHumidade()));
                        nomeCidadeTextView.setText(caraEscolhido.getNomeCidade());
                        return view;
                    }
                };
                previsoesListView.setAdapter(adapter);
            }

            public void buscar(View view) {
                String cidade = cidadeEditText.
                        getEditableText().toString();

                StringBuilder sb = new StringBuilder("");
                sb.append(getString(R.string.url_ws));
                sb.append(cidade);
                sb.append("&appid=").
                        append(getString(R.string.chave)).
                        append("&lang=pt").
                        append("&units=metric");
                new BuscaJSON().execute(sb.toString());

        /*final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().
                url(sb.toString()).
                build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.
                            newCall(request).execute();
                    String corpo = response.body().string();
                    runOnUiThread(()->{
                            Toast.makeText(MainActivity.this,
                                    corpo,
                                    Toast.LENGTH_SHORT).show();
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/


            }

            private class BuscaJSON extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... url) {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder().
                            url(url[0]).
                            build();
                    try {
                        return client.newCall(request).execute().body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        String erro =
                                String.format("{%s : %s}", "erro", e.getMessage());
                        return erro;
                    }
                }

                @Override
                protected void onPostExecute(String json) {
                    //Toast.makeText(MainActivity.this, json,
                    //      Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject previsao = new JSONObject(json);
                        JSONArray list = previsao.getJSONArray("list");
                        JSONObject dia = list.getJSONObject(0);
                        long dt = dia.getLong("dt");
                        double min = dia.getJSONObject("temp").getDouble("min");
                        double max = dia.getJSONObject("temp").getDouble("max");
                        int humidade = dia.getInt("humidity");
                        String descricao =
                                dia.getJSONArray("weather").
                                        getJSONObject(0).getString("description");
                        Date date = new Date();
                        date.setTime(dt * 1000);
                        String diaSemana = sdf.format(date);
                        String nomeCidade = previsao.
                                getJSONObject("city").
                                getString("name");

                        String icone = dia.
                                getJSONArray("weather").
                                getJSONObject(0).
                                getString("icon");
                        Previsao p = new Previsao(descricao, diaSemana, min, max, humidade, nomeCidade, icone);
                        previsoes.add(p);
                        long id = previsaoDAO.inserir(p);
                        Toast.makeText(MainActivity.this,
                                Long.toString(id), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }
}
