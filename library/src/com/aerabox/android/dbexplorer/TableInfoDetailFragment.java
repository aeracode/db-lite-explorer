package com.aerabox.android.dbexplorer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerabox.android.dbexplorer.model.Model;
import com.aerabox.android.dbexplorer.model.TableInfo;
import com.inqbarna.tablefixheaders.TableFixHeaders;

/**
 * A fragment representing a single TableInfo detail screen.
 * This fragment is either contained in a {@link TableInfoListActivity} in two-pane mode (on tablets) or a
 * {@link TableInfoDetailActivity} on handsets.
 */
public class TableInfoDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_POSITION = "item_position";
	private TableInfo mTableInfo;

	public TableInfoDetailFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_POSITION)) {
			final int position = getArguments().getInt(ARG_ITEM_POSITION);
			mTableInfo = Model.getInstance(getActivity()).getTabelInfos().get(position);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tableinfo_detail, container, false);

		// Show the dummy content as text in a TextView.
		if (mTableInfo != null) {
			final String tableName = mTableInfo.name;

			final FragmentActivity activity = getActivity();
			if (activity instanceof TableInfoDetailActivity) {
				activity.setTitle(tableName);
			}

			TableFixHeaders table = ((TableFixHeaders) rootView.findViewById(R.id.tableinfo_detail));
			
			table.setAdapter(new TableInfoGrigAdapter(activity, mTableInfo));

		}

		return rootView;
	}
}
