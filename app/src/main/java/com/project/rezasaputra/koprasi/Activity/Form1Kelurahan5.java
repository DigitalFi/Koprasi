package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rezasaputra.koprasi.R;

public class Form1Kelurahan5 extends AppCompatActivity {


    private static final String TAG = Login.class.getSimpleName();

    private SharedPreferences idkop;
    private SharedPreferences pref_idKelembagaan;
    private SharedPreferences idJabatan;
    private SharedPreferences status;
    private EditText inputNama;
    private EditText inputTlp;
    private EditText inputAlamat;
    private TextView namaKop;
    Switch simpleSwitch1;
    Button btnNext;
    Button btnAdd;
    private ProgressDialog pDialog;

    Spinner spinner;
    String URL="http://koperasidev.lavenderprograms.com/apigw/reff/jabatan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner=(Spinner)findViewById(R.id.jabatanpengawas);


        status = getSharedPreferences("st", MODE_PRIVATE);
        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);
        idJabatan = getSharedPreferences("dataJabatan", MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputNama = (EditText) findViewById(R.id.et_pengawas_nama_kel);
        inputTlp = (EditText) findViewById(R.id.et_pengawas_tlp_kel);
        inputAlamat = (EditText) findViewById(R.id.et_pengawas_alamat_kel);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        simpleSwitch1 = (Switch) findViewById(R.id.jenis_klm);
        namaKop = (TextView) findViewById(R.id.nama_koperasi);

        //ambil data dari pref
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        final String nama = idkop.getString("nama", "");
        //deklarasikan text view
        TextView namakoptxt = (TextView) findViewById(R.id.nama_koperasi);
        //ambil data ke text view
        namakoptxt.setText(nama);

        final String jenisKlm;
        if (simpleSwitch1.isChecked())
            jenisKlm = simpleSwitch1.getTextOn().toString();
        else
            jenisKlm = simpleSwitch1.getTextOff().toString();


        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jabatan_profile jabatan = (jabatan_profile) adapterView.getSelectedItem();
                final SharedPreferences idJabatan = getApplicationContext().getSharedPreferences("koperasi", 0);// 0 - for private mode
                final SharedPreferences.Editor editor = idJabatan.edit();
                editor.putString("id_jabatan", jabatan.getId());
                editor.commit();

                //Toast.makeText(Form1Kelurahan2.this, "Id Jabatan: "+idJabatan.getString("id_jabatan", null)+",  Nama Koperasi : "+jabatan.getName(), Toast.LENGTH_SHORT).show();

                //fungsi buton btnNext
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                        final String idKop = idkop.getString("id_kop", "");
                        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);
                        final String idKelembagaan = pref_idKelembagaan.getString("id_kelembagaan","");
                        final String id_jabatan = idJabatan.getString("id_jabatan","");
                        String nama = inputNama.getText().toString().trim();
                        String tlp = inputTlp.getText().toString().trim();
                        String alamat = inputAlamat.getText().toString().trim();


                        //memberikan status
                        SharedPreferences.Editor editor = status.edit();
                        editor.putInt("status", 2);
                        editor.commit();

                        // ngecek apakah inputannya kosong atau Tidak
                        if (!jenisKlm.isEmpty() && !idKop.isEmpty() && !idKelembagaan.isEmpty() && !id_jabatan.isEmpty() &&  !nama.isEmpty() && !tlp.isEmpty() && !alamat.isEmpty()){
                            // login user
                            checkUpload(jenisKlm, idKop, idKelembagaan, id_jabatan, nama, tlp, alamat);
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

                        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                        final String idKop = idkop.getString("id_kop", "");
                        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);
                        final String idKelembagaan = pref_idKelembagaan.getString("id_kelembagaan","");
                        final String id_jabatan = idJabatan.getString("id_jabatan","");
                        String nama = inputNama.getText().toString().trim();
                        String tlp = inputTlp.getText().toString().trim();
                        String alamat = inputAlamat.getText().toString().trim();


                        //memberikan status
                        SharedPreferences.Editor editor = status.edit();
                        editor.putInt("status", 1);
                        editor.commit();


                        // ngecek apakah inputannya kosong atau Tidak
                        if (!jenisKlm.isEmpty() && !idKop.isEmpty() && !idKelembagaan.isEmpty() && !id_jabatan.isEmpty() &&  !nama.isEmpty() && !tlp.isEmpty() && !alamat.isEmpty()){
                            // login user
                            checkUpload(jenisKlm, idKop, idKelembagaan, id_jabatan, nama, tlp, alamat);
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

    private void checkUpload(String jenisKlm, String idKop, String idKelembagaan, String id_jabatan, String nama, String tlp, String alamat) {

    }

    private void loadSpinnerData(String url) {

    }

}
