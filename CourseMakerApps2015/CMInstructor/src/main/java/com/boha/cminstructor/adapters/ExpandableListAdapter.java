package com.boha.cminstructor.adapters;

import java.util.List;

import com.boha.cminstructor.R;
import com.boha.coursemaker.dto.CategoryDTO;
import com.boha.coursemaker.dto.CourseDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<CategoryDTO> categories;
	
	public ExpandableListAdapter(Context context, List<CategoryDTO> groups) {
		this.context = context;
		this.categories = groups;
	}
	
	public void addItem(CourseDTO item, CategoryDTO group) {
		if (!categories.contains(group)) {
			categories.add(group);
		}
		int index = categories.indexOf(group);
		List<CourseDTO> ch = categories.get(index).getCourseList();
		ch.add(item);
		categories.get(index).setCourseList(ch);
	}
	public Object getChild(int groupPosition, int childPosition) {
		List<CourseDTO> chList = categories.get(groupPosition).getCourseList();
		return chList.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		final CourseDTO course = (CourseDTO) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) 
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.course_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.COURSE_name);
		tv.setText(course.getCourseName());
		CheckBox chk = (CheckBox)view.findViewById(R.id.COURSE_chkBox);
		if (course.isSelected()) {
			chk.setChecked(true);
		} else {
			chk.setChecked(false);
		}
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				course.setSelected(isChecked);
			}
		});
		return view;
	}

	public int getChildrenCount(int groupPosition) {
		List<CourseDTO> chList = categories.get(groupPosition).getCourseList();
		return chList.size();

	}

	public Object getGroup(int groupPosition) {
		return categories.get(groupPosition);
	}

	public int getGroupCount() {
		return categories.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		CategoryDTO group = (CategoryDTO) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) 
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.category_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.CAT_name);
		tv.setText(group.getCategoryName());
		// TODO Auto-generated method stub
		return view;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}

