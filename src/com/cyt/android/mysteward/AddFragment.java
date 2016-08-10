package com.cyt.android.mysteward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddFragment extends Fragment {
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
                //Toast.makeText(getActivity(),  
                  //      "i am an ImageButton in PlanAddFragment ! ",  
                    //    Toast.LENGTH_SHORT).show();  
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
}
