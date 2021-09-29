package br.unip.calculadoraDeGorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    // Constantes usadas ao se salvar pares salvar/restaurar estado do app.
    private static final String CONTA = "CONTA";
    private static final String PERCENTUAL = "PERCENTUAL";

    // Atributos que armazenam os valores que devem ser mantidos quando o app reiniciar:
    private double conta;
    private double percentual;

    // Armazena as referências aos componentes da interface gráfica:
    private EditText contaEditText;
    private EditText ngorjeta5EditText;
    private EditText ngorjeta10EditText;
    private EditText ngorjeta15EditText;
    private SeekBar percentualSeekBar;
    private EditText percentualEditText;
    private EditText gorjetaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obter referências aos componentes (views) da tela (activity)
        contaEditText = (EditText) findViewById(R.id.contaEditText) ;
        ngorjeta5EditText = (EditText) findViewById(R.id.ngorjeta5EditText);
        ngorjeta10EditText = (EditText) findViewById(R.id.ngorjeta10EditText);
        ngorjeta15EditText = (EditText) findViewById(R.id.ngorjeta15EditText);
        percentualSeekBar = (SeekBar) findViewById(R.id.percentualSeekBar);
        percentualEditText = (EditText) findViewById(R.id.percentualEditText);
        gorjetaEditText = (EditText) findViewById(R.id.gorjetaEditText);

        // Crio os ouvintes de eventos para as views interativas
        contaEditText.addTextChangedListener(ouvinteContaEditText);
        percentualSeekBar.setOnSeekBarChangeListener(ouvintePercenturalSeekBar);

        // Verifica se o aplicativo acabou de ser iniciado ou se está sendo restaurado
        if(savedInstanceState == null) {
            conta = 0;
            percentual = 7;
        }
        else{
            // O aplicativo está sendo restaurado da memória, não está sendo executado a
            // partir do zero. Assim, os valores da conta e percentual são restaurados.
            conta = savedInstanceState.getDouble(CONTA);
            percentual = savedInstanceState.getDouble(PERCENTUAL);
        }
        // Atualiza as views com os valores atualizados.
        contaEditText.setText(String.format("%.2f", conta));
        percentualSeekBar.setProgress((int) percentual );
    }

    // Atualiza o valor das gorjetas padrão
    private void atualizaGorjetas(){
        double[] gorjetas = Calculadora.gorjeta(conta);
        ngorjeta5EditText.setText(String.format("%.2f", gorjetas[0]));
        ngorjeta10EditText.setText(String.format("%.2f", gorjetas[1]));
        ngorjeta15EditText.setText(String.format("%.2f", gorjetas[2]));
    }

    // Atualiza o valor da gorjeta personalizada
    private void atualizaGorjetaPersonalizada(){
        gorjetaEditText.setText(String.format("%.2f", Calculadora.gorjeta(conta, percentual)));
    }

    // Define o objeto ouvinte de mudança de texto do contaEditText:
    private TextWatcher ouvinteContaEditText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Este método deve ser sobrescrito mas não é usado neste aplicativo
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try{
                conta = Double.parseDouble(contaEditText.getText().toString());
            }
            catch (NumberFormatException e) {
                conta = 0;
            }
            atualizaGorjetas();
            atualizaGorjetaPersonalizada();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Este método deve ser sobrescrito mas não é usado neste aplicativo
        }
    };

    // Define o objeto ouvinte de mudança no percentualSeekBar:
    private SeekBar.OnSeekBarChangeListener ouvintePercenturalSeekBar =
            new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            percentual = (double) percentualSeekBar.getProgress();
            percentualEditText.setText(String.format("%.1f", percentual));
            atualizaGorjetaPersonalizada();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Este método deve ser sobrescrito mas não é usado neste aplicativo
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Este método deve ser sobrescrito mas não é usado neste aplicativo
        }
    };

    // Este método é chamado quando o aplicativo é interrompido. Nele, criamos nossa lógica
    // para armazenar as informações que devem ser recuperadas quando o aplicativo é reiniciado.
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState){
     super.onSaveInstanceState(outState, outPersistentState);
     outState.putDouble(CONTA, conta);
     outState.putDouble(PERCENTUAL, percentual);

    }
}