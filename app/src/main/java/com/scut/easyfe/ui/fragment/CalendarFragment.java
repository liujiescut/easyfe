package com.scut.easyfe.ui.fragment;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.scut.easyfe.ui.adapter.DayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hirondelle.date4j.DateTime;

public class CalendarFragment extends CaldroidFragment {
	private List<DateTime> mWorkDays = new ArrayList<>();
	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		DayAdapter adapter =  new DayAdapter(getActivity(), month, year,
				getCaldroidData(), extraData);
		adapter.setWorkDays(mWorkDays);
		return adapter;
	}

	public List<DateTime> getWorkDays() {
		return mWorkDays;
	}

	public void setWorkDays(List<DateTime> mWorkDays) {
		this.mWorkDays = mWorkDays;
	}
}
