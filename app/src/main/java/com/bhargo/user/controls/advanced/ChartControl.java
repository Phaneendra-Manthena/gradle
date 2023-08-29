package com.bhargo.user.controls.advanced;

import static com.bhargo.user.utils.ImproveHelper.getValueListFromOfflineTable;
import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.Axis;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.googlecode.mp4parser.authoring.tracks.TextTrackImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartControl implements UIVariables {

    private Activity context;
    private ControlObject controlObject;
    private boolean subFormFlag;
    private int subFormPos;
    private String subFormName;
    private LinearLayout linearLayout,ll_main_inside,ll_control_ui;
    CardView cv_all;
    private CustomTextView tv_displayName,tv_hint,ct_showText;
    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;
    private HorizontalBarChart rowChart;
    private LineChart areaChart;
    private ScatterChart scatterChart;
    private CombinedChart combinedChart;
    private List<String> chartColors;
    View rView;

    public ChartControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        chartColors = new ArrayList<>();
        initViews();
    }

    private void initViews(){
        linearLayout = new LinearLayout(context);
        linearLayout.setTag(controlObject.getControlName());
        ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
        linearLayout.setLayoutParams(ImproveHelper.layout_params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View rView = lInflater.inflate(R.layout.control_chart, null);
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                rView = lInflater.inflate(R.layout.control_chart_six, null);
            }else {
//            rView = lInflater.inflate(R.layout.control_chart, null);
                rView = lInflater.inflate(R.layout.control_chart_default, null);
            }
        } else {
//            rView = lInflater.inflate(R.layout.control_chart, null);
            rView = lInflater.inflate(R.layout.control_chart_default, null);
        }
        tv_displayName = rView.findViewById(R.id.tv_displayName);
        tv_hint = rView.findViewById(R.id.tv_hint);
        ll_main_inside = rView.findViewById(R.id.ll_main_inside);
        ll_control_ui = rView.findViewById(R.id.ll_control_ui);
        cv_all = rView.findViewById(R.id.cv_all);
        ct_showText = rView.findViewById(R.id.ct_showText);

        lineChart = rView.findViewById(R.id.lineChart);
        barChart = rView.findViewById(R.id.barChart);
        pieChart = rView.findViewById(R.id.pieChart);
        rowChart = rView.findViewById(R.id.horizontalBarChart);
        areaChart = rView.findViewById(R.id.areaChart);
        scatterChart = rView.findViewById(R.id.scatterChart);
        combinedChart = rView.findViewById(R.id.combinedChart);
        if(controlObject != null && controlObject.getChartType() != null) {
            if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_LINE)) {
                lineChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    lineChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_BAR)) {
                barChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    barChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_STACKED)) {
                barChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    barChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_PIE)) {
                pieChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    pieChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_ROW)) {
                rowChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    rowChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_AREA)) {
                areaChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    areaChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_SCATTER)) {
                scatterChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    scatterChart.getLegend().setEnabled(false);
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_COMBO)) {
                combinedChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    combinedChart.getLegend().setEnabled(false);
                }
            }
        }
      /*  if(controlObject != null && controlObject.getChartType() != null) {
            if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_LINE)) {
                lineChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_BAR)) {
                barChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_STACKED)) {
                barChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_PIE)) {
                pieChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_ROW)) {
                rowChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_AREA)) {
                areaChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_SCATTER)) {
                scatterChart.setVisibility(View.VISIBLE);
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_COMBO)) {
                combinedChart.setVisibility(View.VISIBLE);
            }
        }*/
        setControlValues();
        linearLayout.addView(rView);
    }

    private void setControlValues() {
        try {
            tv_displayName.setText(controlObject.getDisplayName());
            if (controlObject.getHint() == null || controlObject.getHint().contentEquals("")) {
                tv_hint.setVisibility(View.GONE);
            } else {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            }

            if (controlObject.isHideDisplayName()) {
                tv_displayName.setVisibility(View.GONE);
                tv_hint.setVisibility(View.GONE);
            }

            chartColors = controlObject.getChartColors();

        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }

    public LinearLayout getChartLayout(){
        return linearLayout;
    }

    ActionWithoutCondition_Bean actionObject;
    LinkedHashMap<String, List<String>> outputData ;
    public void setChartData(ActionWithoutCondition_Bean actionWithoutCondition_bean, LinkedHashMap<String, List<String>> map){
        actionObject = actionWithoutCondition_bean;
        outputData = map;
        setChartData(controlObject.getChartType());
    }


    private List<BarEntry> getBarEntries(List<String> yAxisValues){
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i <yAxisValues.size() ; i++) {
            BarEntry barEntry = new BarEntry(i+1,Float.parseFloat(yAxisValues.get(i)));
            barEntries.add(barEntry);
        }
        return barEntries;
    }
    private List<BarEntry> getStackedBarEntries(List<String> yAxisParams){
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i <yAxisParams.size() ; i++) {


        }
        /*for (int i = 0; i <yAxisValues.size() ; i++) {
            BarEntry barEntry = new BarEntry(i+1,Float.parseFloat(yAxisValues.get(i)));
            barEntries.add(barEntry);
        }*/
        return barEntries;
    }

    private void setChartData(String chartType){
        try {
            if (chartType.contentEquals(AppConstants.CHART_TYPE_PIE)) {
                List<PieEntry> entries = new ArrayList<>();
                String pieChartControlName = actionObject.getPieChartControlName();
                String measurementValue = actionObject.getPieChartMeasurementValue();
                List<String> pieChartValueData = outputData.get(measurementValue.toLowerCase());
                List<String> pieChartLabelData = outputData.get(pieChartControlName.toLowerCase());
                List<Integer> sliceColors = new ArrayList<>();

                for (int i = 0; i < pieChartValueData.size(); i++) {
                    PieEntry pieEntry = new PieEntry(Float.parseFloat(pieChartValueData.get(i)), pieChartLabelData.get(i));
                    entries.add(pieEntry);
                    sliceColors.add(Color.parseColor(ImproveHelper.randomColor()));
//                LegendEntry l1=new LegendEntry("Male");
                }

                PieDataSet dataSet = new PieDataSet(entries, "XYZ");
                dataSet.setColors(sliceColors);
//            dataSet.setSliceSpace(1f);

                PieData data = new PieData(dataSet);
                data.setValueTextSize(8f);
                //data.setValueFormatter(new PercentFormatter());
                pieChart.setData(data);
                Legend l = pieChart.getLegend();

                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setWordWrapEnabled(true);
                l.setEnabled(true);
                pieChart.setTouchEnabled(false);
                pieChart.invalidate();
        /*    pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            pieChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            pieChart.getLegend().setWordWrapEnabled(true);
            pieChart.setTouchEnabled(false);
            //pieChart.setUsePercentValues(true);
            pieChart.invalidate(); // refresh
*/
            } else if (chartType.contentEquals(AppConstants.CHART_TYPE_GAUGE)) {

            } else {

                List<String> xAxisValues = actionObject.getxAxisValues();
                List<String> yAxisValues = actionObject.getyAxisValues();
                List<String> xAxisLabelsList = new ArrayList<>();
                List<String> yAxisLabelsList = new ArrayList<>();

                if (chartType.contentEquals(AppConstants.CHART_TYPE_LINE)) {
                    List<ILineDataSet> dataSets = new ArrayList<>();
                    List<List<Entry>> differentEntriesList = new ArrayList<>();
                    if(xAxisValues.size()>0){
                        List<String> xAxisLabels = outputData.get(xAxisValues.get(0).toLowerCase());
                        for (int i = 0; i < yAxisValues.size(); i++) {
                            List<Entry> entries = new ArrayList<>();
                            List<String> yAxisData = outputData.get(yAxisValues.get(i).toLowerCase());

                            for (int j = 0; j < xAxisLabels.size(); j++) {
                                xAxisLabelsList.add(xAxisLabels.get(j));
                                Entry entry = new Entry((float) j, Float.parseFloat(yAxisData.get(j)));
                                entries.add(entry);
                            }

                            differentEntriesList.add(entries);
                        }
                    }
                    for (int i = 0; i < differentEntriesList.size(); i++) {
                        LineDataSet lineDataSet = new LineDataSet(differentEntriesList.get(i), "" + (i + 1));
//                        lineDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
//                        lineDataSet.setCircleColor(Color.parseColor(ImproveHelper.randomColor()));
                        if (chartColors != null && chartColors.size() > 0 && i <= chartColors.size() - 1) {
                            lineDataSet.setColor(Color.parseColor(chartColors.get(i)));
                            lineDataSet.setCircleColor(Color.parseColor(chartColors.get(i)));
                        }else{
                            lineDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                            lineDataSet.setCircleColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        lineDataSet.setLineWidth(2.0f);
                        dataSets.add(lineDataSet);
                    }

                    LineData data = new LineData(dataSets);
                    lineChart.setData(data);
                    lineChart.invalidate();
                    XAxis xAxis = lineChart.getXAxis();
                    YAxis leftAxis = lineChart.getAxisLeft();
                    YAxis rightAxis = lineChart.getAxisRight();
                    rightAxis.setEnabled(false);
                    lineChart.setDrawGridBackground(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    leftAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    lineChart.setTouchEnabled(false);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabelsList));
                } else if (chartType.contentEquals(AppConstants.CHART_TYPE_ROW)) {
                    /*List<String> xAxisParams = actionObject.getxAxisValues();
                    List<String> yAxisParams = actionObject.getyAxisValues();
                    List<Axis> yAxisLabelsList1 = actionObject.getyAxisList();
                    List<IBarDataSet> barDataSets = new ArrayList<>();

                    for (int i = 0; i < yAxisParams.size(); i++) {
                        List<String> yAxisValues1 = outputData.get(yAxisParams.get(i).toLowerCase());
                        BarDataSet barDataSet = new BarDataSet(getBarEntries(yAxisValues1), yAxisLabelsList1.get(i).getLabel());
                        if (chartColors != null && chartColors.size() > 0 && i <= chartColors.size() - 1) {
                            barDataSet.setColor(Color.parseColor(chartColors.get(i)));
                        } else {
                            barDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        barDataSets.add(barDataSet);
                    }


                    BarData barData = new BarData(barDataSets);
                    rowChart.setData(barData);
                    rowChart.getDescription().setEnabled(false); // To remove description from the chart
                    rowChart.setDragEnabled(true);
                    rowChart.setTouchEnabled(true);
                    rowChart.setDoubleTapToZoomEnabled(true);
                    XAxis xAxis = rowChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(outputData.get(xAxisParams.get(0).toLowerCase())));
                    if (outputData.get(xAxisParams.get(0).toLowerCase()).size() > 3) {
                        xAxis.setLabelRotationAngle(-60);
                    }
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMinimum(0f);
                    xAxis.setDrawGridLines(false);

                    barData.setBarWidth(0.15f);
                    if (barDataSets.size() > 1) {
                        barChart.groupBars(0f, 0.5f, 0.1f);
                    } else {
                        barData.setBarWidth(0.25f);
                    }
                    rowChart.setDrawGridBackground(false);
                    rowChart.animate();
                    rowChart.invalidate();*/
                    List<IBarDataSet> dataSets = new ArrayList<>();
                    List<List<BarEntry>> differentEntriesList = new ArrayList<>();
                    if(xAxisValues.size()>0){
                        List<String> xAxisLabels = outputData.get(xAxisValues.get(0).toLowerCase());
                        for (int i = 0; i < yAxisValues.size(); i++) {
                            List<BarEntry> entries = new ArrayList<>();
                            List<String> yAxisData = outputData.get(yAxisValues.get(i).toLowerCase());

                            for (int j = 0; j < yAxisData.size(); j++) {
                                yAxisLabelsList.add(yAxisData.get(j));
                                BarEntry entry = new BarEntry((float) j, Float.parseFloat(xAxisLabels.get(j)));
                                entries.add(entry);
                            }

                            differentEntriesList.add(entries);
                        }
                    }

                    for (int i = 0; i < differentEntriesList.size(); i++) {
                        BarDataSet barDataSet = new BarDataSet(differentEntriesList.get(i), "" + (i + 1));
                        //barDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        if (chartColors != null && chartColors.size() > 0 && i <= chartColors.size() - 1) {
                            barDataSet.setColor(Color.parseColor(chartColors.get(i)));
                        }else{
                            barDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        dataSets.add(barDataSet);
                    }

                    BarData data = new BarData(dataSets);
                    data.setBarWidth(0.9f);
                    rowChart.setData(data);
                    rowChart.setFitBars(true);
                    rowChart.invalidate();

                    XAxis xAxis = rowChart.getXAxis();
                    YAxis leftAxis = rowChart.getAxisLeft();
                    YAxis rightAxis = rowChart.getAxisRight();
                    rightAxis.setAxisMinimum(0f);
                    leftAxis.setAxisMinimum(0f);
                    leftAxis.setEnabled(false);
                    rowChart.setDrawGridBackground(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    leftAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    rowChart.setTouchEnabled(false);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(yAxisLabelsList));
                } else if (chartType.contentEquals(AppConstants.CHART_TYPE_AREA)) {
                    List<ILineDataSet> dataSets = new ArrayList<>();
                    List<List<Entry>> differentEntriesList = new ArrayList<>();
                    if(xAxisValues.size()>0) {
                        List<String> xAxisLabels = outputData.get(xAxisValues.get(0).toLowerCase());
                        for (int i = 0; i < yAxisValues.size(); i++) {
                            List<Entry> entries = new ArrayList<>();
                            List<String> yAxisData = outputData.get(yAxisValues.get(i));
                            for (int j = 0; j < xAxisLabels.size(); j++) {
                                xAxisLabelsList.add(xAxisLabels.get(j));
                                Entry entry = new Entry((float) j, Float.parseFloat(yAxisData.get(j)));
                                entries.add(entry);
                            }
                            differentEntriesList.add(entries);
                        }
                    }



                    for (int i = 0; i < differentEntriesList.size(); i++) {
                        LineDataSet lineDataSet = new LineDataSet(differentEntriesList.get(i), "" + (i + 1));
                        if (chartColors != null && chartColors.size() > 0 && i <= chartColors.size() - 1) {
                            lineDataSet.setColor(Color.parseColor(chartColors.get(i)));
                            lineDataSet.setCircleColor(Color.parseColor(chartColors.get(i)));
                            lineDataSet.setFillColor(Color.parseColor(chartColors.get(i)));
                        } else {
                            lineDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                            lineDataSet.setCircleColor(Color.parseColor(ImproveHelper.randomColor()));
                            lineDataSet.setFillColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                       /* lineDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        lineDataSet.setCircleColor(Color.parseColor(ImproveHelper.randomColor()));*/
                        lineDataSet.setDrawFilled(true);
//                        lineDataSet.setFillColor(Color.parseColor(ImproveHelper.randomColor()));
                        lineDataSet.setLineWidth(2.0f);
                        dataSets.add(lineDataSet);
                    }

                    LineData data = new LineData(dataSets);
                    areaChart.setData(data);
                    areaChart.invalidate();
                    XAxis xAxis = areaChart.getXAxis();
                    YAxis leftAxis = areaChart.getAxisLeft();
                    YAxis rightAxis = areaChart.getAxisRight();
                    rightAxis.setEnabled(false);
                    areaChart.setDrawGridBackground(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    leftAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    areaChart.setTouchEnabled(false);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabelsList));
                } else if (chartType.contentEquals(AppConstants.CHART_TYPE_SCATTER)) {
                    List<IScatterDataSet> dataSets = new ArrayList<>();
                    List<List<Entry>> differentEntriesList = new ArrayList<>();
                    if(xAxisValues.size()>0) {
                        List<String> xAxisLabels = outputData.get(xAxisValues.get(0).toLowerCase());
                        for (int i = 0; i < yAxisValues.size(); i++) {
                            List<Entry> entries = new ArrayList<>();
                            List<String> yAxisData = outputData.get(yAxisValues.get(i));

                            for (int j = 0; j < xAxisLabels.size(); j++) {
                                xAxisLabelsList.add(xAxisLabels.get(j));
                                Entry entry = new Entry((float) j, Float.parseFloat(yAxisData.get(j)));
                                entries.add(entry);
                            }

                            differentEntriesList.add(entries);
                        }
                    }

                    for (int i = 0; i < differentEntriesList.size(); i++) {
                        ScatterDataSet scatterDataSet = new ScatterDataSet(differentEntriesList.get(i), "" + (i + 1));
                        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
                        scatterDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        scatterDataSet.setScatterShapeHoleColor(Color.parseColor(ImproveHelper.randomColor()));
                        dataSets.add(scatterDataSet);
                    }

                    ScatterData data = new ScatterData(dataSets);
                    scatterChart.setData(data);
                    scatterChart.invalidate();
                    XAxis xAxis = scatterChart.getXAxis();
                    YAxis leftAxis = scatterChart.getAxisLeft();
                    YAxis rightAxis = scatterChart.getAxisRight();
                    rightAxis.setEnabled(false);
                    scatterChart.setDrawGridBackground(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    leftAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    scatterChart.setTouchEnabled(false);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabelsList));
                } else if (chartType.contentEquals(AppConstants.CHART_TYPE_COMBO)) {
                    List<List<Entry>> differentLineEntriesList = new ArrayList<>();
                    List<List<BarEntry>> differentBarEntriesList = new ArrayList<>();
//                    List<String> comboChartTypes = actionObject.getComboChartTypes();
                    List<String> comboChartTypes = new ArrayList<>();
                    comboChartTypes.add(AppConstants.CHART_TYPE_LINE);
                    comboChartTypes.add(AppConstants.CHART_TYPE_BAR);


                    for (int i = 0; i < yAxisValues.size(); i++) {
                        List<Entry> lineEntries = new ArrayList<>();
                        List<BarEntry> barEntries = new ArrayList<>();
                        List<String> xAxisLabels = outputData.get(xAxisValues.get(0).toLowerCase());
                        List<String> yAxisData = outputData.get(yAxisValues.get(i).toLowerCase());

                        if (comboChartTypes.get(i).contentEquals(AppConstants.CHART_TYPE_LINE)) {
                            xAxisLabelsList = new ArrayList<>();
                            for (int j = 0; j < xAxisLabels.size(); j++) {
                                xAxisLabelsList.add(xAxisLabels.get(j));
                                Entry entry = new Entry((float) j, Float.parseFloat(yAxisData.get(j)));
                                lineEntries.add(entry);
                            }
                            differentLineEntriesList.add(lineEntries);
                        } else if (comboChartTypes.get(i).contentEquals(AppConstants.CHART_TYPE_BAR)) {
                            xAxisLabelsList = new ArrayList<>();
                            for (int j = 0; j < xAxisLabels.size(); j++) {
                                xAxisLabelsList.add(xAxisLabels.get(j));
                                BarEntry barEntry = new BarEntry((float) j, Float.parseFloat(yAxisData.get(j)));
                                barEntries.add(barEntry);
                            }
                            differentBarEntriesList.add(barEntries);
                        }
                    }

                    CombinedData data = new CombinedData();

                    for (int i = 0; i < differentLineEntriesList.size(); i++) {

                        LineDataSet lineDataSet = new LineDataSet(differentLineEntriesList.get(i), "" + (i + 1));
                        if (chartColors != null && chartColors.size() > 0) {
                            lineDataSet.setColor(Color.parseColor(chartColors.get(0)));
                            lineDataSet.setCircleColor(Color.parseColor(chartColors.get(0)));
                        }else {
                            lineDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                            lineDataSet.setCircleColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        lineDataSet.setLineWidth(2.0f);
                        LineData d = new LineData(lineDataSet);
                        data.setData(d);
                    }

                    for (int i = 0; i < differentBarEntriesList.size(); i++) {

                        BarDataSet barDataSet = new BarDataSet(differentBarEntriesList.get(i), "" + (i + 1));
                        if (chartColors != null && chartColors.size() > 0 && 1<=chartColors.size()-1) {
                            barDataSet.setColor(Color.parseColor(chartColors.get(1)));
                        }else {
                            barDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        BarData d = new BarData(barDataSet);
                        data.setData(d);
                    }

                    XAxis xAxis = combinedChart.getXAxis();
                    YAxis leftAxis = combinedChart.getAxisLeft();
                    YAxis rightAxis = combinedChart.getAxisRight();
                    leftAxis.setAxisMinimum(0f);
                    rightAxis.setEnabled(false);
                    combinedChart.setDrawGridBackground(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    leftAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    combinedChart.setTouchEnabled(false);

                    combinedChart.setData(data);
                    combinedChart.invalidate();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabelsList));
                } else if (chartType.contentEquals(AppConstants.CHART_TYPE_BAR)) {
                    List<String> xAxisParams = actionObject.getxAxisValues();
                    List<String> yAxisParams = actionObject.getyAxisValues();
                    List<Axis> yAxisLabelsList1 = actionObject.getyAxisList();
                    List<IBarDataSet> barDataSets = new ArrayList<>();

                    for (int i = 0; i < yAxisParams.size(); i++) {
                        List<String> yAxisValues1 = outputData.get(yAxisParams.get(i).toLowerCase());
                        BarDataSet barDataSet = new BarDataSet(getBarEntries(yAxisValues1), yAxisLabelsList1.get(i).getLabel());
                        if (chartColors != null && chartColors.size() > 0 && i <= chartColors.size() - 1) {
                            barDataSet.setColor(Color.parseColor(chartColors.get(i)));
                        } else {
                            barDataSet.setColor(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        barDataSets.add(barDataSet);
                    }


                    BarData barData = new BarData(barDataSets);
                    barChart.setData(barData);
                    barChart.getDescription().setEnabled(false); // To remove description from the chart
                    barChart.setDragEnabled(true);
                    barChart.setTouchEnabled(true);
                    barChart.setDoubleTapToZoomEnabled(true);
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(outputData.get(xAxisParams.get(0).toLowerCase())));
                    if (outputData.get(xAxisParams.get(0).toLowerCase()).size() > 3) {
                        xAxis.setLabelRotationAngle(-60);
                    }
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMinimum(0f);
                    xAxis.setDrawGridLines(false);

                    barData.setBarWidth(0.15f);
                    if (barDataSets.size() > 1) {
                        barChart.groupBars(0f, 0.5f, 0.1f);
                    } else {
                        barData.setBarWidth(0.25f);
                    }
                    barChart.setDrawGridBackground(false);
                    barChart.animate();
                    barChart.invalidate();
                }if (chartType.contentEquals(AppConstants.CHART_TYPE_STACKED)) {
                    List<String> xAxisParams = actionObject.getxAxisValues();
                    List<String> yAxisParams = actionObject.getyAxisValues();
                    List<Axis> yAxisLabelsList1 = actionObject.getyAxisList();
                    List<String>xValues = outputData.get(xAxisParams.get(0));
                    List<float[]> yArrayValues= new ArrayList<>();
                    for (int i = 0; i <xValues.size() ; i++) {
                        float [] values = new float[yAxisParams.size()];
                        for (int j = 0; j <yAxisParams.size() ; j++) {
                            values[j] = Float.valueOf(outputData.get(yAxisParams.get(j)).get(i));
                        }
                        yArrayValues.add(values);
                    }
                    List<BarEntry> barEntries = new ArrayList<>();
                    for (int i = 0; i <xValues.size() ; i++) {
                        BarEntry barEntry = new BarEntry(i+1,yArrayValues.get(i));
                        barEntries.add(barEntry);
                    }
                    List<IBarDataSet> barDataSets = new ArrayList<>();
                    BarDataSet barDataSet = new BarDataSet(barEntries, "");
                    List<Integer> colors= new ArrayList<>();
                    String [] stackLabels = new String[yAxisParams.size()];
                    for (int i = 0; i <yAxisLabelsList1.size() ; i++) {
                        stackLabels[i]= yAxisLabelsList1.get(i).getLabel();
                    }
                    for (int i = 0; i <yAxisParams.size() ; i++) {
                        if (chartColors != null && chartColors.size() > 0 && i <= chartColors.size() - 1) {
                            colors.add(Color.parseColor(chartColors.get(i)));
                        }else{
                           colors.add(Color.parseColor(ImproveHelper.randomColor()));
                        }
                        //colors.add(Color.parseColor(chartColors.get(i)));
                    }
                    barDataSet.setColors(colors);
                    barDataSet.setStackLabels(stackLabels);
                    barDataSets.add(barDataSet);
                    BarData barData = new BarData(barDataSets);
                    barChart.setData(barData);
                    barChart.getDescription().setEnabled(false); // To remove description from the chart
                    barChart.setDragEnabled(true);
                    barChart.setTouchEnabled(true);
                    barChart.setDoubleTapToZoomEnabled(true);
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(outputData.get(xAxisParams.get(0).toLowerCase())));
                    if (outputData.get(xAxisParams.get(0).toLowerCase()).size() > 3) {
                        xAxis.setLabelRotationAngle(-60);
                    }
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMinimum(0f);
                    xAxis.setDrawGridLines(false);

                    barData.setBarWidth(0.15f);
                    if (barDataSets.size() > 1) {
                        barChart.groupBars(0f, 0.5f, 0.1f);
                    } else {
                        barData.setBarWidth(0.25f);
                    }
                    barChart.setDrawGridBackground(false);
                    barChart.animate();
                    barChart.invalidate();

                    /*for (int i = 0; i < yAxisParams.size(); i++) {
                        List<String> yAxisValues1 = outputData.get(yAxisParams.get(i).toLowerCase());
                    }*/

                }

            }
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }

    }

    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }


    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        }
    }

    public void setDisplayName(String strDisplayName){
        tv_displayName.setText(strDisplayName);
        controlObject.setDisplayName(strDisplayName);
    }
    public void setHint(String strHint){
        if(strHint != null && !strHint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(strHint);
            controlObject.setHint(strHint);
        }else{
            tv_hint.setVisibility(View.GONE);
        }
    }
    public void setHideDisplayName(Boolean hideDisplayName){
        if(hideDisplayName){
            tv_displayName.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
            controlObject.setHideDisplayName(hideDisplayName);
        }
    }

    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        setViewDisableOrEnableDefault(context,rView, enabled);
        controlObject.setDisable(!enabled);
    }
    /* Control Ui Settings*/
    public LinearLayout getLl_main_inside() {

        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui() {

        return ll_control_ui;
    }
    public CustomTextView getTv_displayName() {

        return tv_displayName;
    }
    public CardView getCv_all() {

        return cv_all;
    }
    public LineChart getLineChart() {

        return lineChart;
    }
    public BarChart getBarChart() {

        return barChart;
    }
    public PieChart getPieChart() {

        return pieChart;
    }

    public HorizontalBarChart getRowChart() {

        return rowChart;
    }
    public LineChart getAreaChart() {

        return areaChart;
    }
    public ScatterChart getScatterChart() {

        return scatterChart;
    }
    public CombinedChart getCombinedChart() {

        return combinedChart;
    }

    /* Control Ui Settings*/
    public ControlObject getControlObject() {
        return controlObject;
    }
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

    public void hideLegends(boolean hideLegends){
        controlObject.setHideLegends(hideLegends);
            if(controlObject != null && controlObject.getChartType() != null) {
            if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_LINE)) {
                lineChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    lineChart.getLegend().setEnabled(false);
                    lineChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_BAR)) {
                barChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    barChart.getLegend().setEnabled(false);
                    barChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_STACKED)) {
                barChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    barChart.getLegend().setEnabled(false);
                    barChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_PIE)) {
                pieChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    pieChart.getLegend().setEnabled(false);
                    pieChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_ROW)) {
                rowChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    rowChart.getLegend().setEnabled(false);
                    rowChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_AREA)) {
                areaChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    areaChart.getLegend().setEnabled(false);
                    areaChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_SCATTER)) {
                scatterChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    scatterChart.getLegend().setEnabled(false);
                    scatterChart.invalidate();
                }
            } else if (controlObject.getChartType().contentEquals(AppConstants.CHART_TYPE_COMBO)) {
                combinedChart.setVisibility(View.VISIBLE);
                if (controlObject.isHideLegends()) {
                    combinedChart.getLegend().setEnabled(false);
                    combinedChart.invalidate();
                }
            }
        }
    }
}
