package com.bhargo.user.utils;

import static android.content.Context.POWER_SERVICE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_GENERATION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_NUMBER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDAR_EVENT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHART;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_UP_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CURRENCY;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_TABLE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_VIEWER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DECIMAL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_EMAIL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GRID_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LARGE_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LiveTracking;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_MAP;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_POST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PROGRESS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SECTION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBMIT_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TIME;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.AppConstants.Global_Controls_Variables;
import static com.bhargo.user.utils.AppConstants.Global_Single_Forms;
import static com.bhargo.user.utils.AppConstants.SP_MOBILE_NO;
import static com.bhargo.user.utils.AppConstants.SP_USER_DETAILS;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.ActionWithCondition_Bean;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.Java_Beans.OrderByEvents_Object;
import com.bhargo.user.Java_Beans.Variable_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.AutoCompletionControl;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.ChartControl;
import com.bhargo.user.controls.advanced.DataTableControl;
import com.bhargo.user.controls.advanced.DataViewer;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.LiveTracking;
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.ProgressControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.SectionControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.advanced.UserControl;
import com.bhargo.user.controls.data_controls.DataControl;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.AutoNumber;
import com.bhargo.user.controls.standard.CalendarEventControl;
import com.bhargo.user.controls.standard.Camera;
import com.bhargo.user.controls.standard.CheckList;
import com.bhargo.user.controls.standard.Checkbox;
import com.bhargo.user.controls.standard.CountDownTimerControl;
import com.bhargo.user.controls.standard.CountUpTimerControl;
import com.bhargo.user.controls.standard.Currency;
import com.bhargo.user.controls.standard.DecimalView;
import com.bhargo.user.controls.standard.DropDown;
import com.bhargo.user.controls.standard.DynamicLabel;
import com.bhargo.user.controls.standard.Email;
import com.bhargo.user.controls.standard.FileBrowsing;
import com.bhargo.user.controls.standard.Image;
import com.bhargo.user.controls.standard.LargeInput;
import com.bhargo.user.controls.standard.MapControl;
import com.bhargo.user.controls.standard.NumericInput;
import com.bhargo.user.controls.standard.Password;
import com.bhargo.user.controls.standard.Percentage;
import com.bhargo.user.controls.standard.Phone;
import com.bhargo.user.controls.standard.RadioGroupView;
import com.bhargo.user.controls.standard.Rating;
import com.bhargo.user.controls.standard.SignatureView;
import com.bhargo.user.controls.standard.SubmitButton;
import com.bhargo.user.controls.standard.TextInput;
import com.bhargo.user.controls.standard.Time;
import com.bhargo.user.controls.standard.UrlView;
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomCheckBox;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextInputEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.Callback;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.pojos.EditOrViewColumns;
import com.bhargo.user.pojos.PostSubLocationsModel;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.pojos.UserData;
import com.bhargo.user.pojos.UserPostDetails;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.screens.ViewDataActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hsalf.smilerating.SmileRating;

import net.dankito.richtexteditor.android.RichTextEditor;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import nk.bluefrog.library.utils.Helper;
import nk.mobileapps.spinnerlib.SearchableMultiSpinner;
import nk.mobileapps.spinnerlib.SearchableSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ImproveHelper {

    static final double pi = 3.14159265358979323846;
    private static final String TAG = "ImproveHelper";
    public static LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    public static LinearLayout.LayoutParams layout_params_match_parent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    public static double PI = 3.14159265;
    public static double TWOPI = 2 * PI;
    public static boolean fromMapExisting = false;
    static int position = 1;
    public ProgressDialog progressDialog;
    public Dialog dialog;
    Context context;
    ProgressBar progressBar;
    TextView msg;

    public ImproveHelper() {

    }

    public ImproveHelper(Context context) {
        this.context = context;
    }


    public static String getID(String id) {
        // create Clock Object
        Clock clock = Clock.systemDefaultZone();

        // get Instant Object of Clock object
        // in milliseconds using millis() method
        long milliseconds = clock.millis();
        return id + milliseconds;
    }

    public static String getTransactionIDWithYMD_HMS(String id) {
        Calendar cal = Calendar.getInstance();
        //cal.setTime(new Date());
        int yr = cal.get(Calendar.YEAR);
        String year = "" + (yr > 9 ? "" + yr : "0" + yr);
        String month = (cal.get(Calendar.MONTH) + 1) > 9 ? ""
                + (cal.get(Calendar.MONTH) + 1) : "0"
                + (cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) > 9 ? ""
                + cal.get(Calendar.DAY_OF_MONTH) : "0"
                + cal.get(Calendar.DAY_OF_MONTH);

        String hour = cal.get(Calendar.HOUR) > 9 ? "" + cal.get(Calendar.HOUR)
                : "0" + cal.get(Calendar.HOUR);
        String min = cal.get(Calendar.MINUTE) > 9 ? ""
                + cal.get(Calendar.MINUTE) : "0" + cal.get(Calendar.MINUTE);
        String sec = cal.get(Calendar.SECOND) > 9 ? ""
                + cal.get(Calendar.SECOND) : "0" + cal.get(Calendar.SECOND);
        String code26 = id + year + month + day + hour + min + sec;
        System.out.println("code12: " + code26);
        System.gc();
        return code26;
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });

    }
    public static String getOutputParamswithPathsInStr(String SuccessCaseDetails) {
        String outputStr="";
        try {
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            outputStr = String.valueOf(jobj.getJSONArray("OutputParameters"));
        } catch (JSONException e) {
            outputStr="";
        }
        return outputStr;
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        makeTextViewResizable(tv, 3, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public static void setTags(LinearLayout ll_add_labels) {
        for (int labels = 0; labels < ll_add_labels.getChildCount(); labels++) {

            View view = ll_add_labels.getChildAt(labels);

            view.setTag(labels);
        }
    }

    public static void showToastAlert(Context context, String message) {

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View layout = LayoutInflater.from(context).inflate(R.layout.message_toast_success, null);
        layout.setLayoutParams(ll_params);
        TextView text = layout.findViewById(R.id.text);
        text.setText(String.format("%s   ", message));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    /* AppConstants.GlobalObjects.getCurrentApp_ControlNames()*/

    public static void showToastRunOnUI(Activity activity, String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(activity.getApplicationContext(), message);
            }
        });
    }

    public static void showToast(Context context, String message) {

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View layout = LayoutInflater.from(context).inflate(R.layout.message_toast, null);
        layout.setLayoutParams(ll_params);
        TextView text = layout.findViewById(R.id.text);
        text.setText(String.format("%s   ", message));
        Toast toast = new Toast(context);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    public static String toLowerCaseWithUnderscore(String str) {

        str = str.replace(" ", "_");
        str = str.toLowerCase();

        return str;
    }

    public static String ColorToHex(int color) {
        int alpha = Color.alpha(color);
        int blue = Color.blue(color);
        int green = Color.green(color);
        int red = Color.red(color);

        String alphaHex = To00Hex(alpha);
        String blueHex = To00Hex(blue);
        String greenHex = To00Hex(green);
        String redHex = To00Hex(red);

        // hexBinary value: aabbggrr
        StringBuilder str = new StringBuilder("#");
        str.append(alphaHex);
        str.append(blueHex);
        str.append(greenHex);
        str.append(redHex);

        return str.toString();
    }

    private static String To00Hex(int value) {
        String hex = "00".concat(Integer.toHexString(value));
        return hex.substring(hex.length() - 2);
    }

    public static ArrayList<SpinnerData> getValueMappingData() {
        ArrayList<SpinnerData> apiArrayList = new ArrayList<>();

        SpinnerData spinnerData = new SpinnerData();
        spinnerData.setId("1");
        spinnerData.setName("Static");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("2");
        spinnerData.setName("Global");
        apiArrayList.add(spinnerData);

        return apiArrayList;
    }

    public static ArrayList<SpinnerData> getConditionRelationTypes() {
        ArrayList<SpinnerData> apiArrayList = new ArrayList<>();

        SpinnerData spinnerData = new SpinnerData();
        spinnerData.setId("1");
        spinnerData.setName("All conditions satisfied");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("2");
        spinnerData.setName("Atleast one condition satisfied");
        apiArrayList.add(spinnerData);

        return apiArrayList;
    }

    public static ArrayList<SpinnerData> getConditions() {
        ArrayList<SpinnerData> apiArrayList = new ArrayList<>();

        SpinnerData spinnerData = new SpinnerData();
        spinnerData.setId("Equals");
        spinnerData.setName("Equals");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("lessThan");
        spinnerData.setName("lessThan");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("GreaterThan");
        spinnerData.setName("GreaterThan");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("LessThanEqualsTo");
        spinnerData.setName("LessThanEqualsTo");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("GreaterThanEqualsTo");
        spinnerData.setName("GreaterThanEqualsTo");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("Contains");
        spinnerData.setName("Contains");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("StartsWith");
        spinnerData.setName("StartsWith");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("7");
        spinnerData.setName("StartsWith");
        apiArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("8");
        spinnerData.setName("StartsWith");

        return apiArrayList;
    }

    public static ArrayList<SpinnerData> getControlNames() {
        ArrayList<SpinnerData> apiArrayList = new ArrayList<>();


        List<String> controlNamesList = AppConstants.GlobalObjects.getCurrentApp_ControlNames();

        if (controlNamesList == null)
            controlNamesList = new ArrayList<>();

        if (controlNamesList.size() > 0) {

            for (int i = 0; i < controlNamesList.size(); i++) {

                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(controlNamesList.get(i));
                spinnerData.setName(controlNamesList.get(i));
                apiArrayList.add(spinnerData);
            }

        }

        return apiArrayList;
    }

    public static String getControlType(List<ControlObject> controlObjectList, String controlName) {
        String controlType = "";
        for (int i = 0; i < controlObjectList.size(); i++) {
            if(controlObjectList.get(i).getControlType().contentEquals(CONTROL_TYPE_SECTION)){
                List<ControlObject> sectionControlObjectList = controlObjectList.get(i).getSubFormControlList();
                for (int j = 0; j <sectionControlObjectList.size() ; j++) {
                    if (controlName.contentEquals(sectionControlObjectList.get(j).getControlName())) {
                        controlType = sectionControlObjectList.get(j).getControlType();
                        return controlType;
                    }
                }

            }else {
                if (controlName.contentEquals(controlObjectList.get(i).getControlName())) {
                    controlType = controlObjectList.get(i).getControlType();
                    return controlType;
                }
            }
        }
        return controlType;
    }

    public static List<ControlObject> getSubControlsList(List<ControlObject> controlObjectList, String controlName) {
        List<ControlObject> subControlsList = new ArrayList<>();
        for (int i = 0; i < controlObjectList.size(); i++) {

            if ((controlObjectList.get(i).getControlType().contentEquals(CONTROL_TYPE_SUBFORM)
                    || controlObjectList.get(i).getControlType().contentEquals(CONTROL_TYPE_GRID_CONTROL)
                    || controlObjectList.get(i).getControlType().contentEquals(CONTROL_TYPE_SECTION))
                    && controlObjectList.get(i).getControlName().contentEquals(controlName)) {
                subControlsList.addAll(controlObjectList.get(i).getSubFormControlList());
                break;
            }

        }
        return subControlsList;
    }

    public static ArrayList<SpinnerData> getOperatorsValues() {
        ArrayList<SpinnerData> operatorsArrayList = new ArrayList<SpinnerData>();

        SpinnerData spinnerData = new SpinnerData();
        spinnerData.setId("0");
        spinnerData.setName("Equals");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("1");
        spinnerData.setName("lessThan");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("2");
        spinnerData.setName("GreaterThan");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("3");
        spinnerData.setName("LessThanEqualsTo");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("4");
        spinnerData.setName("GreaterThanEqualsTo");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("5");
        spinnerData.setName("Contains");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("6");
        spinnerData.setName("StartsWith");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("7");
        spinnerData.setName("EndsWith");
        operatorsArrayList.add(spinnerData);

        spinnerData = new SpinnerData();
        spinnerData.setId("8");
        spinnerData.setName("isEmpty");
        operatorsArrayList.add(spinnerData);
        return operatorsArrayList;
    }

    public static void setFocus(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public static List<SpinnerData> loadTextsizeItems() {


        List<SpinnerData> spinnerDataList = new ArrayList<>();

        for (int i = 10; i < 25; i++) {
            SpinnerData spinnerData = new SpinnerData();
            spinnerData.setId(String.valueOf(i));
            spinnerData.setName(String.valueOf(i));
            spinnerDataList.add(spinnerData);
        }

        return spinnerDataList;


    }

//    public static LinearLayout.LayoutParams paramsMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    /*public static LinearLayout controlsSwitch(Context context, ControlObject controlObject) {

        LinearLayout linearLayout = new LinearLayout(context);
        switch (controlObject.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = new TextInput((Activity) context, controlObject);
                linearLayout = textInput.getTextInputView();
//                linearLayout.addView(textInput.getView());
                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                break;
            case CONTROL_TYPE_PHONE:
                break;

        }
        return linearLayout;

    }*/

    //EmailValidation
    public static boolean getcheckEmailId(String email) {
        boolean flag = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
        if (email.matches(emailPattern) && email.length() > 0) {
            flag = true;
        }
        return flag;
    }

    //TodayDate_Formate(DD/MM/YYYY HH:MM:SS AM/PM)
    public static String gettodaydate() {
        String mYear = "", mnth = "", day = "";
        final Calendar c = Calendar.getInstance();
        mYear = "" + c.get(Calendar.YEAR);

        mnth = "" + (c.get(Calendar.MONTH) + 1);
        day = "" + c.get(Calendar.DAY_OF_MONTH);
        if (mnth.length() == 1) {
            mnth = "0" + mnth;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        String AMPM = "AM";
        final Calendar timer = Calendar.getInstance();
        int hr = timer.getTime().getHours();
        int mm = timer.getTime().getMinutes();
        int ss = timer.getTime().getSeconds();
        if (hr > 12) {
            AMPM = "PM";
            hr = hr - 12;
        }

        return day + "/" + mnth + "/" + mYear + " " + hr + ":" + mm + ":" + ss
                + " " + AMPM;
    }

    //Image_String_From_Path
    public static String sgetImageStringFromBitmap(String path, int width,
                                                   int height) {
        String Imgstring = "0";

        try {
            Bitmap bmp = sgetBitmapFromUrl(path, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            Imgstring = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }
        return Imgstring.trim();
    }

    //Createing_BitmapFrom_Path
    public static Bitmap sgetBitmapFromUrl(String url, int width, int height) {
        Bitmap bmp = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url, opt);
        int pw = opt.outWidth;
        int ph = opt.outHeight;
        int scaleFactore = Math.min(pw / width, ph / height);
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = scaleFactore;
        opt.inPurgeable = true;
        bmp = BitmapFactory.decodeFile(url, opt);

        return bmp;
    }

    public static String getSubFormControlsPrefix(String displayName) {

        String prefix = "";

        if (displayName.split(" ").length > 1) {
            String[] array = displayName.split(" ");
            for (int i = 0; i < array.length; i++) {

                prefix = prefix + array[i].charAt(0);

            }
        } else {
            prefix = displayName.substring(0, 2);
        }
        return prefix;

    }

    public static void mSetTags(LinearLayout ll_add_labels, int tagCount) {
        for (int labels = 0; labels < ll_add_labels.getChildCount(); labels++) {

            View view = ll_add_labels.getChildAt(labels);

            view.setTag(labels);
        }

        tagCount--;
    }

    public static void showSoftKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftKeyboard(Context context, EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static String readTxtFromXMl() {
        File sdcard = Environment.getExternalStorageDirectory();

        // Get the text file
        File file = new File(sdcard, "Improve_User/" + "MadeMenuXml.txt");
//        File file = new File(sdcard, "Improve_User/" + "TestXMLFIle.xml");

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
//                System.out.println("ReadTxtFromXMl:   "+text.append(line) + "\n");
            }
            br.close();
        } catch (IOException e) {
            // You'll need to add proper error handling here
        }
        return text.toString();
    }

//    /*Display Settings on CustomEditText*/
//    public static void setDisplaySettings(Context context, CustomEditText ce_TextType, ControlObject controlObject) {
//
//        ce_TextType.setTextSize(Float.parseFloat(controlObject.getTextSize()));
//        ce_TextType.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
////        ce_TextType.setTextColor(Color.parseColor("#ff5733"));
//        if (controlObject.getTextStyle().equalsIgnoreCase("Bold")) {
//
//            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
//            ce_TextType.setTypeface(typeface_bold);
//        } else if (controlObject.getTextStyle().equalsIgnoreCase("Italic")) {
//
//            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
//            ce_TextType.setTypeface(typeface_italic);
//        }
//    }

    public static void replaceFragmentWithBundle(View view, Bundle bundle,
                                                 Fragment fragment, String fragmentTag) {

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragMan = activity.getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragment.setArguments(bundle);
        fragTrans.replace(R.id.container, fragment, fragmentTag);
        fragTrans.commit();
    }

    public static void replaceFragment(View view, Fragment fragment, String fragmentTag) {

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragMan = activity.getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.container, fragment, fragmentTag);
        fragTrans.commit();
    }

    public static void setViewDisableTextInput(View view, boolean enabled, ControlObject controlObject, CustomEditText ce_TextType) {
        if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
            ce_TextType.setText(controlObject.getDefaultValue());
        }
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewDisable(child, enabled);
            }
        }
    }

    public static void setViewDisable(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child != null) {
                    setViewDisable(child, enabled);
                    if (enabled) {
                        child.setAlpha(1);
                        child.setClickable(true);
                    } else {
                        child.setAlpha(0.8f);
                        child.setClickable(false);
                    }
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void applyDisableColor(Context context, LinearLayout linearLayout) {

        LinearLayout llDisable = new LinearLayout(context);
        llDisable.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llDisable.setOrientation(LinearLayout.VERTICAL);
        llDisable.setBackgroundColor(context.getColor(R.color.semi_transparent));
        linearLayout.addView(llDisable);

    }

    public static void mScreenAwake(Context context, String TAG) {
        PowerManager.WakeLock screenLock = ((PowerManager) context.getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        screenLock.acquire();
        screenLock.release();
    }

    public static void ValidatorsForControls(Node ControlNode, ControlObject controlObject) {

        NodeList validatorsList = ((Element) ControlNode).getElementsByTagName("Validators").item(0).getChildNodes();
        if (validatorsList != null && validatorsList.getLength() > 0) {
            for (int k = 0; k < validatorsList.getLength(); k++) {
                Node Opton = validatorsList.item(k);
                if (Opton.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) Opton;
                    Log.d("XMLHELPER_Type", eElement.getAttribute("type"));
                    if (eElement.getAttribute("type").equalsIgnoreCase("mandatory")) {
                        controlObject.setNullAllowed(true);
                        Log.d("XMLMandatoryError", eElement.getElementsByTagName("Message").item(0).getTextContent());
                        controlObject.setMandatoryFieldError(eElement.getElementsByTagName("Message").item(0).getTextContent());
                    } else if (eElement.getAttribute("type").equalsIgnoreCase("unique")) {
                        controlObject.setUniqueField(true);
                        Log.d("XMLUniqueError", eElement.getElementsByTagName("Message").item(0).getTextContent());
                        controlObject.setUniqueFieldError(eElement.getElementsByTagName("Message").item(0).getTextContent());
                    }
                }
            }
        }
    }

    /*Display Settings for every DisplayName(CustomTextView)*/
    public static void setDisplaySettings(Context context, CustomTextView ce_TextType, ControlObject controlObject) {
        try {
            Log.d(TAG, "setDisplaySettingsColors: " + controlObject.getDisplayName() + " " + controlObject.getTextHexColor());
            if (controlObject.getTextSize() != null) {
                ce_TextType.setTextSize(Float.parseFloat(controlObject.getTextSize()));
            }

            if (controlObject.getTextHexColor() != null && !controlObject.getTextHexColor().equalsIgnoreCase("")) {
//        Log.d(TAG, "setDisplaySettingsColors: " + controlObject.getDisplayName() + " " + controlObject.getTextHexColor());
                ce_TextType.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
            }
            if (controlObject.getTextStyle() != null && controlObject.getTextStyle().equalsIgnoreCase("Bold")) {
                Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                ce_TextType.setTypeface(typeface_bold);
            } else if (controlObject.getTextStyle() != null && controlObject.getTextStyle().equalsIgnoreCase("Italic")) {

                Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                ce_TextType.setTypeface(typeface_italic);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setDisplaySettings", e);
        }
    }

    /*Display Settings on CustomTextInputEditText*/
    public static void setDisplaySettings(Context context, EditText ce_TextType, ControlObject controlObject) {

        ce_TextType.setTextSize(Float.parseFloat(controlObject.getTextSize()));
        ce_TextType.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
        if (controlObject.getTextStyle().equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
            ce_TextType.setTypeface(typeface_bold);
        } else if (controlObject.getTextStyle().equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
            ce_TextType.setTypeface(typeface_italic);
        }
    }

    public static void setDisplaySettingsMenuButton(Context context, CustomButton customButton, List<ControlObject> controlObjectList, int position) {

//        Typeface typeface = Typeface.createFromAsset(getAssets(), "Helv Neue 67 Med Cond.ttf");
        customButton.setText(controlObjectList.get(position).getDisplayName());
//        n.setTypeface(typeface);

        customButton.setTextSize(Float.parseFloat(controlObjectList.get(position).getTextSize()));
        if (controlObjectList.get(position).getTextHexColor() != null && controlObjectList.get(position).getTextHexColor() != "") {
            customButton.setTextColor(Color.parseColor(controlObjectList.get(position).getTextHexColor()));
//            ce_TextType.setTextColor(Color.parseColor("#d72631"));
        }
//        ce_TextType.setTextColor(Color.parseColor("#ff5733"));
        if (controlObjectList.get(position).getTextStyle().equalsIgnoreCase("Bold")) {

            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
            customButton.setTypeface(typeface_bold);
        } else if (controlObjectList.get(position).getTextStyle().equalsIgnoreCase("Italic")) {

            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
            customButton.setTypeface(typeface_italic);
        }
    }

    public static void setDisplaySettingsButton(Context context, CustomButton customButton, ControlObject controlObject) {

        if (controlObject.getButtonHexColor() != null && !controlObject.getButtonHexColor().isEmpty()) {
            customButton.setBackgroundColor(Color.parseColor(controlObject.getButtonHexColor()));
        }
        if (controlObject.getTextSize() != null) {
            customButton.setTextSize(Float.parseFloat(controlObject.getTextSize()));
        }
        if (controlObject.getTextHexColor() != null && controlObject.getTextHexColor() != "") {
            customButton.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
        }
//        if (controlObject.isEnableChangeButtonColorBG() && controlObject.getTextHexColorBG() != null && controlObject.getTextHexColorBG() != "") {
//            customButton.setBackgroundColor(Color.parseColor(controlObject.getTextHexColorBG()));
//        }
        if (controlObject.getTextHexColorBG() != null && !controlObject.getTextHexColorBG().equalsIgnoreCase("")) {
            customButton.setBackgroundColor(Color.parseColor(controlObject.getTextHexColorBG()));
        } else {
//            customButton.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.success_green));
            customButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.success_green)));

        }


//        ce_TextType.setTextColor(Color.parseColor("#ff5733"));
        if (controlObject.getTextStyle() != null && controlObject.getTextStyle().equalsIgnoreCase("Bold")) {

            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
            customButton.setTypeface(typeface_bold);
        } else if (controlObject.getTextStyle() != null && controlObject.getTextStyle().equalsIgnoreCase("Italic")) {

            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
            customButton.setTypeface(typeface_italic);
        }

    }

    public static Bitmap Base64StringToBitmap(String base64String) {

        Bitmap bmpReturn = null;

        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        bmpReturn = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return bmpReturn;
    }

    public static UserData getUserDetails(Context context) {
        Gson gson = new Gson();
        String jsonUserDeatils = PrefManger.getSharedPreferencesString(context, SP_USER_DETAILS, "");
        UserData userDetailsObj = gson.fromJson(jsonUserDeatils, UserData.class);
        return userDetailsObj;

    }

    public static JSONObject getUserDetailsJson(Context context) {
        Gson gson = new Gson();
        String jsonUserDeatils = PrefManger.getSharedPreferencesString(context, SP_USER_DETAILS, "");
        // UserDetails userDetailsObj = gson.fromJson(jsonUserDeatils, UserDetails.class);

        try {
            return new JSONObject(jsonUserDeatils);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }

    public static byte[] convertBitMapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    public static String readTextFileFromSD(Context context, String filePath) {
        String textStr = "";
        try {
            File dir = new File(filePath);
            if (dir.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(dir));
                String line;
                while ((line = in.readLine()) != null) {
                    textStr = line;
                }
                in.close();
            }
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());

        }
        return textStr;
    }

//    public static boolean isNetworkStatusAvialable(Context application) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Network nw = connectivityManager.getActiveNetwork();
//            if (nw == null) return false;
//            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
//            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
//        } else {
//            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
//            return nwInfo != null && nwInfo.isConnected();
//        }
//    }

    public static String readTextFileFromSD(Context context, String txtFile, String org_id) {
        String textStr = "";
        try {
//            File sdcard = Environment.getExternalStorageDirectory();
//            File dir = new File(sdcard.getAbsolutePath() + "/Improve_User/" + org_id + "/");
            String sdcardUrl = "Improve_User" + "/" + org_id;
            File dir = context.getExternalFilesDir(sdcardUrl);
            if (dir.exists()) {
//                File file = new File(dir, txtFile);
                File appSpecificExternalDir = new File(dir.getAbsolutePath(), txtFile);
//                File file = new File(dir, "GCBF_Admin_Mandal.txt");
                BufferedReader in = new BufferedReader(new FileReader(appSpecificExternalDir));
                String line;
                while ((line = in.readLine()) != null) {
                    textStr = line;
                }
                in.close();
            }
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());

        }
        return textStr;
    }

    public static List<OrderByEvents_Object> getEventsByOrder(Control_EventObject control_EventObject) {

        List<OrderByEvents_Object> totalActionsList = new ArrayList<>();
        List<ActionWithoutCondition_Bean> listActionWithoutConditions = control_EventObject.getActionWithOutConditionList() != null ? control_EventObject.getActionWithOutConditionList() : new ArrayList<ActionWithoutCondition_Bean>();
        List<ActionWithCondition_Bean> listActionWithConditions = control_EventObject.getActionWithConditionList() != null ? control_EventObject.getActionWithConditionList() : new ArrayList<ActionWithCondition_Bean>();

        int totalActions = listActionWithoutConditions.size() + listActionWithConditions.size();

        if (totalActions > 0) {

            for (int i = 0; i < totalActions; i++) {

                for (int j = 0; j < listActionWithConditions.size(); j++) {
                    ActionWithCondition_Bean actionWithCondition_bean = listActionWithConditions.get(j);

                    if (actionWithCondition_bean.getPositionInEvent() == i) {

                        OrderByEvents_Object orderByEvents_object = new OrderByEvents_Object();

                        orderByEvents_object.setActionType(AppConstants.ACTION_WITH_CONDITION);
                        orderByEvents_object.setPositionInEvent(i);
                        orderByEvents_object.setActionWithCondition(actionWithCondition_bean);

                        totalActionsList.add(orderByEvents_object);
                    }

                }

                for (int j = 0; j < listActionWithoutConditions.size(); j++) {
                    ActionWithoutCondition_Bean actionWithoutCondition_bean = listActionWithoutConditions.get(j);

                    if (actionWithoutCondition_bean.getPositionInEvent() == i) {

                        OrderByEvents_Object orderByEvents_object = new OrderByEvents_Object();

                        orderByEvents_object.setActionType(AppConstants.ACTION_WITH_OUT_CONDITION);
                        orderByEvents_object.setPositionInEvent(i);
                        orderByEvents_object.setActionWithoutCondition(actionWithoutCondition_bean);

                        totalActionsList.add(orderByEvents_object);

                    }
                }
            }
        }

        return totalActionsList;

    }

    public static boolean isNetworkStatusAvialableCopy(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null) {
                return netInfos.isConnected();
            }
        }
        return false;
    }
    public static boolean isNetworkStatusAvialable(Context context) {
        boolean hasInternet = false;
        // Get the connectivity manager
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

// Check if the device is connected to the internet
        boolean isConnected = false;
        if (cm.getActiveNetworkInfo() != null) {
            isConnected = cm.getActiveNetworkInfo().isConnected();
        }

// Use the isConnected variable to perform actions based on internet availability
        if (isConnected) {

            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }

            return hasInternet;
        } else {
            // Internet is not available
            return false;
        }
    }

    public static int getFileSize(String selectedPath) {

        File file = new File(selectedPath);
        int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));

        return file_size;

    }

    public static void confirmDialog(Context context, String title, String msg, String positiveBtnText, String negativeBtnText, final Helper.IL il) {
        try {
            AlertDialog.Builder alertDialogBuilder = getBuilder(context);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (il != null)
                        il.onSuccess();
                    dialog.dismiss();
                }
            });
            if (!negativeBtnText.equals("")) {
                alertDialogBuilder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (il != null)
                            il.onCancel();
                        dialog.dismiss();
                    }
                });
            }


            alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (il != null) {
                            if (!negativeBtnText.equals("")) {
                                il.onCancel();
                            } else {
                                il.onSuccess();
                            }
                        }

                        dialog.dismiss();
                    }
                    return false;
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create(); // create
            // alert
            // dialog
            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void confirmDialog(Context context, String msg, String positiveBtnText, String negativeBtnText, final Helper.IL il) {
        try {
            AlertDialog.Builder alertDialogBuilder = getBuilder(context);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (il != null)
                        il.onSuccess();
                    dialog.dismiss();
                }
            });
            if (!negativeBtnText.equals("")) {
                alertDialogBuilder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (il != null)
                            il.onCancel();
                        dialog.dismiss();
                    }
                });
            }


            alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (il != null) {
                            if (!negativeBtnText.equals("")) {
                                il.onCancel();
                            } else {
                                il.onSuccess();
                            }
                        }

                        dialog.dismiss();
                    }
                    return false;
                }
            });
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.bhargo_color_one, typedValue, true);
            @ColorInt int color = typedValue.data;
            String hexColor = "#" + Integer.toHexString(color);
            AlertDialog alertDialog = alertDialogBuilder.create(); // create
            // alert
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                }
            });
            // dialog
            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static AlertDialog.Builder getBuilder(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false);
        return alertDialogBuilder;
    }

    public static void my_showAlert(Context context, String title, String message, String IconType) {
        int iconType = R.drawable.ic_dialog_alert;
        String Head = title;
        if (IconType.equalsIgnoreCase("1")) {
            Head = "Info";
            iconType = R.drawable.ic_dialog_info;
        } else if (IconType.equalsIgnoreCase("2")) {
            iconType = R.drawable.ic_dialog_alert;
            Head = "Alert";
        } else if (IconType.equalsIgnoreCase("3")) {
            iconType = R.drawable.green_mark;
            Head = "Success";
        } else {
            Head = "Message";
            iconType = -1;
        }
        TextView myMessage = new TextView(context);
        myMessage.setText(message);
        myMessage.setGravity(Gravity.CENTER);
        myMessage.setTextSize(20);
        myMessage.setPadding(40, 40, 40, 40);
        new AlertDialog.Builder(context)
                .setTitle(title.equals("") ? Head : title)
                .setView(myMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setIcon(iconType)
                .show();
    }

    public static void my_showAlertWithExit(final Context context, String title, String message, String IconType) {
        Typeface typeface_satoshi = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi));
        int textSize = (int) context.getResources().getDimension(R.dimen.control_input_height);
        int iconType = R.drawable.ic_dialog_alert;
        String Head = title;
        if (IconType.equalsIgnoreCase("Info") ||IconType.contentEquals("") || IconType.equalsIgnoreCase("1")) {
            Head = "Info";
            iconType = R.drawable.ic_dialog_info;
        } else if (IconType.equalsIgnoreCase("Error") || IconType.equalsIgnoreCase("2")) {
            iconType = R.drawable.ic_dialog_alert;
            Head = "Alert";
        } else if (IconType.equalsIgnoreCase("Success") || IconType.equalsIgnoreCase("3")) {
            iconType = R.drawable.green_mark;
            Head = "Success";
        } else if (IconType.equalsIgnoreCase("None")){
            Head = "";
            iconType = -1;
        }
