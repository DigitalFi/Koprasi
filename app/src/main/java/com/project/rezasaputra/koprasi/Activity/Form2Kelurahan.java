package com.project.rezasaputra.koprasi.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.rezasaputra.koprasi.Activity.helper.AppConfig;
import com.project.rezasaputra.koprasi.Activity.helper.AppController;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Form2Kelurahan extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private ProgressDialog pDialog;
    private SharedPreferences idkop;
    private SharedPreferences pref_idPerkembangan;
    private SharedPreferences pref;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView date_show;
    private Button btnDatePicker;
    private Button btnNext;
    private EditText dataawalKea;
    private EditText bulanberKea;
    Switch dataawal_rat, bulanberjalan_rat, dataawal_ad, bulanberjalan_ad, dataawal_art, bulanberjalan_art, dataawal_pad, bulanberjalan_pad, dataawal_pk, bulanberjalan_pk, dataawal_kd, bulanberjalan_kd;

    String selectedMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2_kelurahan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Inialisasi Prefrences
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        pref_idPerkembangan = getSharedPreferences("idper", MODE_PRIVATE);
        pref = getSharedPreferences("data", MODE_PRIVATE);

        // inisialisasi variable
        btnNext = (Button) findViewById(R.id.btnNext);
        date_show = (TextView) findViewById(R.id.date_show);
        dataawalKea = (EditText) findViewById(R.id.et_dataawal_keanggotaan);
        bulanberKea = (EditText) findViewById(R.id.et_bulanberjalan_keanggotaan);
        dataawal_rat = (Switch) findViewById(R.id.dataawal_rat);
        bulanberjalan_rat = (Switch) findViewById(R.id.bulanberjalan_rat);
        dataawal_ad = (Switch) findViewById(R.id.dataawal_ad);
        bulanberjalan_ad = (Switch) findViewById(R.id.bulanberjalan_ad);
        dataawal_art = (Switch) findViewById(R.id.dataawal_art);
        dataawal_pad = (Switch) findViewById(R.id.dataawal_pad);
        bulanberjalan_pad = (Switch) findViewById(R.id.bulanberjalan_pad);
        dataawal_pk = (Switch) findViewById(R.id.dataawal_pk);
        bulanberjalan_pk = (Switch) findViewById(R.id.bulanberjalan_pk);
        dataawal_kd = (Switch) findViewById(R.id.dataawal_kd);
        bulanberjalan_kd = (Switch) findViewById(R.id.bulanberjalan_kd);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        date_show = (TextView) findViewById(R.id.date_show);
        btnDatePicker = (Button) findViewById(R.id.btnDatepicker);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        final String nama = idkop.getString("nama", "");
        final String badanhukum = idkop.getString("badanhukum", "");
        final String alamat = idkop.getString("alamat", "");

        TextView badanhktxt = (TextView) findViewById(R.id.badanhukumKp);
        TextView alamattxt = (TextView) findViewById(R.id.alamatKp);
        TextView namatxt  = (TextView) findViewById(R.id.namaKp);

        badanhktxt.setText(badanhukum);
        alamattxt.setText(alamat);
        namatxt.setText(nama);

        //mengambil data dari switch
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dataawalrat, bulanberjalanrat, dataawalad, bulanberjalanad, dataawalart, dataawalpad, bulanberjalanpad, dataawalpk, bulanberjalanpk, dataawalkd, bulanberjalankd;
                if (dataawal_rat.isChecked())
                    dataawalrat = dataawal_rat.getTextOn().toString();
                else
                    dataawalrat = dataawal_rat.getTextOff().toString();
                if (dataawal_ad.isChecked())
                    dataawalad = dataawal_ad.getTextOn().toString();
                else
                    dataawalad = dataawal_ad.getTextOff().toString();
                if (dataawal_art.isChecked())
                    dataawalart = dataawal_art.getTextOn().toString();
                else
                    dataawalart = dataawal_art.getTextOff().toString();
                if (dataawal_pad.isChecked())
                    dataawalpad = dataawal_pad.getTextOn().toString();
                else
                    dataawalpad = dataawal_pad.getTextOff().toString();
                if (dataawal_pk.isChecked())
                    dataawalpk = dataawal_pk.getTextOn().toString();
                else
                    dataawalpk = dataawal_pk.getTextOff().toString();
                if (dataawal_kd.isChecked())
                    dataawalkd = dataawal_kd.getTextOn().toString();
                else
                    dataawalkd = dataawal_kd.getTextOff().toString();

                if (bulanberjalan_rat.isChecked())
                    bulanberjalanrat = bulanberjalan_rat.getTextOn().toString();
                else
                    bulanberjalanrat = bulanberjalan_rat.getTextOff().toString();
                if (bulanberjalan_ad.isChecked())
                    bulanberjalanad = bulanberjalan_ad.getTextOn().toString();
                else
                    bulanberjalanad = bulanberjalan_ad.getTextOff().toString();
                if (bulanberjalan_pad.isChecked())
                    bulanberjalanpad = bulanberjalan_pad.getTextOn().toString();
                else
                    bulanberjalanpad = bulanberjalan_pad.getTextOff().toString();
                if (bulanberjalan_pk.isChecked())
                    bulanberjalanpk = bulanberjalan_pk.getTextOn().toString();
                else
                    bulanberjalanpk = bulanberjalan_pk.getTextOff().toString();
                if (bulanberjalan_kd.isChecked())
                    bulanberjalankd = bulanberjalan_kd.getTextOn().toString();
                else
                    bulanberjalankd = bulanberjalan_kd.getTextOff().toString();

                final String idKop = idkop.getString("id_kop", "");
                final String idPer = pref_idPerkembangan.getString("id_perkembangan", "");
                final String idUser = pref.getString("user_id","");
                String tanggal = date_show.getText().toString().trim();
                String dtkea = dataawalKea.getText().toString().trim();
                String bbkea = bulanberKea.getText().toString().trim();

                // ngecek apakah inputannya kosong atau Tidak
                if (!idUser.isEmpty() && !idKop.isEmpty() && !tanggal.isEmpty() && !dtkea.isEmpty() && !bbkea.isEmpty() && !dataawalrat.isEmpty() && !bulanberjalanrat.isEmpty() && !dataawalad.isEmpty() && !bulanberjalanad.isEmpty() && !dataawalart.isEmpty() && !dataawalpad.isEmpty() && !bulanberjalanpad.isEmpty() && !dataawalpk.isEmpty() && !bulanberjalanpk.isEmpty() && !dataawalkd.isEmpty() && !bulanberjalankd.isEmpty()){
                    // login user
                    checkUpload(idUser, idKop, tanggal, dtkea, bbkea, dataawalrat, bulanberjalanrat, dataawalad, bulanberjalanad, dataawalart, dataawalpad, bulanberjalanpad, dataawalpk, bulanberjalanpk, dataawalkd, bulanberjalankd);

                    //Toast.makeText(getApplicationContext(), "idPer :" + idPer , Toast.LENGTH_LONG).show(); // display the current state for switch's
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "harap isi dengan benar", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });


        //inisialisasi enable switch for ad
        /*String dtad;
        if (dataawal_ad.isChecked()) {
            dtad = dataawal_ad.getTextOn().toString();
            Toast.makeText(getApplicationContext(), "cek" + dtad, Toast.LENGTH_LONG).show();
        } else {
            dtad = dataawal_ad.getTextOff().toString();
            Toast.makeText(getApplicationContext(), "cek" + dtad, Toast.LENGTH_LONG).show();
        }

        if (dtad == "Tidak"){
            //memberi kondisi jika tidak ada data awal maka dia akan muncul bulan berjalan
            bulanberjalan_ad.setVisibility(View.GONE);
        }else {
            bulanberjalan_ad.setVisibility(View.VISIBLE);
        }*/

        dataawal_ad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                boolean checked = ((Switch) buttonView).isChecked();
                    bulanberjalan_ad.setVisibility(View.VISIBLE);
                if (checked)
                {
                    bulanberjalan_ad.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Form2Kelurahan.this,Form1Kelurahan4.class);
        startActivity(setIntent);
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
                date_show.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }

    private void checkUpload(final String idUser, final String idKop, final String tanggal, final String dtkea, final String bbkea, final String dtrat, final String bbrat, final String dtad, final String bbad, final String dtart, final String dtpad, final String bbpad, final String dtpk, final String bbpk, final String dtkd, final String bbkd) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_req_input";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_PERKEMBANGAN_KELEMBAGAAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String idPerkembangan = jObj.getString("id_perkembangan");
                    //save id kelembagaan
                    SharedPreferences.Editor editor = pref_idPerkembangan.edit();
                    editor.putString("id_perkembangan", idPerkembangan);
                    editor.commit();


                    // ngecek node error dari api
                    if (!error) {
                        // user berhasil login

                        //jika sudah masuk ke mainactivity
                        Intent intent = new Intent(Form2Kelurahan.this,
                                Form2Kelurahan3.class);
                        startActivity(intent);
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
                params.put("tgl_kunjungan", tanggal);
                params.put("jml_anggota_awal", dtkea);
                params.put("jml_anggota_berjalan", bbkea);
                params.put("rapat_anggota_awal", dtrat);
                params.put("rapat_anggota_berjalan", bbrat);
                params.put("anggaran_dasar_awal", dtad);
                params.put("anggaran_dasar_berjalan", bbad);
                params.put("art_awal", dtart);
                params.put("ubah_anggaran_dsr_awal", dtpad);
                params.put("ubah_anggaran_dsr_berjalan", bbpad);
                params.put("peraturan_khusus_awal", dtpk);
                params.put("peraturan_khusus_berjalan", bbpk);
                params.put("anggota_dekopinda_awal", dtkd);
                params.put("anggota_dekopinda_berjalan", bbkd);
                params.put("user_id", idUser);
                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
