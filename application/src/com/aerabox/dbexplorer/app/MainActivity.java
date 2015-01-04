package com.aerabox.dbexplorer.app;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.aerabox.android.dbexplorer.DbExplorer;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.action_open_test_db).setOnClickListener(this);


		DbExplorer.setConfiguration(new DbExplorer.Configuration() {

			private SQLiteOpenHelper dbHelper = new TestSqlDb(getApplicationContext());

			@Override
			public SQLiteDatabase getDatabase() {
				return this.dbHelper.getReadableDatabase();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_open_test_db:
			DbExplorer.start(this);
			break;

		default:
			break;
		}

	}


}