/*
        if (IconType.equalsIgnoreCase("1")) {
            Head = "Info";
            iconType = R.drawable.ic_dialog_info;
        } else if (IconType.equalsIgnoreCase("2")) {
            iconType = R.drawable.ic_dialog_alert;
            Head = "Alert";
        } else if (IconType.equalsIgnoreCase("3")) {
            iconType = R.drawable.green_mark;
            Head = "Success";
        } else {
            Head = "Message";
            iconType = -1;
        }
*/

        TextView myMessage = new TextView(context);
        myMessage.setText(message);
        myMessage.setTypeface(typeface_satoshi);
        myMessage.setGravity(Gravity.CENTER);
        myMessage.setTextSize(20);
        myMessage.setPadding(40, 40, 40, 40);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(Head)
                .setView(myMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                        dialog.dismiss();
                    }
                })

                .setIcon(iconType)
                .show();
        alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTypeface(typeface_satoshi);
        alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.colorPrimary));

    }

    public static ControlObject getControlObjFromGlobalObject(Context context, String Value) {
        ControlObject controlObject = null;
        String formName = "", tempStr = "", ValueType = "", ConditionValue = "";
        String ValueStr = Value;
        if (ValueStr.startsWith("(im:") || ValueStr.startsWith("(IM:")) {
            if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") &&
                    !(AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)
                            || AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT)) &&
                    !ValueStr.startsWith("(im:" + AppConstants.Global_PseudoControl.toLowerCase())) {
                formName = ValueStr.substring(4, ValueStr.indexOf("."));//(im:form 2.controlname.gender)
                tempStr = ValueStr.substring(4 + formName.length() + 1);
                ValueType = tempStr.substring(0, tempStr.indexOf("."));
                ValueStr = ValueStr.substring(4 + formName.length() + 1, ValueStr.lastIndexOf(")"));
            } else {
                ValueType = ValueStr.substring(4, ValueStr.indexOf("."));//controlname
                ValueStr = ValueStr.substring(4, ValueStr.lastIndexOf(")"));//controlname.gender
            }
            if (ValueType.equalsIgnoreCase(AppConstants.Global_ControlsOnForm)) {
                ConditionValue = ValueStr.split("\\.")[1];
                List<ControlObject> list_Controls = new ArrayList<>();
                LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<>();
                if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                    list_Controls = Global_Single_Forms.get(formName).getControls_list();
                    List_ControlClassObjects = Global_Controls_Variables.get(formName);
                } else {
                    if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                        list_Controls = ((MainActivity) context).dataCollectionObject.getControls_list();
                        List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                    } else {
                        list_Controls = ((MainActivity) context).dataCollectionObject.getControls_list();
                        List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                    }
                }
                for (int a = 0; a < list_Controls.size(); a++) {
                    ControlObject temp_controlObj = new ControlObject();
                    temp_controlObj = list_Controls.get(a);
                    if ((temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) || (temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)
                    ||(temp_controlObj.getControlName().trim() + "_Coordinates").equalsIgnoreCase(ConditionValue)
                            ||(temp_controlObj.getControlName().trim() + "_Type").equalsIgnoreCase(ConditionValue)) {
                        controlObject = temp_controlObj;
                        break;
                    }
                }
            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                String SubformName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                boolean Allrows = ConditionValue.toLowerCase().endsWith("_allrows") || ConditionValue.toLowerCase().endsWith("_allrows)");
                if (ConditionValue.toLowerCase().contains("_currentrow")) {
                    ConditionValue = ConditionValue.substring(0, ConditionValue.toLowerCase().indexOf("_currentrow"));
                } else if (ConditionValue.toLowerCase().contains("_processrow")) {
                    ConditionValue = ConditionValue.substring(0, ConditionValue.toLowerCase().indexOf("_processrow"));
                } else if (ConditionValue.toLowerCase().contains("_allrows")) {
                    ConditionValue = ConditionValue.substring(0, ConditionValue.toLowerCase().indexOf("_allrows"));
                }
                if (SubformName.equalsIgnoreCase("grid_control")) {
                    GridControl gridControl = null;
                    if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                        gridControl = (GridControl) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    } else {
                        gridControl = (GridControl) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    }
                    List<ControlObject> list_Controls = gridControl.controlObject.getSubFormControlList();
                    LinkedHashMap<String, Object> List_ControlClassObjects = gridControl.gridControl_List_ControlClassObjects.get(AppConstants.SF_Container_position);
                    if (!Allrows) {
                        for (int a = 0; a < list_Controls.size(); a++) {
                            ControlObject temp_controlObj = list_Controls.get(a);
                            if ((temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) || (temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)
                                    ||(temp_controlObj.getControlName().trim() + "_Coordinates").equalsIgnoreCase(ConditionValue)
                                    ||(temp_controlObj.getControlName().trim() + "_Type)").equalsIgnoreCase(ConditionValue)) {
                                controlObject = temp_controlObj;
                                break;
                            }
                        }
                    } else {
                        for (int x = 0; x < gridControl.getSubFormRows(); x++) {
                            for (int a = 0; a < list_Controls.size(); a++) {
                                ControlObject temp_controlObj = list_Controls.get(a);
                                if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)|| (temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)
                                        ||(temp_controlObj.getControlName().trim() + "_Coordinates").equalsIgnoreCase(ConditionValue)
                                        ||(temp_controlObj.getControlName().trim() + "_Type)").equalsIgnoreCase(ConditionValue)) {
                                    controlObject = temp_controlObj;
                                    break;
                                }

                            }
                        }
                    }
                } else {
                    SubformView subview = null;
                    if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                        subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    }/*else if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                        subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    }*/ else {
                        subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    }
                    List<ControlObject> list_Controls = subview.controlObject.getSubFormControlList();
                    LinkedHashMap<String, Object> List_ControlClassObjects = subview.subform_List_ControlClassObjects.get(AppConstants.SF_Container_position);
                    if (!Allrows) {
                        for (int a = 0; a < list_Controls.size(); a++) {
                            ControlObject temp_controlObj = list_Controls.get(a);
                            if ((temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) || (temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)
                                    ||(temp_controlObj.getControlName().trim() + "_Coordinates").equalsIgnoreCase(ConditionValue)
                                    ||(temp_controlObj.getControlName().trim() + "_Type)").equalsIgnoreCase(ConditionValue)) {
                                controlObject = temp_controlObj;
                                break;
                            }
                        }
                    } else {
                        for (int x = 0; x < subview.getSubFormRows(); x++) {
                            for (int a = 0; a < list_Controls.size(); a++) {
                                ControlObject temp_controlObj = list_Controls.get(a);
                                if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)|| (temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)
                                        ||(temp_controlObj.getControlName().trim() + "_Coordinates").equalsIgnoreCase(ConditionValue)
                                        ||(temp_controlObj.getControlName().trim() + "_Type)").equalsIgnoreCase(ConditionValue)) {
                                    controlObject = temp_controlObj;
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        }
        return controlObject;
    }

    static boolean stopForLoopFlag = false;

    private static String getCtrlObjValue(ControlObject temp_controlObj, String ConditionValue,
                                          LinkedHashMap<String, Object> List_ControlClassObjects) {
        String value = "";
        if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_AUTO_COMPLETION)
                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)
                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_USER)) {

            if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue) || (temp_controlObj.getControlName().trim() + "_id").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                if (value.contains(",")) {
                    value = value.substring(0, value.indexOf(","));
                }
                stopForLoopFlag = true;
            } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.substring(value.indexOf(",") + 1);
                stopForLoopFlag = true;
            }
        } else if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)) {
            if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                String tempValue = getvaluefromObject(temp_controlObj, List_ControlClassObjects);

                for (int j = 0; j < tempValue.split("\\$").length; j++) {
                    value = value + "," + tempValue.split("\\$")[j].split("\\,")[1];
                }
                value = value.substring(1);
                stopForLoopFlag = true;
            } else if ((temp_controlObj.getControlName().trim()).equalsIgnoreCase(ConditionValue)) {
                String tempValue = getvaluefromObject(temp_controlObj, List_ControlClassObjects);

                for (int j = 0; j < tempValue.split("\\$").length; j++) {
                    value = value + "," + tempValue.split("\\$")[j].split("\\,")[0];
                }
                value = value.substring(1);
                stopForLoopFlag = true;
            }
        } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CALENDAR_EVENT)) {
            if ((temp_controlObj.getControlName().trim() + "_Date").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.substring(0, value.indexOf("^"));
                stopForLoopFlag = true;
            } else if ((temp_controlObj.getControlName().trim() + "_Message").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.substring(value.indexOf("^") + 1);
                stopForLoopFlag = true;
            }
        } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_VIEWER)) {
            if ((temp_controlObj.getControlName().trim() + "_Header").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.split("\\^")[0];
                stopForLoopFlag = true;
            } else if ((temp_controlObj.getControlName().trim() + "_Subheader").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.split("\\^")[1];
                stopForLoopFlag = true;
            } else if ((temp_controlObj.getControlName().trim() + "_Description").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.split("\\^")[2];
                stopForLoopFlag = true;
            } else if ((temp_controlObj.getControlName().trim() + "_DateandTime").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.split("\\^")[3];
                stopForLoopFlag = true;
            } else if ((temp_controlObj.getControlName().trim() + "_Trans_id").equalsIgnoreCase(ConditionValue)) {
                value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                value = value.split("\\^")[4];
                stopForLoopFlag = true;
            }
        } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_MAP)) {
            if ((temp_controlObj.getControlName().trim() + "_TransId").equalsIgnoreCase(ConditionValue)) {
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(temp_controlObj.getControlName().trim());
                if (AppConstants.MAP_MARKER_POSITION != -1 && mapControlView.getTransIdsList() != null && mapControlView.getTransIdsList().size() > AppConstants.MAP_MARKER_POSITION) {
                    value = mapControlView.getTransIdsList().get(AppConstants.MAP_MARKER_POSITION);
                }
                stopForLoopFlag = true;
            } else {
                if (ConditionValue.equalsIgnoreCase("map_lat")) {
                    String valueLat = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                    String[] valueLatSplit = valueLat.split("\\$");
                    value = valueLatSplit[0];
                    stopForLoopFlag = true;
                } else if (ConditionValue.equalsIgnoreCase("map_lon")) {
                    String valueLat = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                    String[] valueLatSplit = valueLat.split("\\$");
                    value = valueLatSplit[1];
                    stopForLoopFlag = true;
                } else if (ConditionValue.equalsIgnoreCase("map_latlon")) {
                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                    stopForLoopFlag = true;
                } else {
                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                    stopForLoopFlag = true;
                }
            }

        } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_GPS)) {
            if (ConditionValue.endsWith("_coordinates")) {
                String gps = ConditionValue.substring(0, ConditionValue.lastIndexOf("_"));
                if (gps.trim().equalsIgnoreCase(temp_controlObj.getControlName())) {
                    Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    List<LatLng> latLngList = controlGPS.getLatLngList();
                    for (int j = 0; j < controlGPS.getLatLngList().size(); j++) {
                        LatLng latlang = latLngList.get(j);
                        value = value + " ^" + latlang.latitude + "$" + latlang.longitude;
                    }
//                value = "\""+value.substring(value.indexOf("^") + 1)+"\"";
                    value = value.substring(value.indexOf("^") + 1);
                }
                stopForLoopFlag = true;
            } else if (ConditionValue.endsWith("_type")) {
                String type = ConditionValue.substring(0, ConditionValue.lastIndexOf("_"));
                if (type.trim().equalsIgnoreCase(temp_controlObj.getControlName())) {
                    Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    value = controlGPS.getGPS_Type();
                }
                stopForLoopFlag = true;
            }

        } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
            value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
            stopForLoopFlag = true;
        }

        return value;
    }

    public static String getValueFromGlobalObject(Context context, String Value) {
        //(im:controlname.gender)
        //Value = "(im:offlinetable.apps_list_table.AppName)";
        HashMap<String, String> controlPositionInUI = new HashMap<>();
        if (context instanceof MainActivity) {
            controlPositionInUI = AppConstants.controlPositionInUIAllApps.get(((MainActivity) context).dataCollectionObject.getApp_Name());
        }
        String value = "", ValueType = "", ConditionValue = "", ValueStr = "", formName = "", tempStr = "";
        ValueStr = Value;
        String SubformName = "";
        try {
            if (ValueStr.startsWith("(im:") || ValueStr.startsWith("(IM:")) {
                ValueStr = Value.toLowerCase();
                if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") &&
                        !(AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)
                                || AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT)) &&
                        !ValueStr.startsWith("(im:" + AppConstants.Global_PseudoControl.toLowerCase())) {
                    formName = ValueStr.substring(4, ValueStr.indexOf("."));//(im:form 2.controlname.gender)
                    if(isValidValueType(formName)){//To check if
                        ValueType = ValueStr.substring(4, ValueStr.indexOf("."));//controlname
                        ValueStr = ValueStr.substring(4, ValueStr.lastIndexOf(")"));//controlname.gender
                    }else {
                        tempStr = ValueStr.substring(4 + formName.length() + 1);
                        ValueType = tempStr.substring(0, tempStr.indexOf("."));
                        ValueStr = ValueStr.substring(4 + formName.length() + 1, ValueStr.lastIndexOf(")"));
                    }
                } else {
                    ValueType = ValueStr.substring(4, ValueStr.indexOf("."));//controlname
                    ValueStr = ValueStr.substring(4, ValueStr.lastIndexOf(")"));//controlname.gender
                }
                if (ValueType.equalsIgnoreCase(AppConstants.Global_PseudoControl)) {
                    ConditionValue = ValueStr.split("\\.")[1];

                    if (ConditionValue.equalsIgnoreCase(AppConstants.ORG_Name)) {
                        value = AppConstants.GlobalObjects.getOrg_Name();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Post_Id)) {
                        value = "" + AppConstants.GlobalObjects.getUser_PostID() + "";
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Post_Name)) {
                        value = AppConstants.GlobalObjects.getUser_PostName();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Post_Location)) {
                        value = AppConstants.GlobalObjects.getUser_Post_Location();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Post_Location_Name)) {
                        value = AppConstants.GlobalObjects.getUser_Post_Location_Name();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_post_id)) {
                        value = "" + AppConstants.GlobalObjects.getReporting_PostID() + "";
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_post_Department_ID)) {
                        value = "" + AppConstants.GlobalObjects.getReporting_PostDepartmentID() + "";
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_ID)) {
                        value = AppConstants.GlobalObjects.getUser_ID();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_Name)) {
                        value = AppConstants.GlobalObjects.getUser_Name();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_MobileNo)) {
                        value = AppConstants.GlobalObjects.getUser_MobileNo();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_Email)) {
                        value = AppConstants.GlobalObjects.getUser_Email();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_Desigination)) {
                        value = AppConstants.GlobalObjects.getUser_Desigination();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_Department)) {
                        value = AppConstants.GlobalObjects.getUser_Department();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_location)) {
                        value = AppConstants.GlobalObjects.getUser_location();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_ID)) {
                        value = AppConstants.GlobalObjects.getReporter_ID();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_Name)) {
                        value = AppConstants.GlobalObjects.getReporter_Name();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_MobileNo)) {
                        value = AppConstants.GlobalObjects.getReporter_MobileNo();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_Email)) {
                        value = AppConstants.GlobalObjects.getReporter_Email();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_Desigination)) {
                        value = AppConstants.GlobalObjects.getReporter_Desigination();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_Department)) {
                        value = AppConstants.GlobalObjects.getReporter_Department();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Reporter_Role)) {
                        value = AppConstants.GlobalObjects.getReporter_Role();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.APP_Language)) {
                        value = AppConstants.GlobalObjects.getAppLanguage();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Current_Date)) {
                        value = ImproveHelper.getCurrentDateFromHelper();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Current_Time)) {
                        value = ImproveHelper.getCurrentTime();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.login_status)) {
                        value = AppConstants.GlobalObjects.getLogin_status();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Login_Device)) {
                        value = "Mobile";
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Login_Device_Id)) {
                        value = new SessionManager(context).getDeviceIdFromSession();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.App_Version)) {
                        value = new ImproveHelper(context).getAppVersionFromGradle();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.Login_OS_Version)) {
                        value = String.valueOf(Build.VERSION.SDK_INT);
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.CURRENT_APP_NAME)) {
                        value = new SessionManager(context).getAppName();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.BHARGO_LOGIN_ACTION_STATUS)) {
                        value = AppConstants.GlobalObjects.getBhargoLoginStatus();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.BHARGO_LOGIN_ACTION_MESSAGE)) {
                        value = AppConstants.GlobalObjects.getBhargoLoginMessage();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.User_location_name)) {
                        value = AppConstants.GlobalObjects.getUser_location_name();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.USER_TYPE)) {
                        value = AppConstants.GlobalObjects.getUser_type();
                    } else if (ConditionValue.equalsIgnoreCase(AppConstants.USER_TYPE_ID)) {
                        value = AppConstants.GlobalObjects.getUser_type_id();
                    }

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_submitresponse)) {
                    ConditionValue = ValueStr.split("\\.")[1];

                    if (ConditionValue.endsWith("_status")) {
                        value = AppConstants.GlobalObjects.getSubmitresponse_Status();
                    } else if (ConditionValue.endsWith("_message")) {
                        value = AppConstants.GlobalObjects.getSubmitresponse_Message();
                    } else if (ConditionValue.toLowerCase().contentEquals("autogenerateid")) {
                        JSONArray jsonArray = AppConstants.GlobalObjects.getAutoIdsResponseArray();
                        if (jsonArray != null) {
                            JSONObject object = jsonArray.getJSONObject(0);
                            if (object.has(ValueStr.split("\\.")[2])) {
                                value = object.getString(ValueStr.split("\\.")[2]);
                            }
                        }

                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Transactional_offline)) {
                    ConditionValue = ValueStr.split("\\.")[1];
                    JSONArray jsonArray = AppConstants.GlobalObjects.getAutoIdsResponseArray();
                    if (jsonArray != null) {
                        JSONObject object = jsonArray.getJSONObject(0);
                        if (object.has(ConditionValue)) {
                            value = object.getString(ConditionValue);
                        }
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_ControlsOnForm)) {
                    ConditionValue = ValueStr.split("\\.")[1];
                    List<ControlObject> list_Controls = new ArrayList<>();
                    LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<>();
                    //nk_view LinearLayout linearLayout = null;
                    if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                        list_Controls = Global_Single_Forms.get(formName).getControls_list();
                        List_ControlClassObjects = Global_Controls_Variables.get(formName);
                        //nk_view linearLayout = AppConstants.Global_Layouts.get(formName);
                    } else {
                        if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                            list_Controls = ((MainActivity) context).dataCollectionObject.getControls_list();
                            List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                            //nk_view linearLayout = ((MainActivity) context).linearLayout;
                        } else {
                            list_Controls = ((MainActivity) context).dataCollectionObject.getControls_list();
                            List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                            //nk_view linearLayout = ((MainActivity) context).linearLayout;
                        }
                    }
                    stopForLoopFlag = false;
                    for (int a = 0; a < list_Controls.size(); a++) {
                        ControlObject temp_controlObj = list_Controls.get(a);
                        if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_SECTION)) {
                            String section_lo_CtrlName = ValueStr.split("\\.")[2];
                            List<ControlObject> section_lo_ContrlList = temp_controlObj.getSubFormControlList();
                            ControlObject section_lo_controlObj = null;
                            for (int s_ctrl = 0; s_ctrl < section_lo_ContrlList.size(); s_ctrl++) {
//                                if (section_lo_ContrlList.get(s_ctrl).getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                if (section_lo_ContrlList.get(s_ctrl).getControlName().trim().equalsIgnoreCase(section_lo_CtrlName)) {
                                    section_lo_controlObj = section_lo_ContrlList.get(s_ctrl);
                                    break;
                                }
                            }
                            if (section_lo_controlObj != null) {
                                value = getCtrlObjValue(section_lo_controlObj, section_lo_CtrlName, List_ControlClassObjects);
                                if (stopForLoopFlag) {
                                    break;
                                }
                            }
                        } else {
                            value = getCtrlObjValue(temp_controlObj, ConditionValue, List_ControlClassObjects);
                            if (stopForLoopFlag) {
                                break;
                            }
                        }
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Calenders)) {
                    ConditionValue = ValueStr.split("\\.")[1];

                    List<ControlObject> list_Controls = ((MainActivity) context).dataCollectionObject.getControls_list();
                    LinkedHashMap<String, Object> List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;

                    LinearLayout linearLayout = ((MainActivity) context).linearLayout;

                    if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                        list_Controls = Global_Single_Forms.get(formName).getControls_list();
                        List_ControlClassObjects = Global_Controls_Variables.get(formName);
                        linearLayout = AppConstants.Global_Layouts.get(formName);
                    }

