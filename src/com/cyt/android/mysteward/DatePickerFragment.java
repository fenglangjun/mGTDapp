package com.cyt.android.mysteward;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {
	public static final String EXTRA_DATE = 
			"com.cyt.android.mysteward.start_date";
	public static final String EXTRA_HAS_A_DATE = 
			"com.cyt.android.mysteward.has_start_date";
	public static final String EXTRA_IS_START_DATE = 
			"com.cyt.android.mysteward.is_start_date";
	
	private Date mDate;
	private boolean hasDate;
	private boolean isStartDate;
	
	private void sendResult(int resultCode){
		if(getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		i.putExtra(EXTRA_HAS_A_DATE, hasDate);
		i.putExtra(EXTRA_IS_START_DATE, isStartDate);
		
		//PlanFragment中的
		getTargetFragment()
		.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		hasDate = (Boolean)getArguments().getSerializable(EXTRA_HAS_A_DATE);
		isStartDate = (Boolean)getArguments().getSerializable(EXTRA_IS_START_DATE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_ornot, null);
		
		//接受PlanFragment数据，初始化日期选择器
		DatePicker datePicker = (DatePicker)view.findViewById(R.id.id_dialog_datepicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				mDate = new GregorianCalendar(year, month, day).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
		});
		
		CheckBox mHasStartesDate = (CheckBox)view.findViewById(R.id.id_dialog_choose);
		mHasStartesDate.setChecked(!hasDate);
		mHasStartesDate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				hasDate = !(isChecked);
				getArguments().putSerializable(EXTRA_HAS_A_DATE, hasDate);
			}
		});
		
		return new AlertDialog.Builder(getActivity())
				.setView(view)
				.setTitle(R.string.dialog_time_label)
				.setPositiveButton(android.R.string.ok, 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				})
				.create();
	}
	
	public static DatePickerFragment newInstance(Date date, boolean flag, boolean isStartDate){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		args.putSerializable(EXTRA_HAS_A_DATE, flag);
		args.putSerializable(EXTRA_IS_START_DATE, isStartDate);
		
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
