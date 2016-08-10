package com.cyt.android.mysteward;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class PlanActivity extends SingleFragmentActivity{
	
	@Override
	protected Fragment createFragment(){
		UUID planId = (UUID)getIntent().getSerializableExtra(PlanFragment.EXTRA_PLAN_ID);
		
		return PlanFragment.newInstance(planId);
	}
	
	/*
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);
		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		Fragment fragment = new PlanFragment();
		transaction.replace(R.id.id_fragmentContainer, fragment);
		transaction.commit();
		
	}
	*/

}
