package com.project.rezasaputra.koprasi.Activity.helper;

/**
 * Created by Muhammad on 4/23/2018.
 */

public class AppConfig {
    public static String url= "https://koperasidev.gobisnis.online/apigw";
    public static String data= "https://koperasidev.gobisnis.online/apigw/koperasi";
    public static String kelembagaan ="https://koperasidev.gobisnis.online/apigw/kelembagaan";
    public static String pengawas ="https://koperasidev.gobisnis.online/apigw/pengawas";
    public static String pengurus ="https://koperasidev.gobisnis.online/apigw/pengurus";
    public static String keuangan ="https://koperasidev.gobisnis.online/apigw/bidang_usaha";
    public static String getdatakopapproval ="https://koperasidev.gobisnis.online/apigw/perkembangan";
    public static String perkembangan ="https://koperasidev.gobisnis.online/apigw/perkembangan";
    public static String perkembangan_usaha ="https://koperasidev.gobisnis.online/apigw/perkembangan";
    public static String perkembangan_keuangan ="https://koperasidev.gobisnis.online/apigw/perkembangan";
    public static String temuan ="https://koperasidev.gobisnis.online/apigw/temuan";
    public static String listkec ="http://koperasidev.gobisnis.online/apigw/approval";
    public static String processkec ="http://koperasidev.gobisnis.online/apigw/approval";
    public static String listpem ="http://koperasidev.gobisnis.online/apigw/approval";
    public static String processpem ="http://koperasidev.gobisnis.online/apigw/approval";
    public static String historykec ="http://koperasidev.gobisnis.online/apigw/history";



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
    public static String URL_INPUT_LEGALITAS_PERKEMBANGAN = perkembangan+"/insert_legalitas_lembaga";
    public static String URL_LEGALITAS_PERKEMBANGAN = perkembangan+"/legalitas_kelembagaan";
    public static String URL_INPUT_PERKEMBANGAN_USAHA = perkembangan_usaha+"/updatedata_perkembangan_usaha";
    public static String URL_INPUT_PERKEMBANGAN_KEUANGAN = perkembangan_keuangan+"/updatedata_perkembangan_keuangan";
    public static String URL_INPUT_TEMUAN = temuan+ "/insert_temuan";
    public static String URL_INPUT_LEGALITAS_TEMUAN = temuan+ "/insert_legalitas_temuan";
    public static String URL_GET_LIST_KEC = listkec+ "/list_approval";
    public static String URL_POST_PRO_KEC = processkec+ "/process_data";
    public static String URL_GET_LIST_PEM = listpem+ "/list_approval_pemkot";
    public static String URL_POST_PRO_PEM = processpem+ "/process_data_pemkot";
    public static String URL_POST_HIS_KEC = historykec+ "/list_history";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
}
