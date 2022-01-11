import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

public class baju {
    private int id;
    private String merek_baju = null;
    private String ukuran = null;
    private String kode_baju = null;
    private String harga = null;
    public Object conn;

    public baju(int inputId, String inputMerek_Baju, String inputUkuran, String inputKode_Baju, String inputHarga) {
        this.id = inputId;
        this.merek_baju = inputMerek_Baju;
        this.ukuran = inputUkuran;
        this.kode_baju = inputKode_Baju;
        this.harga = inputHarga;
    }



    public int getId(){
        return id;
    }

    public String getMerek_baju(){
        return merek_baju;
    }

    public String getUkuran(){
        return ukuran;
    }

    public String getKode_baju(){
        return kode_baju;
    }

    public String getHarga(){
        return harga;
    }


    public void setId(String text) {
    }


    public void setHarga() {
    }


    public void setHarga(String text) {
    }


    public void setHarga(double parseDouble) {
    }


    public void setMerek_baju(String text) {
    }


    public void setUkuran(String text) {
    }


    public void setKode_baju(String text) {
    }



}