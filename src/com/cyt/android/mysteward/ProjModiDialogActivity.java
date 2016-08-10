package com.cyt.android.mysteward;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ProjModiDialogActivity extends SingleFragmentActivity{
	
	@Override
	protected Fragment createFragment(){
		UUID planProId = (UUID)getIntent().getSerializableExtra(ProjModiDialogFragment.EXTRA_PLANPRO_MODI_ID);
		
		return ProjModiDialogFragment.newInstance(planProId);
	}
	

}
