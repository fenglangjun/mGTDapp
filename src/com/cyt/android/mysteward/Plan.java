package com.cyt.android.mysteward;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Plan {
	private UUID mUuid ;
	private String mTitle;
	private String mNote;
	private Date mStartDate;
	private boolean mSolved;
	private boolean mStarted;
	private boolean mEnded;
	private Date mEndDate;
	private PlanProject mPlanProject;

	public String getNote() {
		return mNote;
	}

	public void setNote(String note) {
		mNote = note;
	}

	public PlanProject getPlanProject() {
		return mPlanProject;
	}

	public void setPlanProject(PlanProject planProject) {
		mPlanProject = planProject;
	}

	public Date getEndDate() {
		return mEndDate;
	}

	public void setEndDate(Date endDate) {
		mEndDate = endDate;
	}

	public boolean isEnded() {
		return mEnded;
	}

	public void setEnded(boolean ended) {
		mEnded = ended;
	}

	public boolean isStarted() {
		return mStarted;
	}

	public void setStarted(boolean started) {
		mStarted = started;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	public Date getStartDate() {
		return mStartDate;
	}

	public void setStartDate(Date startDate) {
		mStartDate = startDate;
	}
	

	public Plan() {
		mUuid = UUID.randomUUID();//生成唯一标识符
		mStartDate = new Date();
		mStarted = true;
		mEndDate = new Date();
		mEnded = false;
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
	
	@Override
	public String toString(){
		return mTitle;
	}
	
	@Override  
    public boolean equals(Object obj) {  
          
        boolean flag = obj instanceof Plan;  
        if(flag == false){  
            return false;  
        }  
        Plan emp = (Plan)obj;  
        if(this.getUuid()==emp.getUuid() && this.getTitle().equals(emp.getTitle())){  
            return true;  
        }else {  
            return false;  
        }  
    } 
	
	
}
