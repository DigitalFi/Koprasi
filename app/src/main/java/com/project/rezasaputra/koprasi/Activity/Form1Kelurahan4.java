package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Form1Kelurahan4 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private Button btnNext;
    private Button btnCapture;
    private android.widget.ImageView ImageView;
    private EditText inputAlamat;
    private EditText inputOmzet;
    private EditText inputSimpananPokok;
    private EditText inputJumlahSimpananPokok;
    private EditText inputSimpananWajib;
    private EditText inputJumlahSimpananWajib;
    private EditText inputShuTahunan;
    private Switch simpleSwitch1;
    private ProgressDialog pDialog;
    private SharedPreferences idkop;
    private SharedPreferences form1;
    private SharedPreferences pref;
    private SharedPreferences getLoc;
    private SharedPreferences idUsaha;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    Spinner spinner;
    String URL="http://koperasidev.lavenderprograms.com/apigw/reff/jenis_usaha";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        form1 = getSharedPreferences("dataForm1", MODE_PRIVATE);
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        pref = getSharedPreferences("data", MODE_PRIVATE);
        idUsaha = getSharedPreferences("usaha", MODE_PRIVATE);
        getLoc = getSharedPreferences("location", MODE_PRIVATE);
        spinner=(Spinner)findViewById(R.id.bidangusaha);

        inputAlamat = (EditText) findViewById(R.id.et_data_alamat_usaha);
        inputJumlahSimpananPokok = (EditText) findViewById(R.id.et_data_jumlah_simpanan_usaha);
        inputJumlahSimpananWajib = (EditText) findViewById(R.id.et_data_jumlah_simpanan_wajib_usaha);
        inputOmzet = (EditText) findViewById(R.id.et_data_omzet);
        inputSimpananPokok = (EditText) findViewById(R.id.et_data_simpanan_usaha);
        inputSimpananWajib = (EditText) findViewById(R.id.et_data_simpanan_wajib_usaha);
        inputShuTahunan = (EditText) findViewById(R.id.et_data_shu_tahunan);

        inputOmzet.addTextChangedListener(OMZET());
        inputSimpananPokok.addTextChangedListener(SIMPANANPOKOK());
        inputSimpananWajib.addTextChangedListener(SIMPANANWAJIB());
        inputJumlahSimpananPokok.addTextChangedListener(JMLSIMPANANPOKOK());
        inputJumlahSimpananWajib.addTextChangedListener(JMLSIMPANANWAJIB());
        inputShuTahunan.addTextChangedListener(SHU());

        btnCapture = (Button) findViewById(R.id.btnCapture);
        ImageView = (android.widget.ImageView) findViewById(R.id.ImageView);
        btnNext = (Button) findViewById(R.id.btnNext);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);

        loadSpinnerData(URL);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bidang_usaha usaha = (bidang_usaha) adapterView.getSelectedItem();
                final SharedPreferences idUsaha = getApplicationContext().getSharedPreferences("usaha", 0);// 0 - for private mode
                SharedPreferences.Editor editor = idUsaha.edit();
                editor.putString("id_unit_usaha", usaha.getId());
                editor.commit();

                //Toast.makeText(Form1Kelurahan4.this, "Id Usaha: "+idUsaha.getString("id_unit_usaha", null)+",  Nama Koperasi : "+usaha.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Form1Kelurahan4.this,Form1Kelurahan3.class);
        startActivity(setIntent);
    }

    // currency format for editext
    private TextWatcher OMZET() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputOmzet.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    inputOmzet.setText(formattedString);
                    inputOmzet.setSelection(inputOmzet.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputOmzet.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher SIMPANANPOKOK() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputSimpananPokok.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    inputSimpananPokok.setText(formattedString);
                    inputSimpananPokok.setSelection(inputSimpananPokok.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputSimpananPokok.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher SIMPANANWAJIB() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputSimpananWajib.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    inputSimpananWajib.setText(formattedString);
                    inputSimpananWajib.setSelection(inputSimpananWajib.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputSimpananWajib.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher JMLSIMPANANPOKOK() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputJumlahSimpananPokok.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    inputJumlahSimpananPokok.setText(formattedString);
                    inputJumlahSimpananPokok.setSelection(inputJumlahSimpananPokok.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputJumlahSimpananPokok.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher JMLSIMPANANWAJIB() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputJumlahSimpananWajib.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    inputJumlahSimpananWajib.setText(formattedString);
                    inputJumlahSimpananWajib.setSelection(inputJumlahSimpananWajib.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputJumlahSimpananWajib.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher SHU() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputShuTahunan.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    inputShuTahunan.setText(formattedString);
                    inputShuTahunan.setSelection(inputShuTahunan.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                inputShuTahunan.addTextChangedListener(this);
            }
        };
    }
    // currency format for editext


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap bitmap = (Bitmap) extras.get("data");
            ImageView.setImageBitmap(bitmap);

            //fungsi buton btnNext
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //ini dari sini
                    String status;
                    if (simpleSwitch1.isChecked())
                        status = simpleSwitch1.getTextOn().toString();
                    else
                        status = simpleSwitch1.getTextOff().toString();

                    final String idKop = idkop.getString("id_kop", "");
                    final String id_bidangusaha = idUsaha.getString("id_unit_usaha","");
                    String alamat = inputAlamat.getText().toString().trim();
                    String omzet = inputOmzet.getText().toString().trim();
                    String simpananpokok = inputSimpananPokok.getText().toString().trim();
                    String jmlsimpananpokok = inputJumlahSimpananPokok.getText().toString().trim();
                    String simpananwajib = inputSimpananWajib.getText().toString().trim();
                    String jmlsimpananwajib = inputJumlahSimpananWajib.getText().toString().trim();
                    String shutahunan = inputShuTahunan.getText().toString().trim();

                    final String Loc = getLoc.getString("LattitudeandLongti","");

                    // ngecek apakah inputannya kosong atau Tidak
                    //if (bitmap != null){
                        // login user
                        uploadBitmap(bitmap, idKop, id_bidangusaha, alamat, status, omzet, Loc, simpananpokok, jmlsimpananpokok, simpananwajib, jmlsimpananwajib, shutahunan);
                        //Toast.makeText(getApplicationContext(), "idkop:"+idKop + "idbidangusaha:"+id_bidangusaha +"alamat:"+ alamat +"omzet:"+ omzet +"loc:"+ Loc +"simpananpokok:"+ simpananpokok +"jmlhsimpanan:"+ jmlsimpananpokok +"jmlsimpananwjb:"+ jmlsimpananwajib +"shuthn:"+ shutahunan , Toast.LENGTH_LONG).show(); // display the current state for switch's

                    //} else {
                        // jika inputan kosong tampilkan pesan
                        //Toast.makeText(getApplicationContext(),
                        //        "harap isi dengan benar", Toast.LENGTH_LONG)
                        //        .show();
                        //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "kelembagaan :" + idKelembagaan + "\n" + "jabatan :" + id_jabatan + "\n" +  "nama :" + nama + "\n" +  "tlp :" + tlp + "\n" +  "alamat :" + alamat, Toast.LENGTH_LONG).show(); // display the current state for switch's
                        //Toast.makeText(getApplicationContext(), "idkop:"+idKop + "idbidangusaha:"+id_bidangusaha +"alamat:"+ alamat +"omzet:"+ omzet +"loc:"+ Loc +"simpananpokok:"+ simpananpokok +"jmlhsimpanan:"+ jmlsimpananpokok +"jmlsimpananwjb:"+ jmlsimpananwajib +"shuthn:"+ shutahunan , Toast.LENGTH_LONG).show(); // display the current state for switch's

                    //}
                    //ini akhir
                }
            });

        }else{

            Toast.makeText(getApplicationContext(),"Mohon Dicek kembali", Toast.LENGTH_LONG).show(); // display the current state for switch's
            Intent intent = new Intent(Form1Kelurahan4.this,
                    Form1Kelurahan4.class);
            startActivity(intent);
            finish();
        }
    }



    private void uploadBitmap(final Bitmap bitmap, final String idKop, final String id_bidangusaha, final String alamat, final String status, final String omzet, final String Loc, final String simpananpokok, final String jmlsimpananpokok, final String simpananwajib, final String jmlsimpananwajib, final String shutahunan) {
        //our custom volley request
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        pDialog.setMessage("Loading ...");
        showDialog();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_INPUT_KEUANGAN,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        hideDialog();

                            Intent intent = new Intent(Form1Kelurahan4.this,
                                    SlidePageF1.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "Succes Insert", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Data Error: " + error.getMessage());
                        //cek error timeout, noconnection dan network error
                        if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Check Your Connection",
                                    Toast.LENGTH_SHORT).show();}
                        hideDialog();
                    }
                }) {



            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_koperasi", idKop);
                params.put("bidang_usaha", id_bidangusaha);
                params.put("alamat_usaha", alamat);
                params.put("status_usaha", status);
                params.put("omzet", omzet);
                params.put("location", Loc);
                params.put("simp_pokok", simpananpokok);
                params.put("jml_simp_pokok", jmlsimpananpokok);
                params.put("simp_wajib", simpananwajib);
                params.put("jml_simp_wajib", jmlsimpananwajib);
                params.put("shu_tahunan", shutahunan);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    /*
* The method is taking Bitmap as an argument
* then it will return the byte[] array for the given bitmap
* and we will send this array to the server
* here we are using PNG Compression with 80% quality
* you can give quality between 0 to 100
* 0 means worse quality
* 100 means best quality
* */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /*private void checkUpload(final String idKop, final String id_bidangusaha, final String alamat, final String status, final String omzet, final String simpananpokok, final String jmlsimpananpokok, final String simpananwajib, final String jmlsimpananwajib, final String shutahunan) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_KEUANGAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // ngecek node error dari api
                    //if (jObj.getString("status")==success) {

                    //String msg = jObj.getString("msg");
                   //Toast.makeText(getApplicationContext(), idkop, Toast.LENGTH_LONG).show();

                       JSONObject jObj1 = jObj.getString("data");
                        String idkop = jObj1.getString("id_koperasi");
                        String tanggal = jObj1.getString("tgl_kunjungan");
                        String status = jObj1.getString("status_keaktifan");
                        String rapat = jObj1.getString("rapat_anggota");
                        String jumlah = jObj1.getString("jml_anggota");
                        String dateinput = jObj1.getString("create_date");
                        String namainput = jObj1.getString("create_by");

                        SharedPreferences.Editor editor = form1.edit();
                        editor.putString("idSes", idkop);
                        editor.putString("tglSes", tanggal);
                        editor.putString("keaktifanSes", status);
                        editor.putString("rapatSes", rapat);
                        editor.putString("jumlahSes", jumlah);
                        editor.putString("createSes", dateinput);
                        editor.putString("namaSes", namainput);
                        editor.commit();

                    //jika sudah masuk ke mainactivity
                    Intent intent = new Intent(Form1Kelurahan4.this,
                            SlidePageF1.class);
                    startActivity(intent);
                    finish();
                    //} else {
                    //terjadi error dan tampilkan pesan error dari API
                    //   Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                    // }
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
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection",
                            Toast.LENGTH_SHORT).show();}
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_koperasi", idKop);
                params.put("bidang_usaha", id_bidangusaha);
                params.put("alamat_usaha", alamat);
                params.put("status_usaha", status);
                params.put("omzet", omzet);
                params.put("simp_pokok", simpananpokok);
                params.put("jml_simp_pokok", jmlsimpananpokok);
                params.put("simp_wajib", simpananwajib);
                params.put("jml_simp_wajib", jmlsimpananwajib);
                params.put("shu_tahunan", shutahunan);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }*/

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<bidang_usaha> usahaList = new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("name");
                        usahaList.add(new bidang_usaha("", "-Select-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            usahaList.add(new bidang_usaha(jsonObject1.getString("unit_usaha_koperasi"), jsonObject1.getString("unit_usaha_koperasi")));
                        }
                        ArrayAdapter<bidang_usaha> adapter = new ArrayAdapter<bidang_usaha>(Form1Kelurahan4.this, android.R.layout.simple_spinner_dropdown_item, usahaList);
                        spinner.setAdapter(adapter);
                    }
                    ArrayAdapter<bidang_usaha> adapter = new ArrayAdapter<bidang_usaha>(Form1Kelurahan4.this, android.R.layout.simple_spinner_dropdown_item, usahaList);
                    spinner.setAdapter(adapter);
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Data Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection",
                            Toast.LENGTH_SHORT).show();}
                hideDialog();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
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
