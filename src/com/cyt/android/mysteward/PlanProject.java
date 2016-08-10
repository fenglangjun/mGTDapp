package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.UUID;

public class PlanProject {
	private UUID mUuid;
	private String mTitle, mProNote;
	private ArrayList<Plan> mProjectPlans;
	
	public ArrayList<Plan> getProjectPlans() {
		return mProjectPlans;
	}

	public String getProNote() {
		return mProNote;
	}

	public void setProNote(String proNote) {
		mProNote = proNote;
	}

	public void addProjectPlans(Plan mPlan) {
		mProjectPlans.add(mPlan);
	}
	
	public void removeProjectPlans(Plan mPlan){
		mProjectPlans.remove(mPlan);
	}
	

	public PlanProject(){
		mUuid = UUID.randomUUID();
		mProjectPlans = new ArrayList<Plan>();
	}
	
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		mTitle = title;
	}
	public UUID getUuid() {
		return mUuid;
	}
	
}
