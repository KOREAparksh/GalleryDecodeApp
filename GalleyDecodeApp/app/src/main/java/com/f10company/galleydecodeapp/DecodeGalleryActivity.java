package com.f10company.galleydecodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DecodeGalleryActivity extends AppCompatActivity {

    CropImageView cropImageView;
    Button choose, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_gallery);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView1);
        choose = (Button) findViewById(R.id.choose);
        exit = (Button) findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = cropImageView.getCroppedImage();

                decodeImage(b);
            }
        });

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    String imagePath = data.getData().getPath();
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);

                    cropImageView.setImageBitmap(img);
                    in.close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.deselect_photo, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
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
            Toast.makeText(getApplicationContext(), getString(R.string.decode_complete) + " : " + resultString, Toast.LENGTH_SHORT).show();

            SimpleDateFormat sdf = new SimpleDateFormat( "yy-MM-dd HH:mm:ss" , Locale.KOREA );
            String timeString = sdf.format(new Date(result1.getTimestamp()));
            Log.d("decode","코드 : "+result1.getText());
            Log.d("decode","코드포맷 : "+result1.getBarcodeFormat());
            Log.d("decode","타임스탬프 : "+timeString);


            Intent intent = new Intent(DecodeGalleryActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.INTENT_CODE_STRING, result1.getText());
            intent.putExtra(MainActivity.INTENT_CODE_FORMAT, result1.getBarcodeFormat().toString());
            intent.putExtra(MainActivity.INTENT_CODE_TIMESTAMP, timeString);




            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        } catch (Exception e) {
            resultString = "ERROR";
            Toast.makeText(getApplicationContext(), getString(R.string.decode_failed) + " : " + resultString, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

}
