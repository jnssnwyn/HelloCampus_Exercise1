package com.example.compundinterestcalcu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText etPrincipal, etTotal, etRate, etTime;
    Spinner spinnerCalculate, spinnerCompound;
    Button btnCalculate, btnClear;
    TextView tvResult, tvSteps;

    DecimalFormat df = new DecimalFormat("#,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPrincipal = findViewById(R.id.etPrincipal);
        etTotal = findViewById(R.id.etTotal);
        etRate = findViewById(R.id.etRate);
        etTime = findViewById(R.id.etTime);
        spinnerCalculate = findViewById(R.id.spinnerCalculate);
        spinnerCompound = findViewById(R.id.spinnerCompound);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        tvResult = findViewById(R.id.tvResult);
        tvSteps = findViewById(R.id.tvSteps);


        String[] calculateOptions = {
                "Total P + I (A)",
                "Principal (P)",
                "Rate (R)",
                "Time (t)"
        };

        ArrayAdapter<String> calculateAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                calculateOptions
        );

        spinnerCalculate.setAdapter(calculateAdapter);


        String[] compoundOptions = {
                "Continuously",
                "Daily",
                "Weekly",
                "Biweekly",
                "Semimonthly",
                "Monthly",
                "Bimonthly",
                "Quarterly",
                "Semiannually",
                "Annually"
        };

        ArrayAdapter<String> compoundAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                compoundOptions
        );

        spinnerCompound.setAdapter(compoundAdapter);

        // Hide calculated field dynamically
        spinnerCalculate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                clearAll();

                etPrincipal.setVisibility(View.VISIBLE);
                etTotal.setVisibility(View.VISIBLE);
                etRate.setVisibility(View.VISIBLE);
                etTime.setVisibility(View.VISIBLE);

                String selected = spinnerCalculate.getSelectedItem().toString();

                if (selected.contains("Total")) {
                    etTotal.setVisibility(View.GONE);
                } else if (selected.contains("Principal")) {
                    etPrincipal.setVisibility(View.GONE);
                } else if (selected.contains("Rate")) {
                    etRate.setVisibility(View.GONE);
                } else if (selected.contains("Time")) {
                    etTime.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnCalculate.setOnClickListener(v -> calculate());
        btnClear.setOnClickListener(v -> clearAll());
    }

    private void calculate() {

        String selected = spinnerCalculate.getSelectedItem().toString();
        String compoundType = spinnerCompound.getSelectedItem().toString();

        // Clear previous errors
        etPrincipal.setError(null);
        etTotal.setError(null);
        etRate.setError(null);
        etTime.setError(null);

        boolean isContinuous = false;
        int n = 1;

        switch (compoundType) {
            case "Daily": n = 365; break;
            case "Weekly": n = 52; break;
            case "Biweekly": n = 26; break;
            case "Semimonthly": n = 24; break;
            case "Monthly": n = 12; break;
            case "Bimonthly": n = 6; break;
            case "Quarterly": n = 4; break;
            case "Semiannually": n = 2; break;
            case "Annually": n = 1; break;
            case "Continuously": isContinuous = true; break;
        }

        try {


            if (selected.contains("Total")) {

                if (etPrincipal.getText().toString().isEmpty()) {
                    etPrincipal.setError("Principal is required");
                    etPrincipal.requestFocus();
                    return;
                }

                if (etRate.getText().toString().isEmpty()) {
                    etRate.setError("Rate is required");
                    etRate.requestFocus();
                    return;
                }

                if (etTime.getText().toString().isEmpty()) {
                    etTime.setError("Time is required");
                    etTime.requestFocus();
                    return;
                }

                double P = Double.parseDouble(etPrincipal.getText().toString());
                double rPercent = Double.parseDouble(etRate.getText().toString());
                double r = rPercent / 100;
                double t = Double.parseDouble(etTime.getText().toString());

                double A;

                if (isContinuous) {
                    A = P * Math.exp(r * t);
                } else {
                    A = P * Math.pow((1 + r / n), n * t);
                }

                double interest = A - P;

                tvResult.setText("Answer:\nA = ₱" + df.format(A) +
                        "\nInterest = ₱" + df.format(interest));

                if (isContinuous) {
                    tvSteps.setText(
                            "Formula:\nA = Pe^(rt)\n\n" +
                                    "Substitute:\nA = " + P + "e^(" + r + "×" + t + ")\n\n" +
                                    "A = ₱" + df.format(A)
                    );
                } else {
                    tvSteps.setText(
                            "Formula:\nA = P(1 + r/n)^(nt)\n\n" +
                                    "Substitute:\nA = " + P + "(1 + " + r + "/" + n + ")^(" + n + "×" + t + ")\n\n" +
                                    "A = ₱" + df.format(A)
                    );
                }
            }


            else if (selected.contains("Principal")) {

                if (etTotal.getText().toString().isEmpty()) {
                    etTotal.setError("Total amount is required");
                    etTotal.requestFocus();
                    return;
                }

                if (etRate.getText().toString().isEmpty()) {
                    etRate.setError("Rate is required");
                    etRate.requestFocus();
                    return;
                }

                if (etTime.getText().toString().isEmpty()) {
                    etTime.setError("Time is required");
                    etTime.requestFocus();
                    return;
                }

                double A = Double.parseDouble(etTotal.getText().toString());
                double rPercent = Double.parseDouble(etRate.getText().toString());
                double r = rPercent / 100;
                double t = Double.parseDouble(etTime.getText().toString());

                double P;

                if (isContinuous) {
                    P = A / Math.exp(r * t);
                } else {
                    P = A / Math.pow((1 + r / n), n * t);
                }

                tvResult.setText("Answer:\nP = ₱" + df.format(P));
            }


            else if (selected.contains("Rate")) {

                if (etPrincipal.getText().toString().isEmpty()) {
                    etPrincipal.setError("Principal is required");
                    etPrincipal.requestFocus();
                    return;
                }

                if (etTotal.getText().toString().isEmpty()) {
                    etTotal.setError("Total amount is required");
                    etTotal.requestFocus();
                    return;
                }

                if (etTime.getText().toString().isEmpty()) {
                    etTime.setError("Time is required");
                    etTime.requestFocus();
                    return;
                }

                double P = Double.parseDouble(etPrincipal.getText().toString());
                double A = Double.parseDouble(etTotal.getText().toString());
                double t = Double.parseDouble(etTime.getText().toString());

                if (A <= P) {
                    etTotal.setError("Total must be greater than Principal");
                    etTotal.requestFocus();
                    return;
                }

                double rate;

                if (isContinuous) {
                    rate = Math.log(A / P) / t;
                } else {
                    rate = n * (Math.pow(A / P, 1.0 / (n * t)) - 1);
                }

                tvResult.setText("Answer:\nR = " + df.format(rate * 100) + "%");
            }


            else if (selected.contains("Time")) {

                if (etPrincipal.getText().toString().isEmpty()) {
                    etPrincipal.setError("Principal is required");
                    etPrincipal.requestFocus();
                    return;
                }

                if (etTotal.getText().toString().isEmpty()) {
                    etTotal.setError("Total amount is required");
                    etTotal.requestFocus();
                    return;
                }

                if (etRate.getText().toString().isEmpty()) {
                    etRate.setError("Rate is required");
                    etRate.requestFocus();
                    return;
                }

                double P = Double.parseDouble(etPrincipal.getText().toString());
                double A = Double.parseDouble(etTotal.getText().toString());
                double rPercent = Double.parseDouble(etRate.getText().toString());
                double r = rPercent / 100;

                if (A <= P) {
                    etTotal.setError("Total must be greater than Principal");
                    etTotal.requestFocus();
                    return;
                }

                double t;

                if (isContinuous) {
                    t = Math.log(A / P) / r;
                } else {
                    t = Math.log(A / P) /
                            (n * Math.log(1 + r / n));
                }

                tvResult.setText("Answer:\nt = " + df.format(t) + " years");
            }

        } catch (Exception e) {
            tvResult.setText("Invalid numeric input.");
        }
    }

    private void clearAll() {
        etPrincipal.setText("");
        etTotal.setText("");
        etRate.setText("");
        etTime.setText("");
        tvResult.setText("");
        tvSteps.setText("");
    }
}