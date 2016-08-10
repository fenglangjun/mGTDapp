package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowFragment extends Fragment {
	private TextView mTextView, mTomoTextView;
	private ImageButton mImageButton;
	private ArrayList<Plan> mPlans;
	private StringBuffer sBuffer;
	
	 	@Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
	 		View view = inflater.inflate(R.layout.fragment_show, container, false); 
	 		mTextView = (TextView)view.findViewById(R.id.id_show_today_textview);
	 		mTomoTextView = (TextView)view.findViewById(R.id.id_show_tomo_textview);
	 		mImageButton = (ImageButton)view.findViewById(R.id.id_add_button);
	 		sBuffer = new StringBuffer();
	 		//mTextView.setText("TODAY!");

	 		mPlans = PlanPool.get(getActivity()).getPlans();
	 		PlanPool.get(getActivity()).mSortPlan(mPlans);

	 		for(int i=0; i<mPlans.size(); i++){
	 			if(isToDo(mPlans.get(i), true)){
	 				sBuffer.append(mPlans.get(i).getTitle()+"\n");
	 			}
	 		}
	 		mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
	 		mTextView.setText(sBuffer);

	 		sBuffer.setLength(0);
	 		for(int i=0; i<mPlans.size(); i++){
	 			if(isToDo(mPlans.get(i), false)){
	 				sBuffer.append(mPlans.get(i).getTitle()+"\n");
	 			}
	 		}
	 		mTomoTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
	 		mTomoTextView.setText(sBuffer);
	 		
	 		mImageButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(getActivity(),"бЁжа", Toast.LENGTH_SHORT).show();  
	                Plan mPlan = new Plan();
	                PlanPool.get(getActivity()).addPlan(mPlan);
	                String str = getActivity().getString(R.string.input_notsure);
	                mPlan.setTitle(str);
	                Intent intent = new Intent(getActivity(), PlanActivity.class);
	                intent.putExtra(PlanFragment.EXTRA_PLAN_ID, mPlan.getUuid());
	                startActivityForResult(intent, 0);
				}
			});
	 		 
	 		return view;
	    }
	 	
	 	public boolean isToDo(Plan mPlan, Boolean judgeToday) {
	 		Date mDate = new Date();
	 		Calendar c = Calendar.getInstance();
	        c.setTime(mDate);   
	        c.add(Calendar.DATE, 1); 
	        Date tomdate = c.getTime();
	 		if(judgeToday){
	 			if(!mPlan.isSolved())
	 				if(mPlan.isStarted())
	 					if((mPlan.getStartDate()).compareTo(mDate) < 0) return true;
	 		}else{
	 			if(!mPlan.isSolved())
	 				if(mPlan.isStarted())
	 					if((mPlan.getStartDate()).compareTo(tomdate) < 0) return true;
	 		}
			return false;
		}
}
