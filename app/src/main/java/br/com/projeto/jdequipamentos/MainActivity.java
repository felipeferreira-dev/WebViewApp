package br.com.projeto.jdequipamentos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adicionando a barra de carregamento visual da página ....................
        final ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        //Testando a conectividade da rede
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Toast.makeText(MainActivity.this, "Bem Vindo(a) a JDequipamentos!", Toast.LENGTH_SHORT).show();

        //Trazendo o site para dentro do app
        final WebView webView = findViewById(R.id.webview);
        webView.loadUrl("http://jdequipamentos.com.br/beta/index.html");
        webView.addJavascriptInterface(this, "Android");
        webView.setWebViewClient(new WebViewClient());

        //Adicionando a leitura do java no app
        webView.getSettings().setJavaScriptEnabled(true);

            //Carregando os métodos da barra de carregamento, quando a página inicia e quando finaliza.
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE); // mostra a progress
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.INVISIBLE); // esconde a progress
                }

            });
        } else {
            Toast.makeText(MainActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
            finish();
            System.exit(0);
        }

    }

    //Mudando a ação do botão 'voltar' para 'home'
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}