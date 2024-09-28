package com.example.trabalho;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etIdade, etDisciplina, etNota1, etNota2;
    private TextView tvErro, tvResumo;
    private Button btEnviar, btLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etIdade = findViewById(R.id.etIdade);
        etDisciplina = findViewById(R.id.etDisciplina);
        etNota1 = findViewById(R.id.etNota1);
        etNota2 = findViewById(R.id.etNota2);
        btEnviar = findViewById(R.id.btEnviar);
        btLimpar = findViewById(R.id.btLimpar);
        tvErro = findViewById(R.id.tvErro);
        tvResumo = findViewById(R.id.tvResumo);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (processarDados()) {
                    calcularMedia();
                }
            }
        });

        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });
    }

    private boolean processarDados() {
        String nome = etNome.getText().toString();
        String email = etEmail.getText().toString();
        String idadeStr = etIdade.getText().toString();
        String nota1Str = etNota1.getText().toString();
        String nota2Str = etNota2.getText().toString();

        if (TextUtils.isEmpty(nome)) {
            mostrarErro("O CAMPO NOME DEVE SER PREENCHIDO.");
            return false;
        }
        if (!isValidEmail(email)) {
            mostrarErro("E-MAIL INVÁLIDO.");
            return false;
        }
        if (!isNumeric(idadeStr) || Integer.parseInt(idadeStr) <= 0) {
            mostrarErro("IDADE INCORRETA.");
            return false;
        }
        if (!isNumeric(nota1Str) || !isNumeric(nota2Str) || !validaNotas(nota1Str, nota2Str)) {
            mostrarErro("SOMENTE NOTAS DE 0 e 10 SÃO VÁLIDAS.");
            return false;
        }
        tvErro.setVisibility(View.GONE);
        return true;
    }

    private void calcularMedia() {
        double nota1 = Double.parseDouble(etNota1.getText().toString());
        double nota2 = Double.parseDouble(etNota2.getText().toString());
        double media = (nota1 + nota2) / 2;

        String resumo = String.format("Nome: %s\nE-mail:" +
                        " %s\nIdade:" +
                        " %s\nDisciplina:" +
                        " %s\nNota 1° Bimestre:" +
                        " %.2f\nNota 2° Bimestre:" +
                        " %.2f\nMédia: %.2f",
                 etNome.getText().toString(),
                 etEmail.getText().toString(),
                 etIdade.getText().toString(),
                 etDisciplina.getText().toString(),
                nota1, nota2, media);

        tvResumo.setText(resumo);
        tvResumo.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Dados enviados!", Toast.LENGTH_SHORT).show();
    }

    private void mostrarErro(String mensagem) {
        tvErro.setText(mensagem);
        tvErro.setVisibility(View.VISIBLE);
        tvResumo.setVisibility(View.GONE);
    }

    private void limparCampos() {
        etNome.setText("");
        etEmail.setText("");
        etIdade.setText("");
        etDisciplina.setText("");
        etNota1.setText("");
        etNota2.setText("");
        tvErro.setVisibility(View.GONE);
        tvResumo.setVisibility(View.GONE);
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }

    private boolean validaNotas(String nota1Str, String nota2Str) {
        double nota1 = Double.parseDouble(nota1Str);
        double nota2 = Double.parseDouble(nota2Str);
        return (nota1 >= 0 && nota1 <= 10) && (nota2 >= 0 && nota2 <= 10);
    }
}
