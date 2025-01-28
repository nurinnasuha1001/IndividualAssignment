package com.example.individualassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText goldWeightEditText, goldValueEditText;
    private Spinner goldTypeSpinner;
    private TextView totalValueTextView, zakatPayableTextView, totalZakatTextView;

    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here
            int id = item.getItemId();

            if (id == R.id.action_share) {
                // Share the app link
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Zakat Calculator App: https://github.com/YourGithub");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                return true;
            } else if (id == R.id.action_about) {
                // Open AboutActivity
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }

        goldWeightEditText = findViewById(R.id.editTextGoldWeight);
        goldValueEditText = findViewById(R.id.editTextGoldValue);
        goldTypeSpinner = findViewById(R.id.spinnerGoldType);
        totalValueTextView = findViewById(R.id.textViewTotalValue);
        zakatPayableTextView = findViewById(R.id.textViewZakatPayable);
        totalZakatTextView = findViewById(R.id.textViewTotalZakat);

        Button calculateButton = findViewById(R.id.buttonCalculate);
        calculateButton.setOnClickListener(v -> calculateZakat());

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button aboutButton = findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
    }

    @SuppressLint("SetTextI18n")
    private void calculateZakat() {
        try {
            double weight = Double.parseDouble(goldWeightEditText.getText().toString());
            double value = Double.parseDouble(goldValueEditText.getText().toString());
            String type = goldTypeSpinner.getSelectedItem().toString();

            double uruf = type.equals("Keep") ? 85 : 200;
            double totalValue = weight * value;
            double zakatPayableValue = Math.max(0, (weight - uruf) * value);
            double totalZakat = zakatPayableValue * 0.025;

            totalValueTextView.setText("Total Gold Value: RM " + totalValue);
            zakatPayableTextView.setText("Zakat Payable: RM " + zakatPayableValue);
            totalZakatTextView.setText("Total Zakat: RM " + totalZakat);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}