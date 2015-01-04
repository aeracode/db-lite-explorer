package com.aerabox.android.dbexplorer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An activity representing a list of TableInfos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TableInfoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a {@link TableInfoListFragment} and the item details
 * (if present) is a {@link TableInfoDetailFragment}.
 * <p>
 * This activity also implements the required {@link TableInfoListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class TableInfoListActivity extends FragmentActivity
		implements TableInfoListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tableinfo_list);

		if (findViewById(R.id.tableinfo_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((TableInfoListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.tableinfo_list))
					.setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link TableInfoListFragment.Callbacks} indicating that the item with the given ID was
	 * selected.
	 */
	@Override
	public void onItemSelected(int id) {
		Bundle arguments = new Bundle();
		arguments.putInt(TableInfoDetailFragment.ARG_ITEM_POSITION, id);
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			TableInfoDetailFragment fragment = new TableInfoDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.tableinfo_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			TableInfoDetailActivity.start(this, arguments);
		}
	}
}
