package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class PlanProjectPool {
	private ArrayList<PlanProject> mPlanProjects;
	private static PlanProjectPool sPlanProjectPool;
	private Context mContext;
	public static PlanProject sPlanProject = new PlanProject();
	
	private PlanProjectPool(Context context){
		mContext = context;
		mPlanProjects = new ArrayList<PlanProject>();
		mPlanProjects.add(sPlanProject);//自备的一个分类
        String tmpStr = context.getString(R.string.explain1_fenlei_title);
		sPlanProject.setTitle(tmpStr);
		tmpStr = context.getString(R.string.explain1_fenlei_note);
		sPlanProject.setProNote(tmpStr);
		PlanProject planProject = new PlanProject();
		//自备的第二个分类
        tmpStr = context.getString(R.string.explain2_fenlei_title);
		planProject.setTitle(tmpStr);
        tmpStr = context.getString(R.string.explain2_fenlei_note);
		planProject.setProNote(tmpStr);
		mPlanProjects.add(planProject);
	}
	
	

	public static PlanProjectPool get(Context context){

		if (sPlanProjectPool == null) {
			sPlanProjectPool = new PlanProjectPool(context.getApplicationContext());
		}
		return sPlanProjectPool;
	}
	
	public ArrayList<PlanProject> getPlanProjects() {
		return mPlanProjects;
	}
	
	public PlanProject getPlanProject(UUID uuid) {
		for(PlanProject planProject : mPlanProjects){
			if(planProject.getUuid().equals(uuid))
				return planProject;
		}
		return null;
	}
	
	public static void removeRemainPlan(PlanProject mPlanProject, Plan mPlan){
		for(PlanProject planProject : sPlanProjectPool.getPlanProjects()){
			if(planProject.getUuid().equals(mPlanProject.getUuid())){ }
			else{
				//delete plan from plan_project
				planProject.removeProjectPlans(mPlan);
			}
		}
	}
	
	public static void removePlan(Plan mPlan){
		for(PlanProject planProject : sPlanProjectPool.getPlanProjects()){
			planProject.removeProjectPlans(mPlan);
		}
	}
	
	public void removePlanPro(PlanProject mPlanProject){
		mPlanProjects.remove(mPlanProject);
	}
	
}
