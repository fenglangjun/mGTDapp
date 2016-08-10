package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlanProFragment extends Fragment {
	private TextView mTextView;
	private TextView mTitleText;
	private PlanProject mPlanProject;
	private ArrayList<Plan> mProjectPlans;
	private ListView mListView;
	
	public static final String EXTRA_PLANPRO_ID =
			"com.cyt.android.mysteward.extra_planpro_id";
	private static final int REQUEST_PLANPRO = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	
	 private Fragment getRootFragment() {
		  Fragment fragment = getParentFragment();
		  while (fragment.getParentFragment() != null) {
		   fragment = fragment.getParentFragment();
		  }
		  return fragment;

	}
	
	 private class ProPlanAdapter extends ArrayAdapter<Plan>{
			
			public ProPlanAdapter(ArrayList<Plan> plans){
				super(getActivity(), 0, plans);
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent){
				if(convertView == null){
					convertView = getActivity().getLayoutInflater()
							.inflate(R.layout.list_item_plan, null);
				}
				
				Plan plan = getItem(position);
				
				TextView titleTextView = 
						(TextView)convertView.findViewById(R.id.id_plan_list_item_titleTextView);
				titleTextView.setText(plan.getTitle());
				CheckBox solvedCheckBox = 
						(CheckBox)convertView.findViewById(R.id.id_plan_list_item_solvedCheckBox);
				solvedCheckBox.setChecked(plan.isSolved());
				
				return convertView;
			}
		}
	
 	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
 		 View view = inflater.inflate(R.layout.fragment_planproject, container, false); 
 		 
 		 UUID planProId = (UUID)getArguments().getSerializable(EXTRA_PLANPRO_ID);
 		 mPlanProject = PlanProjectPool.get(getActivity()).getPlanProject(planProId);
 		 mTextView = (TextView)view.findViewById(R.id.id_planproj_note);
 		 mTitleText = (TextView)view.findViewById(R.id.id_planproj_titletext);
 		 mTextView.setMovementMethod(ScrollingMovementMethod.getInstance()); 
 		 mProjectPlans = mPlanProject.getProjectPlans();
 		 /*
 		 StringBuffer sBuffer = new StringBuffer();
 		 for(int i=0; i<mProjectPlans.size(); i++){
 			 sBuffer.append(mProjectPlans.get(i).getTitle()+"\n");
 		 }
 		 */
 		 mTextView.setText(mPlanProject.getProNote());
 		 mTitleText.setText(mPlanProject.getTitle());
 		 mTitleText.setClickable(true);
 		 mTitleText.setFocusable(true);
 		 mTitleText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(),"TitleTextÑ¡ÖÐ", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), ProjModiDialogActivity.class);
                intent.putExtra(ProjModiDialogFragment.EXTRA_PLANPRO_MODI_ID, mPlanProject.getUuid());
                getRootFragment().startActivityForResult(intent, REQUEST_PLANPRO);
			}
		});
 		 
 		mListView = (ListView)view.findViewById(R.id.id_planpro_planlist);
 		ProPlanAdapter adapter = new ProPlanAdapter(mProjectPlans);
 		mListView.setAdapter(adapter);
 		
 		 return view;
    }
 	
 	
 	
	@Override
	public void onActivityResult(int requesetCode, int resultCode, Intent data){
		if(resultCode != Activity.RESULT_OK) return;
		if(requesetCode == REQUEST_PLANPRO){
			UUID mUuid  =(UUID)data
					.getSerializableExtra(ProjModiDialogFragment.EXTRA_PLANPRO_MODI_ID);
			mPlanProject = PlanProjectPool.get(getActivity()).getPlanProject(mUuid);
			mTitleText.setText(mPlanProject.getTitle());
		}
	}
 	
 	public static PlanProFragment newInstance(UUID planProId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_PLANPRO_ID, planProId);
		
		PlanProFragment fragment = new PlanProFragment();
		fragment.setArguments(args);
		
		return fragment;
	}

}
