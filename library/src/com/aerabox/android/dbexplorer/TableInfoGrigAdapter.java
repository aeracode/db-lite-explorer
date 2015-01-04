package com.aerabox.android.dbexplorer;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.aerabox.android.dbexplorer.model.ColumnInfo;
import com.aerabox.android.dbexplorer.model.ColumnType;
import com.aerabox.android.dbexplorer.model.TableInfo;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

public class TableInfoGrigAdapter extends BaseTableAdapter {

	private TableInfo mTableInfo;
	private Context mContext;
	private List<List<String>> mData;
	private int mColumnsNum;
	private int mRowsNum;
	private int mRowHeight;
	private int mRowIdColumnWidth;
	private int[] mColumnsWidths;
	private int mColumnHeaderHeight;
	private float mDensity;

	public TableInfoGrigAdapter(Context activity, TableInfo tableInfo) {
		mContext = activity;
		mDensity = mContext.getResources().getDisplayMetrics().density;

		mTableInfo = tableInfo;

		mData = mTableInfo.fetchData();

		mColumnsNum = mTableInfo.columns.size();
		mRowsNum = mData.size();

		mRowIdColumnWidth = dp2px(30);
		mRowHeight = dp2px(30);
		mColumnHeaderHeight = dp2px(40);

		mColumnsWidths = calculateWidths();
	}
	
	// NOTE: Introduce dependence with `Utils` package or @inline
	private int dp2px(float dp) {
		return (int) (dp * mDensity);
	}

	private int[] calculateWidths() {
		final int padding = dp2px(25);
		final int textual = dp2px(70);
		final int colNum = getColumnCount();
		final int[] arr = new int[colNum];
		final TextPaint paint = ((TextView) getView(-1, 0, null, null)).getPaint();
		for (int col = 0; col < colNum; col++) {
			final ColumnInfo cinfo = mTableInfo.columns.get(col);
			final ColumnType type = cinfo.type;
			final float w = Math.max(paint.measureText(cinfo.name), paint.measureText("[" + type.name() + "]"));
			arr[col] = (int) (w + padding + (type == ColumnType.TEXT ? textual : 0));
		}
		return arr;
	}

	@Override
	public int getRowCount() {
		return mRowsNum;
	}

	@Override
	public int getColumnCount() {
		return mColumnsNum;
	}

	@Override
	public View getView(int row, int column, View convertView, ViewGroup parent) {
		TextView v;
		if (convertView == null) {
			v = new TextView(mContext);
			v.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
			v.setGravity(Gravity.CENTER);
		} else {
			v = (TextView) convertView;
		}
		CharSequence item = getItem(row, column);
		if (row == -1 && column != -1) {
			item = item + "\n[" + mTableInfo.columns.get(column).type.name() + "]";
		}
		v.setText(item);
		v.setBackgroundColor(column == -1 || row == -1 ? Color.GRAY : Color.WHITE);
		v.setTextColor(column == -1 || row == -1 ? Color.WHITE : Color.BLACK);
		return v;
	}

	private CharSequence getItem(int row, int column) {
		return row == -1
				? (column == -1)
						? ""
						: mTableInfo.columns.get(column).name
				: (column == -1)
						? String.valueOf(row + 1)
						: mData.get(row).get(column);
	}

	@Override
	public int getWidth(int column) {
		return column == -1 ? mRowIdColumnWidth : mColumnsWidths[column];
	}

	@Override
	public int getHeight(int row) {
		return row == -1 ? mColumnHeaderHeight : mRowHeight;
	}

	@Override
	public int getItemViewType(int row, int column) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

}
