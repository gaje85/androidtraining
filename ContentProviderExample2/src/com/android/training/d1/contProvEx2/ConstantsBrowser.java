
package com.android.training.d1.contProvEx2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ConstantsBrowser extends ListActivity {
	private static final int ADD_ID = Menu.FIRST + 1;
	private static final int DELETE_ID = Menu.FIRST + 3;
	private static final int CLOSE_ID = Menu.FIRST + 4;
	private static final String[] PROJECTION = new String[] {
			Provider.SolarSystem._ID, Provider.SolarSystem.TITLE,
			Provider.SolarSystem.VALUE };

	// A mock Cursor class that isolates the test code from real Cursor
	// implementation.
	private Cursor constantsCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		constantsCursor = managedQuery(Provider.SolarSystem.CONTENT_URI,
				PROJECTION, null, null, null);

		// An easy adapter to map columns from a cursor to TextViews or
		// ImageViews defined in an XML file.
		ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.row,
				constantsCursor, new String[] { Provider.SolarSystem.TITLE,
						Provider.SolarSystem.VALUE }, new int[] { R.id.title,
						R.id.value });

		// Provide the cursor for the list view.
		setListAdapter(adapter);

		// Registers a context menu to be shown for the given view (multiple
		// views can show the context menu).
		registerForContextMenu(getListView());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// Closes the Cursor, releasing all of its resources and making it
		// completely invalid.
		constantsCursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "Add").setIcon(R.drawable.add)
				.setAlphabeticShortcut('a');
		menu.add(Menu.NONE, CLOSE_ID, Menu.NONE, "Close").setIcon(
				R.drawable.eject).setAlphabeticShortcut('c');

		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ADD_ID:
			add();
			return (true);

		case CLOSE_ID:
			finish();
			return (true);
		}

		return (super.onOptionsItemSelected(item));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete").setIcon(
				R.drawable.delete).setAlphabeticShortcut('d');
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();

			delete(info.id);
			return (true);
		}

		return (super.onOptionsItemSelected(item));
	}

	private void add() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View addView = inflater.inflate(R.layout.add_edit, null);
		final DialogWrapper wrapper = new DialogWrapper(addView);

		new AlertDialog.Builder(this).setTitle(R.string.add_title).setView(
				addView).setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						processAdd(wrapper);
					}
				}).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// ignore, just dismiss
					}
				}).show();
	}

	private void delete(final long rowId) {
		if (rowId > 0) {
			new AlertDialog.Builder(this).setTitle(R.string.delete_title)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									processDelete(rowId);
								}
							}).setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// ignore, just dismiss
								}
							}).show();
		}
	}

	private void processAdd(DialogWrapper wrapper) {

		// This class is used to store a set of values that the ContentResolver
		// can process.
		ContentValues values = new ContentValues(2);

		// Adds a value to the set.
		values.put(Provider.SolarSystem.TITLE, wrapper.getTitle());
		values.put(Provider.SolarSystem.VALUE, wrapper.getValue());

		// Inserts a row into a table at the given URL. If the content provider
		// supports transactions the insertion will be atomic.
		getContentResolver().insert(Provider.SolarSystem.CONTENT_URI, values);

		// Performs the query that created the cursor again, refreshing its
		// contents.
		constantsCursor.requery();
	}

	private void processDelete(long rowId) {

		// Appends the given ID to the end of the path.
		Uri uri = ContentUris.withAppendedId(Provider.SolarSystem.CONTENT_URI,
				rowId);

		// Deletes row(s) specified by a content URI. If the content provider
		// supports transactions, the deletion will be atomic.
		getContentResolver().delete(uri, null, null);
		
		constantsCursor.requery();
	}

	class DialogWrapper {
		EditText titleField = null;
		EditText valueField = null;
		View base = null;

		DialogWrapper(View base) {
			this.base = base;
			valueField = (EditText) base.findViewById(R.id.value);
		}

		String getTitle() {
			return (getTitleField().getText().toString());
		}

		String getValue() {
			return (getValueField().getText().toString());
		}

		private EditText getTitleField() {
			if (titleField == null) {
				titleField = (EditText) base.findViewById(R.id.title);
			}

			return (titleField);
		}

		private EditText getValueField() {
			if (valueField == null) {
				valueField = (EditText) base.findViewById(R.id.value);
			}

			return (valueField);
		}
	}
}