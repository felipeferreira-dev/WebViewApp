package br.com.projeto.jdequipamentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import static android.view.KeyEvent.KEYCODE_BACK;


public class MotionScreen extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animacao);

//Testando a conectividade da rede
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        //Carregando o Motion Layout e chamando o MainActivity
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            Thread timerThread = new Thread() {

                @Override
                public void run() {

                    try {
                        sleep(3750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(MotionScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            };

            timerThread.start();


        }  else {
            Toast.makeText(MotionScreen.this, "Erro: Você está sem conexão de internet!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //Mudando a ação do botão 'voltar' para 'home'
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            moveTaskToBack(true);
            onAttachedToWindow();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}



