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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.project.rezasaputra.koprasi.Activity.helper.AppConfig;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Form2Kelurahan2 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    private SharedPreferences pref_idPerkembangan;
    private SharedPreferences pref;
    private SharedPreferences getLoc;


    private Button btnCapture;
    private ImageView imageView;
    private ProgressDialog pDialog;
    private Button btnNext;
    private EditText dt_sp, bb_sp, dt_sw, bb_sw, dt_ss, bb_ss, dt_pu, bb_pu, dt_hu, bb_hu, dt_cd, bb_cd, dt_shu, bb_shu, et_masalah;


    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2_kelurahan2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCapture = (Button) findViewById(R.id.btnCapture);
        imageView = (ImageView) findViewById(R.id.ImageView);


        getLoc = getSharedPreferences("location", MODE_PRIVATE);
        pref_idPerkembangan = getSharedPreferences("idper", MODE_PRIVATE);
        pref = getSharedPreferences("data", MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // inisialisasi variable
        btnCapture = (Button) findViewById(R.id.btnCapture);
        imageView = (ImageView) findViewById(R.id.ImageView);
        btnNext = (Button) findViewById(R.id.btnNext);
        dt_sp = (EditText) findViewById(R.id.dt_sp);
        bb_sp = (EditText) findViewById(R.id.bb_sp);
        dt_sw = (EditText) findViewById(R.id.dt_sw);
        bb_sw = (EditText) findViewById(R.id.bb_sw);
        dt_ss = (EditText) findViewById(R.id.dt_ss);
        bb_ss = (EditText) findViewById(R.id.bb_ss);
        dt_pu = (EditText) findViewById(R.id.dt_pu);
        bb_pu = (EditText) findViewById(R.id.bb_pu);
        dt_hu = (EditText) findViewById(R.id.dt_hu);
        bb_hu = (EditText) findViewById(R.id.bb_hu);
        dt_cd = (EditText) findViewById(R.id.dt_cd);
        bb_cd = (EditText) findViewById(R.id.bb_cd);
        dt_shu = (EditText) findViewById(R.id.dt_shu);
        bb_shu = (EditText) findViewById(R.id.bb_shu);
        et_masalah = (EditText) findViewById(R.id.et_masalah);

        dt_sp.addTextChangedListener(dtsp());
        bb_sp.addTextChangedListener(bbsp());
        dt_sw.addTextChangedListener(dtsw());
        bb_sw.addTextChangedListener(bbsw());
        dt_ss.addTextChangedListener(dtss());
        bb_ss.addTextChangedListener(bbss());
        dt_pu.addTextChangedListener(dtpu());
        bb_pu.addTextChangedListener(bbpu());
        dt_hu.addTextChangedListener(dthu());
        bb_hu.addTextChangedListener(bbhu());
        dt_cd.addTextChangedListener(dtcd());
        bb_cd.addTextChangedListener(bbcd());
        dt_shu.addTextChangedListener(dtshu());
        bb_shu.addTextChangedListener(bbshu());


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Form2Kelurahan2.this,Form2Kelurahan1.class);
        startActivity(setIntent);
    }

    //currency format
    private TextWatcher dtsp() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_sp.removeTextChangedListener(this);

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
                    dt_sp.setText(formattedString);
                    dt_sp.setSelection(dt_sp.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_sp.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbsp() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_sp.removeTextChangedListener(this);

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
                    bb_sp.setText(formattedString);
                    bb_sp.setSelection(bb_sp.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_sp.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher dtsw() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_sw.removeTextChangedListener(this);

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
                    dt_sw.setText(formattedString);
                    dt_sw.setSelection(dt_sw.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_sw.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbsw() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_sw.removeTextChangedListener(this);

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
                    bb_sw.setText(formattedString);
                    bb_sw.setSelection(bb_sw.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_sw.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher dtss() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_ss.removeTextChangedListener(this);

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
                    dt_ss.setText(formattedString);
                    dt_ss.setSelection(dt_ss.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_ss.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbss() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_ss.removeTextChangedListener(this);

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
                    bb_ss.setText(formattedString);
                    bb_ss.setSelection(bb_ss.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_ss.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher dtpu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_pu.removeTextChangedListener(this);

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
                    dt_pu.setText(formattedString);
                    dt_pu.setSelection(dt_pu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_pu.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbpu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_pu.removeTextChangedListener(this);

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
                    bb_pu.setText(formattedString);
                    bb_pu.setSelection(bb_pu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_pu.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher dthu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_hu.removeTextChangedListener(this);

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
                    dt_hu.setText(formattedString);
                    dt_hu.setSelection(dt_hu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_hu.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbhu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_hu.removeTextChangedListener(this);

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
                    bb_hu.setText(formattedString);
                    bb_hu.setSelection(bb_hu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_hu.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher dtcd() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_cd.removeTextChangedListener(this);

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
                    dt_cd.setText(formattedString);
                    dt_cd.setSelection(dt_cd.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_cd.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbcd() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_cd.removeTextChangedListener(this);

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
                    bb_cd.setText(formattedString);
                    bb_cd.setSelection(bb_cd.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_cd.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher dtshu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dt_shu.removeTextChangedListener(this);

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
                    dt_shu.setText(formattedString);
                    dt_shu.setSelection(dt_shu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dt_shu.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbshu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bb_shu.removeTextChangedListener(this);

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
                    bb_shu.setText(formattedString);
                    bb_shu.setSelection(bb_shu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bb_shu.addTextChangedListener(this);
            }
        };
    }

    //currency format

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
            imageView.setImageBitmap(bitmap);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //mengambil data daari editext
                    String dtsp = dt_sp.getText().toString().trim();
                    String bbsp = bb_sp.getText().toString().trim();
                    String dtsw = dt_sw.getText().toString().trim();
                    String bbsw = bb_sw.getText().toString().trim();
                    String dtss = dt_ss.getText().toString().trim();
                    String bbss = bb_ss.getText().toString().trim();
                    String dtpu = dt_pu.getText().toString().trim();
                    String bbpu = bb_pu.getText().toString().trim();
                    String dthu = dt_hu.getText().toString().trim();
                    String bbhu = bb_hu.getText().toString().trim();
                    String dtcd = dt_cd.getText().toString().trim();
                    String bbcd = bb_cd.getText().toString().trim();
                    String dtshu = dt_shu.getText().toString().trim();
                    String bbshu = bb_shu.getText().toString().trim();
                    String etmasalah = et_masalah.getText().toString().trim();



                    final String Loc = getLoc.getString("LattitudeandLongti","");
                    final String idPer = pref_idPerkembangan.getString("id_perkembangan", "");
                    final String idUser = pref.getString("user_id","");


                    //mengecek apakah inputan kosong
                    //if (!idUser.isEmpty() && !idPer.isEmpty() && !Loc.isEmpty() && !dtsp.isEmpty() && !bbsp.isEmpty() && !dtsw.isEmpty() && !bbsw.isEmpty() && !dtss.isEmpty() && !bbss.isEmpty() && !dtpu.isEmpty() && !bbpu.isEmpty() && !dthu.isEmpty() && !bbhu.isEmpty() && !dtcd.isEmpty() && !bbcd.isEmpty() && !dtshu.isEmpty() && !bbshu.isEmpty() && !etmasalah.isEmpty()){
                        // input data
                        uploadBitmap(bitmap, idUser, idPer, Loc, dtsp, bbsp, dtsw, bbsw, dtss, bbss, dtpu, bbpu, dthu, bbhu, dtcd, bbcd, dtshu, bbshu, etmasalah);
                        //Toast.makeText(getApplicationContext(), "Location :" + Loc , Toast.LENGTH_LONG).show(); // display the current state for switch's
                    //} else {
                        // jika inputan kosong tampilkan pesan
                       //Toast.makeText(getApplicationContext(),
                                //"harap isi dengan benar", Toast.LENGTH_LONG)
                               // .show();
                    //}

                }
            });

        }else{

            Toast.makeText(getApplicationContext(),"Mohon Dicek kembali", Toast.LENGTH_LONG).show(); // display the current state for switch's
            Intent intent = new Intent(Form2Kelurahan2.this,
                    Form2Kelurahan2.class);
            startActivity(intent);
            finish();
        }
    }

    private void uploadBitmap(final Bitmap bitmap, final String idUser, final String idPer, final String Loc, final String dtsp, final String bbsp, final String dtsw, final String bbsw, final String dtss, final String bbss, final String dtpu, final String bbpu, final String dthu, final String bbhu, final String dtcd, final String bbcd, final String dtshu, final String bbshu, final String etmasalah) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_INPUT_PERKEMBANGAN_KEUANGAN,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        hideDialog();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Form2Kelurahan2.this,
                                    MainKelurahan.class);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG, "Data Error: " + error.getMessage());
                        //cek error timeout, noconnection dan network error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Check Your Connection",
                                    Toast.LENGTH_SHORT).show();
                        }
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
                params.put("id_perkembangan", idPer);
                params.put("jml_simpok_awal", dtsp);
                params.put("jml_simpok_berjalan", bbsp);
                params.put("jml_simwaj_awal", dtsw);
                params.put("jml_simwaj_berjalan", bbsw);
                params.put("jml_sim_srela_awal", dtss);
                params.put("jml_sim_srela_berjalan", bbss);
                params.put("piutang_awal", dtpu);
                params.put("piutang_berjalan", bbpu);
                params.put("hutang_awal", dthu);
                params.put("hutang_berjalan", bbhu);
                params.put("cadangan_awal", dtcd);
                params.put("cadangan_berjalan", bbcd);
                params.put("shu_awal", dtshu);
                params.put("shu_berjalan", bbshu);
                params.put("permasalahan", etmasalah);
                params.put("location", Loc);
                params.put("user_id", idUser);
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

    /* private void checkUpload(final String idUser, final String idPer, final String dtsp, final String bbsp, final String dtsw, final String bbsw, final String dtss, final String bbss, final String dtpu, final String bbpu, final String dthu, final String bbhu, final String dtcd, final String bbcd, final String dtshu, final String bbshu, final String etmasalah) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_PERKEMBANGAN_KEUANGAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //save id kelembagaan

                    // ngecek node error dari api
                    if (!error) {
                        // user berhasil login

                        //jika sudah masuk ke mainactivity
                        Intent intent = new Intent(Form2Kelurahan2.this,
                                MainKelurahan.class);
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
                params.put("id_perkembangan", idPer);
                params.put("jml_simpok_awal", dtsp);
                params.put("jml_simpok_berjalan", bbsp);
                params.put("jml_simwaj_awal", dtsw);
                params.put("jml_simwaj_berjalan", bbsw);
                params.put("jml_sim_srela_awal", dtss);
                params.put("jml_sim_srela_berjalan", bbss);
                params.put("piutang_awal", dtpu);
                params.put("piutang_berjalan", bbpu);
                params.put("hutang_awal", dthu);
                params.put("hutang_berjalan", bbhu);
                params.put("cadangan_awal", dtcd);
                params.put("cadangan_berjalan", bbcd);
                params.put("shu_awal", dtshu);
                params.put("shu_berjalan", bbshu);
                params.put("permasalahan", etmasalah);
                params.put("user_id", idUser);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }*/

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
