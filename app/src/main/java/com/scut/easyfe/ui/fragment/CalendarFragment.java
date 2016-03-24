package com.scut.easyfe.ui.fragment;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.scut.easyfe.ui.adapter.DayAdapter;

import java.util.Calendar;

import hirondelle.date4j.DateTime;

public class CalendarFragment extends CaldroidFragment {

	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		return new DayAdapter(getActivity(), month, year,
				getCaldroidData(), extraData);
	}

}
