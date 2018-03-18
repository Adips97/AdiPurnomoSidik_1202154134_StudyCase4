package android.example.com.adipurnomosidik_1202154134_studycase4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tampilNama(View view) {
        Intent i = new Intent(getApplicationContext(),TampilkanNama.class);
        startActivity(i);
    }

    public void tampilGambar(View view) {
        Intent intent = new Intent(getApplicationContext(),CariGambar.class);
        startActivity(intent);
    }
}