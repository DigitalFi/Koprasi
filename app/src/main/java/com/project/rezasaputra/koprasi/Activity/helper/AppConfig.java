package com.project.rezasaputra.koprasi.Activity.helper;

/**
 * Created by Muhammad on 4/23/2018.
 */

public class AppConfig {
    public static String url= "https://koperasi.digitalfatih.com/apigw";
    public static String data= "https://koperasi.digitalfatih.com/apigw/koperasi";
    public static String kelembagaan ="https://koperasi.digitalfatih.com/apigw/kelembagaan";
    public static String pengawas ="https://koperasi.digitalfatih.com/apigw/pengawas";
    public static String pengurus ="https://koperasi.digitalfatih.com/apigw/pengurus";
    public static String keuangan ="https://koperasi.digitalfatih.com/apigw/bidang_usaha";
    public static String getdatakopapproval ="https://koperasi.digitalfatih.com/apigw/perkembangan";
    public static String perkembangan ="https://koperasi.digitalfatih.com/apigw/perkembangan";
    public static String perkembangan_usaha ="https://koperasi.digitalfatih.com/apigw/perkembangan";
    public static String perkembangan_keuangan ="https://koperasi.digitalfatih.com/apigw/perkembangan";
    public static String temuan ="https://koperasi.digitalfatih.com/apigw/temuan";



    public static String URL_LOGIN = url+"/auth/";
    public static String URL_KOPRASI = data+"/getkoperasibyid";
    public static String URL_DETIL = data+"/getdtlkoperasibyid";
    public static String URL_INPUT = url+"/kelembagaan";
    public static String URL_INPUT_KELEMBAGAAN = kelembagaan+"/insert_kelembagaan";
    public static String URL_INPUT_PENGAWAS = pengawas+"/insert_pengawas";
    public static String URL_INPUT_PENGURUS = pengurus+"/insert_pengurus";
    public static String URL_GET_DATA_APPROVAL = getdatakopapproval+"/list_approval_perkembangan";
    public static String URL_INPUT_KEUANGAN = keuangan+"/insert_bidang_usaha";
    public static String URL_INPUT_PERKEMBANGAN_KELEMBAGAAN = perkembangan+"/insert_perkembangan_kelembagaan";
    public static String URL_INPUT_PERKEMBANGAN_USAHA = perkembangan_usaha+"/updatedata_perkembangan_usaha";
    public static String URL_INPUT_PERKEMBANGAN_KEUANGAN = perkembangan_keuangan+"/updatedata_perkembangan_keuangan";
    public static String URL_INPUT_TEMUAN = temuan+ "/insert_temuan";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
}