//                    for (int i = 0; i < list_Controls.size(); i++) {
//                        ControlObject temp_controlObj = list_Controls.get(i);
                    int i = 0;
                    for (int a = 0; a < list_Controls.size(); a++) {
                        i = a;
                        ControlObject temp_controlObj = new ControlObject();
                        temp_controlObj = list_Controls.get(a);
                        String controlPos = null;
                        if ((temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) || (temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                            if (((MainActivity) context).dataCollectionObject.isUIFormNeeded) {
                                i = 0;
                                if (linearLayout.getChildCount() != 0) {
                                    controlPos = controlPositionInUI.get(temp_controlObj.getControlName());
                                    if (controlPos != null && controlPos.contains("$")) {//If control is in sublayout
                                        Log.d("controlPos: ", controlPos + temp_controlObj.getControlName());
                                        String[] positions = controlPos.split("\\$");
                                        int layoutPosition = Integer.parseInt(positions[0]);
                                        int sublayoutPosition = Integer.parseInt(positions[1]);
                                        linearLayout = (LinearLayout) ((LinearLayout) ((HorizontalScrollView) ((LinearLayout) linearLayout.getChildAt(layoutPosition)).getChildAt(1)).getChildAt(0)).getChildAt(sublayoutPosition);
                                    } else {
                                        controlPos = controlPositionInUI.get(temp_controlObj.getControlName());
//                                        linearLayout = (LinearLayout) linearLayout.getChildAt(Integer.parseInt(controlPos));
                                        linearLayout = (LinearLayout) ((LinearLayout) linearLayout.getChildAt(Integer.parseInt(controlPos))).getChildAt(2);
                                    }
                                }
                            }
                        }
                        if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue.substring(0, ConditionValue.lastIndexOf("_")))) {
                            value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                            if (ConditionValue.substring(ConditionValue.lastIndexOf("_") + 1).equalsIgnoreCase("Date")) {
                                value = value.substring(0, value.indexOf("^"));
                            } else {
                                value = value.substring(value.indexOf("^") + 1);
                            }
                            break;
                        }
                    }

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    SubformName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    boolean Allrows = ConditionValue.toLowerCase().endsWith("_allrows") || ConditionValue.toLowerCase().endsWith("_allrows)");
                    if (ConditionValue.contains("_currentrow")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_currentrow"));
                    } else if (ConditionValue.contains("_processrow")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_processrow"));
                    } else if (ConditionValue.contains("_allrows")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_allrows"));
                    }
                    SubformView subControl = null;
                    GridControl gridControl = null;
                    if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                        List<ControlObject> list_Controls = new ArrayList<>();
                        LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<>();
                        LinearLayout linearLayout = null;
                        list_Controls = Global_Single_Forms.get(formName).getControls_list();
                        List_ControlClassObjects = Global_Controls_Variables.get(formName);
                        linearLayout = AppConstants.Global_Layouts.get(formName);
                        if (((MainActivity) context).List_ControlClassObjects.get(SubformName).toString().contains("Grid")) {
                            gridControl = (GridControl) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                        } else {
                            subControl = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                        }
                    }/*else if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                        subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    }*/ else {
                        if (((MainActivity) context).List_ControlClassObjects.get(SubformName).toString().contains("Grid")) {
                            gridControl = (GridControl) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                        } else {
                            subControl = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                        }
                    }
                    List<ControlObject> list_Controls = new ArrayList<>();
                    int SubOrGridFormRows = 0;
                    LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<>();
                    if (subControl != null) {
                        list_Controls = subControl.controlObject.getSubFormControlList();
                        List_ControlClassObjects = subControl.subform_List_ControlClassObjects.get(AppConstants.SF_Container_position);
                        SubOrGridFormRows = subControl.getSubFormRows();
                    }
                    if (gridControl != null) {
                        list_Controls = gridControl.controlObject.getSubFormControlList();
                        List_ControlClassObjects = gridControl.gridControl_List_ControlClassObjects.get(AppConstants.SF_Container_position);
                        SubOrGridFormRows = gridControl.getSubFormRows();
                    }
                    if (!Allrows) {
                        int i = 0;
                        for (int a = 0; a < list_Controls.size(); a++) {
                            i = a;
                            ControlObject temp_controlObj = list_Controls.get(a);
                            if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_AUTO_COMPLETION)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_USER)) {
                                if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.substring(0, value.indexOf(","));
                                } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.substring(value.indexOf(",") + 1);
                                    break;
                                }

                            } else if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)) {
                                if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                                    String tempValue = getvaluefromObject(temp_controlObj, List_ControlClassObjects);

                                    for (int j = 0; j < tempValue.split("\\$").length; j++) {
                                        value = value + "," + tempValue.split("\\$")[j].split("\\,")[1];
                                    }
                                    value = value.substring(1);
                                } else if ((temp_controlObj.getControlName().trim()).equalsIgnoreCase(ConditionValue)) {
                                    String tempValue = getvaluefromObject(temp_controlObj, List_ControlClassObjects);

                                    for (int j = 0; j < tempValue.split("\\$").length; j++) {
                                        value = value + "," + tempValue.split("\\$")[j].split("\\,")[0];
                                    }
                                    value = value.substring(1);
                                }
                            } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CALENDAR_EVENT)) {
                                if ((temp_controlObj.getControlName().trim() + "_Date").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.substring(0, value.indexOf("^"));
                                } else if ((temp_controlObj.getControlName().trim() + "_Message").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.substring(value.indexOf("^") + 1);
                                }
                            } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_VIEWER)) {
                                if ((temp_controlObj.getControlName().trim() + "_Header").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.split("\\^")[0];
                                } else if ((temp_controlObj.getControlName().trim() + "_Subheader").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.split("\\^")[1];
                                } else if ((temp_controlObj.getControlName().trim() + "_Description").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.split("\\^")[2];
                                } else if ((temp_controlObj.getControlName().trim() + "_DateandTime").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.split("\\^")[3];
                                } else if ((temp_controlObj.getControlName().trim() + "_Trans_id").equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    value = value.split("\\^")[4];
                                }
                            } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_TABLE)) {


                            } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_MAP)) {
                                if ((temp_controlObj.getControlName().trim() + "_TransId").equalsIgnoreCase(ConditionValue)) {
                                    MapControl mapControlView = (MapControl) List_ControlClassObjects.get(temp_controlObj.getControlName().trim());
                                    if (AppConstants.MAP_MARKER_POSITION != -1 && mapControlView.getTransIdsList() != null && mapControlView.getTransIdsList().size() > AppConstants.MAP_MARKER_POSITION) {
                                        value = mapControlView.getTransIdsList().get(AppConstants.MAP_MARKER_POSITION);
                                    }

                                } else {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                }

                            } else {
                                if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                    //nk_view ll_innerSubFormContainer.getChildAt(i),
                                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                    break;
                                }
                            }

                        }
                    } else {
                        String returnvalue = "";
                        for (int x = 0; x < SubOrGridFormRows; x++) {
                            int i = 0;
                            for (int a = 0; a < list_Controls.size(); a++) {
                                i = a;
                                ControlObject temp_controlObj = list_Controls.get(a);
                                String controlPos = null;
                                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_AUTO_COMPLETION)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                                    if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(0, value.indexOf(","));
                                    } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(value.indexOf(",") + 1);
                                        break;
                                    }

                                } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CALENDAR_EVENT)) {
                                    if ((temp_controlObj.getControlName().trim() + "_Date").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(0, value.indexOf("^"));
                                    } else if ((temp_controlObj.getControlName().trim() + "_Message").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(value.indexOf("^") + 1);
                                    }
                                } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_VIEWER)) {
                                    if ((temp_controlObj.getControlName().trim() + "_Header").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.split("\\^")[0];
                                    } else if ((temp_controlObj.getControlName().trim() + "_Subheader").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.split("\\^")[1];
                                    } else if ((temp_controlObj.getControlName().trim() + "_Description").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.split("\\^")[2];
                                    } else if ((temp_controlObj.getControlName().trim() + "_DateandTime").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.split("\\^")[3];
                                    } else if ((temp_controlObj.getControlName().trim() + "_Trans_id").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.split("\\^")[4];
                                    }
                                } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_TABLE)) {


                                } else {
                                    if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        break;
                                    }
                                }

                            }
                            returnvalue = returnvalue + "," + value;
                        }

                        value = returnvalue.substring(1);
                    }


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_OfflineTable)) {
                    String tableName = ValueStr.split("\\.")[1];
                    Log.e("FormNameInGlobal", tableName);
                    String columnName = ValueStr.split("\\.")[2];
                    Log.e("ColumnName", columnName);

                    value = "";
                    value = getValueFromOfflineTable(context, tableName, columnName);

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    List<String>outputPaths=RealmDBHelper.getDataInRealResults(context,APIName+"_OutputPaths","Path",
                            new String[]{"KeyName"},new String[]{ConditionValue});
                    if(outputPaths.size()>0){
                        String outputMapped_ID=outputPaths.get(0);
                        String temp_TableName = APIName;
                        if (outputMapped_ID.contains("/")) {
                            String temp = outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")).replace("/", "_");
                            //outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")==-1?outputMapped_ID.length():outputMapped_ID.lastIndexOf("/")).replace("/", "_");//
                            temp_TableName = APIName + "_" + temp;
                        }
                        List<String> ldata = RealmDBHelper.getTableData(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, RealmTables.APIMapping.ActionIDWithTableName, temp_TableName);
                        if (ldata.size() > 0) {
                            String tableName = ldata.get(0);
                            String colName=outputMapped_ID.substring(outputMapped_ID.lastIndexOf("/") + 1);
                            if (RealmDBHelper.isModifyColNameExist(context,outputMapped_ID)) {
                                colName=tableName + "_" + colName;
                            }
                            value = RealmDBHelper.getSingleColDataWithComma(context, tableName, colName);;
                        }


                        //old process
                        /*String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                        String tableName = APIName.substring(0, 9) + "_" + temp;
                        String colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                        colName = colName.startsWith("@") ? colName.substring(1) : colName;
                        value = RealmDBHelper.getSingleColDataWithComma(context, tableName, colName);*/
                    }
                    Log.d("realm :finalValue", value.toString());

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    String FormName = ValueStr.split("\\.")[1];
                    Log.e("FormNameInGlobal", FormName);
                    ConditionValue = ValueStr.split("\\.")[2];
                    Log.e("ConditionValue", ConditionValue);
                    value = RealmDBHelper.getSingleColDataWithComma(context, FormName, ConditionValue);
                    //nk realm:
                    /*LinkedHashMap<String, LinkedHashMap<String, List<String>>> formsList = AppConstants.GlobalObjects.getForms_ListHash();
                    if (!formName.contentEquals("")) {
                        formsList = AppConstants.GlobalObjects.getAllFormsList_Hash().get(formName.toLowerCase());
                    }
                    Log.e("formsList", formsList.toString());
                    if (formsList.containsKey(FormName)) {
                        LinkedHashMap<String, List<String>> Form_list = formsList.get(FormName);
                        if (Form_list.containsKey(ConditionValue)) {
                            for (int i = 0; i < Form_list.get(ConditionValue).size(); i++) {
//                                value = value + "|" + Form_list.get(ConditionValue).get(i);
                                value = value + "," + Form_list.get(ConditionValue).get(i);
                            }
                            value = value.substring(1);
                            Log.d("finalValue", value);

//                            value = Form_list.get(ConditionValue).get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_GetData)) {
                    String FormName = ValueStr.split("\\.")[1];
                    Log.e("FormNameInGlobal", FormName);
                    ConditionValue = ValueStr.split("\\.")[2];
                    Log.e("ConditionValue", ConditionValue);
                    if (ConditionValue.startsWith("__")) {
                        if (AppConstants.GlobalObjects.getGetData_ResponseHashMap().containsKey(FormName)) {
                            value = AppConstants.GlobalObjects.getGetData_ResponseHashMap().get(FormName).get(ConditionValue);
                        }
                    } else {
                        value = RealmDBHelper.getSingleColDataWithComma(context, FormName, ConditionValue);
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_ManageData)) {
                    String FormName = ValueStr.split("\\.")[1];
                    Log.e("FormNameInGlobal", FormName);
                    ConditionValue = ValueStr.split("\\.")[2];
                    Log.e("ConditionValue", ConditionValue);
                    if (ConditionValue.startsWith("__")) {
                        if (AppConstants.GlobalObjects.getManageData_ResponseHashMap().containsKey(FormName)) {
                            value = AppConstants.GlobalObjects.getManageData_ResponseHashMap().get(FormName).get(ConditionValue);
                        }
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    value = RealmDBHelper.getSingleColDataWithComma(context, APIName, ConditionValue);
                    Log.d("realm :finalValue", value);
                    //nk realm: single item or multiple item with comma
                    /*if (AppConstants.GlobalObjects.getAPIs_ListHash().containsKey(APIName)) {
                        LinkedHashMap<String, List<String>> API_list = AppConstants.GlobalObjects.getAPIs_ListHash().get(APIName);
                        if (API_list.containsKey(ConditionValue)) {
                            value = API_list.get(ConditionValue).get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Dml)) {
                    String FormName = ValueStr.split("\\.")[1];
                    Log.e("FormNameInGlobal", FormName);
                    ConditionValue = ValueStr.split("\\.")[2];
                    Log.e("ConditionValue", ConditionValue);
                    value = RealmDBHelper.getSingleColDataWithComma(context, FormName, ConditionValue);
                    Log.d("realm: finalValue", value);
                    //nk realm:

                    /*LinkedHashMap<String, LinkedHashMap<String, List<String>>> formsList = AppConstants.GlobalObjects.getForms_ListHash();
                    if (!formName.contentEquals("")) {
                        formsList = AppConstants.GlobalObjects.getAllFormsList_Hash().get(formName.toLowerCase());
                    }
                    Log.e("formsList", formsList.toString());
                    if (formsList.containsKey(FormName)) {
                        LinkedHashMap<String, List<String>> Form_list = formsList.get(FormName);
                        if (Form_list.containsKey(ConditionValue)) {
                            for (int i = 0; i < Form_list.get(ConditionValue).size(); i++) {
//                                value = value + "|" + Form_list.get(ConditionValue).get(i);
                                value = value + "," + Form_list.get(ConditionValue).get(i);
                            }
                            value = value.substring(1);
                            Log.d("finalValue", value);

//                            value = Form_list.get(ConditionValue).get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_GPSControl)) {
                    if (AppConstants.GlobalObjects.getCurrent_GPS().trim().length() > 0) {
                        if (AppConstants.GlobalObjects.getCurrent_GPS().trim().contains(",")) {
                            AppConstants.GlobalObjects.setCurrent_GPS(AppConstants.GlobalObjects.getCurrent_GPS().trim().replace(",", "$"));
                        }
                        value = AppConstants.GlobalObjects.getCurrent_GPS().trim();
                    }else{
                        MainActivity.getInstance().callLocationHelper(new Callback() {
                            @Override
                            public void onSuccess(Object result) {
                                AppConstants.GlobalObjects.setCurrent_GPS(result.toString());
                            }

                            @Override
                            public void onFailure(Throwable throwable) {

                            }
                        });
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_variable) || ValueType.equalsIgnoreCase(AppConstants.Offline_variable)) {
                    String VariableType = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    List<Variable_Bean> list_variables = new ArrayList<>();
                    if (!formName.contentEquals("") && AppConstants.Global_Variable_Beans.containsKey(formName)) {

                        list_variables = AppConstants.Global_Variable_Beans.get(formName);

                    } else {
                        if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals("") && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                            list_variables = ((MainActivity) context).dataCollectionObject.getList_Varibles();
                        } else {
                            list_variables = ((MainActivity) context).dataCollectionObject.getList_Varibles();
                        }
                    }
                    if (ValueType.equalsIgnoreCase(AppConstants.Global_variable)) {
                        if (VariableType.equalsIgnoreCase("Single")) {
                            for (int i = 0; i < list_variables.size(); i++) {
                                if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
                                    value = list_variables.get(i).getVariable_singleValue();
                                    break;
                                }
                            }
                        } else if (VariableType.equalsIgnoreCase("Multiple")) {
                            for (int i = 0; i < list_variables.size(); i++) {
                                if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
                                    value = String.join(",", list_variables.get(i).getVariable_multiValue());
                                    break;
                                }
                            }
                        } else {
                            //Multidimensional
                            String tableName=ConditionValue;
                            String colName=ValueStr.split("\\.")[3];
                            value = RealmDBHelper.getSingleColDataWithComma(context, tableName, colName);

                            //value = RealmDBHelper.getTableDataInLHM(context, ConditionValue).toString();
                        }
                    } else if (ValueType.equalsIgnoreCase(AppConstants.Offline_variable)) {
                        if (VariableType.equalsIgnoreCase("Single")) {
                            for (int i = 0; i < list_variables.size(); i++) {
                                if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
//                                    value = list_variables.get(i).getVariable_singleValue();
                                    value = getOfflineSingleVariableFromSharedPref(context, list_variables.get(i).getVariable_Name());
                                    break;
                                }
                            }
                        } else if (VariableType.equalsIgnoreCase("Multiple")) {
                            for (int i = 0; i < list_variables.size(); i++) {
                                if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
//                                    value = String.join(",", list_variables.get(i).getVariable_multiValue());
                                    value = String.join(",", getOfflineMultiVariableFromSharedPref(context, list_variables.get(i).getVariable_Name()));
                                    break;
                                }
                            }
                        } else {
                            //Multidimensional
                            value = RealmDBHelper.getTableDataInLHM(context, ConditionValue).toString();
                        }
                    }

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataTableControls)) {
                    String DataTableName = ValueStr.split("\\.")[1];
                    String[] array = ValueStr.split("\\.");
                    ConditionValue = ValueStr.split("\\.")[array.length - 1];
//                    ConditionValue = ValueStr.split("\\.")[2];
                    boolean Allrows = false;
                    if (ConditionValue.contains("_currentrow")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_currentrow"));
                    } else if (ConditionValue.contains("_processrow")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_processrow"));
                    } else if (ConditionValue.contains("_allrows")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_allrows"));
                        Allrows = true;
                    }
                    DataTableControl DataTableView = (DataTableControl) ((MainActivity) context).List_ControlClassObjects.get(DataTableName);
                    TableLayout ll_data_table_main = DataTableView.tl_body;
                    TableRow llview = null;
                    llview = (TableRow) ll_data_table_main.getChildAt(AppConstants.DATA_TABLE_ROW_POS);
                    for (int j = 0; j < llview.getChildCount(); j++) {
                        View view = llview.getChildAt(j);
                        CustomTextView textView = (CustomTextView) ((LinearLayout) view).getChildAt(1);
                        CustomTextView header = (CustomTextView) ((LinearLayout) view).getChildAt(0);
                        if (header.getTag().toString().contentEquals(ConditionValue)) {
                            value = textView.getText().toString().trim();
                            break;
                        }
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataViewerControls)) {
                    String dataViewerName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    if (ConditionValue.contains("_currentrow")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_currentrow"));
                    }
                    DataViewer dataViewer = (DataViewer) ((MainActivity) context).List_ControlClassObjects.get(dataViewerName);
//                    LinkedHashMap<String, List<String>> outputData = dataViewer.getOutputData();
                    DataViewerModelClass dataViewerModelClass = dataViewer.getDataViewerModel(AppConstants.dw_position);
                    if (ConditionValue.toLowerCase().contentEquals("dv_trans_id")) {
                        value = dataViewerModelClass.getDv_trans_id();
                    } else if (ConditionValue.toLowerCase().contentEquals("dv_header")) {
                        value = dataViewerModelClass.getHeading();
                    }

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_RequestOfflineData)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    //nk realm
                    if (RealmDBHelper.existTable(context, APIName)) {
                        List<String> vals = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                        if (vals.size() > 0) {
                            value = vals.get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }
                    //nk realm
                   /* LinkedHashMap<String, LinkedHashMap<String, List<String>>> apisList = AppConstants.GlobalObjects.getAPIsRequestOfflineData_ListHash();
                    if (apisList.containsKey(APIName.toLowerCase())) {
                        LinkedHashMap<String, List<String>> API_list = apisList.get(APIName);
                        if (API_list.containsKey(ConditionValue)) {
                            value = API_list.get(ConditionValue).get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataControls)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];

                    List<String> vals = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                    if (vals.size() > 0) {
                        value = vals.get(0);
                    } else {
                        value = "";
                    }
                    //nk realm : accessing only zero postion
                   /* LinkedHashMap<String, LinkedHashMap<String, List<String>>> apisList = AppConstants.GlobalObjects.getDataControls_ListHash();
                    if (apisList.containsKey(APIName.toLowerCase())) {
                        LinkedHashMap<String, List<String>> API_list = apisList.get(APIName);
                        if (API_list.containsKey(ConditionValue)) {
                            value = API_list.get(ConditionValue).get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }*/

                }
                /* else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataControls)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];

                    LinkedHashMap<String, LinkedHashMap<String, List<String>>> apisList = AppConstants.GlobalObjects.getDataControls_ListHash();

                    if (apisList.containsKey(APIName.toLowerCase())) {
                        LinkedHashMap<String, List<String>> API_list = apisList.get(APIName);
                        if (API_list.containsKey(ConditionValue)) {
                            value = API_list.get(ConditionValue).get(0);
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }

                } */
                else if (ValueType.equalsIgnoreCase(AppConstants.Global_ScanName)) {
                    ConditionValue = ValueStr.split("\\.")[1];
                    if (AppConstants.GlobalObjects.getScanQRCode_ListHash() != null &&
                            AppConstants.GlobalObjects.getScanQRCode_ListHash().containsKey(ConditionValue)) {
                        System.out.println("ConditionValue=" + ConditionValue);
                        value = AppConstants.GlobalObjects.getScanQRCode_ListHash().get(ConditionValue.toLowerCase());
                    } else {
                        value = "";
                    }

                } else {
                    ConditionValue = ValueType.split("\\.")[1];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    value = ConditionValue.trim();
                }

            } else {
                if (ValueStr != null && !ValueStr.trim().equals("") && ValueStr.trim().charAt(0) == 34) {
                    value = ValueStr.substring(1, ValueStr.length() - 1);
                } else {
                    value = ValueStr;
                }
            }
        } catch (Exception e) {
            value = "";
            improveException(context, "ImproveHelper", "getValueFromGlobalObject", e);
        }
        if (value == null) {
            value = "";
        }
        if (value.endsWith(".0")) {
            value = value.substring(0, value.length() - 2);
        }
        return value;
    }

    private static String getValueFromOfflineTable(Context context, String tableName, String columnName) {

        ImproveDataBase improveDataBase = new ImproveDataBase(context);
       /* String userId = tableName.split("_")[0];
        tableName = tableName.substring(userId.length());
        tableName = userId.toUpperCase()+tableName;*/
        return improveDataBase.getValueFromOfflineTable(tableName, columnName);

    }

    public static List<String> getValueListFromOfflineTable(Context context, String tableName, String columnName) {

        List<String> list = new ArrayList<>();
        ImproveDataBase improveDataBase = new ImproveDataBase(context);
       /* String userId = tableName.split("_")[0];
        tableName = tableName.substring(userId.length());
        tableName = userId.toUpperCase()+tableName;*/
        String result = improveDataBase.getValueFromOfflineTable(tableName, columnName);
        if (result != null && !result.contentEquals("")) {
            String[] array = result.split(",");
            list.addAll(Arrays.asList(array));
        }
        return list;

    }

    public static List<String> getListValueFromGlobalObject(Context context, String ValueStr) {
        String ValueType = "", ConditionValue = "", formName = "", tempStr = "";
        List<String> valueList = new ArrayList<String>();
        try {
            if (ValueStr.startsWith("(im:")) {
                if (AppConstants.IS_MULTI_FORM && !(AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM) || AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT))) {
                    formName = ValueStr.substring(4, ValueStr.indexOf("."));//(im:form 2.controlname.gender)
                    tempStr = ValueStr.substring(4 + formName.length() + 1);
                    ValueType = tempStr.substring(0, tempStr.indexOf("."));
                    ValueStr = ValueStr.substring(4 + formName.length() + 1, ValueStr.lastIndexOf(")"));
                } else {

                    ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                }

                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    String SubformName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    /*LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);
                    valueList = map_SubformData.get(ConditionValue);*/
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, SubformName, ConditionValue);

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    List<String>outputPaths=RealmDBHelper.getDataInRealResults(context,APIName+"_OutputPaths","Path",
                            new String[]{"KeyName"},new String[]{ConditionValue});
                    if(outputPaths.size()>0){
                        String outputMapped_ID=outputPaths.get(0);
                        String temp_TableName = APIName;
                        if (outputMapped_ID.contains("/")) {
                            String temp = outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")).replace("/", "_");
                            //outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")==-1?outputMapped_ID.length():outputMapped_ID.lastIndexOf("/")).replace("/", "_");//
                            temp_TableName = APIName + "_" + temp;
                        }
                        List<String> ldata = RealmDBHelper.getTableData(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, RealmTables.APIMapping.ActionIDWithTableName, temp_TableName);
                        if (ldata.size() > 0) {
                            String tableName = ldata.get(0);
                            String colName=outputMapped_ID.substring(outputMapped_ID.lastIndexOf("/") + 1);
                            if (RealmDBHelper.isModifyColNameExist(context,outputMapped_ID)) {
                                colName=tableName + "_" + colName;
                            }
                            valueList = RealmDBHelper.getSingleColDataInList(context, tableName, colName);
                        }
                       /* String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                        String tableName = APIName.substring(0, 9) + "_" + temp;
                        String colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                        colName = colName.startsWith("@") ? colName.substring(1) : colName;
                        valueList = RealmDBHelper.getSingleColDataInList(context, tableName, colName);*/
                    }
                    Log.d("realm :finalValue", valueList.toString());


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
                    Log.d("realm: finalValue", valueList.toString());
                    //nk realm:
                    /*LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName.toLowerCase());
                    if (!formName.contentEquals("")) {
                        map_FormData = AppConstants.GlobalObjects.getAllFormsList_Hash().get(formName.toLowerCase()).get(FormName.toLowerCase());
                    }
                    if (map_FormData.containsKey(ConditionValue)) {
                        valueList = map_FormData.get(ConditionValue);
                    }*/
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    String QueryName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);
                    Log.d("realm: finalValue", valueList.toString());
                    //nk realm:
                   /* LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                    if (map_QueryData.containsKey(ConditionValue)) {
                        valueList = map_QueryData.get(ConditionValue);
                    }*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_variable)) {
                    String VariableType = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    List<Variable_Bean> list_variables = ((MainActivity) context).list_Variables;
                    if (VariableType.equalsIgnoreCase("Single")) {
                        for (int i = 0; i < list_variables.size(); i++) {
                            if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
                                valueList.add(list_variables.get(i).getVariable_singleValue());
                            }
                        }
                    } else {
                        for (int i = 0; i < list_variables.size(); i++) {
                            if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
                                valueList = list_variables.get(i).getVariable_multiValue();
                            }
                        }
                    }
                } else {
                    valueList.add(getValueFromGlobalObject(context, ValueStr));
                }

            } else {
                if (ValueStr.trim().charAt(0) == 34) {
                    valueList.add(ValueStr.substring(1, ValueStr.length() - 1));
                } else {
                    valueList.add(ValueStr);
                }
            }
        } catch (Exception e) {

        }
        return valueList;
    }

    public static String getvaluefromView_Old(View view, ControlObject cntrlObject, LinkedHashMap<String, Object> List_ControlClassObjects) {
        String value = "";

        switch (cntrlObject.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                CustomEditText ce_TextType = textInput.getCustomEditText();
                value = ce_TextType.getText().toString().trim();
                if (value.contentEquals("Tap here to Scan QR Code") || value.contentEquals("Tap here to Scan BarCode")) {
                    value = "";
                }
                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = numericInput.getTextValue();
                if (value.contentEquals("")) {
                    value = "0.0";
                }
                break;
            case CONTROL_TYPE_PHONE:
                Phone phone = (Phone) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = phone.getTextValue();
                break;
            case CONTROL_TYPE_EMAIL:
                Email email = (Email) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = email.getTextValue();
                break;
            case CONTROL_TYPE_LARGE_INPUT:
                CustomEditText ce_LargeType = view.findViewById(R.id.ce_TextType);
                value = ce_LargeType.getText().toString().trim();
                if (cntrlObject.isHtmlEditorEnabled()) {
                    RichTextEditor editor = view.findViewById(R.id.text_editor);
                    value = editor.getHtml();
                }
                break;
            case CONTROL_TYPE_DYNAMIC_LABEL:
                CustomTextView tv_dynamicLabel = view.findViewById(R.id.tv_dynamicLabel);
                value = tv_dynamicLabel.getText().toString().trim();
                if (cntrlObject.isHtmlViewerEnabled()) {
                    RichTextEditor editor = view.findViewById(R.id.text_editor);
                    value = editor.getHtml();
                }
                break;
            case CONTROL_TYPE_CHECKBOX:
                LinearLayout ll_cb_main = view.findViewById(R.id.cb_container);
                CustomEditText ce_other = view.findViewById(R.id.ce_otherchoice);
                for (int i = 0; i < ll_cb_main.getChildCount(); i++) {
                    CheckBox cb_item = (CheckBox) ll_cb_main.getChildAt(i);
                    /*   if (cb_item.isChecked()) {*/
                    if (cb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                        value = value + "," + ce_other.getText().toString().trim();
                    } else {
                        value = value + "," + cb_item.getText().toString().trim();
                    }
                    //}
                }
                value = value.substring(1);
                break;
            case CONTROL_TYPE_CALENDER:
                com.bhargo.user.controls.standard.Calendar calendar = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(cntrlObject.getControlName());
                CustomEditText ce_CalenderType = view.findViewById(R.id.ce_TextType);
                value = ce_CalenderType.getText().toString().trim();
                if (fromMapExisting) {
                    value = calendar.getFinalDateWithServerFormat();
                }
                break;
            case CONTROL_TYPE_PERCENTAGE:
                CustomEditText ce_PercentageType = view.findViewById(R.id.ce_TextType);
                value = ce_PercentageType.getText().toString().trim();
                break;
            case CONTROL_TYPE_RADIO_BUTTON:
                RadioGroup rg_main = view.findViewById(R.id.rg_container);
                CustomEditText ce_rg_other = view.findViewById(R.id.ce_otherchoice);
                if (rg_main.getCheckedRadioButtonId() != -1) {
                    RadioButton rb_item = view.findViewById(rg_main.getCheckedRadioButtonId());
                    if (rb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                        value = ce_rg_other.getText().toString().trim() + "," + ce_rg_other.getText().toString().trim();
                    } else {
                        value = ((String) rb_item.getTag()).trim() + "," + rb_item.getText().toString().trim();
                    }
                }
                break;
            case CONTROL_TYPE_DROP_DOWN:
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(cntrlObject.getControlName());
                view = dropDown.getDropdown();
                SearchableSpinner searchableSpinner = view.findViewById(R.id.searchableSpinner_main);
                CustomEditText ce_dropother = view.findViewById(R.id.ce_otherchoice);
                if (searchableSpinner.getSelectedName() != null) {
                    if (searchableSpinner.getSelectedName().trim().equalsIgnoreCase("Other")) {
                        value = ce_dropother.getText().toString().trim() + "," + ce_dropother.getText().toString().trim();
                    } else {
                        value = searchableSpinner.getSelectedId().trim() + "," + searchableSpinner.getSelectedName().trim();
                    }
                }
                break;
            case CONTROL_TYPE_CHECK_LIST:
                SearchableMultiSpinner multiSearchableSpinner = view.findViewById(R.id.multiSearchableSpinner);
                List<String> Items = multiSearchableSpinner.getSelectedNames();
                List<String> ItemIds = multiSearchableSpinner.getSelectedIds();
                for (int i = 0; i < Items.size(); i++) {
                    value = value + "$" + Items.get(i) + "," + ItemIds.get(i);
                }
                value = value.substring(1);
                break;
            case CONTROL_TYPE_DECIMAL:
                CustomEditText ce_DecimalType = view.findViewById(R.id.ce_TextType);
                value = ce_DecimalType.getText().toString().trim();
                break;
            case CONTROL_TYPE_PASSWORD:
                CustomTextInputEditText ce_PasswordType = view.findViewById(R.id.tie_password);
                value = ce_PasswordType.getText().toString().trim();
                break;
            case CONTROL_TYPE_CURRENCY:
                CustomEditText ce_CurrType = view.findViewById(R.id.ce_TextType);
                value = ce_CurrType.getText().toString().trim();
                break;
            case CONTROL_TYPE_RATING:
                SmileRating smileRating = view.findViewById(R.id.smile_rating);
                RatingBar ratingBar = view.findViewById(R.id.ratingStar);

                if (cntrlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                    value = "" + smileRating.getRating();
                } else {
                    value = "" + ratingBar.getRating();
                }
                break;
            case CONTROL_TYPE_GPS:
                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(cntrlObject.getControlName());
                List<LatLng> latLngList = controlGPS.getLatLngList();
                for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                    LatLng latlang = latLngList.get(i);
                    value = value + " ^" + latlang.latitude + "$" + latlang.longitude;
                }
