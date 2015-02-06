package com.android.training.d1.contProvEx2;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class Provider extends ContentProvider {
	private static final String DATABASE_NAME = "SolarSystem.UniverseDB";
	private static final int SOLARSYSTEM = 1;
	private static final int SOLARSYSTEM_ID = 2;
	private static final UriMatcher MATCHER;
	private static HashMap<String, String> SOLARSYSTEM_LIST_PROJECTION;

	public static final class SolarSystem implements BaseColumns {
		public static final Uri CONTENT_URI = Uri
				.parse("content://com.android.training.d1.contProvEx2.Provider/SolarSystem");
		public static final String TABLE_NAME = "SolarSystem";
		public static final String DEFAULT_SORT_ORDER = "Planet";
		public static final String TITLE = "Planet";
		public static final String VALUE = "Diameter";
	}

	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI("com.android.training.d1.contProvEx2.Provider",
				"SolarSystem", SOLARSYSTEM);
		MATCHER.addURI("com.android.training.d1.contProvEx2.Provider",
				"SolarSystem/#", SOLARSYSTEM_ID);

		SOLARSYSTEM_LIST_PROJECTION = new HashMap<String, String>();
		SOLARSYSTEM_LIST_PROJECTION.put(Provider.SolarSystem._ID,
				Provider.SolarSystem._ID);
		SOLARSYSTEM_LIST_PROJECTION.put(Provider.SolarSystem.TITLE,
				Provider.SolarSystem.TITLE);
		SOLARSYSTEM_LIST_PROJECTION.put(Provider.SolarSystem.VALUE,
				Provider.SolarSystem.VALUE);
	}

	public String getDbName() {
		return (DATABASE_NAME);
	}

	public int getDbVersion() {
		return (1);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Cursor c = db
					.rawQuery(
							"SELECT name FROM sqlite_master WHERE type='table' AND name='SolarSystem'",
							null);

			try {
				if (c.getCount() == 0) {
					db
							.execSQL("CREATE TABLE SolarSystem (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
									+ SolarSystem.TITLE
									+ " TEXT, "
									+ SolarSystem.VALUE + " TEXT);");

					// This class is used to store a set of values
					ContentValues cv = new ContentValues();

					// Adds a value to the set.
					cv.put(SolarSystem.TITLE, "MERCURY");
					cv.put(SolarSystem.VALUE, "4,880 km");
					// Convenience method for inserting a row into the database.
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "VENUS");
					cv.put(SolarSystem.VALUE, "12,103.6 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "EARTH");
					cv.put(SolarSystem.VALUE, "12,756.3 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "MARS");
					cv.put(SolarSystem.VALUE, "6,794 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "JUPITER");
					cv.put(SolarSystem.VALUE, "142,984 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "SATURN");
					cv.put(SolarSystem.VALUE, "120,536 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "URANUS");
					cv.put(SolarSystem.VALUE, "51,118 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "NEPTUNE");
					cv.put(SolarSystem.VALUE, "49,532 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);

					cv.put(SolarSystem.TITLE, "PLUTO");
					cv.put(SolarSystem.VALUE, "2274 km");
					db.insert(SolarSystem.TABLE_NAME, getNullColumnHack(), cv);
				}
			} finally {
				c.close();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			android.util.Log.w("UniverseDB",
					"Upgrading database, which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS SolarSystem");
			onCreate(db);
		}
	}

	private SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		db = (new DatabaseHelper(getContext())).getWritableDatabase();

		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(getTableName());

		if (isCollectionUri(url)) {
			qb.setProjectionMap(getDefaultProjection());
		} else {
			qb.appendWhere(getIdColumnName() + "="
					+ url.getPathSegments().get(1));
		}

		String orderBy;

		if (TextUtils.isEmpty(sort)) {
			orderBy = getDefaultSortOrder();
		} else {
			orderBy = sort;
		}

		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	@Override
	public String getType(Uri url) {
		if (isCollectionUri(url)) {
			return (getCollectionType());
		}

		return (getSingleType());
	}

	@Override
	public Uri insert(Uri url, ContentValues initialValues) {
		long rowID;
		ContentValues values;

		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		if (!isCollectionUri(url)) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		for (String colName : getRequiredColumns()) {
			if (values.containsKey(colName) == false) {
				throw new IllegalArgumentException("Missing column: " + colName);
			}
		}

		populateDefaultValues(values);

		rowID = db.insert(getTableName(), getNullColumnHack(), values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(getContentUri(), rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}

		throw new SQLException("Failed to insert row into " + url);
	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		int count;
		long rowId = 0;

		if (isCollectionUri(url)) {
			count = db.delete(getTableName(), where, whereArgs);
		} else {
			String segment = url.getPathSegments().get(1);
			rowId = Long.parseLong(segment);
			count = db.delete(getTableName(),
					getIdColumnName()
							+ "="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
		}

		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int count;

		if (isCollectionUri(url)) {
			count = db.update(getTableName(), values, where, whereArgs);
		} else {
			String segment = url.getPathSegments().get(1);
			count = db.update(getTableName(), values,
					getIdColumnName()
							+ "="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
		}

		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	private boolean isCollectionUri(Uri url) {
		return (MATCHER.match(url) == SOLARSYSTEM);
	}

	private HashMap<String, String> getDefaultProjection() {
		return (SOLARSYSTEM_LIST_PROJECTION);
	}

	private String getTableName() {
		return ("SolarSystem");
	}

	private String getIdColumnName() {
		return ("_id");
	}

	private String getDefaultSortOrder() {
		return ("Planet");
	}

	private String getCollectionType() {
		return ("vnd.android.cursor.dir/vnd.com.SolarSystem");
	}

	private String getSingleType() {
		return ("vnd.android.cursor.item/vnd.com.SolarSystem");
	}

	private String[] getRequiredColumns() {
		return (new String[] { "Planet" });
	}

	private void populateDefaultValues(ContentValues values) {
		Long now = Long.valueOf(System.currentTimeMillis());
		Resources r = Resources.getSystem();

		if (values.containsKey(Provider.SolarSystem.VALUE) == false) {
			values.put(Provider.SolarSystem.VALUE, " ");
		}
	}

	private String getNullColumnHack() {
		return ("Planet");
	}

	private Uri getContentUri() {
		return (Provider.SolarSystem.CONTENT_URI);
	}
}