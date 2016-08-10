package com.cyt.android.mysteward;

import java.util.ArrayList;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class PlanListFragment extends ListFragment {
	private ArrayList<Plan> mPlans;
	private static final String Tag = "PlanListFragment"; 
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//先获取PlanPool单例，再获取Plan列表
		//更新列表排序
		mPlans = PlanPool.get(getActivity()).getPlans();
		PlanPool.get(getActivity()).mSortPlan(mPlans);
		
		PlanAdapter adapter = new PlanAdapter(mPlans);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id){
		Plan plan = ((PlanAdapter)getListAdapter()).getItem(position);
		//从fragment中启动activity
		Intent intent = new Intent(getActivity(), PlanActivity.class);
		intent.putExtra(PlanFragment.EXTRA_PLAN_ID, plan.getUuid());
		startActivity(intent);
	}
	/*
	private class PlanAdapter extends ArrayAdapter<Plan>{
		
		public PlanAdapter(ArrayList<Plan> plans){
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
	*/
	private class PlanAdapter extends ArrayAdapter<Plan>{
		
		public PlanAdapter(ArrayList<Plan> plans){
			super(getActivity(), 0, plans);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			if(convertView == null){
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_plan_big, null);
			}
			
			Plan plan = getItem(position);
			
			TextView titleTextView = 
					(TextView)convertView.findViewById(R.id.id_plan_blistitem_titleTextView);
			titleTextView.setText(plan.getTitle());
			CheckBox solvedCheckBox = 
					(CheckBox)convertView.findViewById(R.id.id_plan_blistitem_solvedCheckBox);
			solvedCheckBox.setChecked(plan.isSolved());
			TextView noteText = 
					(TextView)convertView.findViewById(R.id.id_plan_blistitem_note);
			//noteText.setMovementMethod(ScrollingMovementMethod.getInstance());
			noteText.setText(plan.getNote());
			
			return convertView;
		}
	}
	@Override
	public void onResume(){
		super.onResume();
		((PlanAdapter)getListAdapter()).notifyDataSetChanged();
		//更新列表排序
		mPlans = PlanPool.get(getActivity()).getPlans();
		PlanPool.get(getActivity()).mSortPlan(mPlans);
	}
}
