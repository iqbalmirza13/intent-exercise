package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private TextView fullnameText;
    private TextView emailText;
    private TextView homepageText;
    private TextView aboutyouText;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullnameText=findViewById(R.id.label_fullname);
        emailText=findViewById(R.id.label_email);
        homepageText=findViewById(R.id.label_homepage);
        aboutyouText=findViewById(R.id.label_about);
        avatar=findViewById(R.id.image_profile);

        Intent extras = getIntent();
        if  (extras != null) {
            //TODO: display value here
            String fullname=extras.getStringExtra(RegisterActivity.FULLNAME_KEY);
            String email=extras.getStringExtra(RegisterActivity.EMAIL_KEY);
            String homepage=extras.getStringExtra(RegisterActivity.HOME_PAGE);
            String about=extras.getStringExtra(RegisterActivity.ABOUT_YOU);
            String image=extras.getStringExtra(RegisterActivity.IMG_KEY);
            fullnameText.setText(fullname);
            emailText.setText(email);
            homepageText.setText(homepage);
            aboutyouText.setText(about);
            try {
                Bitmap imgbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(image));
                avatar.setImageBitmap(imgbitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
