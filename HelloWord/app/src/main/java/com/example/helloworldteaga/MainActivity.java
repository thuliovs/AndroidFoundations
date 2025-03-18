package com.example.helloworldteaga;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Variavel para armazenar o número de cliques no botão
    private int clickCount=0;

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("texts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);

        // Define o layout da atividade como o arquivo XML especificado
        setContentView(R.layout.activity_main);

        // Vincula o TextView do layout ao codigo java
        TextView resultText = findViewById(R.id.result_text);
        TextView resultClickCount = findViewById(R.id.result_click_count);

        // Vincula o button do layout ao codigo java
        Button incrementButton = findViewById(R.id.increment_button);

        // Define um listener para o botão, que será ao clicar
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clickCount++;
                    //Carregar os textos do arquivo JSON
                    JSONObject obj = new JSONObject(loadJSONFromAsset());
                    String parText = obj.getString("parText"); // Texto para o número par
                    String impairText = obj.getString("impairText"); // Texto para o número impar
                    String clicksLabel = obj.getString("clicksLabel"); // Rótulo de cliques

                    // Atualiza o texto do TextView com base na paridade
                    if(clickCount % 2 == 0)
                        resultText.setText(parText); // Quando é par
                    else
                        resultText.setText(impairText); // Quando é impar

                    // Atualiza o número de cliques no TextView
                    resultClickCount.setText(clicksLabel + clickCount);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
