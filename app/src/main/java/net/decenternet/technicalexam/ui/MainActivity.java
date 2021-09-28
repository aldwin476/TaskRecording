package net.decenternet.technicalexam.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.ui.tasks.TasksActivity;

public class MainActivity extends AppCompatActivity {
    /**
     * Tasks
     * 1. Change the text "Put your favorite quote here" with your own quote.
     * 2. Set any image that best illustrate the quote to ivBrandingLogo.
     * 3. Display this screen for 3 seconds, then redirect to TasksActivity and close this MainActivity.
     */
    private ImageView ivBrandingLogo;
    public static Boolean theCatalyst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivBrandingLogo = findViewById(R.id.ivBrandingLogo);
        Glide.with(this).load(R.drawable.img_bg).into(ivBrandingLogo);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

}