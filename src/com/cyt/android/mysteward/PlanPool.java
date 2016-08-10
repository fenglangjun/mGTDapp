package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import android.app.Application;
import android.content.Context;

public class PlanPool {
	private ArrayList<Plan> mPlans;
	private static PlanPool sPlanPool;
	private Context mContext;
	
	/*
	private ArrayList<PlanProject> mPlanProjects = 
			PlanProjectPool.get(getActivity()).getPlanProjects();	
	*/
	Comparator<Plan> comparator = new Comparator<Plan>(){
		@Override
	    public int compare(Plan o1, Plan o2){
			if(!(o1.isSolved()) && (o2.isSolved()))
				return -1;
			else if((o1.isSolved()) && !(o2.isSolved())) {
				return 1;
			}
			else ;
			if( (o1.isEnded()) && (o2.isEnded()) ){
				return (o1.getEndDate()).compareTo(o2.getEndDate());
			}else if (o1.isEnded()) {
				return -1;
			}else if (o2.isEnded()) {
				return 1;
			}else if ( (o1.isStarted()) && (o2.isStarted()) ) {
				return (o1.getStartDate()).compareTo(o2.getStartDate());
			}else {
				if(o1.isStarted()) {
					return -1;
				}else if (o2.isStarted()) {
					return 1;
				}else {
					return (o1.getTitle()).compareTo(o2.getTitle());
				}
				
			}
		}
	};
	
	private PlanPool(Context context) {//单例需要私有构造方法及get方法
		mContext = context;
		mPlans = new ArrayList<Plan>();
		//通过context锁定字符串资源，只能在这个定义了context的括号里
		String[] mTitles = new String[]{
				mContext.getString(R.string.explain1_title),
				mContext.getString(R.string.explain2_title),
				mContext.getString(R.string.explain3_title),
				mContext.getString(R.string.explain4_title),
				mContext.getString(R.string.explain5_title),
				mContext.getString(R.string.explain6_title),
		};
		String[] mNotes = new String[]{
				mContext.getString(R.string.explain1_note),
				mContext.getString(R.string.explain2_note),
				mContext.getString(R.string.explain3_note),
				mContext.getString(R.string.explain4_note),
				mContext.getString(R.string.explain5_note),
				mContext.getString(R.string.explain6_note),
		};
		//自备plan
		for(int i=0; i<6; i++){
			Plan plan = new Plan();
			plan.setTitle(mTitles[i]);
			plan.setNote(mNotes[i]);
			if(i<4) plan.setSolved(false);
			else plan.setSolved(true);
			PlanProject mPlanProject = PlanProjectPool.get(mContext).getPlanProjects().get(1);
			if(i>=4){
				plan.setPlanProject(PlanProjectPool.sPlanProject);
				PlanProjectPool.sPlanProject.addProjectPlans(plan);
			}else {
				plan.setPlanProject(mPlanProject);
				mPlanProject.addProjectPlans(plan);
			}
			mPlans.add(plan);
		}
		Collections.sort(mPlans, comparator);
	}
	
	public static PlanPool get(Context context) {
		if (sPlanPool == null) {
			sPlanPool = new PlanPool(context.getApplicationContext());
		}
		return sPlanPool;
	}
	
	public ArrayList<Plan> getPlans() {
		return mPlans;
	}
	
	public Plan getPlan(UUID uuid) {
		for(Plan plan : mPlans){
			if(plan.getUuid().equals(uuid))
				return plan;
		}
		return null;
	}
	
	public void deletePlan(Plan mPlan){
		mPlans.remove(mPlan);
	}
	
	public void mSortPlan(ArrayList<Plan> mPlans){
		Collections.sort(mPlans, comparator);
	}
	
	public static void clearProj(PlanProject mPlanProject){
		for(Plan  mPlan : sPlanPool.getPlans()){
			if(mPlan.getPlanProject().equals(mPlanProject)){
				mPlan.setPlanProject(PlanProjectPool.sPlanProject);
				PlanProjectPool.sPlanProject.addProjectPlans(mPlan);
			}
		}
	}
	
	public void addPlan(Plan mPlan){
		mPlans.add(mPlan);
	}
}
