package com.inazaruk.contactsgen;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String AUTOGENERATED_TAG = "autogenerated";

	private static final List<Integer> sColors;

	private TextView mContactsCountTextView;
	private SeekBar mSeekBar;
	private TextView mGenerateCountTextView;
	private Button mGenerateButton;
	private Button mClearButton;
	private ProgressBar mProgressBar;
	private TextView mProgressCountTextView;
	private Button mCancelButton;
	private CheckBox mRandomise;

	private AsyncTask<?, ?, ?> mTask = null;

	static {
		final Field[] fields = Color.class.getDeclaredFields();
		final List<Integer> colors = new ArrayList<Integer>();
		for (final Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
					&& field.getType().equals(int.class)) {

				try {
					colors.add(field.getInt(null));
				} catch (final Exception ex) {
					Log.e(TAG, "Failed to get color field value: " + field.getName(), ex);
					// Ignore
				}
			}
		}
		sColors = Collections.unmodifiableList(colors);
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mContactsCountTextView = (TextView) findViewById(R.id.contacts_count);
		mSeekBar = (SeekBar) findViewById(R.id.seekbar);
		mGenerateCountTextView = (TextView) findViewById(R.id.generate_count);
		mGenerateButton = (Button) findViewById(R.id.generate);
		mClearButton = (Button) findViewById(R.id.clear);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);
		mProgressCountTextView = (TextView) findViewById(R.id.progress_count);
		mCancelButton = (Button) findViewById(R.id.cancel);
		mRandomise = (CheckBox) findViewById(R.id.randomise);

		mSeekBar.setOnSeekBarChangeListener(this);
		mGenerateButton.setOnClickListener(this);
		mClearButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);

		mSeekBar.setProgress(10);
		setProgressState(false);
		syncGenerateCountTextView();
		updateAutogeneratedCount();
	}

	@Override
	public void onClick(final View v) {
		if (v == mGenerateButton) {
			mTask = new Generator().execute(getGenerateCount());
			setProgressState(true);
			setProgress(0, 0);
		} else if (v == mClearButton) {
			mTask = new Cleaner().execute();
			setProgressState(true);
			setProgress(0, 0);
		} else if (v == mCancelButton) {
			if (mTask != null) {
				mTask.cancel(true);
			}
		}
	}

	private void updateAutogeneratedCount() {
		final Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
				new String[] { ContactsContract.Data.CONTACT_ID },
				ContactsContract.Data.MIMETYPE + " = ? and " + CommonDataKinds.Note.NOTE + " = ?",
				new String[] { CommonDataKinds.Note.CONTENT_ITEM_TYPE, AUTOGENERATED_TAG },
				ContactsContract.Data.CONTACT_ID + " ASC");

		int count = 0;
		String previousId = null;
		if (cursor.moveToFirst()) {
			do {
				final String id = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Data.CONTACT_ID));
				if (!id.equals(previousId)) {
					count++;
					previousId = id;
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		mContactsCountTextView.setText(getString(R.string.autogenerated_contacts, count));
	}

	private int getGenerateCount() {
		return mSeekBar.getProgress();
	}

	private void syncGenerateCountTextView() {
		mGenerateCountTextView.setText(Integer.toString(getGenerateCount()));
	}

	@Override
	public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
		syncGenerateCountTextView();
	}

	@Override
	public void onStartTrackingTouch(final SeekBar seekBar) {
		syncGenerateCountTextView();
	}

	@Override
	public void onStopTrackingTouch(final SeekBar seekBar) {
		syncGenerateCountTextView();
	}

	public void setProgressState(final boolean inProgress) {
		mSeekBar.setEnabled(!inProgress);
		mGenerateCountTextView.setEnabled(!inProgress);
		mGenerateButton.setEnabled(!inProgress);
		mClearButton.setEnabled(!inProgress);

		int visibility = View.GONE;
		if (inProgress) {
			visibility = View.VISIBLE;

		}

		mProgressBar.setVisibility(visibility);
		mProgressCountTextView.setVisibility(visibility);
		mCancelButton.setVisibility(visibility);
	}

	public void setProgress(final int current, final int max) {
		mProgressBar.setMax(max);
		mProgressBar.setProgress(current);
		mProgressCountTextView.setText(String.format("%d/%d", current + 1, max));
	}

	private static Bitmap generateBitmap(final int x, final int y, final Random random) {
		final Bitmap bitmap = Bitmap.createBitmap(x, y, Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);

		int color = Color.GREEN;
		if (sColors.size() > 0) {
			final int index = random.nextInt(sColors.size());
			color = sColors.get(index);
		}
		canvas.drawColor(color);

		final Paint paint = new Paint();

		final int circleCount = random.nextInt(20) + 5;
		for (int i = 0; i < circleCount; i++) {
			final int cx = random.nextInt(x);
			final int cy = random.nextInt(y);
			final int radius = random.nextInt((x + y) / 10);
			int ccolor = Color.LTGRAY;
			if (sColors.size() > 0) {
				final int index = random.nextInt(sColors.size());
				ccolor = sColors.get(index);
			}

			paint.setColor(ccolor);
			paint.setAntiAlias(true);
			paint.setAlpha(random.nextInt(200) + 55);

			canvas.drawCircle(cx, cy, radius, paint);
		}

		return bitmap;
	}

	private boolean addContact(final List<ContactField> fields, final Bitmap image) {

		final ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());

		for (final ContactField field : fields) {
			final Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI)
					.withValueBackReference(Data.RAW_CONTACT_ID, 0)
					.withValue(Data.MIMETYPE, field.getContentItemType())
					.withValue(field.getMetaValue(), field.getValue());
			if (field.hasType()) {
				builder.withValue(field.getMetaType(), field.getType());
			}
			ops.add(builder.build());
		}

		if (image != null) {
			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.PNG, 75, stream);

			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
					.withValue(ContactsContract.Data.MIMETYPE,
							CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
					.withValue(CommonDataKinds.Photo.PHOTO, stream.toByteArray()).build());
		}

		// Asking the Contact provider to create a new contact
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	private void onTaskCompleted() {
		setProgressState(false);
		updateAutogeneratedCount();
	}

	private class Generator extends AsyncTask<Integer, Integer, Void> {

		final Random random = new Random();

		@Override
		protected Void doInBackground(final Integer... params) {

			// Large bitmaps take a while to save...
			final int size = 256;

			final int count = params[0];

			final ContactField note = new ContactField(Note.CONTENT_ITEM_TYPE, Note.NOTE,
					AUTOGENERATED_TAG);

			final List<ContactField> fields = new ArrayList<ContactField>();

			for (int i = 0; i < count && !isCancelled(); i++) {
				fields.add(note);

				// We always generate a contact name
				final ContactField name = ContactBuilder.getName();
				fields.add(name);

				// There can be up to three email addresses with a 70% chance of
				// showing any one when randomised
				for (int j = 0; j < 3; j++) {
					if (shouldInclude(0.7f)) {
						fields.add(ContactBuilder.getEmail(name.getValue()));
					}
				}

				// There can be up to three websites with a 50% chance of
				// showing any one when randomised
				for (int j = 0; j < 3; j++) {
					if (shouldInclude(0.5f)) {
						fields.add(ContactBuilder.getWebsite());
					}
				}

				// There can be up to 5 phone numbers of random types with a 35%
				// chance of showing any one
				for (int j = 0; j < 5; j++) {
					if (shouldInclude(0.35f)) {
						fields.add(ContactBuilder.getPhone());
					}
				}

				// There is one nickname with a 33% chance of showing if
				// randomised
				if (shouldInclude(0.33f)) {
					fields.add(ContactBuilder.getNickName());
				}

				// There can be up to two addresses with an 80% chance of
				// showing if randomised
				for (int j = 0; j < 2; j++) {
					if (shouldInclude(0.8f)) {
						fields.add(ContactBuilder.getAddress());
					}
				}

				// There is one organisation with a 50% chance of showing if
				// randomised
				if (shouldInclude(0.5f)) {
					fields.add(ContactBuilder.getOrganisation());
				}

				// Bitmaps are slow to save, so we drop the chance of including
				// one when randomising to 5%
				Bitmap bitmap = null;
				if (shouldInclude(0.05f)) {
					bitmap = generateBitmap(size, size, random);
				}
				addContact(fields, bitmap);

				if (bitmap != null) {
					bitmap.recycle();
				}
				publishProgress(i, count);
				fields.clear();
			}
			return null;
		}

		/**
		 * Randomly determines if something should be included based on the
		 * factor. A factor of 0 indicates the it should never be included
		 * whilst a factor of 1 indicates it should always be included.
		 * 
		 * Note that if the 'Randomise field' checkbox is unchecked, then we
		 * always return true.
		 * 
		 * @param includeFactor
		 *            a value between 0 and 1
		 * @return
		 */
		private boolean shouldInclude(final float includeFactor) {

			if (!mRandomise.isChecked()) {
				return true;
			}

			float theFactor = includeFactor;
			if (theFactor < 0.0f) {
				theFactor = 0.0f;
			}
			if (theFactor > 1.0f) {
				theFactor = 1.0f;
			}
			return random.nextFloat() < theFactor;
		}

		@Override
		protected void onProgressUpdate(final Integer... values) {
			setProgress(values[0], values[1]);
		}

		@Override
		protected void onPostExecute(final Void result) {
			onTaskCompleted();
		}

		@Override
		protected void onCancelled() {
			onTaskCompleted();
		}
	}

	private class Cleaner extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(final Void... params) {
			final Cursor cursor = getContentResolver().query(
					ContactsContract.Data.CONTENT_URI,
					null,
					ContactsContract.Data.MIMETYPE + " = ? and " + CommonDataKinds.Note.NOTE
							+ " = ?",
					new String[] { CommonDataKinds.Note.CONTENT_ITEM_TYPE, AUTOGENERATED_TAG },
					null);

			int counter = 0;
			final int count = cursor.getCount();
			if (cursor.moveToFirst()) {
				do {
					final String id = cursor.getString(cursor
							.getColumnIndex(ContactsContract.Data.CONTACT_ID));

					getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,
							ContactsContract.RawContacts.CONTACT_ID + " = ?", new String[] { id });
					publishProgress(counter, count);
					counter++;
				} while (cursor.moveToNext() && !isCancelled());
			}
			cursor.close();
			return null;
		}

		@Override
		protected void onProgressUpdate(final Integer... values) {
			setProgress(values[0], values[1]);
		}

		@Override
		protected void onPostExecute(final Void result) {
			onTaskCompleted();
		}

		@Override
		protected void onCancelled() {
			onTaskCompleted();
		}
	}
}