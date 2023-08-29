package com.bhargo.user.camera;

import android.os.Environment;

import java.io.File;

public interface AppConstants {

    public final String KEY_CHAT_IMAGE_PATH = "imagePath";
    public final String KEY_CHAT_IMAGE_CAPTION = "imageCaption";
    public final String KEY_CREATE_sample = "create_sample";
    public final String SHOW_CAPTION = "showCaption";
    public static final String IMAGE_SENT_FILE_DIRECTORY = "ImproveUser";
    public static File sample_DIRCTORY = new File(Environment.getExternalStorageDirectory(), "ImproveUser");
    public static File sample_IMAGE_SENT_DIRECTORY = new File(Environment.getExternalStorageDirectory(), IMAGE_SENT_FILE_DIRECTORY);

}
