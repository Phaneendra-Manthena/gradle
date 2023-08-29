package com.bhargo.user.screens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextInputEditText;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;

public class CreateNewTaskActivity extends BaseActivity {

    private static final String TAG = "CreateNewTaskActivity";
    public String strTaskAttemptedBy = null;
    Context context;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    SearchableSpinner sp_priority;
    List<String> priorityList;
    ArrayAdapter adapterPriority;
    CustomEditText etStartDate, etEndDate;
    ImageView iv_taskStartDate, iv_taskEndDate;
    CustomButton btn_nextCT;
    CustomTextInputEditText tie_TaskName, tie_TaskDesc;
    GetServices getServices;
    RadioGroup rg_TaskAttemptedBy;
    CustomRadioButton rb_all_users, rb_anyone_user;
    String strTaskAppsInfo, strTaskContentInfo, strTaskGroupInfo, strTaskIndividualInfo;
    private DatePickerDialog picker;
    private String strPriorityId, startDateFormatForServer, endDateStringFormatForServer;
    private String strTaskEdit, strTaskId, strTaskName, strTaskDesc, strTaskStartDate, strTaskEndDate, strTaskPriority = null, strIsSingleUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        context = CreateNewTaskActivity.this;

        tie_TaskName = findViewById(R.id.tie_TaskName);
        tie_TaskDesc = findViewById(R.id.tie_TaskDesc);
        sp_priority = findViewById(R.id.sp_priority);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        rg_TaskAttemptedBy = findViewById(R.id.rg_TaskAttemptedBy);
        rb_all_users = findViewById(R.id.rb_all_users);
        rb_anyone_user = findViewById(R.id.rb_anyone_user);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            strTaskEdit = getIntent().getStringExtra("TaskEdit");
            strTaskId = getIntent().getStringExtra("TaskId");
            strTaskName = getIntent().getStringExtra("TaskName");
            strTaskDesc = getIntent().getStringExtra("TaskDesc");
            strTaskStartDate = getIntent().getStringExtra("TaskStartDate");
            strTaskEndDate = getIntent().getStringExtra("TaskEndDate");
            strTaskPriority = getIntent().getStringExtra("TaskPriority");
            strTaskAttemptedBy = getIntent().getStringExtra("TaskAttemptedBy");
            strTaskAppsInfo = getIntent().getStringExtra("TaskAppsInfo");
            strTaskContentInfo = getIntent().getStringExtra("TaskContentInfo");
            strTaskGroupInfo = getIntent().getStringExtra("TaskGroupInfo");
            strTaskIndividualInfo = getIntent().getStringExtra("TaskIndividualInfo");

        }

        initViews(context);

    }


    public void initViews(Context context) {
        try {
            sessionManager = new SessionManager(context);
            improveHelper = new ImproveHelper(context);
            initializeActionBar();
            if (strTaskEdit != null) {
                title.setText(getResources().getString(R.string.edit_task));
            } else {
                title.setText(getResources().getString(R.string.create_new_task));
            }
            enableBackNavigation(true);

            getServices = RetrofitUtils.getUserService();

            iv_circle_appIcon.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);

            iv_taskStartDate = findViewById(R.id.iv_taskStartDate);
            iv_taskEndDate = findViewById(R.id.iv_taskEndDate);

            btn_nextCT = findViewById(R.id.btn_nextCT);

            if (strTaskName != null) {
                tie_TaskName.setText(strTaskName);
            }
            if (strTaskDesc != null) {
                tie_TaskDesc.setText(strTaskDesc);
            }
            if (strTaskStartDate != null) {
                etStartDate.setText(strTaskStartDate);
            }
            if (strTaskEndDate != null) {
                etEndDate.setText(strTaskEndDate);
            }
            if (strTaskAttemptedBy != null) {
                if (strTaskAttemptedBy.equalsIgnoreCase("0")) {
                    rb_all_users.setChecked(true);
                } else if (strTaskAttemptedBy.equalsIgnoreCase("1")) {
                    rb_anyone_user.setChecked(true);
                }
            }

            if (strTaskEdit != null && AppConstants.TASK_ATTEMPTS_BY != null
                    && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
                    && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {

                iv_taskStartDate.setOnClickListener(null);
                iv_taskStartDate.setClickable(false);
                iv_taskStartDate.setEnabled(false);
                iv_taskEndDate.setOnClickListener(null);
                iv_taskEndDate.setClickable(false);
                iv_taskEndDate.setEnabled(false);
                etStartDate.setEnabled(false);
                etEndDate.setEnabled(false);
                sp_priority.setEnabled(false);
                sp_priority.setOnItemSelectedListener(null);
                sp_priority.setSelected(false);

                for (int i = 0; i < rg_TaskAttemptedBy.getChildCount(); i++) {
                    rg_TaskAttemptedBy.getChildAt(i).setEnabled(false);
                }

//            rb_all_users.setEnabled(false);
//            rb_anyone_user.setEnabled(false);
            }

            sp_priority.setItems(taskPriorityApi());

            if (strTaskPriority != null) {

                if (strTaskPriority.equalsIgnoreCase("Low")) {
                    sp_priority.setItemID("1");
                } else if (strTaskPriority.equalsIgnoreCase("Medium")) {
                    sp_priority.setItemID("2");
                } else if (strTaskPriority.equalsIgnoreCase("High")) {
                    sp_priority.setItemID("3");
                }
            }


            sp_priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (sp_priority.getSelectedId() != null) {

                        strPriorityId = sp_priority.getSelectedId();
                        strTaskPriority = sp_priority.getSelectedName();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            rg_TaskAttemptedBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    CustomRadioButton rb = group.findViewById(checkedId);
                    if (null != rb && checkedId > -1) {
                        if (rb.getText().toString().equalsIgnoreCase("All Users")) {
                            strTaskAttemptedBy = "0";
                        } else if (rb.getText().toString().equalsIgnoreCase("Any one user")) {
                            strTaskAttemptedBy = "1";
                        }
//                    Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            etStartDate.setInputType(InputType.TYPE_NULL);
            iv_taskStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    String startDateFormatForEditText = String.format("%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year);
                                    etStartDate.setText(startDateFormatForEditText);
//                                startDateFormatForServer = String.format("%d-%02d-%02d", year , (monthOfYear + 1), dayOfMonth);
//                                timePicker(etStartDate, dateString);
                                }
                            }, year, month, day);
                    picker.show();
                    picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
            });

            etEndDate.setInputType(InputType.TYPE_NULL);
            iv_taskEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    String EndDateStringFormatForEditText = String.format("%02d-%02d-%02d", dayOfMonth, (monthOfYear + 1), year);
                                    etEndDate.setText(EndDateStringFormatForEditText);
