package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.TtsSpan.ElectronicBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewFragment extends Fragment {
	private TextView mReviewText, mHintText;
	private boolean isGood;
	private ArrayList<Plan> mPlans;
	private StringBuffer sBuffer;
	private int mCurrentIndex = 0;
	
	private HintMess[] mHintBank = new HintMess[]{
			new HintMess(R.string.hint_good_congra),
			new HintMess(R.string.hint_bad_care),
			new HintMess(R.string.hint_hello),
			new HintMess(R.string.hint_care),
			new HintMess(R.string.hint_care2),
			new HintMess(R.string.hint_hello2),
			new HintMess(R.string.hint_question),
			
	};


	private void updateHint(){
		int mHint = mHintBank[mCurrentIndex].getHint();
		mHintText.setText(mHint);
	}
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {
		View view = inflater.inflate(R.layout.fragment_review, container, false);
		
		sBuffer = new StringBuffer();
		mPlans = PlanPool.get(getActivity()).getPlans();
		mReviewText = (TextView)view.findViewById(R.id.id_review_textview);
		mHintText = (TextView)view.findViewById(R.id.id_hint_textview);
		
		sBuffer.append(this.getString(R.string.review_effici_label));
		sBuffer.append(getEfficiency(mPlans)+"%\n");
		if(getEfficiency(mPlans)>60.0) isGood = true;
		sBuffer.append(this.getString(R.string.review_solved_label));
		sBuffer.append(getSolvedNum(mPlans)+"\n");
		sBuffer.append(this.getString(R.string.review_alllist_label)+mPlans.size());
		mReviewText.setText(sBuffer);
		
		mHintText.setText(R.string.hint_hello);
		mHintText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				while(true){
					int tmp = (int)(Math.random()*mHintBank.length);
					if(isGood && tmp==1) continue;
					if (!isGood && tmp==0) continue;
					if(tmp!=mCurrentIndex){ mCurrentIndex = tmp; break;}
				}
				//mCurrentIndex = (mCurrentIndex + 1) % mHintBank.length;
				updateHint();
			}
		});
		
		
		
		return view;
    }
	
	private float getEfficiency(ArrayList<Plan> mPlans) {
		Double a=0.0, b=0.0;
		Plan tmpPlan;
 		Date mDate = new Date();
		for(int i=0; i<mPlans.size(); i++){
			tmpPlan = mPlans.get(i);
			if(tmpPlan.isStarted() && tmpPlan.isEnded()){
				int isntBegin = (tmpPlan.getStartDate()).compareTo(mDate);
				int isntEnd = (tmpPlan.getEndDate()).compareTo(mDate);
				if (isntBegin>0 && isntEnd>0) {//未开始未结束
					if(tmpPlan.isSolved()){ a+=4; b+=3;}
					else;
				} else if( isntEnd>0){//已开始未结束
					if(tmpPlan.isSolved()){ a+=4; b+=3;}
					else { a+=1; b+=3;}
				}else if (isntBegin<0) {//已开始已结束
					if(tmpPlan.isSolved()){ a+=34; b+=3;}
					else { b+=3;}
				}
			}else if (tmpPlan.isStarted()) {
				int isntBegin = (tmpPlan.getStartDate()).compareTo(mDate);
				if (isntBegin>0) {//未开始
					if(tmpPlan.isSolved()){ a+=4; b+=3;}
					else;
				} else {//已开始
					if(tmpPlan.isSolved()){ a+=3; b+=3;}
					else { a+=1; b+=3;}
				}
			}else if (tmpPlan.isEnded()) {
				int isntEnd = (tmpPlan.getEndDate()).compareTo(mDate);
				if (isntEnd>0) {//未结束
					if(tmpPlan.isSolved()){ a+=4; b+=3;}
					else { a+=1; b+=3;}
				} else {//已结束
					if(tmpPlan.isSolved()){ a+=3; b+=3;}
					else { b+=3;}
				}
			}
			else;
		}
		if(a>b) a=b;
		a = (a/b)*100;
		float result = (float)(Math.round(a*100))/100;
		return result;
	}
	
	private int getSolvedNum(ArrayList<Plan> mPlans) {
		int result=0;
		for(int i=0; i<mPlans.size(); i++){
			if(mPlans.get(i).isSolved())
				result++;
		}
		return result;
	}
	
}
