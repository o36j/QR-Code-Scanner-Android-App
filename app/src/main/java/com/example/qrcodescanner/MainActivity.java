package com.example.qrcodescanner;


import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        button = findViewById(R.id.button);

        // Set click listener for the button to initiate QR code scanning
        button.setOnClickListener(v -> scanCode());
    }

    // Method to initiate QR code scanning
    private void scanCode() {
        // Configure options for the QR code scanner
        ScanOptions options = new ScanOptions();
        options.setPrompt("press volume up to turn on the flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);

        // Launch the QR code scanner using the configured options
        barlucher.launch(options);
    }

    // Activity result launcher for handling the result of QR code scanning
    ActivityResultLauncher<ScanOptions> barlucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            // Check if the scanned content is a valid URL
            if (isValidUrl(result.getContents())) {
                // If it's a URL, ask the user whether to open it in a browser or copy
                askUserForAction(result.getContents());
            } else {
                // If not a URL, show the result dialog with Copy option
                showResultDialog(result.getContents());
            }
        }
    });

    // Method to check if a string is a valid URL
    private boolean isValidUrl(String url) {
        // You can use a simple URL validation logic here
        return android.util.Patterns.WEB_URL.matcher(url).matches();
    }

    // Method to ask the user whether to open the URL in a browser or copy
    private void askUserForAction(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Action");
        builder.setMessage("Do you want to open the URL in a browser or copy it?");
        builder.setPositiveButton("Open", (dialog, which) -> {
            // Open the URL in a browser
            openUrlInBrowser(url);
            dialog.dismiss();
        });
        builder.setNegativeButton("Copy", (dialog, which) -> {
            // Copy the text to the clipboard
            copyToClipboard(url);
            dialog.dismiss();
        });
        builder.show();
    }

    // Method to open a URL in a browser
    private void openUrlInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    // Method to copy the text to the clipboard
    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Scan Result", text);
        clipboard.setPrimaryClip(clip);

        // Optionally, provide a toast or message indicating successful copy
        Toast.makeText(MainActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    // Method to show a dialog displaying the scanned result
    private void showResultDialog(String resultContents) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Result");
        builder.setMessage(resultContents);
        builder.setPositiveButton("Copy", (dialog, which) -> {
            // Copy the text to the clipboard
            copyToClipboard(resultContents);
            dialog.dismiss();
        });
        builder.setNegativeButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