//                                endDateStringFormatForServer = String.format("%d-%02d-%02d", year, (monthOfYear + 1), dayOfMonth);
//                                timePicker(etEndDate, dateString);
                                }
                            }, year, month, day);
                    picker.show();
                    picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
            });

            btn_nextCT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                staticIntentData();

                    if (tie_TaskName.getText().toString().length() == 0 || tie_TaskName.getText().toString().equalsIgnoreCase("")
                            || tie_TaskName.getText().toString().isEmpty()) {
                        ImproveHelper.showToast(context, "Please enter task Name");
                    } else if (etStartDate.getText().toString().length() == 0 || etStartDate.getText().toString().equalsIgnoreCase("")
                            || etStartDate.getText().toString().isEmpty()) {
                        ImproveHelper.showToast(context, "Please check start date and time");
                    } else if (etEndDate.getText().toString().length() == 0 || etEndDate.getText().toString().equalsIgnoreCase("")
                            || etEndDate.getText().toString().isEmpty()) {
                        ImproveHelper.showToast(context, "Please check end date and time");
                    } else if (strPriorityId == null) {
                        ImproveHelper.showToast(context, "Please select priority");
                    } else if (strTaskAttemptedBy == null) {
                        ImproveHelper.showToast(context, "Please select task attempted by");
                    } else if (tie_TaskDesc.getText().toString().length() == 0 || tie_TaskDesc.getText().toString().equalsIgnoreCase("")
                            || tie_TaskDesc.getText().toString().isEmpty()) {
                        ImproveHelper.showToast(context, "Please enter task description");
                    } else {

                        String dtStart = etStartDate.getText().toString();
                        String dtEnd = etEndDate.getText().toString();
                        SimpleDateFormat formatStart = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat formatEnd = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date dateStart = formatStart.parse(dtStart);
                            Date dateEnd = formatEnd.parse(dtEnd);
                            Log.d(TAG, "onClickCheckDateValidation: " + dateStart + " == " + dateEnd);
                            if (dateStart.compareTo(dateEnd) <= 0) {

                                Intent intent = new Intent(context, TaskContentActivity.class);
                                intent.putExtra("TaskEdit", strTaskEdit);
                                intent.putExtra("TaskId", strTaskId);
                                intent.putExtra("TaskName", tie_TaskName.getText().toString());
                                intent.putExtra("TaskDesc", tie_TaskDesc.getText().toString());
                                intent.putExtra("TaskStartDate", etStartDate.getText().toString());
                                intent.putExtra("TaskEndDate", etEndDate.getText().toString());
                                intent.putExtra("TaskPriority", strPriorityId);
                                intent.putExtra("TaskPriorityTitle", strTaskPriority);
                                intent.putExtra("TaskAttemptedBy", strTaskAttemptedBy);
                                intent.putExtra("TaskAppsInfo", strTaskAppsInfo);
                                intent.putExtra("TaskContentInfo", strTaskContentInfo);
                                intent.putExtra("TaskGroupInfo", strTaskGroupInfo);
                                intent.putExtra("TaskIndividualInfo", strTaskIndividualInfo);


                                startActivity(intent);
                            } else {
                                ImproveHelper.showToast(context, "Please check start date and end date");

                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    public List<SpinnerData> taskPriorityApi() {

        List<SpinnerData> spinnerDataArrayListP = new ArrayList<>();
        try {
            SpinnerData spinnerDataLow = new SpinnerData();
            spinnerDataLow.setName("Low");
            spinnerDataLow.setId("1");

            SpinnerData spinnerDataMedium = new SpinnerData();
            spinnerDataMedium.setName("Medium");
            spinnerDataMedium.setId("2");

            SpinnerData spinnerDataHigh = new SpinnerData();
            spinnerDataHigh.setName("High");
            spinnerDataHigh.setId("3");

            spinnerDataArrayListP.add(spinnerDataLow);
            spinnerDataArrayListP.add(spinnerDataMedium);
            spinnerDataArrayListP.add(spinnerDataHigh);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "taskPriorityApi", e);
        }
        return spinnerDataArrayListP;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backClick();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        backClick();
    }

    public void timePicker(CustomEditText customEditText, String dateString) {
        int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        try {
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            cal.set(Calendar.MINUTE, minute);

                            Format formatter = new SimpleDateFormat("hh:mm:ss");

//                        String strFormatTime = String.format("%02d:%02d", hourOfDay, minute);
//                        customEditText.setText(dateString+" "+strFormatTime);
                            customEditText.setText(dateString + " " + formatter.format(cal.getTime()));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "timePicker", e);
        }
    }

    public void backClick() {
        try {
            Intent intent = new Intent(context, BottomNavigationActivity.class);
            intent.putExtra("FromTaskDeployment", "FromTaskDeployment");
            startActivity(intent);
            finish();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "backClick", e);
        }
    }


    public void staticIntentData() {

        Intent intent = new Intent(context, TaskContentActivity.class);
        intent.putExtra("TaskEdit", "strTaskEdit");
        intent.putExtra("TaskId", "strTaskId");
        intent.putExtra("TaskName", "tie_TaskName.getText().toString()");
        intent.putExtra("TaskDesc", "tie_TaskDesc.getText().toString()");
        intent.putExtra("TaskStartDate", "00-00-0000");
        intent.putExtra("TaskEndDate", "00-00-0000");
        intent.putExtra("TaskPriority", "1");
        intent.putExtra("TaskPriorityTitle", "strTaskPriority");
        intent.putExtra("TaskAttemptedBy", "0");
        intent.putExtra("TaskAppsInfo", strTaskAppsInfo);
        intent.putExtra("TaskContentInfo", strTaskContentInfo);
        intent.putExtra("TaskGroupInfo", strTaskGroupInfo);
        intent.putExtra("TaskIndividualInfo", strTaskIndividualInfo);
        startActivity(intent);

    }
}
