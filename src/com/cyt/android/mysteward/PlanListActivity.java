package com.cyt.android.mysteward;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextDirectionHeuristic;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class PlanListActivity extends BaseFragmentActiviy{
	
	private RadioGroup mRadioGroup;
	private RadioButton b1;
	private RadioButton b2;
	private RadioButton b3;
	private RadioButton b4;
	private ShowFragment mRoleFrag;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	 @Override  
	    protected void onCreate(Bundle savedInstanceState)  
	    {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.menu_activity); 
	        setDefaultFragment1();
	        setDefaultFragment2();
	        setDefaultFragment3();
	        
			b1=(RadioButton)findViewById(R.id.id_role_tab_button);
			b2=(RadioButton)findViewById(R.id.id_abilities_tab_button);
			b3=(RadioButton)findViewById(R.id.id_menu_tab_button);
			b4=(RadioButton)findViewById(R.id.id_settings_tab_button);
			mRadioGroup = (RadioGroup)findViewById(R.id.id_radioGroup_tab);
			mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {

			        FragmentManager fm1 = getSupportFragmentManager();
					Fragment fragment_content = fm1.findFragmentById(R.id.id_fragment_content);
					Fragment fragment_modify = fm1.findFragmentById(R.id.id_fragment_modify);
					Fragment fragment_fullcontent = fm1.findFragmentById(R.id.id_fragment_fullcontent);
					FragmentTransaction transaction = fm1.beginTransaction();
					if(checkedId==b1.getId()){
	                   // Toast.makeText(PlanListActivity.this,"b1选中", Toast.LENGTH_SHORT).show();
	                    fragment_fullcontent = new ShowFragment();
	        			transaction
        					.hide(fragment_content)
        					.hide(fragment_modify)
        					.replace(R.id.id_fragment_fullcontent,  fragment_fullcontent);
	                }
					if(checkedId==b2.getId()){
	                    //Toast.makeText(PlanListActivity.this,"b2选中", Toast.LENGTH_SHORT).show();
	                    fragment_content = new PlanProjPagerFragment();
	                    fragment_modify = new ProjModiFragment();
	        			transaction
	        				.hide(fragment_fullcontent)
	        				.replace(R.id.id_fragment_modify,  fragment_modify)
	        				.replace(R.id.id_fragment_content,  fragment_content);
	                }
					if(checkedId==b3.getId()){
	                    //Toast.makeText(PlanListActivity.this,"b3选中", Toast.LENGTH_SHORT).show();
	                    fragment_content = new PlanListFragment();
	                    fragment_modify = new AddFragment();
	        			transaction
	        				.hide(fragment_fullcontent)
	        				.replace(R.id.id_fragment_modify,  fragment_modify)
	        				.replace(R.id.id_fragment_content,  fragment_content);
	                }
					if(checkedId==b4.getId()){
	                    //Toast.makeText(PlanListActivity.this,"b4选中", Toast.LENGTH_SHORT).show();
	                    fragment_fullcontent = new ReviewFragment();
	        			transaction
	    					.hide(fragment_content)
	    					.hide(fragment_modify)
	    					.replace(R.id.id_fragment_fullcontent,  fragment_fullcontent);
	                }
					transaction.commit();
				}
			});
	}
	
	private void setDefaultFragment1(){
		 
        FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.id_fragment_content);
		FragmentTransaction transaction = fm.beginTransaction();
		if(fragment == null){
			fragment = new PlanListFragment();
			transaction
				.add(R.id.id_fragment_content,  fragment)
				.commit();
		}  
	}
	
	private void setDefaultFragment2(){
		 
        FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.id_fragment_modify);
		FragmentTransaction transaction = fm.beginTransaction();
		if(fragment == null){
			fragment = new AddFragment();
			transaction
				.add(R.id.id_fragment_modify,  fragment)
				.commit();
		}  
	}
	
	private void setDefaultFragment3(){
		 
        FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.id_fragment_fullcontent);
		FragmentTransaction transaction = fm.beginTransaction();
		if(fragment == null){
			fragment = new PlanListFragment();
			transaction
				.add(R.id.id_fragment_fullcontent,  fragment)
				.hide(fragment)
				.commit();
		}  
	}
	
}