//                value = "\""+value.substring(value.indexOf("^") + 1)+"\"";
                value = value.substring(value.indexOf("^") + 1);
                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(cntrlObject.getControlName());
                String campath = camera.getControlRealPath().getTag().toString();
                if (campath.trim().length() > 0) {
                    if (campath.contains("http")) {
                        Log.d("getvaluefromView1: ", campath);
                        value = campath;
                    } else {
                        Log.d("getvaluefromView2: ", campath);
//                        value = ImproveHelper.sgetImageStringFromBitmap(campath, 500, 500);
                        value = campath;
                    }
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(cntrlObject.getControlName());
                String filepath = fileBrowsing.getControlRealPath().getTag().toString();
                if (filepath.trim().length() > 0) {
//                    if (filepath.contains("http")) {
                    value = filepath;

//                    } else {
//                        value = "";
//                    }
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_IMAGE:
                Image imageView = (Image) List_ControlClassObjects.get(cntrlObject.getControlName());
                String Imagepath = imageView.ImagePath;
                if (Imagepath.trim().length() > 0) {
                    value = ImproveHelper.sgetImageStringFromBitmap(Imagepath, 500, 500);
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_DATA_CONTROL:
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (dataControl.dropdownItemIsSelected()) {
                    value = dataControl.getSelectedDropDownItemId() + "," + dataControl.getSelectedDropDownItemName();
                    Log.d("Result", value);
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_CALENDAR_EVENT:
                CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(cntrlObject.getControlName());

                value = calendarEventControl.getSelectedDateandMessage();

                break;
            case CONTROL_TYPE_DATA_VIEWER:
                DataViewer DataViewer = (DataViewer) List_ControlClassObjects.get(cntrlObject.getControlName());

                DataViewerModelClass DataViewerModelClass = DataViewer.customAdapter.getSelectedDataViewerModelClass();

                value = DataViewerModelClass.getHeading() + "^" + DataViewerModelClass.getSubHeading() + "^" +
                        DataViewerModelClass.getDescription() + "^" + DataViewerModelClass.getDateandTime() + "^" + DataViewerModelClass.getDv_trans_id();

                break;
            case CONTROL_TYPE_MAP:
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(cntrlObject.getControlName());

                value = mapControlView.getSelectedMarkerGPSPosition();
                break;
            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
//                value = autoCompletionControl.getAutoCompleteTextValue();
                value = autoCompletionControl.getSelectedItemValue().trim() + "," + autoCompletionControl.getSelectedText();
                break;
            case CONTROL_TYPE_TIME:
                Time time = (Time) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = time.getEditTextTimeValue();
                break;
            case CONTROL_TYPE_USER:
                UserControl userControl = (UserControl) List_ControlClassObjects.get(cntrlObject.getControlName());
//                value = autoCompletionControl.getAutoCompleteTextValue();
                value = userControl.getSelectedUserId().trim() + "," + userControl.getSelectedUserName();
                break;
            case CONTROL_TYPE_VIEWFILE:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = viewFileControl.getFileLink();
                break;
            case CONTROL_TYPE_AUTO_GENERATION:
                value = "";
                break;
        }
        return value;
    }

    public static String getvaluefromObject(ControlObject cntrlObject, LinkedHashMap<String, Object> List_ControlClassObjects) {
        String value = "";
        switch (cntrlObject.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = textInput.getTextValue();
                if (value.contentEquals("Tap here to Scan QR Code") || value.contentEquals("Tap here to Scan BarCode")) {
                    value = "";
                }
                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = numericInput.getTextValue();
                if (value.contentEquals("")) {
                    value = "0.0";
                }
                break;
            case CONTROL_TYPE_PHONE:
                Phone phone = (Phone) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = phone.getTextValue();
                break;
            case CONTROL_TYPE_EMAIL:
                Email email = (Email) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = email.getTextValue();
                break;
            case CONTROL_TYPE_LARGE_INPUT:
                LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = largeInput.getTextValue();
                if (cntrlObject.isHtmlEditorEnabled()) {
                    value = largeInput.getHtml();
                }
                break;
            case CONTROL_TYPE_DYNAMIC_LABEL:
                DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = dynamicLabel.getTextValue();
                if (cntrlObject.isHtmlViewerEnabled()) {
                    value = dynamicLabel.getHtml();
                }
                break;
            case CONTROL_TYPE_CHECKBOX:
                Checkbox checkbox = (Checkbox) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = checkbox.getSelectedNameCheckboxString();
                break;
            case CONTROL_TYPE_CALENDER:
                com.bhargo.user.controls.standard.Calendar calendar = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = calendar.getCalendarValue();
                if (fromMapExisting) {
                    value = calendar.getFinalDateWithServerFormat();
                }
                break;
            case CONTROL_TYPE_PERCENTAGE:
                Percentage percentage = (Percentage) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = percentage.getTextValue();
                break;
            case CONTROL_TYPE_RADIO_BUTTON:
                RadioGroupView radioGroupView = (RadioGroupView) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = radioGroupView.getSelectedRadioButtonString();

                break;
            case CONTROL_TYPE_DROP_DOWN:
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = dropDown.getSelectedDropDownString();
                break;
            case CONTROL_TYPE_CHECK_LIST:
                CheckList checkList = (CheckList) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = checkList.getSelectedCheckListString();
                break;
            case CONTROL_TYPE_DECIMAL:
                DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = decimalView.getTextValue();
                break;
            case CONTROL_TYPE_PASSWORD:
                Password password = (Password) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = password.getTextValue();
                break;
            case CONTROL_TYPE_CURRENCY:
                Currency currency = (Currency) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = currency.getTextValue();
                break;
            case CONTROL_TYPE_RATING:
                Rating rating = (Rating) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = rating.getSelectedRating();
                break;
            case CONTROL_TYPE_GPS:
                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = controlGPS.getGPSString();
                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (camera.getControlRealPath() != null && camera.getControlRealPath().getTag() != null) {
                    String campath = camera.getControlRealPath().getTag().toString();
                    if (campath.trim().length() > 0) {
                        if (campath.contains("http")) {
                            Log.d("getvaluefromView1: ", campath);
                            value = campath;
                        } else {
                            Log.d("getvaluefromView2: ", campath);
                            value = campath;
                        }
                    } else {
                        value = "";
                    }
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(cntrlObject.getControlName());
                String filepath = fileBrowsing.getControlRealPath().getTag().toString();
                if (filepath.trim().length() > 0) {
                    value = filepath;
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_IMAGE:
                Image imageView = (Image) List_ControlClassObjects.get(cntrlObject.getControlName());
                String Imagepath = imageView.ImagePath;
                if (Imagepath.trim().length() > 0) {
                    value = ImproveHelper.sgetImageStringFromBitmap(Imagepath, 500, 500);
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_DATA_CONTROL:
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (dataControl.dropdownItemIsSelected()) {
                    value = dataControl.getSelectedDropDownItemId() + "," + dataControl.getSelectedDropDownItemName();
                    Log.d("Result", value);
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_CALENDAR_EVENT:
                CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = calendarEventControl.getSelectedDateandMessage();

                break;
            case CONTROL_TYPE_DATA_VIEWER:
                DataViewer DataViewer = (DataViewer) List_ControlClassObjects.get(cntrlObject.getControlName());
                DataViewerModelClass DataViewerModelClass = DataViewer.customAdapter.getSelectedDataViewerModelClass();
                value = DataViewerModelClass.getHeading() + "^" + DataViewerModelClass.getSubHeading() + "^" +
                        DataViewerModelClass.getDescription() + "^" + DataViewerModelClass.getDateandTime() + "^" + DataViewerModelClass.getDv_trans_id();

                break;
            case CONTROL_TYPE_MAP:
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = mapControlView.getSelectedMarkerGPSPosition();
                break;
            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = autoCompletionControl.getSelectedItemValue().trim() + "," + autoCompletionControl.getSelectedText();
                break;
            case CONTROL_TYPE_TIME:
                Time time = (Time) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = time.getEditTextTimeValue();
                break;
            case CONTROL_TYPE_USER:
                UserControl userControl = (UserControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = userControl.getSelectedUserId().trim() + "," + userControl.getSelectedUserName();
                break;
            case CONTROL_TYPE_VIEWFILE:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = viewFileControl.getFileLink();
                break;
            case CONTROL_TYPE_AUTO_GENERATION:
                value = "";
                break;
        }
        return value;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isAppIsInBackground(Context context) {

        boolean isInBackground = true;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {

            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {

                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {

                    for (String activeProcess : processInfo.pkgList) {

                        if (activeProcess.equals(context.getPackageName())) {

                            isInBackground = false;

                        }

                    }

                }

            }

        } else {

            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

            ComponentName componentInfo = taskInfo.get(0).topActivity;

            if (componentInfo.getPackageName().equals(context.getPackageName())) {

                isInBackground = false;

            }

        }


        return isInBackground;

    }

    public static double deg2rad(double deg) {
        return (deg * pi / 180);
    }

    public static double rad2deg(double rad) {
        return (rad * 180 / pi);
    }

    public static boolean coordinate_is_inside_polygon(double latitude, double longitude, ArrayList<Double> lat_array, ArrayList<Double> long_array) {
        int i;
        double angle = 0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i = 0; i < n; i++) {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i + 1) % n) - latitude;
            //you should have paid more attention in high school geometry.
            point2_long = long_array.get((i + 1) % n) - longitude;
            angle += Angle2D(point1_lat, point1_long, point2_lat, point2_long);
        }

        return !(Math.abs(angle) < PI);
    }

    public static double Angle2D(double y1, double x1, double y2, double x2) {
        double dtheta, theta1, theta2;

        theta1 = Math.atan2(y1, x1);
        theta2 = Math.atan2(y2, x2);
        dtheta = theta2 - theta1;
        while (dtheta > PI)
            dtheta -= TWOPI;
        while (dtheta < -PI)
            dtheta += TWOPI;

        return (dtheta);
    }

    public static boolean isFileExitsInSDCard(String filename) {
        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();
    }

    public static boolean deleteFileIfExistsInSDCard(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.delete();

    }

    public static LinkedHashMap<String, List<String>> getsubformValues(Context context, String SubformName) {
        LinkedHashMap<String, List<String>> map_Data = new LinkedHashMap<String, List<String>>();

        SubformView subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
        List<ControlObject> list_Controls = subview.controlObject.getSubFormControlList();
        /*nk view LinearLayout ll_MainSubFormContainer = subview.getSubFormView().findViewById(R.id.ll_MainSubFormContainer);
        if (ll_MainSubFormContainer == null) {
            ll_MainSubFormContainer = subview.getSubFormView().findViewById(R.id.ll_grid_view);
        }*/


        for (int x = 0; x < subview.getSubFormRows(); x++) {
            LinkedHashMap<String, Object> List_ControlClassObjects = subview.subform_List_ControlClassObjects.get(x);

            /*nk view View innerView = ll_MainSubFormContainer.getChildAt(x);
            LinearLayout ll_innerSubFormContainer = innerView.findViewById(R.id.ll_innerSubFormContainer);
            if (ll_innerSubFormContainer == null) {
                ll_innerSubFormContainer = (LinearLayout) innerView;
            }*/
            for (int i = 0; i < list_Controls.size(); i++) {
                String value = "";
                ControlObject temp_controlObj = list_Controls.get(i);
                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                    //nk view ll_innerSubFormContainer.getChildAt(i),
                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                    value = value.substring(0, value.indexOf(","));


                } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CALENDAR_EVENT)) {
                    //nk view ll_innerSubFormContainer.getChildAt(i),
                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                    value = value.substring(0, value.indexOf("^"));
                } else {
                    //nk view ll_innerSubFormContainer.getChildAt(i),
                    value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                }

                if (map_Data.containsKey(temp_controlObj.getControlName())) {
                    List<String> ControlData = map_Data.get(temp_controlObj.getControlName());
                    ControlData.add(value);
                    map_Data.put(temp_controlObj.getControlName(), ControlData);
                } else {
                    List<String> ControlData = new ArrayList<>();
                    ControlData.add(value);
                    map_Data.put(temp_controlObj.getControlName(), ControlData);
                }

            }
        }


        return map_Data;
    }

    public static List<String> getListOfValuesFromGlobalObject(Context context, String ValueStr) {
        String ValueType = "", ConditionValue = "", formName = "", tempStr = "";
        List<String> valueList = new ArrayList<String>();
        try {
            HashMap<String, String> controlPositionInUI = new HashMap<>();
            if (context instanceof MainActivity) {
                controlPositionInUI = AppConstants.controlPositionInUIAllApps.get(((MainActivity) context).dataCollectionObject.getApp_Name());
            }

            ValueStr = ValueStr.toLowerCase();
            if (ValueStr.startsWith("(im:")) {
                if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                    formName = ValueStr.substring(4, ValueStr.indexOf("."));//(im:form 2.controlname.gender)
                    if(isValidValueType(formName)){
                        ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                    } else {
                        tempStr = ValueStr.substring(4 + formName.length() + 1);
                        ValueType = tempStr.substring(0, tempStr.indexOf("."));
                        ValueStr = ValueStr.substring(4 + formName.length() + 1, ValueStr.lastIndexOf(")"));
                    }
                } else {
                    ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                }
                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    String SubformName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    if (ConditionValue.endsWith("_allrows)")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_allrows)"));
                    } else if (ConditionValue.endsWith("_checkedrows)")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_checkedrows)"));
                        valueList.addAll(getCheckedBoxValues(context, SubformName, ConditionValue, true));
                        return valueList;
                    } else if (ConditionValue.endsWith("_uncheckedrows)")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_uncheckedrows)"));
                        valueList.addAll(getCheckedBoxValues(context, SubformName, ConditionValue, false));
                        return valueList;
                    } else {
                        if (ConditionValue.endsWith("_allrows")) {
                            ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_allrows"));
                        } else if (ConditionValue.endsWith("_checkedrows")) {
                            ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_checkedrows"));
                            valueList.addAll(getCheckedBoxValues(context, SubformName, ConditionValue, true));
                            return valueList;
                        } else if (ConditionValue.endsWith("_uncheckedrows")) {
                            ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_uncheckedrows"));
                            valueList.addAll(getCheckedBoxValues(context, SubformName, ConditionValue, false));
                            return valueList;
                        } else {
                            valueList.add(getValueFromGlobalObject(context, ValueStr));
                            return valueList;
                        }
                    }

                    SubformView subcontrol = null;
                    GridControl gridControl = null;
                    if (!formName.contentEquals("") && Global_Controls_Variables.containsKey(formName)) {
                        List<ControlObject> list_Controls = new ArrayList<>();
                        LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<>();
                        LinearLayout linearLayout = null;
                        list_Controls = Global_Single_Forms.get(formName).getControls_list();
                        List_ControlClassObjects = Global_Controls_Variables.get(formName);
                        linearLayout = AppConstants.Global_Layouts.get(formName);
                        //nk pending for gridcontrol case
                        subcontrol = (SubformView) List_ControlClassObjects.get(SubformName);
                    }/*else
                    if (AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)) {
                        subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                    }*/ else {
                        if (((MainActivity) context).List_ControlClassObjects.get(SubformName).toString().contains("Grid")) {
                            gridControl = (GridControl) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                        } else {
                            subcontrol = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(SubformName);
                        }

                    }
                    List<ControlObject> list_Controls = new ArrayList<>();
                    if (subcontrol != null) {
                        list_Controls = subcontrol.controlObject.getSubFormControlList();
                        for (int x = 0; x < subcontrol.getSubFormRows(); x++) {
                            LinkedHashMap<String, Object> List_ControlClassObjects = subcontrol.subform_List_ControlClassObjects.get(x);
                            int i = 0;
                            for (int a = 0; a < list_Controls.size(); a++) {
                                i = a;
                                ControlObject temp_controlObj = list_Controls.get(a);
                                String controlPos = null;
                                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                                    if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        if (value.contains(",")) {
                                            value = value.substring(0, value.indexOf(","));
                                        }
                                        valueList.add(value);
                                    } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(value.indexOf(",") + 1);
                                        valueList.add(value);
                                        break;
                                    }

                                } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CALENDAR_EVENT)) {
                                    if ((temp_controlObj.getControlName().trim() + "_Date").equalsIgnoreCase(ConditionValue)) {
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(0, value.indexOf("^"));
                                        valueList.add(value);
                                    } else if ((temp_controlObj.getControlName().trim() + "_Message").equalsIgnoreCase(ConditionValue)) {
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(value.indexOf("^") + 1);
                                        valueList.add(value);
                                    }
                                } else {
                                    if(ConditionValue.endsWith("_coordinates")){
                                        ConditionValue= ConditionValue.substring(0, ConditionValue.indexOf("_coordinates"));
                                    }
                                    if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        valueList.add(value);
                                        break;
                                    }
                                }


                            }
                        }
                    }
                    if (gridControl != null) {
                        list_Controls = gridControl.controlObject.getSubFormControlList();
                        for (int x = 0; x < gridControl.getSubFormRows(); x++) {
                            LinkedHashMap<String, Object> List_ControlClassObjects = gridControl.gridControl_List_ControlClassObjects.get(x);
                            int i = 0;
                            for (int a = 0; a < list_Controls.size(); a++) {
                                i = a;
                                ControlObject temp_controlObj = list_Controls.get(a);
                                String controlPos = null;

                                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                                    if ((temp_controlObj.getControlName().trim() + "_ID").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(0, value.indexOf(","));
                                        valueList.add(value);
                                    } else if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(value.indexOf(",") + 1);
                                        valueList.add(value);
                                        break;
                                    }

                                } else if (temp_controlObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CALENDAR_EVENT)) {
                                    if ((temp_controlObj.getControlName().trim() + "_Date").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(0, value.indexOf("^"));
                                        valueList.add(value);
                                    } else if ((temp_controlObj.getControlName().trim() + "_Message").equalsIgnoreCase(ConditionValue)) {
                                        //nk view ll_innerSubFormContainer.getChildAt(i),
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        value = value.substring(value.indexOf("^") + 1);
                                        valueList.add(value);
                                    }
                                } else {
                                    if(ConditionValue.endsWith("_coordinates")){
                                        ConditionValue= ConditionValue.substring(0, ConditionValue.lastIndexOf("_coordinates"));
                                    }
                                    if (temp_controlObj.getControlName().trim().equalsIgnoreCase(ConditionValue)) {
                                        String value = getvaluefromObject(temp_controlObj, List_ControlClassObjects);
                                        valueList.add(value);
                                        break;
                                    }
                                }
                            }
                        }
                    }


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    List<String>outputPaths=RealmDBHelper.getDataInRealResults(context,APIName+"_OutputPaths","Path",
                            new String[]{"KeyName"},new String[]{ConditionValue});
                    if(outputPaths.size()>0){
                        String outputMapped_ID=outputPaths.get(0);
                        String temp_TableName = APIName;
                        if (outputMapped_ID.contains("/")) {
                            String temp = outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")).replace("/", "_");
                            //outputMapped_ID.substring(0, outputMapped_ID.lastIndexOf("/")==-1?outputMapped_ID.length():outputMapped_ID.lastIndexOf("/")).replace("/", "_");//
                            temp_TableName = APIName + "_" + temp;
                        }
                        List<String> ldata = RealmDBHelper.getTableData(context, RealmTables.APIMapping.TABLE_NAME, RealmTables.APIMapping.MapppingID, RealmTables.APIMapping.ActionIDWithTableName, temp_TableName);
                        if (ldata.size() > 0) {
                            String tableName = ldata.get(0);
                            String colName=outputMapped_ID.substring(outputMapped_ID.lastIndexOf("/") + 1);
                            if (RealmDBHelper.isModifyColNameExist(context,outputMapped_ID)) {
                                colName=tableName + "_" + colName;
                            }
                            valueList = RealmDBHelper.getColumnDataInList(context, tableName, colName);
                        }

                        /*//old process
                        String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                        String tableName = APIName.substring(0, 9) + "_" + temp;
                        String colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                        colName = colName.startsWith("@") ? colName.substring(1) : colName;
                        valueList = RealmDBHelper.getColumnDataInList(context, tableName, colName);*/
                    }
                    Log.d("realm :finalValue", valueList.toString());
                    //nk realm: (im:api.2023032816112018727cedbf95915.temp_c1)
                  /*  LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);
*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
                    Log.d("realm: finalValue", valueList.toString());

                    /*LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
                    valueList = map_FormData.get(ConditionValue);
*/
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    String QueryName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);
                    Log.d("realm :finalValue", valueList.toString());
                    //nk realm:
                    /*LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                    valueList = map_QueryData.get(ConditionValue);
*/
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_variable)) {
                    String VariableType = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    List<Variable_Bean> list_variables = new ArrayList<>();
                    if (AppConstants.IS_MULTI_FORM) {
                        list_variables = ((MainActivity) context).list_Variables;
                    } else {
                        list_variables = ((MainActivity) context).list_Variables;
                    }
                    if (VariableType.equalsIgnoreCase("Single")) {
                        for (int i = 0; i < list_variables.size(); i++) {
                            if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
                                if (list_variables.get(i).getVariable_singleValue() != null) {
                                    valueList.add(list_variables.get(i).getVariable_singleValue());
                                } else {
                                    valueList.add("");
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < list_variables.size(); i++) {
                            if (list_variables.get(i).getVariable_Name().equalsIgnoreCase(ConditionValue)) {
                                if (list_variables.get(i).getVariable_multiValue() != null) {
                                    valueList = list_variables.get(i).getVariable_multiValue();
                                }

                            }
                        }
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataTableControls)) {
                    /* if (ConditionValue.endsWith("_allrows)")) {*/
                    String DataTableName = ValueStr.split("\\.")[1];
                    String[] array = ValueStr.split("\\.");
                    ConditionValue = ValueStr.split("\\.")[array.length - 1];
                    boolean Allrows = false;
                    if (ConditionValue.contains("_currentrow")) {
                        valueList.add(getValueFromGlobalObject(context, ValueStr));
                        return valueList;
//                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_currentrow"));
                    } else if (ConditionValue.contains("_processrow")) {
                        valueList.add(getValueFromGlobalObject(context, ValueStr));
                        return valueList;
//                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_processrow"));
                    } else if (ConditionValue.contains("_allrows")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_allrows"));
                        Allrows = true;
                    }
                    DataTableControl DataTableView = (DataTableControl) ((MainActivity) context).List_ControlClassObjects.get(DataTableName);
                    TableLayout ll_data_table_main = DataTableView.tl_body;
                    TableRow llview = null;

                    for (int i = 0; i < ll_data_table_main.getChildCount(); i++) {
                        llview = (TableRow) ll_data_table_main.getChildAt(i);
                        for (int j = 0; j < llview.getChildCount(); j++) {
                            View view = llview.getChildAt(j);
                            CustomTextView textView = (CustomTextView) ((LinearLayout) view).getChildAt(1);
                            CustomTextView header = (CustomTextView) ((LinearLayout) view).getChildAt(0);
                            if (header.getTag().toString().contentEquals(ConditionValue)) {
                                valueList.add(textView.getText().toString().trim());
                                break;
                            }

                        }


                    }


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataViewerControls)) {
                    boolean Allrows = false;
                    String dataViewerName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    if (ConditionValue.contains("_currentrow")) {
                        valueList.add(getValueFromGlobalObject(context, ValueStr));
                        return valueList;
                    } else if (ConditionValue.contains("_allrows")) {
                        ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf("_allrows"));
                        Allrows = true;
                    }
                    DataViewer dataViewer = (DataViewer) ((MainActivity) context).List_ControlClassObjects.get(dataViewerName);
                    LinkedHashMap<String, List<String>> outputData = dataViewer.getOutputData();
                    valueList.addAll(outputData.get(ConditionValue.toLowerCase()));


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_RequestOfflineData)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                    /*LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIsRequestOfflineData_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);*/
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataControls)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    //nk realm:
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                   /* LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getDataControls_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);*/
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return valueList;
    }

    public static List<String> getCheckedBoxValues(Context context, String subFormName, String conditionValue, boolean isChecked) {
        List<String> valuesList = new ArrayList<>();

        SubformView subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(subFormName);
        List<ControlObject> list_Controls = subview.controlObject.getSubFormControlList();


        LinearLayout ll_MainSubFormContainer = subview.getSubFormView().findViewById(R.id.ll_MainSubFormContainer);
        if (ll_MainSubFormContainer == null) {
            ll_MainSubFormContainer = subview.getSubFormView().findViewById(R.id.ll_grid_view);
        }


        for (int x = 0; x < ll_MainSubFormContainer.getChildCount(); x++) {
            LinkedHashMap<String, Object> List_ControlClassObjects = subview.subform_List_ControlClassObjects.get(x);

            View innerView = ll_MainSubFormContainer.getChildAt(x);

            LinearLayout ll_innerSubFormContainer = innerView.findViewById(R.id.ll_innerSubFormContainer);
            if (ll_innerSubFormContainer == null) {
                ll_innerSubFormContainer = (LinearLayout) innerView;
            }
            for (int i = 0; i < list_Controls.size(); i++) {
                ControlObject temp_controlObj = list_Controls.get(i);
                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)) {
                    if (temp_controlObj.getControlName().trim().equalsIgnoreCase(conditionValue)) {
                        String value = "";
                        View view = ll_innerSubFormContainer.getChildAt(i);
                        LinearLayout ll_cb_main = view.findViewById(R.id.cb_container);
                        CustomEditText ce_other = view.findViewById(R.id.ce_otherchoice);
                        for (int j = 0; j < ll_cb_main.getChildCount(); j++) {
                            CheckBox cb_item = (CheckBox) ll_cb_main.getChildAt(j);
                            if (isChecked) {
                                if (cb_item.isChecked()) {
                                    if (cb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                                        value = value + "," + ce_other.getText().toString().trim();
                                    } else {
                                        value = value + "," + cb_item.getText().toString().trim();
                                    }
                                }
                            } else {
                                if (!cb_item.isChecked()) {
                                    if (cb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                                        value = value + "," + ce_other.getText().toString().trim();
                                    } else {
                                        value = value + "," + cb_item.getText().toString().trim();
                                    }
                                }
                            }
                        }
                        if (!value.contentEquals("")) {
                            value = value.substring(1);
                            valuesList.add(value);
                        }
                        break;
                    }

                }


            }
        }
        return valuesList;
    }

    public static List<String> getUnCheckedValuesList(Context context, String conditionValue) {
        List<String> valuesList = new ArrayList<>();
        return valuesList;
    }

    public static boolean isFileExists(String filename) {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder.exists();
    }

    public static boolean isFileExistsInPackage(String filename) {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder.exists();

    }

    public static String getMyDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void BlinkAnimation(View view) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        view.startAnimation(animation);
    }

    public static List<String> getInputParams(String SuccessCaseDetails) {
        List<String> inputs = new ArrayList<String>();
        try {
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            JSONObject inputObject = jobj.getJSONObject("InputParameters");

            JSONArray inputParamJArr = inputObject.getJSONArray("Input Parameters");
            JSONObject AuthorizationObject = inputObject.getJSONObject("Authorization");
            JSONArray HeaderJArr = inputObject.getJSONArray("Header");
            JSONObject BodyObject = inputObject.getJSONObject("Body");

            for (int i = 0; i < inputParamJArr.length(); i++) {
                inputs.add(inputParamJArr.getJSONObject(i).getString("KeyName"));
            }

            for (int i = 0; i < HeaderJArr.length(); i++) {
                inputs.add(HeaderJArr.getJSONObject(i).getString("KeyName"));
            }


        } catch (JSONException e) {
            System.out.println("Error At getInputParams=" + e);
        }
        return inputs;
    }

    public static List<String> getOutputParams(String SuccessCaseDetails) {
        List<String> outputs = new ArrayList<String>();
        try {
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            JSONArray outputArr = jobj.getJSONArray("OutputParameters");
            for (int i = 0; i < outputArr.length(); i++) {
                outputs.add(outputArr.getJSONObject(i).getString("KeyName"));
            }

            System.out.println(outputs.size());

        } catch (JSONException e) {
            System.out.println("Error At getInputParams=" + e);
        }
        return outputs;
    }

    public static LinkedHashMap<String, String> getOutputParamswithPaths(String SuccessCaseDetails) {
        LinkedHashMap<String, String> OutputPaths = new LinkedHashMap<String, String>();
        try {
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            JSONArray outputArr = jobj.getJSONArray("OutputParameters");
            for (int i = 0; i < outputArr.length(); i++) {
                OutputPaths.put(outputArr.getJSONObject(i).getString("KeyName"), outputArr.getJSONObject(i).getString("Path"));
            }

        } catch (JSONException e) {
            System.out.println("Error At getInputParams=" + e);
        }
        return OutputPaths;
    }

    public static LinkedHashMap<String, String> getHeaders(String SuccessCaseDetails) {
        LinkedHashMap<String, String> headersMap = new LinkedHashMap<String, String>();
        try {
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            JSONObject inputObject = jobj.getJSONObject("InputParameters");
            JSONArray HeaderJArr = inputObject.getJSONArray("Header");
            for (int i = 0; i < HeaderJArr.length(); i++) {
                JSONObject hObj = HeaderJArr.getJSONObject(i);
                headersMap.put(hObj.getString("KeyName"), hObj.getString("KeyValue"));
            }

        } catch (JSONException e) {
            System.out.println("Error At getInputParams=" + e);
        }
        return headersMap;
    }

    public static String gethdOutputSaveFlow(String SuccessCaseDetails) {
        String hdOutputSaveFlow = "";
        try {
            JSONObject jobj = new JSONObject(SuccessCaseDetails);
            hdOutputSaveFlow = jobj.getString("hdOutputSaveFlow");


        } catch (JSONException e) {
            System.out.println("Error At getInputParams=" + e);
        }
        return hdOutputSaveFlow;
    }

    public static void saveLocale(Context context, String language) {
        SharedPreferences preferences = context.getSharedPreferences("localePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", language);
        editor.apply();
    }

    public static String getLocale(Context context) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("localePref", Context.MODE_PRIVATE);
            return preferences.getString("language", "");
        } else {
            return "en";
        }
    }

    public static void saveTempLocale(Context context, String language) {
        SharedPreferences preferences = context.getSharedPreferences("localePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tempLanguage", language);
        editor.apply();
    }

    public static String getTempLocale(Context context) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("localePref", Context.MODE_PRIVATE);
            return preferences.getString("tempLanguage", "");
        } else {
            return "";
        }
    }

    public static void changeLanguage(Context context, String langCode) {
        Locale locale;
        locale = new Locale(langCode);
        Configuration config = new Configuration();
        Locale.setDefault(locale);
        config.setLocale(locale);

        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
//        ((Activity)context).recreate();
    }

    public static String getSelectedId(String selectedName, ControlObject controlObject) {
        String selectedId = "";
        List<Item> itemList = controlObject.getItemsList();
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            if (selectedName.contentEquals(item.getValue())) {
                selectedId = item.getId();
                break;
            }

        }
        return selectedId;
    }

    public static String currentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String strDate = sdfDate.format(new Date());
        return strDate;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String strDate = sdfDate.format(new Date());
        return strDate;
    }
    public static String getCurrentTime(String strTimeFormatOptions) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(strTimeFormatOptions, Locale.getDefault());
        String strDate = sdfDate.format(new Date());
        return strDate;
    }

    public static String getCurrentDateFromHelper() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String strDate = sdfDate.format(new Date());
        return strDate;
    }

    public static void loadImageForDataViewer(Context context, String url, ImageView view, boolean profilepic) {
        if (profilepic) {
            Glide.with(context).load(url).placeholder(context.getDrawable(R.drawable.ic_time_line_in_progress)).into(view);
//            Glide.with(context).load(url).placeholder(context.getDrawable(R.drawable.icon_individual)).into(view);
        } else {
            Glide.with(context).load(url).placeholder(context.getDrawable(R.drawable.image_not_found)).into(view);
        }
    }

    public static void loadImage_new(Context context, String url, ImageView view, boolean profilepic, String defaultUrl) {

        if (profilepic) {
            Glide.with(context).load(url).placeholder(context.getDrawable(R.drawable.icon_individual))
                    .thumbnail(Glide.with(context).load(defaultUrl)).into(view);
        } else {

           /* Glide.with(context)
                    .load(url)
                    .thumbnail(0.25f)
                    .into(view);*/

           /* final ProgressBar progressBar = new ProgressBar(context);

            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(view);*/

            /*            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();*/
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(getCircularProgressViw(context));
            requestOptions.error(R.drawable.image_not_found);
            requestOptions.skipMemoryCache(true);
            requestOptions.fitCenter();
            Glide.with(context).load(url).thumbnail(Glide.with(context).load(defaultUrl)).apply(requestOptions).into(view);
            /* Glide.with(context).load(url).placeholder(context.getDrawable(R.drawable.image_not_found))
                    .thumbnail(Glide.with(context).load(defaultUrl)).into(view);*/
        }


    }

    public static String getYoutubeID(String youtubeUrl) {

        if (TextUtils.isEmpty(youtubeUrl)) {
            return "";
        }
        String video_id = "";

        String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        CharSequence input = youtubeUrl;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String groupIndex1 = matcher.group(7);
            if (groupIndex1 != null && groupIndex1.length() == 11)
                video_id = groupIndex1;
        }
        if (TextUtils.isEmpty(video_id)) {
            if (youtubeUrl.contains("youtu.be/")) {
                String spl = youtubeUrl.split("youtu.be/")[1];
                if (spl.contains("\\?")) {
                    video_id = spl.split("\\?")[0];
                } else {
                    video_id = spl;
                }

            }
        }

        return video_id;
    }

    public static void navigateInMultiForm(Context context, AppDetails appDetails) {
        SessionManager sessionManager = new SessionManager(context);
        Intent intent = new Intent(context, ViewDataActivity.class);
        intent.putExtra("s_app_design", appDetails.getDesignFormat());
        intent.putExtra("s_app_version", appDetails.getAppVersion());
        intent.putExtra("s_app_type", "Datacollection");
        intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
        intent.putExtra("s_app_name", appDetails.getAppName());
        intent.putExtra("s_created_by", appDetails.getCreatedBy());
        intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
        intent.putExtra("s_distribution_id", appDetails.getDistrubutionID());
        intent.putExtra("s_user_post_id", appDetails.getPostID());
        context.startActivity(intent);

    }

    public static String getOparator(String Oparator) {
        String returnOparator = "=";

        if (Oparator.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
            returnOparator = "=";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
            returnOparator = "!=";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {
            returnOparator = "<";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {
            returnOparator = ">";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_LessThanEqualsTo)) {
            returnOparator = "<=";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_GreaterThanEqualsTo)) {
            returnOparator = ">=";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_Contains)) {
            returnOparator = "CONTAINS";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_StartsWith)) {
            returnOparator = "%STARTSWITH";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_EndsWith)) {
            returnOparator = "%ENDSWITH";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_IsNull)) {
            returnOparator = "IS NULL";
        } else if (Oparator.equalsIgnoreCase(AppConstants.Conditions_IsNotNull)) {
            returnOparator = "NOT NULL";
        }


        return returnOparator;
    }

    public static String getTableNameWithOutSpace(String tableName) {

        if (tableName.split(" ").length > 0) {

            return tableName.replace(" ", "_");
        } else {
            return tableName;
        }

    }

    public static int getMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;
    }

    public static String getControlName(String globalVariableOutput) {
        String controlName = "";
        if (globalVariableOutput.toLowerCase().contains("(im:")) {
            String ControlType = globalVariableOutput.substring(globalVariableOutput.indexOf(":") + 1, globalVariableOutput.indexOf("."));
            if (ControlType.equalsIgnoreCase("ControlName")) {
                controlName = globalVariableOutput.substring(globalVariableOutput.indexOf(".") + 1, globalVariableOutput.indexOf(")"));
            } else if (ControlType.equalsIgnoreCase("SubControls")) {
                int first = globalVariableOutput.indexOf(".") + 1;
                String controlName_ = globalVariableOutput.substring(globalVariableOutput.indexOf(".", first + 1), globalVariableOutput.indexOf(")"));
                controlName = controlName_.replaceFirst(".", "");
            }

        }
        return controlName;
    }

    public static String getControlNameForMapExisting(String globalVariableOutput) {
        String controlName = globalVariableOutput;
        if (globalVariableOutput.toLowerCase().contains("(im:")) {
            String ControlType = globalVariableOutput.substring(globalVariableOutput.indexOf(":") + 1, globalVariableOutput.indexOf("."));
            if (ControlType.equalsIgnoreCase("ControlName")) {
                controlName = globalVariableOutput.substring(globalVariableOutput.indexOf(".") + 1, globalVariableOutput.indexOf(")"));
            } else if (ControlType.equalsIgnoreCase("SubControls")) {
                int first = globalVariableOutput.indexOf(".") + 1;
                String controlName_ = globalVariableOutput.substring(globalVariableOutput.indexOf(".", first + 1), globalVariableOutput.indexOf(")"));
                controlName = controlName_.replaceFirst(".", "");
            }

        }
        return controlName;
    }

    // Function to remove duplicates from an List
    public static <T> List<T> removeDuplicates(List<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }

    public static String spilitandgetcolumnname(String Data) {
        String Conetentvalue="",controlname ="";
        if(Data.toLowerCase().startsWith("(im:")) {
            Conetentvalue = Data.substring(4, Data.lastIndexOf(")"));
            controlname = Conetentvalue.split("\\.")[2].toLowerCase();
            controlname = Conetentvalue.substring(Conetentvalue.lastIndexOf(".") + 1);
        }else {
            controlname = Data;
        }
//        String controlname = Data;
        if (controlname.toLowerCase().endsWith("_allrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_processrow")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_checkedrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_uncheckedrows")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else if (controlname.toLowerCase().endsWith("_allcolumns")) {
            controlname = controlname.substring(0, controlname.lastIndexOf("_"));
        } else {
            controlname = controlname;
        }


        return controlname;
    }

    public static Bitmap getBitmapFromURL(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static boolean checkMainFormHasSubFormColumns(String subFormName, DataCollectionObject dataCollectionObject) {
        boolean result = false;
        List<String> table_columns = dataCollectionObject.getList_Table_Columns();
        ControlObject subFormObject = null;
        for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
            if (dataCollectionObject.getControls_list().get(i).getControlName().contentEquals(subFormName)) {
                subFormObject = dataCollectionObject.getControls_list().get(i);
                break;
            }
        }
        for (int j = 0; j < subFormObject.getSubFormControlList().size(); j++) {
            if (table_columns != null && table_columns.contains(subFormObject.getSubFormControlList().get(j).getControlName())) {
                result = true;
            }
        }
        return result;
    }

    public static String randomColor() {
        String color = "#000000";
        String[] colors = {
                "#003f5c",
                "#2f4b7c",
                "#665191",
                "#a05195",
                "#d45087",
                "#f95d6a",
                "#ff7c43",
                "#ffa600",
                "#00876c",
                "#439981",
                "#6aaa96",
                "#8cbcac",
                "#aecdc2",
                "#cfdfd9",
                "#f1f1f1",
                "#f1d4d4",
                "#f0b8b8",
                "#ec9c9d",
                "#e67f83",
                "#de6069",
                "#d43d51",
        };


        Random random = new Random();
        int index = random.nextInt(colors.length);
        color = colors[index];
        return color;
    }

    public static int lock_match(String s, String t) {


        int totalw = word_count(s);
        int total = 100;
        int perw = total / totalw;
        int gotperw = 0;

        if (!s.equals(t)) {

            for (int i = 1; i <= totalw; i++) {
                if (simple_match(split_string(s, i), t) == 1) {
                    gotperw = ((perw * (total - 10)) / total) + gotperw;
                } else if (front_full_match(split_string(s, i), t) == 1) {
                    gotperw = ((perw * (total - 20)) / total) + gotperw;
                } else if (anywhere_match(split_string(s, i), t) == 1) {
                    gotperw = ((perw * (total - 30)) / total) + gotperw;
                } else {
                    gotperw = ((perw * smart_match(split_string(s, i), t)) / total) + gotperw;
                }
            }
        } else {
            gotperw = 100;
        }
        return gotperw;
    }

    public static int word_count(String s) {
        int x = 1;
        int c;
        s = s.trim();
        if (s.isEmpty()) {
            x = 0;
        } else {
            if (s.contains(" ")) {
                for (; ; ) {
                    x++;
                    c = s.indexOf(" ");
                    s = s.substring(c);
                    s = s.trim();
                    if (s.contains(" ")) {
                    } else {
                        break;
                    }
                }
            }
        }
        return x;
    }

    public static int simple_match(String s, String t) {
        int x = 0;
        String tempt;
        int len = s.length();


        //----------Work Body----------//
        for (int i = 1; i <= word_count(t); i++) {
            tempt = split_string(t, i);
            if (tempt.length() == s.length()) {
                if (s.contains(tempt)) {
                    x = 1;
                    break;
                }
            }
        }
        //---------END---------------//
        if (len == 0) {
            x = 0;
        }
        return x;
    }

    public static String split_string(String s, int n) {

        int index;
        String temp;
        temp = s;
        String temp2 = null;

        int temp3 = 0;

        for (int i = 0; i < n; i++) {
            int strlen = temp.length();
            index = temp.indexOf(" ");
            if (index < 0) {
                index = strlen;
            }
            temp2 = temp.substring(temp3, index);
            temp = temp.substring(index, strlen);
            temp = temp.trim();

        }
        return temp2;
    }

    public static int front_full_match(String s, String t) {
        int x = 0;
        String tempt;
        int len = s.length();

        //----------Work Body----------//
        for (int i = 1; i <= word_count(t); i++) {
            tempt = split_string(t, i);
            if (tempt.length() >= s.length()) {
                tempt = tempt.substring(0, len);
                if (s.contains(tempt)) {
                    x = 1;
                    break;
                }
            }
        }
        //---------END---------------//
        if (len == 0) {
            x = 0;
        }
        return x;
    }

    public static int anywhere_match(String s, String t) {
        int x = 0;
        if (t.contains(s)) {
            x = 1;
        }
        return x;
    }

    public static int smart_match(String ts, String tt) {

        char[] s = new char[ts.length()];
        s = ts.toCharArray();
        char[] t = new char[tt.length()];
        t = tt.toCharArray();


        int slen = s.length;
        //number of 3 combinations per word//
        int combs = (slen - 3) + 1;
        //percentage per combination of 3 characters//
        int ppc = 0;
        if (slen >= 3) {
            ppc = 100 / combs;
        }
        //initialising an integer to store the total % this class genrate//
        int x = 0;
        //declaring a temporary new source char array
        char[] ns = new char[3];
        //check if source char array has more then 3 characters//
        if (slen < 3) {
        } else {
            for (int i = 0; i < combs; i++) {
                for (int j = 0; j < 3; j++) {
                    ns[j] = s[j + i];
                }
                if (cross_full_match(ns, t) == 1) {
                    x = x + 1;
                }
            }
        }
        x = ppc * x;
        return x;
    }

    public static int cross_full_match(char[] s, char[] t) {
        int z = t.length - s.length;
        int x = 0;
        if (s.length > t.length) {
            return x;
        } else {
            for (int i = 0; i <= z; i++) {
                for (int j = 0; j <= (s.length - 1); j++) {
                    if (s[j] == t[j + i]) {
                        // x=1 if any charecer matches
                        x = 1;
                    } else {
                        // if x=0 mean an character do not matches and loop break out
                        x = 0;
                        break;
                    }
                }
                if (x == 1) {
                    break;
                }
            }
        }
        return x;
    }

    public static boolean checkSubFormHasMainFormColumns(String subFormName, @NonNull DataCollectionObject dataCollectionObject) {
        boolean result = false;

        ControlObject subFormObject = null;
        for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
            if (dataCollectionObject.getControls_list().get(i).getControlType().equalsIgnoreCase(CONTROL_TYPE_SECTION)) {
                List<ControlObject> sectionControlObjectList = dataCollectionObject.getControls_list().get(i).getSubFormControlList();
                for (int j = 0; j < sectionControlObjectList.size(); j++) {
                    if (sectionControlObjectList.get(j).getControlName().contentEquals(subFormName)) {
                        subFormObject = sectionControlObjectList.get(j);
                        break;
                    }
                }
            } else {
                if (dataCollectionObject.getControls_list().get(i).getControlName().contentEquals(subFormName)) {
                    subFormObject = dataCollectionObject.getControls_list().get(i);
                    break;
                }
            }
        }
        if (subFormObject.getSubFormtableSettingsObject() != null && subFormObject.getSubFormtableSettingsObject().mainFormInSubForm.get(subFormName) != null && !subFormObject.getSubFormtableSettingsObject().mainFormInSubForm.get(subFormName).equalsIgnoreCase("")) {
            result = true;
        }
        return result;
    }

    public static String checkAnySubFormHasMainFormColumns(DataCollectionObject dataCollectionObject) {
        String subFormName = null;
        String subFormWithMainForm = null;
        ControlObject subFormObject = null;
        for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
            if (dataCollectionObject.getControls_list().get(i).getControlType().contentEquals(CONTROL_TYPE_SUBFORM) || dataCollectionObject.getControls_list().get(i).getControlType().contentEquals(CONTROL_TYPE_GRID_CONTROL)) {
                subFormObject = dataCollectionObject.getControls_list().get(i);
                subFormName = dataCollectionObject.getControls_list().get(i).getControlName();
                if (subFormObject.getSubFormtableSettingsObject() != null && subFormObject.getSubFormtableSettingsObject().mainFormInSubForm.get(subFormName) != null && !subFormObject.getSubFormtableSettingsObject().mainFormInSubForm.get(subFormName).equalsIgnoreCase("")) {
                    subFormWithMainForm = subFormName;
                }
                break;
            }
        }

        return subFormWithMainForm;
    }

    public static List<String> getSubFormColumns(String subFormName, DataCollectionObject dataCollectionObject) {
        ControlObject subFormObject = null;
        for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
            if (dataCollectionObject.getControls_list().get(i).getControlType().equalsIgnoreCase(CONTROL_TYPE_SECTION)) {
                List<ControlObject> sectionControlObjectList = dataCollectionObject.getControls_list().get(i).getSubFormControlList();
                for (int j = 0; j < sectionControlObjectList.size(); j++) {
                    if (sectionControlObjectList.get(j).getControlName().contentEquals(subFormName)) {
                        subFormObject = sectionControlObjectList.get(j);
                        break;
                    }
                }
            } else {
                if (dataCollectionObject.getControls_list().get(i).getControlName().contentEquals(subFormName)) {
                    subFormObject = dataCollectionObject.getControls_list().get(i);
                    break;
                }
            }
        }
        return subFormObject.getSubFormList_Table_Columns();
    }

    public static List<String> checkAndGetSubFormColumns(DataCollectionObject dataCollectionObject) {
        List<String> subformColumns = new ArrayList<>();
        List<ControlObject> list_Control = dataCollectionObject.getControls_list();
        for (ControlObject controlObject : list_Control) {
            if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_SUBFORM) || controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {
                if (controlObject.getSubFormList_Table_Columns() != null && controlObject.getSubFormList_Table_Columns().size() > 0) {
                    subformColumns.addAll(controlObject.getSubFormList_Table_Columns());
                }
            }
        }
        return subformColumns;
    }

    public static HashMap<String, ControlObject> globalControlObjects(DataCollectionObject dataCollectionObject) {
        HashMap<String, ControlObject> controlObjectMap = new HashMap<>();
        List<ControlObject> controlObjectList = dataCollectionObject.getControls_list();
        for (int i = 0; i < controlObjectList.size(); i++) {

            ControlObject controlObject = controlObjectList.get(i);
            if (!controlObject.getControlName().equals("")) {
                /*if (controlObject.getControlType().contentEquals(CONTROL_TYPE_SECTION)) {
                    List<ControlObject> sectionControlList = controlObject.getSectionControlList();
                    if(sectionControlList != null && sectionControlList.size() > 0) {
                        for (int k = 0; k < sectionControlList.size(); k++) {
                            if (!sectionControlList.get(k).getControlName().equals("")) {
                                controlObjectMap.put(sectionControlList.get(k).getControlName(), sectionControlList.get(k));
                            }
                        }
                    }
                } else*/
                if (controlObject.getControlType().contentEquals(CONTROL_TYPE_SUBFORM) || controlObject.getControlType().contentEquals(CONTROL_TYPE_GRID_CONTROL)) {
                    List<ControlObject> subControlList = controlObjectList.get(i).getSubFormControlList();
                    controlObjectMap.put(controlObject.getControlName(), controlObject);
                    for (int j = 0; j < subControlList.size(); j++) {
                        if (!subControlList.get(j).getControlName().equals("")) {
                            controlObjectMap.put(subControlList.get(j).getControlName(), subControlList.get(j));
                        }
                    }
                } else {
                    controlObjectMap.put(controlObject.getControlName(), controlObject);
                }
            }
        }
        return controlObjectMap;
    }

    public static void writeExceptionDataToFile(Context context, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory() + "/ImproveUserFiles/", "ErrorLog");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "ImproveExceptionLog.txt");
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            String textStr = "";
            if (gpxfile.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(gpxfile));
                String line;
                while ((line = in.readLine()) != null) {
                    textStr = textStr + line;
                }
                in.close();
            }

            if (textStr.trim().length() > 0) {
                textStr = textStr + "\n\n" + sBody;
            } else {
                textStr = sBody;
            }

            FileWriter writer = new FileWriter(gpxfile);
            writer.write(textStr);
            writer.flush();
            writer.close();


