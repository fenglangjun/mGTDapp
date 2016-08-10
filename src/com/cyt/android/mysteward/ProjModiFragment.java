package com.cyt.android.mysteward;

import java.util.Date;
import java.util.UUID;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProjModiFragment extends Fragment {
	private ImageButton mAddBtn;  

	  
	
	
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
        View view = inflater.inflate(R.layout.fragment_add, container, false);  
        mAddBtn = (ImageButton) view.findViewById(R.id.id_add_button);  
        mAddBtn.setOnClickListener(new View.OnClickListener()  
        {  
            @Override  
            public void onClick(View v)  
            {  
                PlanProject mPlanProject = new PlanProject();
                String str = getActivity().getString(R.string.input_notsure);
                mPlanProject.setTitle(str);
                PlanProjectPool.get(getActivity()).getPlanProjects().add(mPlanProject);

                Intent intent = new Intent(getActivity(), ProjModiDialogActivity.class);
                intent.putExtra(ProjModiDialogFragment.EXTRA_PLANPRO_MODI_ID, mPlanProject.getUuid());
                startActivity(intent);
            }  
        });  
        return view;  
    }  
    
/*
	@Override
	public void onActivityResult(int requesetCode, int resultCode, Intent data){
		if(resultCode != Activity.RESULT_OK) return;
		if(requesetCode == REQUEST_PLANPRO){
			UUID mUuid  =(UUID)data
					.getSerializableExtra(PlanProjModifyFragment.EXTRA_PLANPRO_ID);
			Toast.makeText(getActivity(),"add over?", Toast.LENGTH_SHORT).show();
		}
	}
	*/
	@Override
	public void onResume(){
		super.onResume();
		 //Toast.makeText(getActivity(),"RESUME_modi", Toast.LENGTH_SHORT).show();
		 
	}
	
}
