package com.project.rezasaputra.koprasi.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.rezasaputra.koprasi.Activity.helper.AppConfig;
import com.project.rezasaputra.koprasi.Activity.helper.AppController;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Form2Kelurahan3 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private SharedPreferences idkop;
    private SharedPreferences pref_idPerkembangan;
    private SharedPreferences idLegal;
    private SharedPreferences status;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText etNomor;
    private TextView Date_show, tvTanggal;
    private TextView legal1txt, legal2txt, legal3txt;
    Button btnNext;
    Button btnDatePicker;
    Button btnAdd;
    private ProgressDialog pDialog;

    Spinner spinner;
    String URL = "http://koperasidev.dfiserver.com/apigw/reff/type_legalitas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2_kelurahan3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        status = getSharedPreferences("st", MODE_PRIVATE);
        spinner = (Spinner) findViewById(R.id.legalitas);
        idLegal = getSharedPreferences("dataLegal", MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        etNomor = (EditText) findViewById(R.id.et_nomor_badan);
        tvTanggal = (TextView) findViewById(R.id.date_show);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        legal1txt = (TextView) findViewById(R.id.legal_1);
        legal2txt = (TextView) findViewById(R.id.legal_2);
        legal3txt = (TextView) findViewById(R.id.legal_3);



        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        pref_idPerkembangan = getSharedPreferences("idper", MODE_PRIVATE);
        final String idPer = pref_idPerkembangan.getString("id_perkembangan", "");
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        final String idKop = idkop.getString("id_kop", "");
        //deklarasikan text view
        //ambil data ke text view
        //legal1.setText(nama);

        Date_show = (TextView) findViewById(R.id.date_show);
        btnDatePicker = (Button) findViewById(R.id.btnDatepicker);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        // ngecek apakah inputannya kosong atau Tidak
        if (!idPer.isEmpty()) {
            // login user
            loadList(idPer);
            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + keaktifan + "\n" + "Switch2 :" + rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's
        } else {
            // jika inputan kosong tampilkan pesan
            Toast.makeText(getApplicationContext(),
                    "harap isi dengan benar", Toast.LENGTH_LONG)
                    .show();
            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "kelembagaan :" + idKelembagaan + "\n" + "jabatan :" + id_jabatan + "\n" +  "nama :" + nama + "\n" +  "tlp :" + tlp + "\n" +  "alamat :" + alamat, Toast.LENGTH_LONG).show(); // display the current state for switch's

        }

        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipe_legalitas legalitas = (tipe_legalitas) adapterView.getSelectedItem();
                final SharedPreferences idLegalitas = getApplicationContext().getSharedPreferences("koperasi", 0);// 0 - for private mode
                final SharedPreferences.Editor editor = idLegalitas.edit();
                editor.putString("id_type_legalitas", legalitas.getId());
                editor.commit();

                //Toast.makeText(Form1Kelurahan2.this, "Id Jabatan: "+idJabatan.getString("id_jabatan", null)+",  Nama Koperasi : "+jabatan.getName(), Toast.LENGTH_SHORT).show();

                //fungsi buton btnNext
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String idKop = idkop.getString("id_kop", "");
                        final String id_legalitas = idLegalitas.getString("id_type_legalitas", "");
                        String nomor = etNomor.getText().toString().trim();
                        String tgl = tvTanggal.getText().toString().trim();

                        //memberikan status
                        SharedPreferences.Editor editor = status.edit();
                        editor.putInt("status", 2);
                        editor.commit();

                        // ngecek apakah inputannya kosong atau Tidak
                        if (!idKop.isEmpty() && !idPer.isEmpty() && !id_legalitas.isEmpty() && !nomor.isEmpty() && !tgl.isEmpty()) {
                            // login user
                            checkUpload(idKop, idPer, id_legalitas, nomor, tgl);
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + keaktifan + "\n" + "Switch2 :" + rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's
                        } else {
                            // jika inputan kosong tampilkan pesan
                            Toast.makeText(getApplicationContext(),
                                    "harap isi dengan benar", Toast.LENGTH_LONG)
                                    .show();
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "kelembagaan :" + idKelembagaan + "\n" + "jabatan :" + id_jabatan + "\n" +  "nama :" + nama + "\n" +  "tlp :" + tlp + "\n" +  "alamat :" + alamat, Toast.LENGTH_LONG).show(); // display the current state for switch's

                        }

                    }
                });

                //fungsi buton btnNext
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String id_legalitas = idLegalitas.getString("id_type_legalitas", "");
                        String nomor = etNomor.getText().toString().trim();
                        String tgl = tvTanggal.getText().toString().trim();

                        //memberikan status
                        SharedPreferences.Editor editor = status.edit();
                        editor.putInt("status", 1);
                        editor.commit();

                        // ngecek apakah inputannya kosong atau Tidak
                        if (!idKop.isEmpty() && !idPer.isEmpty() && !id_legalitas.isEmpty() && !nomor.isEmpty() && !tgl.isEmpty()) {
                            // login user
                            checkUpload(idKop, idPer, id_legalitas, nomor, tgl);
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + keaktifan + "\n" + "Switch2 :" + rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's
                        } else {
                            // jika inputan kosong tampilkan pesan
                            Toast.makeText(getApplicationContext(),
                                    "harap isi dengan benar", Toast.LENGTH_LONG)
                                    .show();
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "kelembagaan :" + idKelembagaan + "\n" + "jabatan :" + id_jabatan + "\n" +  "nama :" + nama + "\n" +  "tlp :" + tlp + "\n" +  "alamat :" + alamat, Toast.LENGTH_LONG).show(); // display the current state for switch's

                        }

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Form2Kelurahan3.this,Form2Kelurahan.class);
        startActivity(setIntent);
    }

    private void loadList(final String idPer) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LEGALITAS_PERKEMBANGAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //String idKelembagaan = jObj.getString("id_klembagaan");
                    //save id kelembagaan
                    //SharedPreferences.Editor editor = pref_idKelembagaan.edit();
                    //editor.putString("id_kelembagaan", idKelembagaan);
                    //editor.commit();

                    // ngecek node error dari api
                    if (!error) {
                        // user berhasil login
                        JSONArray stObj = jObj.getJSONArray("data");
                        for (int i = 0; i < stObj.length(); i++)
                        {
                            jObj = stObj.getJSONObject(i);
                            String legal1 = jObj.getString("legal1");
                            String legal2 = jObj.getString("legal2");
                            String legal3 = jObj.getString("legal3");


                            //ambil data ke text view
                            legal1txt.setText(legal1);
                            legal2txt.setText(legal2);
                            legal3txt.setText(legal3);


                        }

                    } else {
                        //terjadi error dan tampilkan pesan error dari API
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection",
                            Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_perkembangan", idPer);
                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void checkUpload(final String idKop, final String idPer, final String id_legalitas, final String nomor, final String tgl) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_LEGALITAS_PERKEMBANGAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //String idKelembagaan = jObj.getString("id_klembagaan");
                    //save id kelembagaan
                    //SharedPreferences.Editor editor = pref_idKelembagaan.edit();
                    //editor.putString("id_kelembagaan", idKelembagaan);
                    //editor.commit();

                    // ngecek node error dari api
                    if (!error) {
                        // user berhasil login
                        /*JSONObject data = jObj.getJSONObject("data");
                        String koperasi_id = data.getString("id_koperasi");
                        String keaktifan_status = data.getString("status_keaktifan");
                        String anggota_rapat = data.getString("rapat_anggota");
                        String roles_name = data.getString("jml_anggota");
                        String kec_id = data.getString("photo");
                        String kel_id = jObj.getString("create_date");
                        Integer roles_id = jObj.getInt("create_by");*/

                        //jika sudah masuk ke mainactivity
                        status = getSharedPreferences("st", Context.MODE_PRIVATE);
                        final int stat = status.getInt("status", 0);

                        if (stat == 1) {

                            etNomor.setText("");
                            tvTanggal.setText("");
                            Toast.makeText(getApplicationContext(),
                                    "Data Sudah Di tambahkan , silahkan isi kembali untuk menambahkan", Toast.LENGTH_LONG)
                                    .show();

                            Intent intent = new Intent(Form2Kelurahan3.this,
                                    Form2Kelurahan3.class);
                            startActivity(intent);

                        } else if (stat == 2) {
                            Intent intent = new Intent(Form2Kelurahan3.this,
                                    Form2Kelurahan1.class);
                            startActivity(intent);
                        }
                        finish();

                    } else {
                        //terjadi error dan tampilkan pesan error dari API
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection",
                            Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_koperasi", idKop);
                params.put("id_perkembangan", idPer);
                params.put("id_type_legalitas", id_legalitas);
                params.put("no_legalitas", nomor);
                params.put("tgl_legalitas", tgl);
                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<tipe_legalitas> legalitasList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("name");
                        legalitasList.add(new tipe_legalitas("", "-Select-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            legalitasList.add(new tipe_legalitas(jsonObject1.getString("id_type_legalitas"), jsonObject1.getString("type_legalitas")));
                        }
                        ArrayAdapter<tipe_legalitas> adapter = new ArrayAdapter<tipe_legalitas>(Form2Kelurahan3.this, android.R.layout.simple_spinner_dropdown_item, legalitasList);
                        spinner.setAdapter(adapter);
                    }
                    ArrayAdapter<tipe_legalitas> adapter = new ArrayAdapter<tipe_legalitas>(Form2Kelurahan3.this, android.R.layout.simple_spinner_dropdown_item, legalitasList);
                    spinner.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void showDateDialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                Date_show.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }

    //untuk menampilkan loading dialog
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
