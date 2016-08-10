package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.UUID;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextDirectionHeuristic;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class PlanProjPagerFragment extends Fragment {
	private ViewPager mViewPager;
	private FragmentManager fm;
	private ArrayList<PlanProject> mPlanProjects;
	
	class mAdapter1 extends FragmentStatePagerAdapter{

		public mAdapter1(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			PlanProject mPlanProject = mPlanProjects.get(arg0);
			return PlanProFragment.newInstance(mPlanProject.getUuid());
		}

		@Override
		public int getCount() {
			return mPlanProjects.size();
		}
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mPlanProjects = PlanProjectPool.get(getActivity()).getPlanProjects();
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, 
			Bundle savedInstanceState){
		UUID planProId = (UUID)getActivity().getIntent()
				.getSerializableExtra(PlanProFragment.EXTRA_PLANPRO_ID);
		fm = getChildFragmentManager();
		mViewPager = new ViewPager(getActivity());
		mViewPager.setId(R.id.viewPager);
		mAdapter1 mAdapter = new mAdapter1(fm);
		mViewPager.setAdapter(mAdapter);
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		for(int i = 0; i< mPlanProjects.size(); i++){
			if(mPlanProjects.get(i).getUuid().equals(planProId)){
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
		return mViewPager;
	}
	
	@Override
	public void onActivityResult(int requesetCode, int resultCode, Intent data){
		super.onActivityResult(requesetCode, resultCode, data);
		if(resultCode != Activity.RESULT_OK) return;
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		 //Toast.makeText(getActivity(),"RESUME_page", Toast.LENGTH_SHORT).show();
		 mAdapter1 mAdapter =(mAdapter1)(((ViewPager)getView()).getAdapter());
		mAdapter.notifyDataSetChanged();
	}

}
