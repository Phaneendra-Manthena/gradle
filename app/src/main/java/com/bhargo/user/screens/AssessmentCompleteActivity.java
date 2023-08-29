package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;

public class AssessmentCompleteActivity extends AppCompatActivity {

    CustomButton cbt_done;
    CustomTextView ctv_marksObtained, ctv_marksObtained_status;
    String marksObtained, marksObtained_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this, AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_complete);
//        setContentView(R.layout.activity_assessment_complete_copy);

        cbt_done = findViewById(R.id.cbt_done);
        ctv_marksObtained = findViewById(R.id.ctv_marksObtained);
        ctv_marksObtained_status = findViewById(R.id.ctv_marksObtained_status);

        Bundle extras = getIntent().getExtras();

        if (extras.getString("MarksObtained") != null &&
                !extras.getString("MarksObtained").isEmpty()) {
            marksObtained = extras.getString("MarksObtained");
            ctv_marksObtained.setText(marksObtained);
        } else {
            ctv_marksObtained.setVisibility(View.GONE);
        }

        if (extras.getString("MarksObtained_status") != null &&
                !extras.getString("MarksObtained_status").isEmpty()) {
            marksObtained_status = extras.getString("MarksObtained_status");
            ctv_marksObtained_status.setText(marksObtained_status);
        } else {
            ctv_marksObtained_status.setVisibility(View.GONE);
        }

        cbt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
