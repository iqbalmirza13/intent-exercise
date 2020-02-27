package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    private EditText fullnameInput;
    @NotEmpty
    @Email
    private EditText emailInput;
    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText passwordInput;
    @NotEmpty
    @ConfirmPassword
    private EditText confirmpasswordInput;
    @NotEmpty
    private EditText homepageInput;
    @NotEmpty
    private EditText aboutyouInput;

    private ImageView avatarImage;
    private String image;

    private static final int FOTO_REQUEST_CODE = 1;

    private Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullnameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passwordInput = findViewById(R.id.text_password);
        confirmpasswordInput = findViewById(R.id.text_confirm_password);
        homepageInput = findViewById(R.id.text_homepage);
        aboutyouInput = findViewById(R.id.text_about);
        avatarImage = findViewById(R.id.image_profile);
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm password";
    public static final String HOME_PAGE = "home page";
    public static final String ABOUT_YOU = "about you";
    public static final String IMG_KEY = "image";

    public void handleOk(View view){validator.validate();
    }

    public void onValidationSucceeded(){
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirm_password = confirmpasswordInput.getText().toString();
        String home_page = homepageInput.getText().toString();
        String about_you = aboutyouInput.getText().toString();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(FULLNAME_KEY,fullname);
        intent.putExtra(EMAIL_KEY,email);
        intent.putExtra(PASSWORD,password);
        intent.putExtra(CONFIRM_PASSWORD,confirm_password);
        intent.putExtra(HOME_PAGE, home_page);
        intent.putExtra(ABOUT_YOU, about_you);
        intent.putExtra(IMG_KEY, image);
        startActivity(intent);
    }

    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

}
    public void handleImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, FOTO_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == FOTO_REQUEST_CODE) {
            if (data !=null){
                try {
                    image=data.getDataString();
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
