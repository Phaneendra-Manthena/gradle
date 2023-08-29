package com.bhargo.user.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargo.user.R;
import com.bhargo.user.utils.ImproveHelper;


public class AlertsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        findViewById(R.id.buttonSuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSuccessDialog();
            }
        });
        findViewById(R.id.buttonWarning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarningDialog();
            }
        });
        findViewById(R.id.buttonError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showErrorDialog();
            }
        });


        findViewById(R.id.buttonRound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImproveHelper.alertDialogWithRoundShapeMaterialTheme(AlertsActivity.this, "Sometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.\nSometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.\nSometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.\nSometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.", "Yes",
                        "No", new ImproveHelper.IL() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });

        findViewById(R.id.buttonCut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImproveHelper.alertDialogWithCutShapeMaterialTheme(AlertsActivity.this, "Sometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.\nSometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.\nSometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.", "Yes",
                        "No", new ImproveHelper.IL() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });

        findViewById(R.id.buttonNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImproveHelper.alertDialogWithMaterialTheme(AlertsActivity.this, "Sometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.", "Yes",
                        "No", new ImproveHelper.IL() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });


    }

    private void showSuccessDialog(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (AlertsActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AlertsActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle))
                .setText("Success Title");
        ((TextView) view.findViewById(R.id.textMessage))
                .setText("Sometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.");
        ((TextView) view.findViewById(R.id.buttonAction))
                .setText("Ok");
        ((ImageView) view.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.icon_done);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(AlertsActivity.this,
                        "Success", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void showWarningDialog(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (AlertsActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AlertsActivity.this).inflate(
                R.layout.layout_warning_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle))
                .setText("Warning Title");
        ((TextView) view.findViewById(R.id.textMessage))
                .setText("Sometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.");
        ((TextView) view.findViewById(R.id.buttonYes))
                .setText(getResources().getString(R.string.yes));
        ((TextView) view.findViewById(R.id.buttonNo))
                .setText(getResources().getString(R.string.no));
        ((ImageView) view.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.ic_baseline_warning_24);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(AlertsActivity.this,
                        "Yes", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(AlertsActivity.this,
                        "No", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void showErrorDialog(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (AlertsActivity.this);
        View view = LayoutInflater.from(AlertsActivity.this).inflate(
                R.layout.layout_error_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle))
                .setText("Error Title");
        ((TextView) view.findViewById(R.id.textMessage))
                .setText("Sometimes in your application, if you wanted to ask the user about deciding between yes or no in response to any particular action taken by the user, by remaining in the same activity and without changing the screen, you can use Alert Dialog.");
        ((TextView) view.findViewById(R.id.buttonAction))
                .setText("Ok");
        ((ImageView) view.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.ic_baseline_error_outline_24);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(AlertsActivity.this,
                        "Error", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    /**/
}