//            if (isNetworkStatusAvialable(context)) {
////                SendErrorFile();
//            }


        } catch (IOException e) {
        }
    }

/*
    public void bothWrapContentAndDp(DataCollectionObject dataCollectionObject, ControlObject controlObject, LinearLayout linearLayout, String strControlType,
                                     String strControlName) {
        if (dataCollectionObject.getPrimaryLayoutModelClass() != null
                && dataCollectionObject.getPrimaryLayoutModelClass().getSubLayoutsModelClassHashMap() != null) {

            for (Integer name : dataCollectionObject.getPrimaryLayoutModelClass().getSubLayoutsModelClassHashMap().keySet()) {
                String key = name.toString();
                SubLayoutsModelClass modelClass = dataCollectionObject.getPrimaryLayoutModelClass().getSubLayoutsModelClassHashMap().get(name);
                System.out.println(key + " " + key);
                if (modelClass != null && modelClass.getLayoutDataAppearance().equalsIgnoreCase("both_wrap_content_dp")) {

//                    if (strControlType.equalsIgnoreCase(CONTROL_TYPE_DATA_VIEWER)) {
//                        linearLayout.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                linearLayout.getMeasuredHeight();
//                                Log.d("LinearMeasured", String.valueOf(linearLayout.getMeasuredHeight()));
//                                Log.d("LinearMeasuredModel", String.valueOf(((modelClass.getLayoutHeight() * screenHeight) / 100)));
//                                Log.d("LinearMeasuredHeight", String.valueOf(((linearLayout.getMeasuredHeight() * screenHeight)/100)));
//                                if (modelClass.getLayoutHeight() > 0 && linearLayout.getMeasuredHeight()  > ((modelClass.getLayoutHeight() * screenHeight) / 100)) {
//                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                                            (ViewGroup.LayoutParams.MATCH_PARENT, (modelClass.getLayoutHeight() * screenHeight) / 100);
//                                    linearLayout.setLayoutParams(layoutParams);
//                                } else {
//                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                    linearLayout.setLayoutParams(layoutParams);
//                                }
//                            }
//                        });
//                    }
                    if (strControlType.equalsIgnoreCase(CONTROL_TYPE_SUBFORM)) {
                        linearLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                linearLayout.getMeasuredHeight();
                                Log.d("LinearMeasured", String.valueOf(linearLayout.getMeasuredHeight()));
                                Log.d("LinearMeasuredModel", String.valueOf(((modelClass.getLayoutHeight() * screenHeight) / 100)));
                                Log.d("LinearMeasuredHeight", String.valueOf(((linearLayout.getMeasuredHeight() * screenHeight) / 100)));
                                if (modelClass.getLayoutHeight() > 0 && linearLayout.getMeasuredHeight() > ((modelClass.getLayoutHeight() * screenHeight) / 100)) {
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT, (modelClass.getLayoutHeight() * screenHeight) / 100);
                                    linearLayout.setLayoutParams(layoutParams);
                                } else {
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    linearLayout.setLayoutParams(layoutParams);
                                }
                            }
                        });
                    }


                }

            }

        }
    }
*/

    public static void improveException(Context context, String TAG, String methodName, Exception e) {
        String ExceptionData = "";
        ExceptionData = ImproveHelper.gettodaydate() + "|" + TAG + "|" + methodName + "|" + Log.getStackTraceString(e);
        System.out.println(ExceptionData);
        ImproveHelper.writeExceptionDataToFile(context, Log.getStackTraceString(e));
        //improveException(context,TAG,methodName,e);
        insertErrorLog(context, TAG,methodName,Log.getStackTraceString(e));

    }

    public static void improveLog(String TAG, String methodName, String log) {
        /*String ExceptionData = "";
        ExceptionData = ImproveHelper.gettodaydate() + "|" + TAG + "|" + methodName + "|" + log;

        ImproveHelper.LoggingTotextFile(ExceptionData);*/

    }

    public static void insertAccessLog(Context context,String details,String message){
        GetServices getServices = RetrofitUtils.getUserService();
        SessionManager sessionManager = new SessionManager(context);
        String userId = PrefManger.getSharedPreferencesString(context,AppConstants.SP_MOBILE_NO,"");
        String clientId = PrefManger.getSharedPreferencesString(context,AppConstants.SP_MOBILE_NO,"");
        if(sessionManager.getUserDataFromSession()!=null && sessionManager.getUserDataFromSession().getUserID()!=null){
            userId = sessionManager.getUserDataFromSession().getUserID();
            clientId = sessionManager.getDeviceIdFromSession();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loglevel","info");
            jsonObject.put("message",message);
            jsonObject.put("userid",userId);
            jsonObject.put("details",details);
            jsonObject.put("clientsource","Android");
            jsonObject.put("clientipaddress","");
            jsonObject.put("clientid",clientId);
            //jsonObject.put("trdate","");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObject jObj = (JsonObject) JsonParser.parseString(jsonObject.toString());

        Call<ResponseBody> call = getServices.insertAccessLog(jObj);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("accessLog",response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("accessLog",t.getMessage());
            }
        });

    }

    public static void insertErrorLog(Context context,String TAG, String methodName, String log){
        GetServices getServices = RetrofitUtils.getUserService();
        SessionManager sessionManager = new SessionManager(context);
        String userId = PrefManger.getSharedPreferencesString(context,AppConstants.SP_MOBILE_NO,"");
        String clientId = PrefManger.getSharedPreferencesString(context,AppConstants.SP_MOBILE_NO,"");
        if(sessionManager.getUserDataFromSession()!=null && sessionManager.getUserDataFromSession().getUserID()!=null){
            userId = sessionManager.getUserDataFromSession().getUserID();
            clientId = sessionManager.getDeviceIdFromSession();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loglevel","error");
            jsonObject.put("message",log);
            jsonObject.put("userid",userId);
            JSONObject detailsObject = new JSONObject();
            detailsObject.put("methodName",methodName);
            detailsObject.put("context",TAG);
            jsonObject.put("details",detailsObject);
            jsonObject.put("clientsource","Android");
            jsonObject.put("clientipaddress","");
            jsonObject.put("clientid",clientId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObject jObj = (JsonObject) JsonParser.parseString(jsonObject.toString());

        Call<ResponseBody> call = getServices.insertErrorLog(jObj);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("accessLog",response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("accessLog",t.getMessage());
            }
        });




    }

    public static void LoggingTotextFile(String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory() + "/ImproveUserFiles/", "ErrorLog");
            if (!root.exists()) {
                root.mkdirs();
            }
            String Datestr = gettodaydate();
            Datestr = Datestr.substring(0, Datestr.indexOf(" "));
            Datestr = Datestr.replace("/", "");

            File gpxfile = new File(root, Datestr + "_ImproveLogData.txt");
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            String textStr = "";
            if (gpxfile.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(gpxfile));
                String line;
                while ((line = in.readLine()) != null) {
                    textStr = textStr + line;
                }
                in.close();
            }

            if (textStr.trim().length() > 0) {
                textStr = textStr + "\n\n" + sBody;
            } else {
                textStr = sBody;
            }

            FileWriter writer = new FileWriter(gpxfile);
            writer.write(textStr);
            writer.flush();
            writer.close();


        } catch (IOException e) {
        }
    }

    public static void Controlflow(String Heading, String MainrootName, String CurrentrootName, String JSONData) {
       /* JSONObject jobj = new JSONObject();
        try {

            jobj.put("DateandTime", ImproveHelper.gettodaydate());
            jobj.put("MainRootName", MainrootName);
            jobj.put("CurrentRootName", CurrentrootName);
            jobj.put("Heading", Heading);
            jobj.put("Data", JSONData);

        } catch (JSONException e) {

        }
        ImproveHelper.ControlflowTotextFile(jobj.toString());*/

    }

    public static void ControlflowTotextFile(String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory() + "/ImproveUserFiles/", "ErrorLog");
            if (!root.exists()) {
                root.mkdirs();
            }
            String Datestr = gettodaydate();
            Datestr = Datestr.substring(0, Datestr.indexOf(" "));
            Datestr = Datestr.replace("/", "");

            File gpxfile = new File(root, Datestr + "_ControlFlow.txt");
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            String textStr = "";
            if (gpxfile.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(gpxfile));
                String line;
                while ((line = in.readLine()) != null) {
                    textStr = textStr + line;
                }
                in.close();
            }

            if (textStr.trim().length() > 0) {
                textStr = textStr + "\n\n" + sBody;
            } else {
                textStr = sBody;
            }

            FileWriter writer = new FileWriter(gpxfile);
            writer.write(textStr);
            writer.flush();
            writer.close();


        } catch (IOException e) {
        }
    }

    public static List<String> getStatementsfromInParams(List<API_InputParam_Bean> list_input) {
        List<String> list_Statements = new ArrayList<>();

        for (int i = 0; i < list_input.size(); i++) {
            if (!list_Statements.contains(list_input.get(i).getInParam_GroupDML_StatementName() + "|" + list_input.get(i).getInParam_GroupDML_Input_Type())) {
                list_Statements.add(list_input.get(i).getInParam_GroupDML_StatementName() + "|" + list_input.get(i).getInParam_GroupDML_Input_Type());
            }
        }

        return list_Statements;
    }

    public static int randomDrawable() {

        Integer[] drawables = {
                R.drawable.icon_attach,
                R.drawable.icon_audio,
                R.drawable.icon_camera,
                R.drawable.icon_chat,
                R.drawable.icon_dashboard,
                R.drawable.icon_dashboard_d,
                R.drawable.icon_document,
                R.drawable.icon_done,
                R.drawable.icon_e_cart,
                R.drawable.icon_edit,
                R.drawable.icon_gallery,
                R.drawable.icon_group,
                R.drawable.icon_individual,
                R.drawable.icon_info_grey,
                R.drawable.icon_location,
                R.drawable.icon_small_notfication,
                R.drawable.icon_tasks,
                R.drawable.icon_video,
                R.drawable.icon_videos,
                R.drawable.icons_barcode_drawable,
                R.drawable.icons_camera_drawable,
                R.drawable.icons_datetime_drawable,
                R.drawable.icons_email_drawable,
                R.drawable.icons_qrcode_drawable,
                R.drawable.icons_video_recording_96x96,
                R.drawable.icons_videoplayer_drawable,
                R.drawable.icons_videoplayer_update
        };

        Random random = new Random();
        int index = random.nextInt(drawables.length);
        return drawables[index];

    }

    public static File createFolder(Context context, String folderName) {

        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/ImproveScanDocuments"
        //Environment.getExternalStorageDirectory().getPath() + "/scanSample
//        String folderPath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+folderName;
        File basePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folderName);
//        File basePath = new File(folderPath);
        if (!basePath.exists()) {
            basePath.mkdirs();
            if (!basePath.mkdir()) {
                basePath.mkdirs();
            }
            Log.e("Folder", "Directory Created.");
        }
