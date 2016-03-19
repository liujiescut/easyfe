package com.scut.easyfe.utils;

/**
 * 计算ListView的总高度
 * 当ListView嵌套在ScrollView中时需要设置ListView的高度为他的总高度，否则无法完全地显示ListView
 * @author liujie
 */

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


public class ListViewUtil {
	public static int getListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			// pre-condition

			return 0;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItem = listAdapter.getView(i, null, listView);

			// listItem.measure（0， 0）;

			listItem.measure(

			MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),

			MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

			totalHeight += listItem.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight

		+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);

		return params.height;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		int totalHeight = getListViewHeightBasedOnChildren(listView);

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight;

		listView.setLayoutParams(params);

	}

	public static void setListViewHeightBasedOnReferencedHeight(
			ListView listView, int refrencedHeight) {
		int totalHeight = getListViewHeightBasedOnChildren(listView);

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = refrencedHeight;
		listView.setLayoutParams(params);
	}
}
