package com.bhargo.user.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.bhargo.user.R;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.ZoomableImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CertificateDisplayActivity extends BaseActivity {

    private static final String TAG = "CertificateDisplayActiv";
    ZoomableImageView pdf_image;
    int pageIndex;
    String strTopicName, strFileName, strFilepath;
    SessionManager sessionManager;
    Context context;
    ImproveHelper improveHelper;
    private ParcelFileDescriptor parcelFileDescriptor;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_display);

        context = CertificateDisplayActivity.this;
        improveHelper = new ImproveHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strTopicName = extras.getString("TopicName");
            strFileName = extras.getString("FileName");
        }

        initViews(context);

        try {
            pageIndex = 0;
            openRenderer(getApplicationContext(), strFileName);
            showPage(pageIndex);
            improveHelper.dismissProgressDialog();
        } catch (IOException e) {
            improveHelper.dismissProgressDialog();
            Log.d(TAG, "onCreatePDFException: " + e.toString());
        }

    }

    public void initViews(Context context) {
        try {
            sessionManager = new SessionManager(context);
            improveHelper.showProgressDialog("Please wait...");
            initializeActionBar();
            title.setText(getResources().getString(R.string.bhargo_certificate));
            enableBackNavigation(true);
            iv_circle_appIcon.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);
            pdf_image = findViewById(R.id.pdf_image);
//            strFilepath = Environment.getExternalStorageDirectory() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName + "/" + "BhargoCertificate" + "/" + strFileName;
            strFilepath = "Improve_User" + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName + "/" + "BhargoCertificate" + "/" + strFileName;
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openRenderer(Context context, String FILENAME) throws IOException {
        try {
            File file = new File(getCacheDir(), FILENAME);
//        File file = new File(Environment.getExternalStorageDirectory() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName, FILENAME);

            FileInputStream fileInputStream = null;
            FileOutputStream output = null;
            try {
//                fileInputStream = new FileInputStream(strFilepath);
                File cDir = context.getApplicationContext().getExternalFilesDir(strFilepath); //
                fileInputStream = new FileInputStream(cDir.getAbsolutePath());
                output = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = fileInputStream.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            fileInputStream.close();
            output.close();
      /*  ParcelFileDescriptor mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
// This is the PdfRenderer we use to render the PDF.
        PdfRenderer renderer = null;
        if (mFileDescriptor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                renderer = new PdfRenderer(mFileDescriptor);
                Bitmap bitmap;
                final int pageCount = renderer.getPageCount();
                for (int i = 0; i < pageCount; i++) {
                    PdfRenderer.Page page = renderer.openPage(i);
// say we render for showing on the screen
                    bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//                    Document document = new Document();
//                    document.open();
//                    document.add(bitmap);
                    imageViewPdf.setImageBitmap(bitmap);
// do stuff with the bitmap
// close the page
                    page.close();
                }
// close the renderer
                renderer.close();
            }
        }*/


            parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            // This is the PdfRenderer we use to render the PDF.
//        if (parcelFileDescriptor != null) {
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openRenderer", e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPage(int index) {
        try {
            if (pdfRenderer.getPageCount() <= index) {
                return;
            }
            // Make sure to close the current page before opening another one.
            if (null != currentPage) {
                currentPage.close();
            }
            // Use `openPage` to open a specific page in PDF.
            currentPage = pdfRenderer.openPage(index);
            // Important: the destination bitmap must be ARGB (not RGB).
            Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                    Bitmap.Config.ARGB_8888);
            // Here, we render the page onto the Bitmap.
            // To render a portion of the page, use the second and third parameter. Pass nulls to get
            // the default result.
            // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            // We are ready to show the Bitmap to user.
            pdf_image.setImageBitmap(bitmap);
//        updateUi();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "showPage", e);
        }
    }
}
