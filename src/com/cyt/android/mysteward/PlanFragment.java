package com.cyt.android.mysteward;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextDirectionHeuristic;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlanFragment extends Fragment {
	private Plan mPlan;
	private EditText mTitleField, mNoteField;
	private Button mStartDateButton;
	private Button mEndDateButton;
	private Button mDelBtn, mSureBtn;
	private CheckBox mSolvedCheckBox;
	private boolean mHasStarted, mHasEnded;
	private Spinner mSpinner;
	private ArrayList<PlanProject> mPlanProjects;
	///
	///
	
	public SimpleDateFormat mDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String EXTRA_PLAN_ID = 
			"com.cyt.android.mysteward.plan_idddd";
	private static final String DIALOG_START_DATE = "start_date";
	private static final int REQUEST_DATE = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		UUID planId = (UUID)getArguments().getSerializable(EXTRA_PLAN_ID);
		mPlan = PlanPool.get(getActivity()).getPlan(planId);
		mPlanProjects = PlanProjectPool.get(getActivity()).getPlanProjects();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_plan, parent, false);
		
		mTitleField = (EditText)v.findViewById(R.id.id_plan_title);
		mTitleField.setText(mPlan.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mPlan.setTitle(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		
		mNoteField = (EditText)v.findViewById(R.id.id_plan_editnote);
		mNoteField.setText(mPlan.getNote());
		mNoteField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mPlan.setNote(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		
		mHasStarted = (boolean)mPlan.isStarted();
		mStartDateButton = (Button)v.findViewById(R.id.id_plan_start_time_button);
		if(mHasStarted){
			mStartDateButton.setText(mDateFormat.format(mPlan.getStartDate()));
		}else{
			mStartDateButton.setText(R.string.no_time_label);
		}
		mStartDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mPlan.getStartDate(), mHasStarted, true);
				dialog.setTargetFragment(PlanFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_START_DATE);
				
			}
		});
		
		mHasEnded = (boolean)mPlan.isEnded();
		mEndDateButton = (Button)v.findViewById(R.id.id_plan_end_time_button);
		if(mHasEnded){
			mEndDateButton.setText(mDateFormat.format(mPlan.getEndDate()));
		}else{
			mEndDateButton.setText(R.string.no_time_label);
		}
		mEndDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mPlan.getEndDate(), mHasEnded, false);
				dialog.setTargetFragment(PlanFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_START_DATE);
				
			}
		});
		
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.id_plan_solved);
		mSolvedCheckBox.setChecked(mPlan.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mPlan.setSolved(isChecked);
			}
		});
		
		mSpinner = (Spinner)v.findViewById(R.id.id_plan_project_select);
		BaseAdapter baseAdapter = new BaseAdapter() {
			private ArrayList<PlanProject> mPlanProjects = 
					PlanProjectPool.get(getActivity()).getPlanProjects();	
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout linearLayout = new LinearLayout(getActivity());
				linearLayout.setOrientation(LinearLayout.HORIZONTAL);
				TextView tView = new TextView(getActivity());
				tView.setText(mPlanProjects.get(position).getTitle().toString());
				tView.setTextSize(24);
				linearLayout.addView(tView);
				return linearLayout;
			}
			@Override
			public long getItemId(int position) {
				return position;
			}
			@Override
			public Object getItem(int position) {
				return mPlanProjects.get(position);
			}
			@Override
			public int getCount() {
				return mPlanProjects.size();
			}
		};
		mSpinner.setAdapter(baseAdapter);
		int index = mPlanProjects.indexOf(mPlan.getPlanProject());
		mSpinner.setSelection(index,true);//2 represents 3
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				PlanProject mPlanProject = (PlanProject)mSpinner.getSelectedItem();
				mPlan.setPlanProject(mPlanProject);
				PlanProjectPool.removeRemainPlan(mPlanProject, mPlan);
				mPlanProject.addProjectPlans(mPlan);
				//Toast.makeText(getActivity(),mPlanProject.getTitle(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
			
		});
		
		mDelBtn = (Button)v.findViewById(R.id.id_plan_delete);
		mDelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PlanPool.get(getActivity()).deletePlan(mPlan);
				PlanProjectPool.removePlan(mPlan);
				Intent intent = new Intent(getActivity(), PlanListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		mSureBtn = (Button)v.findViewById(R.id.id_plan_sure);
		mSureBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PlanListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		return v;
	}
	
	public static PlanFragment newInstance(UUID planId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_PLAN_ID, planId);
		
		PlanFragment fragment = new PlanFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onActivityResult(int requesetCode, int resultCode, Intent data){
		if(resultCode != Activity.RESULT_OK) return;
		if(requesetCode == REQUEST_DATE){
			Date date =(Date)data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			boolean isStartDate = (Boolean)data
					.getSerializableExtra(DatePickerFragment.EXTRA_IS_START_DATE);
			if(isStartDate){
				mHasStarted = (Boolean)data
						.getSerializableExtra(DatePickerFragment.EXTRA_HAS_A_DATE);
				mPlan.setStartDate(date);
				mPlan.setStarted(mHasStarted);
				if(mHasStarted){
					mStartDateButton.setText(mDateFormat.format(mPlan.getStartDate()));
				}else{
					mStartDateButton.setText(R.string.no_time_label);
				}
			}else{
				mHasEnded = (Boolean)data
						.getSerializableExtra(DatePickerFragment.EXTRA_HAS_A_DATE);
				mPlan.setEndDate(date);
				mPlan.setEnded(mHasEnded);
				if(mHasEnded){
					if(mHasStarted){//如果开始时间和结束时间都存在，判断是否合理
						Date starDate = mPlan.getStartDate(),endDate = mPlan.getEndDate();
						if(starDate.compareTo(endDate)>0){
							Toast.makeText(getActivity(),"结束时间不可以这么早!", Toast.LENGTH_SHORT).show();
							mPlan.setEndDate(starDate);
						}
					}
					mEndDateButton.setText(mDateFormat.format(mPlan.getEndDate()));
				}else{
					mEndDateButton.setText(R.string.no_time_label);
				}
			}
			
			
		}
	}
}
