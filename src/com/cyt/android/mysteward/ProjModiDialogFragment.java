package com.cyt.android.mysteward;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProjModiDialogFragment extends Fragment {
	public static final String EXTRA_PLANPRO_MODI_ID = 
			"com.cyt.android.mysteward.planproj_modi_id";

	private UUID mUuid;
	private Button mDelBtn;
	private Button mOkBtn;
	private EditText mTitleField, mEditText;
	private PlanProject mPlanProject;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mUuid = (UUID)getArguments().getSerializable(EXTRA_PLANPRO_MODI_ID);
		mPlanProject = PlanProjectPool.get(getActivity()).getPlanProject(mUuid);
	}
	
	private void sendResult(int resultCode){
		Intent i = new Intent();
		i.putExtra(EXTRA_PLANPRO_MODI_ID, mUuid);
		getActivity().setResult(Activity.RESULT_OK, i);
		getActivity().finish();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.dialog_planproj_modify, parent, false);
		
		mTitleField = (EditText)v.findViewById(R.id.id_planpro_title);
		mTitleField.setText(mPlanProject.getTitle().toString());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mPlanProject.setTitle(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		

		mEditText = (EditText)v.findViewById(R.id.editText1);
		mEditText.setText(mPlanProject.getProNote());
		mEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mPlanProject.setProNote(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});

		mDelBtn = (Button)v.findViewById(R.id.id_planpro_delete);
 		mDelBtn.setOnClickListener(new View.OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				//Toast.makeText(getActivity(),"click!", Toast.LENGTH_SHORT).show();

 				if(mPlanProject.equals(PlanProjectPool.sPlanProject)){
 					Toast.makeText(getActivity(),"你不能删除默认项目!", Toast.LENGTH_SHORT).show();
 				}
 				else{
 				PlanProjectPool.get(getActivity()).removePlanPro(mPlanProject);
 				PlanPool.clearProj(mPlanProject);
 				}
 				getActivity().finish();
 				
 			}
 		});
 		
 		mOkBtn = (Button)v.findViewById(R.id.id_planpro_ok);
 		mOkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendResult(Activity.RESULT_OK);
			}
		});
		
		return v;
	}
	
	public static ProjModiDialogFragment newInstance(UUID planId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_PLANPRO_MODI_ID, planId);
		
		ProjModiDialogFragment fragment = new ProjModiDialogFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
}
