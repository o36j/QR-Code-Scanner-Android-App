package com.example.qrcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;



public class MainActivity2 extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);


        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        Button btnGenerateQR = findViewById(R.id.btnGenerateQR);
        btnGenerateQR.setOnClickListener(v -> generateQRCode());


        ImageButton btndownload = findViewById(R.id.btndownload);
        btndownload.setOnClickListener(v -> saveImageToGallery());

        ImageButton btnshare = findViewById(R.id.btnshare);
        btnshare.setOnClickListener(v -> shareImage());


    }

    private void generateQRCode() {
        String content = editText.getText().toString().trim();

        if (!content.isEmpty()) {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            try {
                Bitmap bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageToGallery() {
        // Get the drawable from the ImageView
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof BitmapDrawable) {
            // Get the Bitmap from the drawable
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            // Save the Bitmap to the device's gallery
            MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    "QR_Code",
                    "Generated QR Code"
            );

            // Display a toast indicating successful save
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } else {
            // Handle the case where the drawable is not a BitmapDrawable
            Toast.makeText(this, "Unable to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareImage() {
        // Get the drawable from the ImageView
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof BitmapDrawable) {
            // Get the Bitmap from the drawable
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            // Create a share intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");

            // Save the Bitmap to a temporary file
            String path = MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    "QR_Code",
                    "Generated QR Code"
            );

            // Get the URI of the saved image
            Uri imageUri = Uri.parse(path);

            // Set the URI as the content of the share intent
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            // Launch the share activity
            startActivity(Intent.createChooser(shareIntent, "Share QR Code"));
        } else {
            // Handle the case where the drawable is not a BitmapDrawable
            Toast.makeText(this, "Unable to share image", Toast.LENGTH_SHORT).show();
        }
    }


}
