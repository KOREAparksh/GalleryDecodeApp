package com.f10company.galleydecodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.InputStream;

import static com.google.zxing.integration.android.IntentIntegrator.QR_CODE;

public class DecodeGalleryActivity extends AppCompatActivity {

    CropImageView cropImageView;
    Button choose,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_gallery);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView1);
        choose = (Button)findViewById(R.id.choose);
        exit = (Button)findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = cropImageView.getCroppedImage();
                /*
                imageView.setImageBitmap(b);
                //imageView.setRotation(rotation);
                Drawable d = imageView.getDrawable();

                decodeImage(((BitmapDrawable)d).getBitmap())
                  */;
                  decodeImage(b);
            }
        });

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri imageUri = data.getData();
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    cropImageView.setImageBitmap(img);

                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    boolean decodeImage(Bitmap bitmap) {
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();

        String resultString;

        try {
            Result result1 = reader.decode(binaryBitmap);
            resultString = result1.toString();
            Toast.makeText(getApplicationContext(),"바코드 식별 완료 : "+resultString,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DecodeGalleryActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.INTENT_CODE_STRING, result1.getText());

            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        } catch (Exception e) {
            resultString = "ERROR";
            Toast.makeText(getApplicationContext(),"코드 식별 불가 : "+resultString,Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