//        basePath = new File(folderPath);
        if (!basePath.isFile()) {
            if (!(basePath.isDirectory())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    /*try {*/
                       /* cDir = getApplicationContext().getExternalFilesDir(strFilepath);
                        appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileNameURl);*/
                    // getActivity().getApplicationContext().getExternalFilesDir(Paths.get(basePath.getAbsolutePath()));
                    context.getApplicationContext().getExternalFilesDir(basePath.getAbsolutePath());
                /*    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();


                    }*/
                } else {
                    basePath.mkdir();
                }
            }
        }

       /* File file = new File(ScanConstants.IMAGE_PATH, "IMG_" + timeStamp +
                ".jpg");
        fileUri = Uri.fromFile(file);*/
        return basePath;
    }

    public static void startFileDownload(Context context, String urlLink) {
        String[] urlSplit = urlLink.split("/");
        String fileName = urlSplit[urlSplit.length - 1];
        Uri uri = Uri.parse(urlLink);
        //Set Folder Path
        //File fileStorage = new File(Environment.getExternalStorageDirectory() + "/ImproveUserFiles/Download");
        File fileStorgeNew = createFolder(context, "ImproveUserFiles/Download");
        File outputFile = new File(fileStorgeNew, fileName);//Create Output file in Main File

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(fileName);
        request.setDescription("Downloading...");
        request.setDestinationUri(Uri.fromFile(outputFile));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long reference = manager.enqueue(request);


    }

    public static void sendMessageWithWhatsApp(Context context, String message) {
        PackageManager pm = context.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = message;

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            showToastAlert(context, "WhatsApp not Installed");

        } catch (Exception e) {
            e.printStackTrace();
            showToastAlert(context, "WhatsApp Failed :" + e.getMessage());
        }

    }

   /* public static void callToNumberWithPermission(Activity activity, String mobile_number) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && activity.checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            Dexter.withContext(activity)
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            String phoneNumber = mobile_number;
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + phoneNumber));
                            activity.startActivity(intent);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            showSettingsDialog(activity);
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    })
                    .withErrorListener(new PermissionRequestErrorListener() {
                        @Override
                        public void onError(DexterError dexterError) {
                            showToastAlert(activity, "Error occurred! " + dexterError.toString());
                        }
                    }).check();

        } else {
            String phoneNumber = mobile_number;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            activity.startActivity(intent);
        }

    }
*/
   /* private static void showSettingsDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings(activity);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }*/

    public static void sendPhoneNumberWithWhatsApp(Context context, String phoneNumber) {
        PackageManager pm = context.getPackageManager();
        try {


            String toNumber = "91" + phoneNumber; // Replace with mobile phone number without +Sign or leading zeros, but with country code.
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" +
                    toNumber + "?body=" + ""));
            sendIntent.setPackage("com.whatsapp");
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            showToastAlert(context, "it may be you dont have whats app");
        }
    }

    public static void sendPhoneNumberAndMessageWithWhatsApp(Context context, String phoneNumber, String message) {
        try {
            String text = message;// Replace with your message.

            String toNumber = "91" + phoneNumber; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callToNumber(Context context, String mobile_number) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context.checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            showToastAlert(context, "Phone Call Permission Denied. Try Again!");

        } else {
            String phoneNumber = mobile_number;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        }

    }

    // navigating user to app settings
    private static void openSettings(Activity activity) {


        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }

    public static void openGoogleMapsNavigationAToBPoints(Context context, double sourcelat, double sourcelog, double destlat, double destlog) {
        try {
            String googleMapsUrl = "http://maps.google.com/maps?saddr=" + sourcelat + "," + sourcelog + "&daddr=" + destlat + "," + destlog + "";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl));
            intent.setPackage("com.google.android.apps.maps");
            //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            context.startActivity(intent);
        } catch (Exception e) {
            showToastAlert(context, e.getMessage() + ". Try Again! ");
        }

    }

    public static void openGoogleMapsNavigationToBPoints(Context context, double latitude, double longitude) {
        try {
            String googleMapsUrl = "google.navigation:q=" + latitude + "," + longitude;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl));
            intent.setPackage("com.google.android.apps.maps");
            //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            context.startActivity(intent);
        } catch (Exception e) {
            showToastAlert(context, e.getMessage() + ". Try Again! ");
        }


    }

    public static void openGoogleMapsNavigationFromToPlace(Context context, String place) {
        try {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + place);

            Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            intent.setPackage("com.google.android.apps.maps");
            //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            context.startActivity(intent);
        } catch (Exception e) {
            showToastAlert(context, e.getMessage() + ". Try Again! ");
        }

    }

    public static void sendEmail(Context context, String[] TO, String[] CC, String subject, String email_message) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        // emailIntent.setData(Uri.parse("mailto:"));
        // emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, email_message);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showToastAlert(context, "There is no email client installed.");
        }
    }

    public static void openEmail(Context context, String[] TO) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        // emailIntent.setData(Uri.parse("mailto:"));
        // emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showToastAlert(context, "There is no email client installed.");
        }
    }

    public static void sendEmail(Context context, String[] to) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void sendEmail(Context context, String email, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static CircularProgressDrawable getCircularProgressViw(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public static void setOfflineVariablesToSharedPref(Context context, List<Variable_Bean> list_Variables) {
        for (int i = 0; i < list_Variables.size(); i++) {
            Variable_Bean variable_bean = list_Variables.get(i);
            if (variable_bean.isOffline_Variable()) {
                if (!PrefManger.checkIsKeyExist(context, variable_bean.getVariable_Name())) {
                    if (variable_bean.getVariable_Type().contains("Single")) {
                        PrefManger.putSharedPreferencesString(context, variable_bean.getVariable_Name(), variable_bean.getVariable_singleValue());
                    } else {
                        PrefManger.putSharedPreferencesListOfString(context, variable_bean.getVariable_Name(), variable_bean.getVariable_multiValue(), ",");
                    }
                }
            } else {
                PrefManger.removeSharedPreferences(context, variable_bean.getVariable_Name());
            }

        }
//        PrefManger.printData(context);
    }

    public static void setOfflineSingleVariableToSharedPref(Context context, String key, String val) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, val);
        edit.commit();
    }

    public static void setOfflineMultiVariableToSharedPref(Context context, String key, List<String> lval, String separator) {

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        SharedPreferences.Editor edit = preferences.edit();

        String value = "";

        if (lval != null)
            for (int i = 0; i < lval.size(); i++) {
                value = value + lval.get(i) + separator;
            }

        edit.putString(key, value);
        edit.commit();

    }

    public static void removeOfflineVariableFromSharedPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove(key);
        edit.commit();
    }

    public static String getOfflineSingleVariableFromSharedPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static List<String> getOfflineMultiVariableFromSharedPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");
        String separator = null;


        if (value != null) {
            separator = value.substring(value.length() - 1);
            value = value.substring(0, value.length() - 1);
            String[] result = value.trim().split(separator.trim());
            return Arrays.asList(result.clone());
        }

        return new ArrayList<>();
    }

    public static void alertDialogWithMaterialTheme(Context context, String msg, String positiveBtnText, String negativeBtnText, final IL il) {
        try {

            new MaterialAlertDialogBuilder(context)
                    .setCancelable(false)
                    // .setTitle(msg)
                    .setMessage(Html.fromHtml("<b>" + msg + "</b>"))
                    .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (il != null)
                                il.onSuccess();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (il != null)
                                il.onCancel();
                            dialogInterface.dismiss();
                        }
                    })
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                if (il != null)
                                    il.onCancel();
                                dialog.dismiss();
                            }
                            return false;
                        }
                    })
                    .show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void alertDialogWithCutShapeMaterialTheme(Context context, String msg, String positiveBtnText, String negativeBtnText, final IL il) {
        try {

            AlertDialog alertDialog = new MaterialAlertDialogBuilder(context, R.style.CutShapeTheme)
                    .setCancelable(false)
                    //.setTitle(msg)
                    .setMessage(Html.fromHtml("<b>" + msg + "</b>"))
                    .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (il != null)
                                il.onSuccess();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (il != null)
                                il.onCancel();
                            dialogInterface.dismiss();
                        }
                    })
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                if (il != null)
                                    il.onCancel();
                                dialog.dismiss();
                            }
                            return false;
                        }
                    })
                    .show();
            TextView textView = alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(16);
            textView.setTypeface(null, Typeface.BOLD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void alertDialogWithRoundShapeMaterialTheme(Context context, String msg, String positiveBtnText, String negativeBtnText, final IL il) {
      /*  try {

            AlertDialog alertDialog = new MaterialAlertDialogBuilder(context, R.style.RoundShapeTheme)
                    .setCancelable(false)
                    //.setTitle(msg)
                    //.setMessage(Html.fromHtml("<b>"+msg+"</b>"))
                    .setMessage(msg)
                    .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (il != null)
                                il.onSuccess();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (il != null)
                                il.onCancel();
                            dialogInterface.dismiss();
                        }
                    })
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                if (il != null)
                                    il.onCancel();
                                dialog.dismiss();
                            }
                            return false;
                        }
                    })
                    .show();

            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(18);
            //textView.setTypeface(null, Typeface.BOLD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/


        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = linflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alertMessage)).setText(msg);
        ((Button) view.findViewById(R.id.buttonYes)).setText(positiveBtnText);
        ((Button) view.findViewById(R.id.buttonNo)).setText(negativeBtnText);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (il != null) {
                    il.onSuccess();
                    alertDialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (il != null) {
                    il.onCancel();
                }
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public static void adWithRoundShapeMaterialThemeAssessmentDetails(Context context, String msg, String positiveBtnText, final IL il) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = linflater.inflate(R.layout.cad_assessment_details, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alertMessage)).setText(msg);
        ((Button) view.findViewById(R.id.buttonYes)).setText(positiveBtnText);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (il != null) {
                    il.onSuccess();
                    alertDialog.dismiss();
                }
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDPControlUI(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, Resources.getSystem().getDisplayMetrics());
    }

    public static int givenNoIntoDP(int no) {
        return (int) (no / Resources.getSystem().getDisplayMetrics().density);
    }

    public static String replaceWithUnderscore(String str) {

        str = str.replace(" ", "_");

        return str;
    }

    public static void saveDesignFormat(Context context, String designFormat) {
        PrefManger.putSharedPreferencesString(context, "DesignFormat", designFormat);
    }

    public static String getDesignFormat(Context context) {
        return PrefManger.getSharedPreferencesString(context, "DesignFormat", "");
    }

    public static void clearDesignFormat(Context context) {
        PrefManger.putSharedPreferencesString(context, "DesignFormat", "");
    }

    public static void showHideBottomSheet(BottomSheetBehavior sheetBehavior) {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setFitToContents(true);
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public static void setViewDisableOrEnableDefaultCopy(Context context, View view, boolean enabled) {

        try {
            LinearLayout ll_tap_text = view.findViewById(R.id.ll_tap_text);
            LinearLayout layout_camera_or_gallery = view.findViewById(R.id.layout_camera_or_gallery);
            TextView tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            EditText ce_TextType = view.findViewById(R.id.ce_TextType);
            if (enabled) {
                if (ll_tap_text != null)
                    if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_SIGNATURE)) {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_default));
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_default));
                        layout_camera_or_gallery.setEnabled(enabled);
                    } else if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_VOICE_RECORDING)) {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_default));
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_default));
                        layout_camera_or_gallery.setEnabled(enabled);
                    } else {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_default_background));
                    }
                if (tv_tapTextType != null)
                    tv_tapTextType.setTextColor(ContextCompat.getColor(context, R.color.control_input_text_color));
                if (ce_TextType != null)
                    ce_TextType.setTextColor(ContextCompat.getColor(context, R.color.control_input_text_color));
            } else {
                if (ll_tap_text != null)
                    if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_SIGNATURE)) {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_disable));
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_disable));
                        layout_camera_or_gallery.setEnabled(enabled);
                    } else if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_VOICE_RECORDING)) {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_disable));
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_disable));
                        layout_camera_or_gallery.setEnabled(enabled);
                    } else {
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_disable_background));
                    }
                if (tv_tapTextType != null)
                    tv_tapTextType.setTextColor(ContextCompat.getColor(context, R.color.control_disable_text_color));
                if (ce_TextType != null)
                    ce_TextType.setTextColor(ContextCompat.getColor(context, R.color.control_disable_text_color));
            }
            ll_tap_text.setEnabled(enabled);
//        if (view instanceof ViewGroup) {
//            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < ll_tap_text.getChildCount(); i++) {
                View child = ll_tap_text.getChildAt(i);
                if (child != null) {
                    setViewDisable(child, enabled);
                }

                //            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setViewDisableOrEnableDefault(Context context, View view, boolean enabled) {

        try {
            LinearLayout ll_tap_text = view.findViewById(R.id.ll_tap_text);
            LinearLayout layout_camera_or_gallery = view.findViewById(R.id.layout_camera_or_gallery);
            TextView tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            EditText ce_TextType = view.findViewById(R.id.ce_TextType);
            if (enabled && ll_tap_text != null) {
                if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_SIGNATURE)) {
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_default));
                    layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_default));
                    layout_camera_or_gallery.setEnabled(enabled);
                } else if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_VOICE_RECORDING)) {
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_default));
                    layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_default));
                    layout_camera_or_gallery.setEnabled(enabled);
                } else if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_RATING)) {
                    ll_tap_text.setBackground(null);
                } else {
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_default_background));
                }
                if (tv_tapTextType != null)
                    tv_tapTextType.setTextColor(ContextCompat.getColor(context, R.color.control_input_text_color));
                if (ce_TextType != null)
                    ce_TextType.setTextColor(ContextCompat.getColor(context, R.color.control_input_text_color));
                ll_tap_text.setEnabled(enabled);
            } else if (!enabled && ll_tap_text != null) {

                if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_SIGNATURE)) {
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_disable));
                    layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_disable));
                    layout_camera_or_gallery.setEnabled(enabled);
                } else if (ll_tap_text.getTag() != null && ll_tap_text.getTag().toString().equalsIgnoreCase(CONTROL_TYPE_VOICE_RECORDING)) {
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_disable));
                    layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_disable));
                    layout_camera_or_gallery.setEnabled(enabled);
                } else {
//                    controlEnableDisableBackground()
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_disable_background));
                }
                if (tv_tapTextType != null)
                    tv_tapTextType.setTextColor(ContextCompat.getColor(context, R.color.control_disable_text_color));
                if (ce_TextType != null)
                    ce_TextType.setTextColor(ContextCompat.getColor(context, R.color.control_disable_text_color));
                ll_tap_text.setEnabled(enabled);
            }

//        if (view instanceof ViewGroup) {
//            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < ll_tap_text.getChildCount(); i++) {
                View child = ll_tap_text.getChildAt(i);
                if (child != null) {
                    setViewDisable(child, enabled);
                }

                //            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setVisible(boolean visible, ControlObject cntrlObject, LinkedHashMap<String, Object> List_ControlClassObjects) {

        switch (cntrlObject.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                textInput.setVisibility(visible);//done.
                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                numericInput.setVisibility(visible);//done.
                break;
            case CONTROL_TYPE_PHONE:
                Phone phone = (Phone) List_ControlClassObjects.get(cntrlObject.getControlName());
                phone.setVisibility(visible);//done.
                break;
            case CONTROL_TYPE_EMAIL:
                Email email = (Email) List_ControlClassObjects.get(cntrlObject.getControlName());
                email.setVisibility(visible);//done.
                break;
            case CONTROL_TYPE_LARGE_INPUT:
                LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                largeInput.setVisibility(visible);//done.
                break;
            case CONTROL_TYPE_DYNAMIC_LABEL:
                DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(cntrlObject.getControlName());
                dynamicLabel.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_CHECKBOX:
                Checkbox checkBox = (Checkbox) List_ControlClassObjects.get(cntrlObject.getControlName());
                checkBox.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_CALENDER:
                com.bhargo.user.controls.standard.Calendar calendar = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(cntrlObject.getControlName());
                calendar.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_PERCENTAGE:
                Percentage percentage = (Percentage) List_ControlClassObjects.get(cntrlObject.getControlName());
                percentage.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_RADIO_BUTTON:
                RadioGroupView radioGroup = (RadioGroupView) List_ControlClassObjects.get(cntrlObject.getControlName());
                radioGroup.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_DROP_DOWN:
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(cntrlObject.getControlName());
                dropDown.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_CHECK_LIST:
                CheckList checkList = (CheckList) List_ControlClassObjects.get(cntrlObject.getControlName());
                checkList.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_DECIMAL:
                DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(cntrlObject.getControlName());
                decimalView.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_PASSWORD:
                Password password = (Password) List_ControlClassObjects.get(cntrlObject.getControlName());
                password.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_CURRENCY:
                Currency currency = (Currency) List_ControlClassObjects.get(cntrlObject.getControlName());
                currency.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_RATING:
                Rating rating = (Rating) List_ControlClassObjects.get(cntrlObject.getControlName());
                rating.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_GPS:
                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(cntrlObject.getControlName());
                controlGPS.setVisibility(visible); // done_
                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(cntrlObject.getControlName());
                camera.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(cntrlObject.getControlName());
                fileBrowsing.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_IMAGE:
                Image imageView = (Image) List_ControlClassObjects.get(cntrlObject.getControlName());
                imageView.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_DATA_CONTROL:
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                dataControl.setVisibility(visible);// done_
                break;
            case CONTROL_TYPE_CALENDAR_EVENT:
                CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                calendarEventControl.setVisibility(visible);// done_
                break;
            case CONTROL_TYPE_DATA_VIEWER:
                DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(cntrlObject.getControlName());
                dataViewer.setVisibility(visible);// done_
                break;
            case CONTROL_TYPE_MAP:
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                mapControlView.setVisibility(visible); // done_
                break;
            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                autoCompletionControl.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_TIME:
                Time time = (Time) List_ControlClassObjects.get(cntrlObject.getControlName());
                time.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_USER:
                UserControl userControl = (UserControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                userControl.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_VIEWFILE:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                viewFileControl.setVisibility(visible);//done
                break;
            case CONTROL_TYPE_AUTO_GENERATION:

                break;
            case CONTROL_TYPE_AUTO_NUMBER:
                AutoNumber autoNumber = (AutoNumber) List_ControlClassObjects.get(cntrlObject.getControlName());
                //autoNumber.setVisibility(visible);

                break;
            case CONTROL_TYPE_VOICE_RECORDING:
                VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(cntrlObject.getControlName());
                voiceRecording.setVisibility(visible);
                break;
            case CONTROL_TYPE_VIDEO_RECORDING:
                VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(cntrlObject.getControlName());
                videoRecording.setVisibility(visible);

                break;
            case CONTROL_TYPE_AUDIO_PLAYER:
                AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(cntrlObject.getControlName());
                audioPlayer.setVisibility(visible);
                break;
            case CONTROL_TYPE_VIDEO_PLAYER:
                VideoPlayer videoPlayer = (VideoPlayer) List_ControlClassObjects.get(cntrlObject.getControlName());
                videoPlayer.setVisibility(visible);
                break;
            case CONTROL_TYPE_SIGNATURE:
                SignatureView signature = (SignatureView) List_ControlClassObjects.get(cntrlObject.getControlName());
                signature.setVisibility(visible);

                break;
            case CONTROL_TYPE_URL_LINK:
                UrlView urlView = (UrlView) List_ControlClassObjects.get(cntrlObject.getControlName());
                urlView.setVisibility(visible);

                break;

            case CONTROL_TYPE_BUTTON:
                com.bhargo.user.controls.standard.Button button = (com.bhargo.user.controls.standard.Button) List_ControlClassObjects.get(cntrlObject.getControlName());
                button.setVisibility(visible);
                break;
            case CONTROL_TYPE_BAR_CODE:
                BarCode barcode = (BarCode) List_ControlClassObjects.get(cntrlObject.getControlName());
                barcode.setVisibility(visible);// done_

                break;
            case CONTROL_TYPE_QR_CODE:
                QRCode qrCode = (QRCode) List_ControlClassObjects.get(cntrlObject.getControlName());
                qrCode.setVisibility(visible);

                break;

            case CONTROL_TYPE_SUBFORM:
                SubformView subformView = (SubformView) List_ControlClassObjects.get(cntrlObject.getControlName());
                subformView.setVisibility(visible);
                break;

            case CONTROL_TYPE_GRID_CONTROL:

                GridControl gridView = (GridControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                gridView.setVisibility(visible);

                break;

            case CONTROL_TYPE_SECTION:
                SectionControl sectionControl = (SectionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                sectionControl.setVisibility(visible);

                break;

            case CONTROL_TYPE_LiveTracking:
                LiveTracking ControlLiveTracking = (LiveTracking) List_ControlClassObjects.get(cntrlObject.getControlName());
                ControlLiveTracking.setVisibility(visible); // done_

                break;

            case CONTROL_TYPE_POST:

                PostControl postControl = (PostControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                postControl.setVisibility(visible);
                break;
            case CONTROL_TYPE_CHART:
                ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                chartControl.setVisibility(visible); // done_
                break;

            case CONTROL_TYPE_PROGRESS:
                ProgressControl progressControl = (ProgressControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                progressControl.setVisibility(visible);
                break;

            case CONTROL_TYPE_COUNT_UP_TIMER:
                CountUpTimerControl countUpTimerControl = (CountUpTimerControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                countUpTimerControl.setVisibility(visible);
                break;
            case CONTROL_TYPE_COUNT_DOWN_TIMER:
                CountDownTimerControl countDownTimerControl = (CountDownTimerControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                countDownTimerControl.setVisibility(visible);
                break;


            case CONTROL_TYPE_DATA_TABLE:
                DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                dataTableControl.setVisibility(visible); // done_

                break;
            case CONTROL_TYPE_SUBMIT_BUTTON:
                SubmitButton submitButton = (SubmitButton) List_ControlClassObjects.get(cntrlObject.getControlName());
                submitButton.setVisibility(visible);
                break;
        }


    }

    /* Actions for Controls actions*/
    /* set controls actions*/
    public static void setControlUtils(ControlObject cntrlObject, LinkedHashMap<String, Object> List_ControlClassObjects,String showMessageData,String strActionName) {

        switch (cntrlObject.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                textInput.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    numericInput.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_PHONE:
                Phone phone = (Phone) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    phone.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_EMAIL:
                Email email = (Email) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    email.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_LARGE_INPUT:
                LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    largeInput.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_DYNAMIC_LABEL:
                DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    dynamicLabel.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CHECKBOX:
                Checkbox checkBox = (Checkbox) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    checkBox.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CALENDER:
                com.bhargo.user.controls.standard.Calendar calendar = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    calendar.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_PERCENTAGE:
                Percentage percentage = (Percentage) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    percentage.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_RADIO_BUTTON:
                RadioGroupView radioGroup = (RadioGroupView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    radioGroup.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_DROP_DOWN:
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    dropDown.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CHECK_LIST:
                CheckList checkList = (CheckList) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    checkList.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_DECIMAL:
                DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    decimalView.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_PASSWORD:
                Password password = (Password) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    password.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CURRENCY:
                Currency currency = (Currency) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    currency.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_RATING:
                Rating rating = (Rating) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    rating.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_GPS:
                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    controlGPS.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    camera.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    fileBrowsing.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_IMAGE:
                Image imageView = (Image) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    imageView.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_DATA_CONTROL:
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    dataControl.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CALENDAR_EVENT:
                CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    calendarEventControl.showMessageBelowControl(showMessageData);
                }
                break;
//            case CONTROL_TYPE_DATA_VIEWER:
//                DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(cntrlObject.getControlName());
//                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
//                    dataViewer.showMessageBelowControl(showMessageData);
//                }
//                break;
            case CONTROL_TYPE_MAP:
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    mapControlView.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    autoCompletionControl.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_TIME:
                Time time = (Time) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    time.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_USER:
                UserControl userControl = (UserControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    userControl.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_VIEWFILE:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    viewFileControl.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_AUTO_GENERATION:

                break;
            case CONTROL_TYPE_AUTO_NUMBER:
                AutoNumber autoNumber = (AutoNumber) List_ControlClassObjects.get(cntrlObject.getControlName());
                //autoNumber.setVisibility(visible);

                break;
            case CONTROL_TYPE_VOICE_RECORDING:
                VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    voiceRecording.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_VIDEO_RECORDING:
                VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    videoRecording.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_AUDIO_PLAYER:
                AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    audioPlayer.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_VIDEO_PLAYER:
                VideoPlayer videoPlayer = (VideoPlayer) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    videoPlayer.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_SIGNATURE:
                SignatureView signature = (SignatureView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    signature.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_URL_LINK:
                UrlView urlView = (UrlView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    urlView.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_BUTTON:
                com.bhargo.user.controls.standard.Button button = (com.bhargo.user.controls.standard.Button) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    button.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_BAR_CODE:
                BarCode barcode = (BarCode) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    barcode.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_QR_CODE:
                QRCode qrCode = (QRCode) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    qrCode.showMessageBelowControl(showMessageData);
                }
                break;

           /* case CONTROL_TYPE_SUBFORM:
                SubformView subformView = (SubformView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    subformView.showMessageBelowControl(showMessageData);
                }
                break;

            case CONTROL_TYPE_GRID_CONTROL:

                GridControl gridView = (GridControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    gridView.showMessageBelowControl(showMessageData);
                }
                break;

            case CONTROL_TYPE_SECTION:
                SectionControl sectionControl = (SectionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    sectionControl.showMessageBelowControl(showMessageData);
                }
                break;
*/
            case CONTROL_TYPE_LiveTracking:
                LiveTracking ControlLiveTracking = (LiveTracking) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    ControlLiveTracking.showMessageBelowControl(showMessageData);
                }
                break;

            case CONTROL_TYPE_POST:

                PostControl postControl = (PostControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    postControl.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_CHART:
                ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    chartControl.showMessageBelowControl(showMessageData);
                }
                break;

            case CONTROL_TYPE_PROGRESS:
                ProgressControl progressControl = (ProgressControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    progressControl.showMessageBelowControl(showMessageData);
                }
                break;

            case CONTROL_TYPE_COUNT_UP_TIMER:
                CountUpTimerControl countUpTimerControl = (CountUpTimerControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    countUpTimerControl.showMessageBelowControl(showMessageData);
                }
                break;
            case CONTROL_TYPE_COUNT_DOWN_TIMER:
                CountDownTimerControl countDownTimerControl = (CountDownTimerControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    countDownTimerControl.showMessageBelowControl(showMessageData);
                }
                break;


            case CONTROL_TYPE_DATA_TABLE:
                DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    dataTableControl.showMessageBelowControl(showMessageData);
                }

                break;
            case CONTROL_TYPE_SUBMIT_BUTTON:
                SubmitButton submitButton = (SubmitButton) List_ControlClassObjects.get(cntrlObject.getControlName());
                if(strActionName.equalsIgnoreCase(AppConstants.SHOW_MESSAGE_BELOW_CONTROL)){
                    submitButton.showMessageBelowControl(showMessageData);
                }
                break;
        }


    }

    public static void setEnable(boolean enable, ControlObject cntrlObject, LinkedHashMap<String, Object> List_ControlClassObjects) {

        switch (cntrlObject.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (textInput != null) {
                    textInput.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (numericInput != null) {
                    numericInput.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_PHONE:
                Phone phone = (Phone) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (phone != null) {
                    phone.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_EMAIL:
                Email email = (Email) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (email != null) {
                    email.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_LARGE_INPUT:
                LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (largeInput != null) {
                    largeInput.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_CAMERA: //no
                Camera camera = (Camera) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (camera != null) {
                    camera.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (fileBrowsing != null) {
                    fileBrowsing.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_CALENDER:
                com.bhargo.user.controls.standard.Calendar calendar = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (calendar != null) {
                    calendar.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_CHECKBOX://no
                Checkbox checkBox = (Checkbox) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (checkBox != null) {
                    checkBox.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_RADIO_BUTTON://no
                RadioGroupView radioGroup = (RadioGroupView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (radioGroup != null) {
                    radioGroup.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_DROP_DOWN://no
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (dropDown != null) {
                    dropDown.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_CHECK_LIST://no
                CheckList checkList = (CheckList) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (checkList != null) {
                    checkList.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_RATING:
                Rating rating = (Rating) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (rating != null) {
                    rating.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_VOICE_RECORDING://no
                VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (voiceRecording != null) {
                    voiceRecording.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_VIDEO_RECORDING://no
                VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (videoRecording != null) {
                    videoRecording.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_AUDIO_PLAYER://no
                AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (audioPlayer != null) {
                    audioPlayer.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_VIDEO_PLAYER://no
                VideoPlayer videoPlayer = (VideoPlayer) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (videoPlayer != null) {
                    videoPlayer.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_PERCENTAGE://no
                Percentage percentage = (Percentage) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (percentage != null) {
                    percentage.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_SIGNATURE://no
                SignatureView signature = (SignatureView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (signature != null) {
                    signature.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_URL_LINK:
                UrlView urlView = (UrlView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (urlView != null) {
                    urlView.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_DECIMAL:
                DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (decimalView != null) {
                    decimalView.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_PASSWORD://no
                Password password = (Password) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (password != null) {
                    password.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_CURRENCY:
                Currency currency = (Currency) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (currency != null) {
                    currency.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_DYNAMIC_LABEL://no
                DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (dynamicLabel != null) {
                    dynamicLabel.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_IMAGE://no
                Image imageView = (Image) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (imageView != null) {
                    imageView.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_BUTTON:
                com.bhargo.user.controls.standard.Button button = (com.bhargo.user.controls.standard.Button) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (button != null) {
                    button.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_AUTO_NUMBER:
                AutoNumber autoNumber = (AutoNumber) List_ControlClassObjects.get(cntrlObject.getControlName());

                break;
            case CONTROL_TYPE_TIME:
                Time time = (Time) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (time != null) {
                    time.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_COUNT_UP_TIMER://no
                CountUpTimerControl countUpTimerControl = (CountUpTimerControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (countUpTimerControl != null) {
                    countUpTimerControl.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_COUNT_DOWN_TIMER://no
                CountDownTimerControl countDownTimerControl = (CountDownTimerControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (countDownTimerControl != null) {
                    countDownTimerControl.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_VIEWFILE://no
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (viewFileControl != null) {
                    viewFileControl.setEnabled(enable);
                }
                break;
            //Advance Controls
            case CONTROL_TYPE_SUBFORM://no
                SubformView subformView = (SubformView) List_ControlClassObjects.get(cntrlObject.getControlName());
                break;
            case CONTROL_TYPE_GRID_CONTROL://no
                GridControl gridView = (GridControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                break;
            case CONTROL_TYPE_GPS:
                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (controlGPS != null) {
                    controlGPS.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_BAR_CODE://no
                BarCode barcode = (BarCode) List_ControlClassObjects.get(cntrlObject.getControlName());

                break;
            case CONTROL_TYPE_QR_CODE://no
                QRCode qrCode = (QRCode) List_ControlClassObjects.get(cntrlObject.getControlName());
                break;
            case CONTROL_TYPE_SECTION://no
                SectionControl sectionControl = (SectionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                break;
            case CONTROL_TYPE_LiveTracking://no
                LiveTracking ControlLiveTracking = (LiveTracking) List_ControlClassObjects.get(cntrlObject.getControlName());
                break;
            case CONTROL_TYPE_USER:
                UserControl userControl = (UserControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (userControl != null) {
                    userControl.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_POST:
                PostControl postControl = (PostControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (postControl != null) {
                    postControl.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (autoCompletionControl != null) {
                    autoCompletionControl.setEnabled(enable);
                }
                break;
            //Data Visual Controls
            case CONTROL_TYPE_CHART://no
                ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(cntrlObject.getControlName());

                break;
            case CONTROL_TYPE_DATA_TABLE://no
                DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                break;
            case CONTROL_TYPE_DATA_VIEWER://no
                DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(cntrlObject.getControlName());

                break;
            case CONTROL_TYPE_CALENDAR_EVENT://no
                CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (calendarEventControl != null) {
                    calendarEventControl.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_MAP://no
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(cntrlObject.getControlName());

                break;
            case CONTROL_TYPE_PROGRESS://no
                ProgressControl progressControl = (ProgressControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (progressControl != null) {
                    progressControl.setEnabled(enable);
                }
                break;
            case CONTROL_TYPE_DATA_CONTROL://no
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (dataControl != null) {
                    dataControl.setEnabled(enable);
                }
                break;

          /*  case CONTROL_TYPE_SUBMIT_BUTTON:
                SubmitButton submitButton = (SubmitButton) List_ControlClassObjects.get(cntrlObject.getControlName());
                submitButton.setEnabled(enable);
                break;*/
        }
    }

    public static boolean isFileExistsInExternalPackage(Context context, String sdcardUrl, String filename) {
        File appSpecificExternalDir = null;
        try {
            File cDir = context.getExternalFilesDir(sdcardUrl);
            appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            Log.d(TAG, "FileExitsELV: " + appSpecificExternalDir);
        } catch (Exception e) {
            Log.d(TAG, "isFileExistsInPackage: " + e);
        }

        return appSpecificExternalDir.exists();
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static String encryptData(String strValue, String encryptionType) {
        String salt = null;
        try {
            salt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String encryptedData = "";
        switch (encryptionType) {

            case "sha-1":
//                encryptedData = get_SHA_1_SecurePassword(strValue,salt);
                encryptedData = get_SHA_1_SecurePassword(strValue);
                break;

            case "sha-256":
//                encryptedData = get_SHA_256_SecurePassword(strValue,salt);
                encryptedData = get_SHA_256_SecurePassword(strValue);
                break;

            case "md5":
                encryptedData = get_MD5_SecurePassword(strValue);
                break;

            case "md5 with salt":
                break;
        }
        return encryptedData;
    }

    public static String get_MD5_SecurePassword(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String get_SHA_1_SecurePassword(String passwordToHash,
                                                  String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String get_SHA_256_SecurePassword(String passwordToHash,
                                                    String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String get_SHA_256_SecurePassword(String passwordToHash) {
        String generatedPassword = null;

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            generatedPassword = hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return generatedPassword;
    }

    public static String get_SHA_1_SecurePassword(String passwordToHash) {
        String generatedPassword = null;

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            final byte[] hash = digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            generatedPassword = hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return generatedPassword;
    }

    public static String get_SHA_384_SecurePassword(String passwordToHash,
                                                    String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash,
                                                    String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Add salt
    public static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    public static int setBhargoThemeNew(String projectThemeNo, Boolean isFormThemeEnable, String formThemeNo) {
        int themeStyle = R.style.AppTheme;
        if (isFormThemeEnable) {
            if (formThemeNo.equalsIgnoreCase("THEME2")) {
                AppConstants.FORM_THEME = "THEME2";
                themeStyle = R.style.AppTheme2;
            } else if (formThemeNo.equalsIgnoreCase("THEME1")) {
                AppConstants.FORM_THEME = "THEME1";
                themeStyle = R.style.AppTheme;
            }
        } else {
            if (projectThemeNo.equalsIgnoreCase("THEME2")) {
                AppConstants.THEME = "THEME2";
                themeStyle = R.style.AppTheme2;
            } else if (projectThemeNo.equalsIgnoreCase("THEME1")) {
                AppConstants.THEME = "THEME1";
                themeStyle = R.style.AppTheme;
            }
        }
        return themeStyle;
    }

    public static void setBhargoTheme(Context context, String projectThemeNo, Boolean isFormThemeEnable, String formThemeNo) {

        if (isFormThemeEnable) { // Individual App theme
            if (!formThemeNo.equalsIgnoreCase("")) {
                if (formThemeNo.equalsIgnoreCase("THEME1")) {
                    context.setTheme(R.style.AppTheme);
                } else if (formThemeNo.equalsIgnoreCase("THEME2")) {
                    context.setTheme(R.style.AppTheme2);
                } else if (formThemeNo.equalsIgnoreCase("THEME3")) {
                    context.setTheme(R.style.AppTheme3);
                } else if (formThemeNo.equalsIgnoreCase("THEME4")) {
                    context.setTheme(R.style.AppTheme4);
                } else if (formThemeNo.equalsIgnoreCase("THEME5")) {
                    context.setTheme(R.style.AppTheme5);
                } else if (formThemeNo.equalsIgnoreCase("THEME6")) {
                    context.setTheme(R.style.AppTheme6);
                } else if (formThemeNo.equalsIgnoreCase("THEME7")) {
                    context.setTheme(R.style.AppTheme7);
                }
            } else {
                if (!projectThemeNo.equalsIgnoreCase("")) {
                    if (projectThemeNo.equalsIgnoreCase("THEME1")) {
                        context.setTheme(R.style.AppTheme);
                    } else if (projectThemeNo.equalsIgnoreCase("THEME2")) {
                        context.setTheme(R.style.AppTheme2);
                    } else if (projectThemeNo.equalsIgnoreCase("THEME3")) {
                        context.setTheme(R.style.AppTheme3);
                    } else if (projectThemeNo.equalsIgnoreCase("THEME4")) {
                        context.setTheme(R.style.AppTheme4);
                    } else if (projectThemeNo.equalsIgnoreCase("THEME5")) {
                        context.setTheme(R.style.AppTheme5);
                    } else if (projectThemeNo.equalsIgnoreCase("THEME6")) {
                        context.setTheme(R.style.AppTheme6);
                    } else if (projectThemeNo.equalsIgnoreCase("THEME7")) {
                        context.setTheme(R.style.AppTheme7);
                    }
                }
            }
        } else { // Project Theme
            if (!projectThemeNo.equalsIgnoreCase("")) {
                if (projectThemeNo.equalsIgnoreCase("THEME1")) {
                    context.setTheme(R.style.AppTheme);
                } else if (projectThemeNo.equalsIgnoreCase("THEME2")) {
                    context.setTheme(R.style.AppTheme2);
                } else if (projectThemeNo.equalsIgnoreCase("THEME3")) {
                    context.setTheme(R.style.AppTheme3);
                } else if (projectThemeNo.equalsIgnoreCase("THEME4")) {
                    context.setTheme(R.style.AppTheme4);
                } else if (projectThemeNo.equalsIgnoreCase("THEME5")) {
                    context.setTheme(R.style.AppTheme5);
                } else if (projectThemeNo.equalsIgnoreCase("THEME6")) {
                    context.setTheme(R.style.AppTheme6);
                } else if (projectThemeNo.equalsIgnoreCase("THEME7")) {
                    context.setTheme(R.style.AppTheme7);
                }
            }
        }
    }

    public static String getMD5Hash(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromMD5Hash(String md5Hash) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < md5Hash.length(); i += 2) {
            String hex = md5Hash.substring(i, i + 2);
            sb.append((char) Integer.parseInt(hex, 16));
        }
        return sb.toString();
    }

    public static SecretKey generateCypherKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static Cipher initCipher(SecretKey secretKey, IvParameterSpec ivParameterSpec) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        return cipher;
    }

    public static String encryptUsingCipher(String message, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
        } catch (BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException |
                 NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptUsingCipher(SecretKey secretKey, String encryptedBytes, IvParameterSpec ivParameterSpec) {
        String decryptedText = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decryptedBytes = new byte[0];
            byte[] decoded = Base64.decode(encryptedBytes, Base64.NO_WRAP);
            byte[] decrypted = cipher.doFinal(decoded);
            decryptedText = new String(decrypted, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException |
                 NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return decryptedText;
    }

   /* public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }*/

    public static void setTextColorAlertDialog(Context context, AlertDialog alert) {
        if (alert.getButton(DialogInterface.BUTTON_POSITIVE) != null) {
            Button buttonPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        if (alert.getButton(DialogInterface.BUTTON_NEGATIVE) != null) {
            Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            buttonNegative.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

    }

    public static List<String> getGpsCoordinates(String coordinates) {
        List<String> gpscoordinates = new ArrayList<>();
//       gpscoordinates.add("18.11657539799718$83.40660531240056^18.11541074103844$83.4060438428486^18.115216997354313$83.40815742357913^18.115482120236457$83.40830762728588");
        gpscoordinates.add(coordinates);
        return gpscoordinates;
    }

    public static Boolean validateUri(Context context, String strUri) {
        boolean bool = false;
        if (null != strUri) {
            try {
                Uri uri = Uri.parse(strUri);
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                inputStream.close();
                bool = true;
            } catch (Exception e) {
                bool = false;
//                Log.w(MY_TAG, "File corresponding to the uri does not exist " + uri.toString());
            }
        }
        return bool;
    }
/*
    public String getLocationLevel(List<UserPostDetails> userPostDetailsList, String postID) {
        String locationLevel = "";
        StringBuilder locationName = new StringBuilder();
        if (userPostDetailsList != null && userPostDetailsList.size() > 0) {
            List<PostSubLocationsModel> postSubLocationsModelList = new ArrayList<>();
            for (UserPostDetails userPostDetails : userPostDetailsList) {
                if (userPostDetails.getPostID().equalsIgnoreCase(postID)) {
                    postSubLocationsModelList = userPostDetails.getPostSubLocations();
                    break;
                }
            }
            for (PostSubLocationsModel postSubLocationsModel : postSubLocationsModelList) {
                locationName.append(postSubLocationsModel.getText()).append(" \\ ");
            }
//            locationLevel = locationName.substring(0, locationName.length() - 2);
            if(!locationName.toString().equals("")) {
                locationLevel = locationName.substring(0, locationName.length() - 3);
            }
//            locationLevel = locationLevel.substring(1);
        }
        return locationLevel;
    }
*/

    public static String getDateFormatInDDMMYYYY(String date) {
        String[] temp;
        if (date.contains("/")) {
            temp = date.split("\\/");
        } else {
            temp = date.split("\\-");
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        if (temp[0].length() > 2) {
            return Integer.parseInt(temp[2]) + "-" + (Integer.parseInt(temp[1])) + "-" + Integer.parseInt(temp[0]);
        } else {
            return Integer.parseInt(temp[0]) + "-" + (Integer.parseInt(temp[1])) + "-" + Integer.parseInt(temp[2]);
        }
    }

    /* public static String getvaluefromObject(ControlObject cntrlObject, LinkedHashMap<String, Object> List_ControlClassObjects) {
         String value = "";

         switch (cntrlObject.getControlType()) {
             case CONTROL_TYPE_TEXT_INPUT:
                 TextInput textInput = (TextInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                 CustomEditText ce_TextType = textInput.getCustomEditText();
                 value = ce_TextType.getText().toString().trim();
                 if (value.contentEquals("Tap here to Scan QR Code") || value.contentEquals("Tap here to Scan BarCode")) {
                     value = "";
                 }
                 break;
             case CONTROL_TYPE_NUMERIC_INPUT:
                 NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = numericInput.getTextValue();
                 if (value.contentEquals("")) {
                     value = "0.0";
                 }
                 break;
             case CONTROL_TYPE_PHONE:
                 Phone phone = (Phone) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = phone.getTextValue();
                 break;
             case CONTROL_TYPE_EMAIL:
                 Email email = (Email) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = email.getTextValue();
                 break;
             case CONTROL_TYPE_LARGE_INPUT:
                 LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = largeInput.getTextValue();
                 if (cntrlObject.isHtmlEditorEnabled()) {
                     value = largeInput.getHtml();
                 }
                 break;
             case CONTROL_TYPE_DYNAMIC_LABEL:
                 DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = dynamicLabel.getTextValue();
                 if (cntrlObject.isHtmlViewerEnabled()) {
                     value = dynamicLabel.getHtml();
                 }
                 break;
             case CONTROL_TYPE_CHECKBOX:
                 Checkbox checkBox = (Checkbox) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = checkBox.getSelectedCheckBoxString();
                 break;
             case CONTROL_TYPE_CALENDER:
                 com.bhargo.user.controls.standard.Calendar calendar = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(cntrlObject.getControlName());
                 value = calendar.getSelectedDate();
                 *//* if (fromMapExisting) {*//*
                value = calendar.getFinalDateWithServerFormat();
                //}
                break;
            case CONTROL_TYPE_PERCENTAGE:
                Percentage percentage = (Percentage) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = percentage.getTextValue();
                break;
            case CONTROL_TYPE_RADIO_BUTTON:
                RadioGroupView radioGroup = (RadioGroupView) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (radioGroup.getSelectedItem() != null) {
                    Item item = radioGroup.getSelectedItem();
                    value = item.getId().trim() + "," + item.getValue().trim();
                }
                *//*view = radioGroup.getRadioGroupView();
                value = radioGroup.getSelectedItem().getValue();
                RadioGroup rg_main = view.findViewById(R.id.rg_container);
                RadioGroup rg_main = radioGroup.getRadioGroup();
                CustomEditText ce_rg_other = view.findViewById(R.id.ce_otherchoice);
                if (rg_main.getCheckedRadioButtonId() != -1) {
                    RadioButton rb_item = view.findViewById(rg_main.getCheckedRadioButtonId());
                    *//**//*if (rb_item.getText().toString().trim().equalsIgnoreCase("Other")) {
                        value = ce_rg_other.getText().toString().trim() + "," + ce_rg_other.getText().toString().trim();
                    } else {*//**//*
                    value = ((String) rb_item.getTag()).trim() + "," + rb_item.getText().toString().trim();
                    //}
                }*//*
                break;
            case CONTROL_TYPE_DROP_DOWN:
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = dropDown.getSelectedDropDownItem();
              *//*  view = dropDown.getDropdown();
                SearchableSpinner searchableSpinner = view.findViewById(R.id.searchableSpinner_main);
                CustomEditText ce_dropother = view.findViewById(R.id.ce_otherchoice);
                if (searchableSpinner.getSelectedName() != null && !searchableSpinner.getSelectedName().contentEquals(AppConstants.SPINNER_INIT_NAME)) {
                    if (searchableSpinner.getSelectedName().trim().equalsIgnoreCase("Other")) {
                        value = ce_dropother.getText().toString().trim() + "," + ce_dropother.getText().toString().trim();
                    } else {
                        value = searchableSpinner.getSelectedId().trim() + "," + searchableSpinner.getSelectedName().trim();
                    }
                }*//*
                break;
            case CONTROL_TYPE_CHECK_LIST:
                CheckList checkList = (CheckList) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = checkList.getSelectedItemsString();
               *//* SearchableMultiSpinner multiSearchableSpinner = view.findViewById(R.id.multiSearchableSpinner);
                List<String> Items = multiSearchableSpinner.getSelectedNames();
                List<String> ItemIds = multiSearchableSpinner.getSelectedIds();
                for (int i = 0; i < Items.size(); i++) {
                    value = value + "$" + Items.get(i) + "," + ItemIds.get(i);
                }
                value = value.substring(1);*//*
                break;
            case CONTROL_TYPE_DECIMAL:
                DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = decimalView.getTextValue();
                break;
            case CONTROL_TYPE_PASSWORD:
                Password password = (Password) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = password.getTextValue();
                //CustomTextInputEditText ce_PasswordType = view.findViewById(R.id.tie_password);

                break;
            case CONTROL_TYPE_CURRENCY:
                Currency currency = (Currency) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = currency.getTextValue();
                //CustomEditText ce_CurrType = view.findViewById(R.id.ce_TextType);
                //value = ce_CurrType.getText().toString().trim();
                break;
            case CONTROL_TYPE_RATING:
                Rating rating = (Rating) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = rating.getRatingString();

               *//* SmileRating smileRating = view.findViewById(R.id.smile_rating);
                RatingBar ratingBar = view.findViewById(R.id.ratingStar);

                if (cntrlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                    value = "" + smileRating.getRating();
                } else {
                    value = "" + ratingBar.getRating();
                }*//*
                break;
            case CONTROL_TYPE_GPS:
                Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(cntrlObject.getControlName());
                List<LatLng> latLngList = controlGPS.getLatLngList();
                for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                    LatLng latlang = latLngList.get(i);
                    value = value + " ^" + latlang.latitude + "$" + latlang.longitude;
                }
//                value = "\""+value.substring(value.indexOf("^") + 1)+"\"";
                value = value.substring(value.indexOf("^") + 1);
                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(cntrlObject.getControlName());
                String campath = camera.getControlRealPath().getTag().toString();
                if (campath.trim().length() > 0) {
                    if (campath.contains("http")) {
                        Log.d("getvaluefromView1: ", campath);
                        value = campath;
                    } else {
                        Log.d("getvaluefromView2: ", campath);
//                        value = ImproveHelper.sgetImageStringFromBitmap(campath, 500, 500);
                        value = campath;
                    }
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(cntrlObject.getControlName());
                String filepath = fileBrowsing.getControlRealPath().getTag().toString();
                if (filepath.trim().length() > 0) {
//                    if (filepath.contains("http")) {
                    value = filepath;

//                    } else {
//                        value = "";
//                    }
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_IMAGE:
                Image imageView = (Image) List_ControlClassObjects.get(cntrlObject.getControlName());
                String Imagepath = imageView.ImagePath;
                if (Imagepath.trim().length() > 0) {
                    value = ImproveHelper.sgetImageStringFromBitmap(Imagepath, 500, 500);
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_DATA_CONTROL:
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                if (dataControl.dropdownItemIsSelected()) {
                    value = dataControl.getSelectedDropDownItemId() + "," + dataControl.getSelectedDropDownItemName();
                    Log.d("Result", value);
                } else {
                    value = "";
                }
                break;
            case CONTROL_TYPE_CALENDAR_EVENT:
                CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(cntrlObject.getControlName());

                value = calendarEventControl.getSelectedDateandMessage();

                break;
            case CONTROL_TYPE_DATA_VIEWER:
                DataViewer DataViewer = (DataViewer) List_ControlClassObjects.get(cntrlObject.getControlName());

                DataViewerModelClass DataViewerModelClass = DataViewer.customAdapter.getSelectedDataViewerModelClass();

                value = DataViewerModelClass.getHeading() + "^" + DataViewerModelClass.getSubHeading() + "^" +
                        DataViewerModelClass.getDescription() + "^" + DataViewerModelClass.getDateandTime() + "^" + DataViewerModelClass.getDv_trans_id();

                break;
            case CONTROL_TYPE_MAP:
                MapControl mapControlView = (MapControl) List_ControlClassObjects.get(cntrlObject.getControlName());

                value = mapControlView.getSelectedMarkerGPSPosition();
                break;
            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(cntrlObject.getControlName());
//                value = autoCompletionControl.getAutoCompleteTextValue();
                value = autoCompletionControl.getSelectedItemValue().trim() + "," + autoCompletionControl.getSelectedText();
                break;
            case CONTROL_TYPE_TIME:
                Time time = (Time) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = time.getEditTextTimeValue();
                break;
            case CONTROL_TYPE_USER:
                UserControl userControl = (UserControl) List_ControlClassObjects.get(cntrlObject.getControlName());
//                value = autoCompletionControl.getAutoCompleteTextValue();
                value = userControl.getSelectedUserId().trim() + "," + userControl.getSelectedUserName();
                break;
            case CONTROL_TYPE_ViewFile:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(cntrlObject.getControlName());
                value = viewFileControl.getFileLink();
                break;
            case CONTROL_TYPE_AUTO_GENERATION:
                value = "";
                break;
        }
        return value;
    }
*/
    public boolean isDateExpiredComparedToCurrentDate(String strRandomDate) { //
        boolean b = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date strDate = sdf.parse(strRandomDate);
            if (new Date().after(strDate)) {
                b = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return b;
    }

    public boolean isGivenDateIsBeforeOrLessThanCurrent(String strRandomDate) {

        boolean b = false;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String strCurrentDate = sdf.format(new Date()); // current Date in String
            Date currentDate = sdf.parse(strCurrentDate); // parse to date format
            Date strDate = sdf.parse(strRandomDate);

            if (currentDate.compareTo(strDate) < 0) {
                b = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean isGivenDateIsAfterOrGraterThanCurrent(String strRandomDate) {

        boolean b = false;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String strCurrentDate = sdf.format(new Date()); // current Date in String
            Date currentDate = sdf.parse(strCurrentDate); // parse to date format
            Date strDate = sdf.parse(strRandomDate);

            if (currentDate.compareTo(strDate) > 0) {
                b = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean isGivenDateIsEqualsToCurrent(String strRandomDate) {

        boolean b = false;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String strCurrentDate = sdf.format(new Date()); // current Date in String
            Date currentDate = sdf.parse(strCurrentDate); // parse to date format
            Date strDate = sdf.parse(strRandomDate);

            if (currentDate.compareTo(strDate) == 0) {
                b = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public void SetStaticValuetoSpinner(Context context, String title, String hint,
                                        final SearchableSpinner Spinner) {
        final CustomEditText myMessage = new CustomEditText(context);
        myMessage.setHint(hint);
        myMessage.setGravity(Gravity.CENTER);
        myMessage.setTextSize(20);
        myMessage.setPadding(40, 40, 40, 40);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(myMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String Value = myMessage.getText().toString().trim();
                        SpinnerData addedvalue = new SpinnerData();
                        addedvalue.setId(Value);
                        addedvalue.setName(Value);
                        List<SpinnerData> data = new ArrayList<SpinnerData>();
                        data.add(addedvalue);
                        Spinner.AddItems(data);
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void showProgressDialogOld(String msg) {
        try {
            progressDialog = new ProgressDialog(context);
            // pd = CustomProgressDialog.ctor(this, msg);
            // pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(String message) {
        try {
            if (dialog != null && dialog.isShowing()) {
                msg.setText(message);
            } else {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_progress_dialog);
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                msg = dialog.findViewById(R.id.msg);
                msg.setText(message);
                progressBar = dialog.findViewById(R.id.progress_bar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
//        System.out.println("progressDialog Close=====");
//        if (progressDialog != null&&progressDialog.isShowing()) {
//            System.out.println("progressDialog Close=====done");
//            progressDialog.dismiss();
//        }
        if (dialog != null && dialog.isShowing()) {
            System.out.println("progressDialog Close=====done");
            dialog.dismiss();
        }
//        dismissWithTryCatch(progressDialog);
    }

    public void snackBarAlertActivities(Context context, View view) {
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void snackBarAlertActivities(Context context) {
        Snackbar snackbar = Snackbar.make(null, context.getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void snackBarAlertFragment(Context context, ViewGroup view) {
        Snackbar snackbar = Snackbar
                .make(view, context.getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void createImproveUserFolder(String folderPathName) {
        File dir = new File(Environment.getExternalStorageDirectory(), folderPathName);
        try {
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkGeoFenceWithinRangeorNot(String currentPosition, String referencePosition, double radius) {
        boolean returnflag = false;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0].trim());
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1].trim());

        if (referencePosition.contains("^")) {
            String[] referencePositionArr = referencePosition.split("\\^");
            for (int i = 0; i < referencePositionArr.length; i++) {

                double lat2 = Double.parseDouble(referencePositionArr[i].split("\\$")[0]);
                double lon2 = Double.parseDouble(referencePositionArr[i].split("\\$")[1]);

                double theta, dist;

                theta = lon1 - lon2;

                dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                        + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

                dist = Math.acos(dist);

                dist = rad2deg(dist);

                dist = dist * 60 * 1.1515;

                dist = dist * 1.609344; // in k.m

                dist = dist * 1000; // in meters

                //return (dist);
                System.out.println("dist===" + dist);
                System.out.println("radius===" + radius);

                if (dist <= radius) {
                    returnflag = true;
                    break;
                }
            }
        } else {
            double lat2 = Double.parseDouble(referencePosition.split("\\$")[0]);
            double lon2 = Double.parseDouble(referencePosition.split("\\$")[1]);

            double theta, dist;

            theta = lon1 - lon2;

            dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

            dist = Math.acos(dist);

            dist = rad2deg(dist);

            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344; // in k.m

            dist = dist * 1000; // in meters

            //return (dist);
            System.out.println("dist===" + dist);
            System.out.println("radius===" + radius);

            if (dist <= radius) {
                returnflag = true;
            }
        }

        return returnflag;
    }

    public boolean checkGeoFenceWithInBoundaryOrNot(String currentPosition, String referencePosition) {
        boolean insideOutside;

        double lat = Double.parseDouble(currentPosition.split("\\^")[0]);
        double lon = Double.parseDouble(currentPosition.split("\\^")[1]);

        ArrayList<Double> lat_array = new ArrayList<Double>();
        ArrayList<Double> long_array = new ArrayList<Double>();
        String[] referenceList = new String[]{};
        if (referencePosition.contains("^")) {
            referenceList = referencePosition.split("\\^");


            for (int i = 0; i < referenceList.length; i++) {
                String s = referenceList[i];

                lat_array.add(Double.parseDouble(s.split("\\^")[0]));
                long_array.add(Double.parseDouble(s.split("\\^")[1]));
            }
        }

        insideOutside = coordinate_is_inside_polygon(lat, lon, lat_array, long_array);


        return insideOutside;
    }

    public void setThumbNail(String imageUrl, ImageButton imageButton) {
        final int THUMB_SIZE = 64;

        String imagePath = "";

        if (isNetworkStatusAvialable(context)) {

            Glide.with(context).load(imageUrl).thumbnail(0.1f)
                    .dontAnimate().into(imageButton);

        } else {
            imagePath = getImagePath(imageUrl);

            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath),
                    THUMB_SIZE, THUMB_SIZE);

            imageButton.setImageBitmap(bitmap);

        }


    }

    private String getImagePath(String strDisplayUrlImage) {
        String[] imgUrlSplit = strDisplayUrlImage.split("/");
        String replaceWithUnderscore = "".replaceAll(" ", "_");

        return "/Improve_User/" + replaceWithUnderscore + "/" + imgUrlSplit[imgUrlSplit.length - 1];

    }

    public void setVideoThumbNail(String videoUrl, ImageButton imageButton) {

        String imagePath = "";

        if (isNetworkStatusAvialable(context)) {
            long thumb = 1000;
            RequestOptions options = new RequestOptions().frame(thumb);
            Glide.with(context).load(videoUrl).apply(options).into(imageButton);

        }

/*
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MINI_KIND);
*/

    }

    public String getAppVersionFromGradle() {

        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Version Exception", e.getMessage());
        }

        if (result != null || !result.isEmpty()) {
//            String version = result;
            return result;
        } else {
            return "0.0";
        }
        //appversion


    }

    public Bitmap GetThumbnailBitmap(String VideoUrl) {
        Bitmap bmThumbnail = null;
        String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        CharSequence input = VideoUrl;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String imgstr = "https://img.youtube.com/vi/" + VideoUrl.substring(VideoUrl.lastIndexOf("/") + 1) + "/1.jpg";
            try {
                URL url = new URL(imgstr);
                bmThumbnail = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            bmThumbnail = ThumbnailUtils.createVideoThumbnail(VideoUrl, MediaStore.Video.Thumbnails.MINI_KIND);
        }

        return bmThumbnail;
    }

    public static ControlObject getControlObject(List<ControlObject> controlObjectList, String controlName) {
        ControlObject controlObject = new ControlObject();
        for (int i = 0; i < controlObjectList.size(); i++) {

            if (controlName.contentEquals(controlObjectList.get(i).getControlName())) {
                controlObject = controlObjectList.get(i);
                return controlObject;
            }
        }
        return controlObject;
    }

    public boolean isDateLiesBetweenTwoDates(String strStartDate, String strEndDate) {

        Date startDate = null, endDate = null;   // assume these are set to something
        Date currentDate = null;      // the date in question


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            startDate = sdf.parse(strStartDate);
            endDate = sdf.parse(strEndDate);
            String strCurrentDate = sdf.format(new Date()); // current Date in String
            currentDate = sdf.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate.compareTo(currentDate) * currentDate.compareTo(endDate) > 0;
    }

    public String getLocationLevel(List<PostsMasterModel> userPostDetailsList, String postID) {
        String locationLevel = "";
        StringBuilder locationName = new StringBuilder();
        if (userPostDetailsList != null && userPostDetailsList.size() > 0) {
            List<PostSubLocationsModel> postSubLocationsModelList = new ArrayList<>();
            for (PostsMasterModel userPostDetails : userPostDetailsList) {
                if (userPostDetails.getID() != null && userPostDetails.getID().equalsIgnoreCase(postID)) {
                    postSubLocationsModelList = userPostDetails.getPostSubLocations();
                    break;
                }
            }

            for (PostSubLocationsModel postSubLocationsModel : postSubLocationsModelList) {
                locationName.append(postSubLocationsModel.getText()).append(" \\ ");
            }
//            locationLevel = locationName.substring(0, locationName.length() - 2);
            if (!locationName.toString().equals("")) {
                locationLevel = locationName.substring(0, locationName.length() - 3);
            }
//            locationLevel = locationLevel.substring(1);
        }
        return locationLevel;
    }

    public String getLocationLevelForUserProfile(List<PostsMasterModel> userPostDetailsList, String postID) {
        String locationLevel = "";
        StringBuilder locationName = new StringBuilder();
        if (userPostDetailsList != null && userPostDetailsList.size() > 0) {
            List<PostSubLocationsModel> postSubLocationsModelList = new ArrayList<>();
            for (PostsMasterModel userPostDetails : userPostDetailsList) {
                if (userPostDetails.getID().equalsIgnoreCase(postID)) {
                    postSubLocationsModelList = userPostDetails.getPostSubLocations();
                    break;
                }
            }
            if(postSubLocationsModelList!=null){
            Collections.reverse(postSubLocationsModelList);
            for (PostSubLocationsModel postSubLocationsModel : postSubLocationsModelList) {
                locationName.append(postSubLocationsModel.getText()).append(" , ");
            }

            locationLevel = locationName.substring(0, locationName.length() - 3);
        }}
        return locationLevel;
    }

    public String getLocationLevelForDashboard(List<UserPostDetails> userPostDetailsList, String postID) {
        String locationLevel = "";
        if (userPostDetailsList != null && userPostDetailsList.size() > 0) {
            List<PostSubLocationsModel> postSubLocationsModelList = new ArrayList<>();
            for (UserPostDetails userPostDetails : userPostDetailsList) {
                if (userPostDetails.getPostID().equalsIgnoreCase(postID)) {
                    postSubLocationsModelList = userPostDetails.getPostSubLocations();
                    break;
                }
            }
            PostSubLocationsModel postSubLocationsModel = postSubLocationsModelList.get(postSubLocationsModelList.size() - 1);
            locationLevel = postSubLocationsModel.getText();

        }
        return locationLevel;
    }

    public HashMap<String, String> getEditValuesFromJSONObject(JSONObject jsonObject, String controlType, String controlName) {
        HashMap<String, String> controlValue = new HashMap<>();
        String controlValue_id = null;
        try {
            if ((jsonObject.has(controlName) && !jsonObject.getString(controlName).equalsIgnoreCase("") && jsonObject.getString(controlName) != null) || (jsonObject.has(controlName + "_id") || jsonObject.has(controlName + "_Coordinates"))) {
                if (controlType.equalsIgnoreCase(CONTROL_TYPE_GPS)) {
                    controlValue.put("Coordinates", jsonObject.getString(controlName + "_Coordinates"));
                    controlValue.put("Type", jsonObject.getString(controlName + "_Type"));
                } else if (controlType.equalsIgnoreCase(CONTROL_TYPE_DATA_CONTROL)) {
                    controlValue.put("Value", jsonObject.getString(controlName));
                    controlValue.put("Value_id", jsonObject.getString(controlName + "_id"));
                } else if (controlType.equalsIgnoreCase(CONTROL_TYPE_CHECKBOX) || controlType.equalsIgnoreCase(CONTROL_TYPE_CHECK_LIST) || controlType.equalsIgnoreCase(CONTROL_TYPE_RADIO_BUTTON) || controlType.equalsIgnoreCase(CONTROL_TYPE_DROP_DOWN) || controlType.equalsIgnoreCase(CONTROL_TYPE_USER) || controlType.equalsIgnoreCase(CONTROL_TYPE_POST)) {
                    controlValue.put("Value", jsonObject.getString(controlName));
                    controlValue.put("Value_id", jsonObject.getString(controlName + "id"));
                } else {
                    controlValue.put("Value", jsonObject.getString(controlName));
                }
            }
        } catch (Exception e) {

        }
        if (controlValue.get("Value") != null && controlValue.get("Value").equalsIgnoreCase("null")) {
            controlValue.put("Value", "");
        }
//        else{
//            controlValue.put("Value", "");
//        }
        return controlValue;
    }

    public HashMap<String, String> getEditValuesFromJSONObject(JSONArray jsonArray, String controlType, String controlName) {

        HashMap<String, String> controlValue = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject subformJson = jsonArray.getJSONObject(i);
                if ((subformJson.has(controlName) && !subformJson.getString(controlName).equalsIgnoreCase("") && subformJson.getString(controlName) != null) || (subformJson.has(controlName + "_id") || subformJson.has(controlName + "_Coordinates"))) {
                    if (controlType.equalsIgnoreCase(CONTROL_TYPE_GPS)) {
                        controlValue.put("Value", subformJson.getString(controlName + "_Coordinates"));
                    } else if (controlType.equalsIgnoreCase(CONTROL_TYPE_DATA_CONTROL)) {
                        controlValue.put("Value", subformJson.getString(controlName));
                        controlValue.put("Value_id", subformJson.getString(controlName + "_id"));
                    } else if (controlType.equalsIgnoreCase(CONTROL_TYPE_CHECKBOX) || controlType.equalsIgnoreCase(CONTROL_TYPE_CHECK_LIST) || controlType.equalsIgnoreCase(CONTROL_TYPE_RADIO_BUTTON) || controlType.equalsIgnoreCase(CONTROL_TYPE_DROP_DOWN)) {
                        controlValue.put("Value", subformJson.getString(controlName));
                        controlValue.put("Value_id", subformJson.getString(controlName + "id"));
                    } else {
                        controlValue.put("Value", subformJson.getString(controlName));
                    }
                }
            } catch (Exception e) {

            }
        }
        return controlValue;
    }

    public String getDeletedRowIDs(List<String> rowIdslist) {
        StringBuilder value = new StringBuilder();
        for (String rowID : rowIdslist) {
//            value = String.join(",", rowID);
            value.append(",").append(rowID);
        }
        String valueStr = "";
        if (value.length() > 1) {
            valueStr = value.substring(1);
        }
        return valueStr;
    }

    public String checkForDeletedRows(JSONArray subformJsonArrayData, List<String> currentRowIdslist) {
        StringBuilder value = new StringBuilder();
        try {
            for (int i = 0; i < subformJsonArrayData.length(); i++) {
                JSONObject jsonObject = subformJsonArrayData.getJSONObject(i);
                if (!currentRowIdslist.contains(jsonObject.getString("Trans_ID"))) {
//                    value = String.join(",", jsonObject.getString("Trans_ID"));
                    value.append(",").append(jsonObject.getString("Trans_ID"));
                }
            }

        } catch (Exception e) {
        }
        String valueStr = "";
        if (value.length() > 1) {
            valueStr = value.substring(1);
        }
        return valueStr;
    }

    public List<String> checkForDeletedRowsOffline(JSONArray subformJsonArrayData, List<String> currentRowIdslist) {
        List<String> deletedRowIds = new ArrayList<>();
        try {
            for (int i = 0; i < subformJsonArrayData.length(); i++) {
                JSONObject jsonObject = subformJsonArrayData.getJSONObject(i);
                if (!currentRowIdslist.contains(jsonObject.getString("Trans_ID"))) {
                    deletedRowIds.add(jsonObject.getString("Trans_ID"));
                }
            }
        } catch (Exception e) {
        }
        return deletedRowIds;
    }

    public List<String> getRowIdsOfEditForm(JSONArray subformJsonArrayData) {
        List<String> editFormRowIds = new ArrayList<>();
        try {
            for (int i = 0; i < subformJsonArrayData.length(); i++) {
                JSONObject jsonObject = subformJsonArrayData.getJSONObject(i);
                editFormRowIds.add(jsonObject.getString("Trans_ID"));
            }
        } catch (Exception e) {
        }
        return editFormRowIds;
    }

    public int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    public List<String> getEditColumnsCopy(List<EditOrViewColumns> editOrViewColumnsList, String type, String subFormName) {
        List<String> editableColumns = new ArrayList<>();
        if (editOrViewColumnsList != null) {
            for (EditOrViewColumns editOrViewColumns : editOrViewColumnsList) {
               /* if (editOrViewColumns.getColumnType().equals(type) && editOrViewColumns.getColumnName().equalsIgnoreCase(subFormName)) {
                    editableColumns.add(editOrViewColumns.getColumnName());
                } else if (editOrViewColumns.getColumnType().equals(type)) {
                    editableColumns.add(editOrViewColumns.getColumnName());
                }*/

                if (editOrViewColumns.getColumnType().equals(type) && editOrViewColumns.getColumnName().equalsIgnoreCase(subFormName)) {
                    if (editOrViewColumns.getColumnName().endsWith("_Coordinates")) {
                        editableColumns.add(editOrViewColumns.getColumnName().substring(0, editOrViewColumns.getColumnName().length() - 12));
                    } else {
                        editableColumns.add(editOrViewColumns.getColumnName());
                    }
                } else if (editOrViewColumns.getColumnType().equals(type)) {
                    if (editOrViewColumns.getColumnName().endsWith("_Coordinates")) {
                        editableColumns.add(editOrViewColumns.getColumnName().substring(0, editOrViewColumns.getColumnName().length() - 12));
                    } else {
                        editableColumns.add(editOrViewColumns.getColumnName());
                    }
                }
            }
        }
        return editableColumns;
    }
    public List<String> getEditColumns(List<EditOrViewColumns> editOrViewColumnsList, String type, String subFormName) {
        List<String> editableColumns = new ArrayList<>();
        if (editOrViewColumnsList != null) {
            for (EditOrViewColumns editOrViewColumns : editOrViewColumnsList) {
                    if (editOrViewColumns.getColumnName().endsWith("_Coordinates")) {
                        editableColumns.add(editOrViewColumns.getColumnName().substring(0, editOrViewColumns.getColumnName().length() - 12));
                    } else {
                        editableColumns.add(editOrViewColumns.getColumnName());
                    }
            }
        }
        return editableColumns;
    }

    public boolean checkPrimaryKeysAsPerDataCollectionObj(AppDetails appDetails, ImproveDataBase improveDataBase) {
        boolean flag = true;
        //only main table checking
        flag = checkPrimaryAndCompositeKeysInTable(improveDataBase, appDetails.getTableName(), appDetails.getPrimaryKey(), appDetails.getCompositeKey());

        //sub form table checking
        if (appDetails.getSubFormDetails() != null) {
            for (int i = 0; i < appDetails.getSubFormDetails().size(); i++) {
                SubFormTableColumns subFormTableColumns = appDetails.getSubFormDetails().get(i);
                flag = checkPrimaryAndCompositeKeysInTable(improveDataBase, subFormTableColumns.getTableName(), subFormTableColumns.getPrimaryKey(), subFormTableColumns.getCompositeKey());
                if (!flag) {
                    break;
                }
            }
        }

        return flag;
    }

    private boolean checkPrimaryAndCompositeKeysInTable(ImproveDataBase improveDataBase, String tableName,
                                                        String primaryKey, String compositeKey) {
        boolean flag = true;
        if (improveDataBase.tableExists(tableName)) {
            List<String> selTabCols = improveDataBase.getPrimaryOrCompositeKeys("SELECT name FROM pragma_table_info('" + tableName + "') where pk");
            System.out.println("ll_pk:" + selTabCols);
            if (primaryKey != null && !primaryKey.trim().equals("")) {
                if (selTabCols.size() == 0) {
                    flag = false;//no key exist in selected table but current obj having only 1 primary key
                } else if (selTabCols.size() == 1) {
                    if (!primaryKey.equals(selTabCols.get(0))) {
                        flag = false;// selected table key is not match with current obj primary key
                    } else {
                        //match with selected table & current obj primary key
                    }
                } else {
                    flag = false;//more keys exists in selected table but current obj having only 1 primary key
                }
            } else {
                if (compositeKey != null && !compositeKey.equals("")) {
                    String[] compositeKeys = compositeKey.split("\\,");
                    if (compositeKeys.length == selTabCols.size()) {
                        for (int i = 0; i < compositeKeys.length; i++) {
                            if (!selTabCols.contains(compositeKeys[i])) {
                                flag = false;// current obj key not exist in selected table
                                break;
                            }
                        }
                    } else {
                        flag = false;// keys exist in selected table size is not equal to current obj
                    }
                } else {
                    if (selTabCols.size() > 0) {
                        flag = false;//key exist in selected table but current obj not having primary key
                    }
                }
            }
        }

        return flag;
    }

    private void openPDFFile(String targetPdf, Context context) {
        try {
            File file = new File(targetPdf);
            if (file.exists()) {
                Uri path = FileProvider.getUriForFile(
                        context,
                        context.getPackageName() + ".xmlToPdf.provider",
                        file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    showToast(context, e.getMessage());
                }
            } else {
                showToast(context, "PDF file is not existing in storage. Your Generated path is " + targetPdf);
            }
        } catch (Exception exception) {
            showToast(context, "Error occurred while opening the PDF. Error message : " + exception.getMessage());
        }

    }

    public void openFile(String url, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            //File file = new File(context.getExternalFilesDir(null), url);
            File file = new File(url);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            if (url.contains(".doc") || url.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.contains(".ppt") || url.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.contains(".xls") || url.contains(".xlsx")) {
                // Excel file application/vnd.ms-excel
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.contains(".zip") || url.contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.contains(".wav") || url.contains(".mp3") || url.contains(".WMA") || url.contains(".ogg")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.contains(".PNG") || url.contains(".JPEG") || url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "openFile: " + e);
            showToast(context, e.getMessage());
        }
    }

    public void openFile_old(String url, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);

            File file = new File(context.getExternalFilesDir(null), url);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
            if (url.contains(".doc") || url.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.contains(".ppt") || url.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.contains(".xls") || url.contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.contains(".zip") || url.contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.contains(".wav") || url.contains(".mp3") || url.contains(".WMA") || url.contains(".ogg")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.contains(".PNG") || url.contains(".JPEG") || url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "openFile: " + e);
        }
    }

    public void setImageFromPackageFolder(Context context, String sdcardUrl, String filename, ImageView imageView) {
        File appSpecificExternalDir = null;
        try {
//            File cDir = context.getApplicationContext().getExternalFilesDir(null); //  /storage/emulated/0/Android/data/com.bhargo.user/files/
            File cDir = context.getApplicationContext().getExternalFilesDir(sdcardUrl); //
            appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            if (appSpecificExternalDir.exists()) {
                Log.d(TAG, "FileExitsInPackage: " + appSpecificExternalDir);
                Bitmap myBitmap = BitmapFactory.decodeFile(appSpecificExternalDir.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);// Images
            }
        } catch (Exception e) {
            Log.d(TAG, "setImageFromPackageFolder: " + e);
        }

    }

    public void showMessage(Context context, String message) {
        Toast mToast = null;
        if (context == null || TextUtils.isEmpty(message))
            return;
        int duration;
        if (message.length() > 10) {
            duration = Toast.LENGTH_LONG;
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public boolean isInternetAvailable(Context context) {
        boolean hasInternet = false;
        // Get the connectivity manager
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

// Check if the device is connected to the internet
        boolean isConnected = false;
        if (cm.getActiveNetworkInfo() != null) {
            isConnected = cm.getActiveNetworkInfo().isConnected();
        }

// Use the isConnected variable to perform actions based on internet availability
        if (isConnected) {

            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }

            return hasInternet;
        } else {
            // Internet is not available
            return false;
        }
    }

    public void controlFocusBackground(String strControlType, String strDisplayNameAlignment, boolean isFocus, LinearLayout ll_tap_text, View rView) {
        try {
            if (strDisplayNameAlignment == null) {
                strDisplayNameAlignment = "0";
            }
            switch (strControlType) {
                case CONTROL_TYPE_AUTO_COMPLETION:
                case CONTROL_TYPE_USER:
                case CONTROL_TYPE_POST:
                case CONTROL_TYPE_TEXT_INPUT:
                case CONTROL_TYPE_NUMERIC_INPUT:
                case CONTROL_TYPE_PHONE:
                case CONTROL_TYPE_EMAIL:
                case CONTROL_TYPE_LARGE_INPUT:
                case CONTROL_TYPE_CALENDER:
                case CONTROL_TYPE_PERCENTAGE:
                case CONTROL_TYPE_DECIMAL:
                case CONTROL_TYPE_CURRENCY:
                case CONTROL_TYPE_TIME:
                case CONTROL_TYPE_CAMERA:
                case CONTROL_TYPE_FILE_BROWSING:
                case CONTROL_TYPE_PASSWORD:

                    if (isFocus) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("1")) {
                            TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);
                            TextInputLayout til_password = rView.findViewById(R.id.til_password);
                            if (til_TextType != null) {
                                til_TextType.setBoxStrokeColor(ContextCompat.getColor(context, R.color.theme_control_active_color));
                            }
                            if (til_password != null) {
                                til_password.setBoxStrokeColor(ContextCompat.getColor(context, R.color.theme_control_active_color));
                            }
                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.theme_control_active_color));
                        }
                    } else {
                        if (strDisplayNameAlignment.equalsIgnoreCase("1")) {
                            TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);
                            TextInputLayout til_password = rView.findViewById(R.id.til_password);
                            if (til_TextType != null) {
                                til_TextType.setBoxStrokeColor(ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                            }
                            if (til_password != null) {
                                til_password.setBoxStrokeColor(ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                            }
                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                        }
                    }

                    break;

            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public void controlEnableDisableBackground(String strControlType, String strDisplayNameAlignment, boolean isEnable, LinearLayout ll_tap_text, View rView) {
        try {
            if (strDisplayNameAlignment == null) {
                strDisplayNameAlignment = "0";
            }
            switch (strControlType) {
                case CONTROL_TYPE_TEXT_INPUT:
                case CONTROL_TYPE_NUMERIC_INPUT:
                case CONTROL_TYPE_PHONE:
                case CONTROL_TYPE_EMAIL:
                case CONTROL_TYPE_LARGE_INPUT:
                case CONTROL_TYPE_CALENDER:
                case CONTROL_TYPE_PERCENTAGE:
                case CONTROL_TYPE_DECIMAL:
                case CONTROL_TYPE_CURRENCY:
                case CONTROL_TYPE_TIME:
                case CONTROL_TYPE_CAMERA:
                case CONTROL_TYPE_FILE_BROWSING:
                case CONTROL_TYPE_PASSWORD:
                case CONTROL_TYPE_DROP_DOWN:
                case CONTROL_TYPE_CHECK_LIST:
                case CONTROL_TYPE_AUTO_COMPLETION:
                case CONTROL_TYPE_RATING:
                case CONTROL_TYPE_USER:
                case CONTROL_TYPE_POST:
                case CONTROL_TYPE_DATA_CONTROL:
                case CONTROL_TYPE_GPS:
                case CONTROL_TYPE_BUTTON:
                    if (!isEnable) { // control disable
                        if (strDisplayNameAlignment.equalsIgnoreCase("1")) {
                            TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);
                            TextInputLayout til_password = rView.findViewById(R.id.til_password);
                            CustomEditText ce_TextType = rView.findViewById(R.id.ce_TextType);
                            CustomEditText tie_password = rView.findViewById(R.id.tie_password);

                            if (til_TextType != null) {
                                til_TextType.setBackgroundColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                til_TextType.setBoxStrokeColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                ce_TextType.setVisibility(View.INVISIBLE);
                            }
                            if (til_password != null) {
                                til_password.setBackgroundColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                til_password.setBoxStrokeColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                tie_password.setVisibility(View.INVISIBLE);
                            }
                        } else {

                            RatingBar ratingBar = rView.findViewById(R.id.ratingStar); // For Rating
                            if(ratingBar != null){
                                int color = ContextCompat.getColor(context, R.color.theme_bhargo_color_four);
                                int Seccolor = ContextCompat.getColor(context, R.color.theme_bhargo_color_four);
                                ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(color));
                                ratingBar.setProgressBackgroundTintList(ColorStateList.valueOf(Seccolor));
                            }else {
                                GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                                drawable.mutate();
                                drawable.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                drawable.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));
                            }
                            LinearLayout ll_editor = rView.findViewById(R.id.ll_editor); // For LargeInput Editor
                            if (ll_editor != null) {
                                RichTextEditor editor = rView.findViewById(R.id.text_editor); // For LargeInput Editor
                                editor.setEnabled(false);
                                editor.setInputEnabled(false);
                                GradientDrawable drawable_2 = (GradientDrawable) ll_editor.getBackground();
                                drawable_2.mutate();
                                drawable_2.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                drawable_2.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));
                                ll_editor.setEnabled(isEnable);

                                for (int i = 0; i < ll_editor.getChildCount(); i++) {
                                    View child = ll_editor.getChildAt(i);
                                    if (child != null) {
                                        setViewDisable(child, isEnable);
                                    }
                                }
                            }

                            LinearLayout ll_user_search = rView.findViewById(R.id.ll_user_search); // For User Controls
                            if (ll_user_search != null) {
                                GradientDrawable drawable_2 = (GradientDrawable) ll_user_search.getBackground();
                                drawable_2.mutate();
                                drawable_2.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                                drawable_2.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));
                                ll_user_search.setEnabled(isEnable);

                                for (int i = 0; i < ll_user_search.getChildCount(); i++) {
                                    View child = ll_user_search.getChildAt(i);
                                    if (child != null) {
                                        setViewDisable(child, isEnable);
                                    }
                                }
                            }
                        }
                    } else { // control enable
                        if (strDisplayNameAlignment.equalsIgnoreCase("1")) {
                            TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);
                            TextInputLayout til_password = rView.findViewById(R.id.til_password);
                            CustomEditText ce_TextType = rView.findViewById(R.id.ce_TextType);
                            CustomEditText tie_password = rView.findViewById(R.id.tie_password);

                            if (til_TextType != null) {
                                ce_TextType.setVisibility(View.VISIBLE);
                                til_TextType.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                                til_TextType.setBoxStrokeColor(ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                            }
                            if (til_password != null) {
                                tie_password.setVisibility(View.VISIBLE);
                                til_password.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                                til_password.setBoxStrokeColor(ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                            }
                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.mat_white));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                            LinearLayout ll_editor = rView.findViewById(R.id.ll_editor); // For LargeInput Editor
                            if (ll_editor != null) {
                                GradientDrawable drawable_2 = (GradientDrawable) ll_editor.getBackground();
                                drawable_2.mutate();
                                drawable_2.setColor(ContextCompat.getColor(context, R.color.mat_white));
                                drawable_2.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));

                                ll_editor.setEnabled(isEnable);

                                for (int i = 0; i < ll_editor.getChildCount(); i++) {
                                    View child = ll_editor.getChildAt(i);
                                    if (child != null) {
                                        setViewDisable(child, isEnable);
                                    }
                                }
                            }
                            LinearLayout ll_user_search = rView.findViewById(R.id.ll_user_search);
                            if (ll_user_search != null) {
                                GradientDrawable drawable_2 = (GradientDrawable) ll_user_search.getBackground();
                                drawable_2.mutate();
                                drawable_2.setColor(ContextCompat.getColor(context, R.color.mat_white));
                                drawable_2.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));

                                ll_user_search.setEnabled(isEnable);

                                for (int i = 0; i < ll_user_search.getChildCount(); i++) {
                                    View child = ll_user_search.getChildAt(i);
                                    if (child != null) {
                                        setViewDisable(child, isEnable);
                                    }
                                }
                            }

                        }
                    }
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    if (isEnable) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("6") || strDisplayNameAlignment.equalsIgnoreCase("7")) {
                            StateListDrawable gradientDrawable = (StateListDrawable) ll_tap_text.getBackground();
                            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
                            Drawable[] children = drawableContainerState.getChildren();
                            GradientDrawable drawable = (GradientDrawable) children[0];
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.colorPrimary));
                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.mat_white));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                        }
                        LinearLayout layout_camera_or_gallery = rView.findViewById(R.id.layout_camera_or_gallery);
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_default));
                        layout_camera_or_gallery.setEnabled(isEnable);

                        LinearLayout layout_delete = rView.findViewById(R.id.layout_delete);
                        if(layout_delete != null) {
                            ImageView iv_delete = (ImageView) layout_delete.getChildAt(0);
                            iv_delete.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_circle_delete));
                            iv_delete.setColorFilter(ContextCompat.getColor(context, R.color.delete_icon), android.graphics.PorterDuff.Mode.SRC_IN);
                            layout_delete.setEnabled(isEnable);
                        }
                    } else {
                        if (strDisplayNameAlignment.equalsIgnoreCase("6") || strDisplayNameAlignment.equalsIgnoreCase("7")) {
                            StateListDrawable gradientDrawable = (StateListDrawable) ll_tap_text.getBackground();
                            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
                            Drawable[] children = drawableContainerState.getChildren();
                            GradientDrawable drawable = (GradientDrawable) children[0];
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));
                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));
                        }
                        LinearLayout layout_camera_or_gallery = rView.findViewById(R.id.layout_camera_or_gallery);
                        if(layout_camera_or_gallery != null) {
                            layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_disable));
//                            layout_camera_or_gallery.setBackground(context.getDrawable(R.drawable.ic_icon_bar_code));
                            layout_camera_or_gallery.setEnabled(isEnable);
                        }
                        LinearLayout layout_delete = rView.findViewById(R.id.layout_delete);
                        if(layout_delete !=null) {
                            ImageView iv_delete = (ImageView) layout_delete.getChildAt(0);
                            iv_delete.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_for_image_disable));
                            iv_delete.setColorFilter(ContextCompat.getColor(context, R.color.control_disable_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
                            layout_delete.setEnabled(isEnable);
                        }
                    }
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    if (isEnable) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("6") || strDisplayNameAlignment.equalsIgnoreCase("7")) {
                            StateListDrawable gradientDrawable = (StateListDrawable) ll_tap_text.getBackground();
                            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
                            Drawable[] children = drawableContainerState.getChildren();
                            GradientDrawable drawable = (GradientDrawable) children[0];
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.colorPrimary));

                            CustomTextView tv_startVideoRecorder = rView.findViewById(R.id.tv_startVideoRecorder);
                            tv_startVideoRecorder.setTextColor(ContextCompat.getColor(context, R.color.white));
                            if (tv_startVideoRecorder.getCompoundDrawables() != null) {
                                if (strDisplayNameAlignment.equalsIgnoreCase("6")) {
                                    if (tv_startVideoRecorder.getCompoundDrawables()[2] != null)
                                        tv_startVideoRecorder.getCompoundDrawables()[2].setTint(ContextCompat.getColor(context, R.color.white));
                                } else {
                                    if (tv_startVideoRecorder.getCompoundDrawables()[0] != null)
                                        tv_startVideoRecorder.getCompoundDrawables()[0].setTint(ContextCompat.getColor(context, R.color.white));
                                }
                            }

                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.mat_white));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));
                        }
                    } else {
                        if (strDisplayNameAlignment.equalsIgnoreCase("6") || strDisplayNameAlignment.equalsIgnoreCase("7")) {
                            StateListDrawable gradientDrawable = (StateListDrawable) ll_tap_text.getBackground();
                            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
                            Drawable[] children = drawableContainerState.getChildren();
                            GradientDrawable drawable = (GradientDrawable) children[0];
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));

                            CustomTextView tv_startVideoRecorder = rView.findViewById(R.id.tv_startVideoRecorder);
                            tv_startVideoRecorder.setTextColor(ContextCompat.getColor(context, R.color.control_disable_text_color));
                            if (tv_startVideoRecorder.getCompoundDrawables() != null) {
                                if (strDisplayNameAlignment.equalsIgnoreCase("6")) {
                                    if (tv_startVideoRecorder.getCompoundDrawables()[2] != null)
                                        tv_startVideoRecorder.getCompoundDrawables()[2].setTint(ContextCompat.getColor(context, R.color.control_disable_text_color));
                                } else {
                                    if (tv_startVideoRecorder.getCompoundDrawables()[0] != null)
                                        tv_startVideoRecorder.getCompoundDrawables()[0].setTint(ContextCompat.getColor(context, R.color.control_disable_text_color));
                                }
                            }
                        } else {
                            GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                            drawable.mutate();
                            drawable.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                            drawable.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));
                        }
                    }
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    if (isEnable) {
                        GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                        drawable.mutate();
                        drawable.setColor(ContextCompat.getColor(context, R.color.mat_white));
                        drawable.setStroke(2, ContextCompat.getColor(context, R.color.theme_bhargo_color_four));

                        LinearLayout layout_camera_or_gallery = rView.findViewById(R.id.layout_camera_or_gallery);
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_default));
                        layout_camera_or_gallery.setEnabled(isEnable);
                        CustomTextView tv_clearSignature = rView.findViewById(R.id.tv_clearSignature);
                        tv_clearSignature.setEnabled(isEnable);
                    } else {
                        GradientDrawable drawable = (GradientDrawable) ll_tap_text.getBackground();
                        drawable.mutate();
                        drawable.setColor(ContextCompat.getColor(context, R.color.control_disable_color));
                        drawable.setStroke(2, ContextCompat.getColor(context, R.color.control_disable_color));

                        LinearLayout layout_camera_or_gallery = rView.findViewById(R.id.layout_camera_or_gallery);
                        layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_disable));
                        layout_camera_or_gallery.setEnabled(isEnable);

                        CustomTextView tv_clearSignature = rView.findViewById(R.id.tv_clearSignature);
                        tv_clearSignature.setEnabled(isEnable);
                    }
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    CustomCheckBox checkBox = rView.findViewById(R.id.checkbox);
                    if (isEnable) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("8") || strDisplayNameAlignment.equalsIgnoreCase("9")) {
                            checkBox.setButtonDrawable(context.getDrawable(R.drawable.custom_checkbox));
                        } else {
                            checkBox.setButtonDrawable(context.getDrawable(R.drawable.custom_checkbox_default));
                        }
                    }else{
                        if (strDisplayNameAlignment.equalsIgnoreCase("8")|| strDisplayNameAlignment.equalsIgnoreCase("9")) {
                            if(!isEnable) {
                                checkBox.setButtonDrawable(context.getDrawable(R.drawable.control_disable_background_checkbox_rounded));
                            }else{
                                checkBox.setButtonDrawable(context.getDrawable(R.drawable.control_disable_background_checkbox_rounded));
                            }
                        }  else {
                            if(!isEnable) {
                                checkBox.setButtonDrawable(context.getDrawable(R.drawable.control_disable_background_checkbox));
                            }else{
                                checkBox.setBackgroundColor(ContextCompat.getColor(context, R.color.control_radio_button_default));
                            }
                        }
                    }
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    CustomRadioButton radioButton = (CustomRadioButton) rView;
                    if (isEnable) {
                        if (strDisplayNameAlignment.equalsIgnoreCase("8")) {
                            radioButton.setBackground(context.getDrawable(R.drawable.radio_flat_selector));
                        } else if (strDisplayNameAlignment.equalsIgnoreCase("9")) {
                            radioButton.setBackground(context.getDrawable(R.drawable.radio_button_nine));
                        } else {
                            radioButton.setButtonDrawable(context.getDrawable(R.drawable.custom_radiobutton_default));
                        }
                    } else {
                        if (strDisplayNameAlignment.equalsIgnoreCase("8") || strDisplayNameAlignment.equalsIgnoreCase("9")) {
                            radioButton.setBackgroundColor(ContextCompat.getColor(context, R.color.control_disable_color));
                        } else {
                            radioButton.setButtonDrawable(context.getDrawable(R.drawable.control_disable_background_checkbox_rounded));
                        }
                    }
                    break;
            }
            ll_tap_text.setEnabled(isEnable);
            for (int i = 0; i < ll_tap_text.getChildCount(); i++) {
                View child = ll_tap_text.getChildAt(i);
                if (child != null) {
                    setViewDisable(child, isEnable);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            Log.d(TAG, "controlEnableDisableBackground: " + e);
        }
    }

    public boolean alwaysEnable(String controlType) {
        boolean result = false;
        try {
            if (/*controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON)
                    ||*/ controlType.equalsIgnoreCase(CONTROL_TYPE_AUTO_NUMBER)
                    || controlType.equalsIgnoreCase(CONTROL_TYPE_AUTO_GENERATION)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_URL_LINK)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_DYNAMIC_LABEL)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_MENU)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_CALENDAR_EVENT)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_CHART)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_PROGRESS)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIEWFILE)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_COUNT_UP_TIMER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_DATA_VIEWER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_MAP)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBMIT_BUTTON)) {
                result = true;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "alwaysEnable", e);
        }
        return result;
    }

    public interface IL {

        void onSuccess();

        void onCancel();
    }


    public static String yyymmdd_To_ddmmyyy(String strInputDate) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(strInputDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return outputDateStr;
    }

    public void clearControls(List<Item> Clear_ControlItems, List<ControlObject> controlObjectList, List<Variable_Bean> variableBeanList, LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {
            for (int var = 0; var < Clear_ControlItems.size(); var++) {
                Item item = Clear_ControlItems.get(var);
                if (item.getId().contentEquals("Control")) {
                    for (int i = 0; i < controlObjectList.size(); i++) {
                        ControlObject temp_controlObj = controlObjectList.get(i);
                        if (item.getValue().contains(temp_controlObj.getControlName())) {
                            switch (temp_controlObj.getControlType()) {
                                case CONTROL_TYPE_TEXT_INPUT:
                                    TextInput clearTextView = (TextInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    clearTextView.Clear();
                                    break;
                                case CONTROL_TYPE_NUMERIC_INPUT:
                                    NumericInput numverTextView = (NumericInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    numverTextView.Clear();
                                    break;
                                case CONTROL_TYPE_PHONE:
                                    Phone PhoneView = (Phone) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    PhoneView.Clear();
                                    break;
                                case CONTROL_TYPE_EMAIL:
                                    Email EmailView = (Email) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    EmailView.Clear();
                                    break;
                                case CONTROL_TYPE_LARGE_INPUT:
                                    LargeInput LargeInputView = (LargeInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    LargeInputView.Clear();
                                    break;
                                case CONTROL_TYPE_CAMERA:
                                    Camera CameraView = (Camera) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    CameraView.clear();
                                    break;
                                case CONTROL_TYPE_FILE_BROWSING:
                                    FileBrowsing FileBrowsingView = (FileBrowsing) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    FileBrowsingView.clear();
                                    break;
                                case CONTROL_TYPE_CALENDER:
                                    com.bhargo.user.controls.standard.Calendar CalendarView = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    CalendarView.Clear();
                                    break;
                                case CONTROL_TYPE_CHECKBOX:
                                    Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    CheckBoxView.clear();
                                    break;
                                case CONTROL_TYPE_RADIO_BUTTON:
                                    RadioGroupView RadioGroupview = (RadioGroupView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    RadioGroupview.Clear();
                                    break;
                                case CONTROL_TYPE_DROP_DOWN:
                                    DropDown DropDownview = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    DropDownview.Clear();
                                    break;
                                case CONTROL_TYPE_CHECK_LIST:
                                    CheckList CheckListview = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    CheckListview.Clear();
                                    break;
                                case CONTROL_TYPE_RATING:
                                    Rating Ratingview = (Rating) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Ratingview.Clear();
                                    break;
                                case CONTROL_TYPE_VOICE_RECORDING:
                                    VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    voiceRecording.clear();
                                    break;
                                case CONTROL_TYPE_VIDEO_RECORDING:
                                    VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    videoRecording.clear();
                                    break;

                                case CONTROL_TYPE_AUDIO_PLAYER:
                                    AudioPlayer audioPlayer_ = (AudioPlayer) List_ControlClassObjects.get(temp_controlObj.getControlName());

                                    break;
                                case CONTROL_TYPE_VIDEO_PLAYER:
                                    VideoPlayer VideoPlayerView = (VideoPlayer) List_ControlClassObjects.get(temp_controlObj.getControlName());

                                    break;
                                case CONTROL_TYPE_PERCENTAGE:
                                    Percentage PercentageView = (Percentage) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    PercentageView.Clear();
                                    break;
                                case CONTROL_TYPE_DECIMAL:
                                    DecimalView Decimalview = (DecimalView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Decimalview.Clear();
                                    break;
                                case CONTROL_TYPE_PASSWORD:
                                    Password Passwordview = (Password) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Passwordview.Clear();
                                    break;
                                case CONTROL_TYPE_CURRENCY:
                                    Currency Currencyview = (Currency) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Currencyview.Clear();
                                    break;
                                case CONTROL_TYPE_TIME:
                                    Time time = (Time) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    time.clear();
                                    break;
                                case CONTROL_TYPE_SIGNATURE:
                                    SignatureView Signatureview = (SignatureView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    Signatureview.Clear();
                                    break;
                                case CONTROL_TYPE_URL_LINK:

                                    break;
                                case CONTROL_TYPE_DATA:
                                    DataControl dataControl = (DataControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    dataControl.clear();
                                    break;
                                case CONTROL_TYPE_GPS:
                                    Gps_Control gps_control = (Gps_Control) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    gps_control.clearAll();
                                    break;
                                case CONTROL_TYPE_USER:
                                    UserControl userControl = (UserControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    userControl.clear();
                                    break;
                                case CONTROL_TYPE_POST:
                                    PostControl postControl = (PostControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    postControl.clear();
                                    break;
                                case CONTROL_TYPE_AUTO_COMPLETION:
                                    AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                                    autoCompletionControl.clear();
                                    break;

                            }
                        }
                    }

                } else if (item.getId().contentEquals("Variable")) {
                    for (int i = 0; i < variableBeanList.size(); i++) {
                        Variable_Bean variable = variableBeanList.get(i);
                        if (variable.getVariable_Type().equalsIgnoreCase("Single")
                                && variable.getVariable_Name().toLowerCase().contentEquals(item.getValue().substring(1, item.getValue().length() - 1).split("\\.")[2].toLowerCase())) {
                            variable.setVariable_singleValue("");
                        } else if (variable.getVariable_Type().equalsIgnoreCase("Multiple") && variable.getVariable_Name().toLowerCase().contentEquals(item.getValue().substring(1, item.getValue().length() - 1).split("\\.")[2].toLowerCase())) {
                            variable.setVariable_multiValue(new ArrayList<>());
                        } else {//Multidimensional
                            if (variable.getVariable_Name().toLowerCase(Locale.ROOT).contentEquals(item.getValue().substring(1, item.getValue().length() - 1).split("\\.")[2].toLowerCase())) {
                                RealmDBHelper.deleteTable(context, variable.getVariable_Name().trim().toLowerCase());
                            }
                        }

                    }

                } else if (item.getId().contentEquals("DataSource")) {
                    if (item.getValue().startsWith("(im:GetData.")) {
                        RealmDBHelper.deleteTable(context, item.getValue().substring(1, item.getValue().length() - 1).split("\\.")[1]);
                    }

                }

            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetClearControls", e);
        }
    }

    public void clearControl(ControlObject temp_controlObj, LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {
            switch (temp_controlObj.getControlType()) {
                case CONTROL_TYPE_TEXT_INPUT:
                    TextInput clearTextView = (TextInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    clearTextView.Clear();
                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numverTextView = (NumericInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    numverTextView.Clear();
                    break;
                case CONTROL_TYPE_PHONE:
                    Phone PhoneView = (Phone) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    PhoneView.Clear();
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email EmailView = (Email) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    EmailView.Clear();
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput LargeInputView = (LargeInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    LargeInputView.Clear();
                    break;
                case CONTROL_TYPE_CAMERA:
                    Camera CameraView = (Camera) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CameraView.clear();
                    break;
                case CONTROL_TYPE_FILE_BROWSING:
                    FileBrowsing FileBrowsingView = (FileBrowsing) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    FileBrowsingView.clear();
                    break;
                case CONTROL_TYPE_CALENDER:
                    com.bhargo.user.controls.standard.Calendar CalendarView = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CalendarView.Clear();
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CheckBoxView.clear();
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView RadioGroupview = (RadioGroupView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    RadioGroupview.Clear();
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown DropDownview = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    DropDownview.Clear();
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    CheckList CheckListview = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CheckListview.Clear();
                    break;
                case CONTROL_TYPE_RATING:
                    Rating Ratingview = (Rating) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Ratingview.Clear();
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    voiceRecording.clear();
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    videoRecording.clear();
                    break;

                case CONTROL_TYPE_AUDIO_PLAYER:
                    AudioPlayer audioPlayer_ = (AudioPlayer) List_ControlClassObjects.get(temp_controlObj.getControlName());

                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:
                    VideoPlayer VideoPlayerView = (VideoPlayer) List_ControlClassObjects.get(temp_controlObj.getControlName());

                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage PercentageView = (Percentage) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    PercentageView.Clear();
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView Decimalview = (DecimalView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Decimalview.Clear();
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password Passwordview = (Password) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Passwordview.Clear();
                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency Currencyview = (Currency) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Currencyview.Clear();
                    break;
                case CONTROL_TYPE_TIME:
                    Time time = (Time) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    time.clear();
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    SignatureView Signatureview = (SignatureView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Signatureview.Clear();
                    break;
                case CONTROL_TYPE_URL_LINK:

                    break;
                case CONTROL_TYPE_DATA:
                    DataControl dataControl = (DataControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    dataControl.clear();
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control gps_control = (Gps_Control) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    gps_control.clearAll();
                    break;
                case CONTROL_TYPE_USER:
                    UserControl userControl = (UserControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    userControl.clear();
                    break;
                case CONTROL_TYPE_POST:
                    PostControl postControl = (PostControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    postControl.clear();
                    break;
                case CONTROL_TYPE_AUTO_COMPLETION:
                    AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    autoCompletionControl.clear();
                    break;


            }


        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetClearControls", e);
        }
    }

    public void clearSubformControls(ControlObject temp_controlObj, LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {
            switch (temp_controlObj.getControlType()) {
                case CONTROL_TYPE_TEXT_INPUT:
                    TextInput clearTextView = (TextInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    clearTextView.Clear();
                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numverTextView = (NumericInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    numverTextView.Clear();
                    break;
                case CONTROL_TYPE_PHONE:
                    Phone PhoneView = (Phone) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    PhoneView.Clear();
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email EmailView = (Email) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    EmailView.Clear();
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput LargeInputView = (LargeInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    LargeInputView.Clear();
                    break;
                case CONTROL_TYPE_CAMERA:
                    Camera CameraView = (Camera) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CameraView.clear();
                    break;
                case CONTROL_TYPE_FILE_BROWSING:
                    FileBrowsing FileBrowsingView = (FileBrowsing) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    FileBrowsingView.clear();
                    break;
                case CONTROL_TYPE_CALENDER:
                    com.bhargo.user.controls.standard.Calendar CalendarView = (com.bhargo.user.controls.standard.Calendar) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CalendarView.Clear();
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CheckBoxView.clear();
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView RadioGroupview = (RadioGroupView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    RadioGroupview.Clear();
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown DropDownview = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    DropDownview.Clear();
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    CheckList CheckListview = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    CheckListview.Clear();
                    break;
                case CONTROL_TYPE_RATING:
                    Rating Ratingview = (Rating) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Ratingview.Clear();
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    voiceRecording.clear();
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    videoRecording.clear();
                    break;

                case CONTROL_TYPE_AUDIO_PLAYER:
                    AudioPlayer audioPlayer_ = (AudioPlayer) List_ControlClassObjects.get(temp_controlObj.getControlName());

                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:
                    VideoPlayer VideoPlayerView = (VideoPlayer) List_ControlClassObjects.get(temp_controlObj.getControlName());

                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage PercentageView = (Percentage) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    PercentageView.Clear();
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView Decimalview = (DecimalView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Decimalview.Clear();
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password Passwordview = (Password) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Passwordview.Clear();
                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency Currencyview = (Currency) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Currencyview.Clear();
                    break;
                case CONTROL_TYPE_TIME:
                    Time time = (Time) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    time.clear();
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    SignatureView Signatureview = (SignatureView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    Signatureview.Clear();
                    break;
                case CONTROL_TYPE_URL_LINK:

                    break;
                case CONTROL_TYPE_DATA:
                    DataControl dataControl = (DataControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    dataControl.clear();
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control gps_control = (Gps_Control) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    gps_control.clearAll();
                    break;
                case CONTROL_TYPE_USER:
                    UserControl userControl = (UserControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    userControl.clear();
                    break;
                case CONTROL_TYPE_POST:
                    PostControl postControl = (PostControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    postControl.clear();
                    break;
                case CONTROL_TYPE_AUTO_COMPLETION:
                    AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                    autoCompletionControl.clear();
                    break;

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SubcontrolClear", e);
        }
    }


    private static  boolean isValidValueType(String type){
        String[] control_types = new String[]{"Static", "ControlName", "Calenders", "SubControls","SystemVariables",
                "Variables", "GPSControl", "API", "Query", "FormFields",
                "SubmitResponse","DataTableControls","RequestOfflineData","DataControls","TransactionalOffline","ScanName"};
        for (int i = 0; i <control_types.length ; i++) {

            if(type.toLowerCase().contentEquals(control_types[i].toLowerCase())){
                return true;
            }

        }
        return false;
    }

    public List<Item> sortListAlphabetically(List<Item> controlObjectItemsList,boolean isSortAlphabeticalOrAscending){
        Collections.sort(controlObjectItemsList, new Comparator<Item>() {
            @Override
            public int compare(Item item, Item t1) {
                String s1 = item.getValue();
                String s2 = t1.getValue();
                if(isSortAlphabeticalOrAscending) {
                    return s1.compareToIgnoreCase(s2);
                }else {
                    return s2.compareToIgnoreCase(s1);
                }
            }

        });


        return controlObjectItemsList;

    }


    public static File getOutputFilePath(Context context,String fileName, String fileType) {
        File createFile;
        File fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/CreateFiles");
        //String filePath = fileName.replace(" ","_") + "_" + System.currentTimeMillis() + fileType;
        String filePath = fileName.replace(" ", "_") + fileType;
        createFile = new File(fileStorage, filePath);//Create Output file
        return createFile;
    }
    public void dismissWithTryCatch(ProgressDialog dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
            Log.getStackTraceString(e);
        } catch (final Exception e) {
            // Do nothing.
            Log.getStackTraceString(e);
        } finally {
            dialog = null;
        }
    }

}

