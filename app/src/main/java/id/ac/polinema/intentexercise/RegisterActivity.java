package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

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
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm password";
    public static final String HOME_PAGE = "home page";
    public static final String ABOUT_YOU = "about you";

    public void handleOke(View view){

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

}
