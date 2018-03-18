package android.example.com.adipurnomosidik_1202154134_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CariGambar extends AppCompatActivity {

    //Deklarasi Komponen yang akan digunakan
    //EditText
    private EditText txtImageURL;
    //Button
    private Button btnImageLoad;
    //ImageView
    private ImageView lblImage;
    //ProgressDialog
    private ProgressDialog loading;
    //Penunjuk jika gambar sudah di load.
    private int ImageIsLoaded=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

        //Inisialisasi Komponen View
        txtImageURL=(EditText)findViewById(R.id.txtImgURL);
        btnImageLoad=(Button)findViewById(R.id.btnImgLoad);
        lblImage=(ImageView)findViewById(R.id.lblImg);


        //Aksi Klik pada Tombol
        btnImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Memanggil Method yang dapat mengupdate
                loadImage();
            }
        });

        //Cek apakah bundle berisikan sesuatu
        if(savedInstanceState!=null){
            //Cek apakah didalm bundle nilai kunci sebagai penunjuk data sudah di load sebelumnya
            if(savedInstanceState.getInt("IMAGE_IS_LOADED")!=0 && !savedInstanceState.getString("EXTRA_TEXT_URL").isEmpty()){
                txtImageURL.setText(""+savedInstanceState.getString("EXTRA_TEXT_URL"));
                loadImage();
            }
        }
    }

    /*
    * Method yang berguna untuk melakukan penyimpanan sesuatu pada package
    */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan untuk URL TEXT agar input tidak dilakukan kembali
        outState.putString("EXTRA_TEXT_URL",txtImageURL.getText().toString());
        //Simpan untuk respon pada savedinstancestate
        outState.putInt("IMAGE_IS_LOADED",ImageIsLoaded);
    }

    /*
    * Method yang digunakan untuk mengupdate tampilan / mengubah gambar dengan menggunakan asynctask url
    */
    private void loadImage(){

        //Mengambil nilai input (String)
        String ImgUrl = txtImageURL.getText().toString();
        //Aksi Asynctask untuk melakukan pencarian/load gambar dari internet
        new LoadImageTask().execute(ImgUrl);
    }

    /*
    * Class Asynctask
    */
    public class LoadImageTask extends AsyncTask<String, Integer, Bitmap> {

        /*
       * Pesan yang akan ditampilkan ketika method dijalankan
       */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading=new ProgressDialog(CariGambar.this);
            loading.setMessage("Waiting ...");
            loading.setMax(100);
            loading.incrementProgressBy(1);
            loading.show();
        }

        /*
        * Method yang digunakan saat eksekusi berlangsung 1x
        *   Tahap ini digunakan untuk mencari gambar dari internet berdasarkan alamat gambar valid dengan mengubahnya menjadi bitmap
        */
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e("doInBackground() - ", e.getMessage());
            }
            return bitmap;
        }

        /*
        * Method yang dilakukan saat proses berlangsung sesuai dengan besarnya request yang dijalankan
        *   Tahap ini digunakan untuk melakukan 'update UI' pada progress bar agar terlihat berjalan
        */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(1000);
                loading.setMessage("Fetching...");
                loading.incrementProgressBy(values[0]);
                loading.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        * Method yang dilakukan setalah proses berhasil di eksekusi
        */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            //Set ImageView
            lblImage.setImageBitmap(bitmap);
            //Parameter Sudah di load
            ImageIsLoaded=1;
            //Menghilangkan Loading(ProgressBar)
            loading.dismiss();
        }
    }
}
