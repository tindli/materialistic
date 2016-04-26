package android.content;

import android.database.Cursor;
import android.net.Uri;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import static org.robolectric.Shadows.shadowOf;

@Implements(value = AsyncQueryHandler.class, inheritImplementationMethods = true)
public class ShadowAsyncQueryHandler {
    @RealObject AsyncQueryHandler realObject;
    @Implementation
    public void startQuery(int token, Object cookie, Uri uri,
                           String[] projection, String selection, String[] selectionArgs,
                           String orderBy) {
        Cursor cursor = shadowOf(RuntimeEnvironment.application.getContentResolver())
                .query(uri, projection, selection, selectionArgs, orderBy);
        realObject.onQueryComplete(token, cookie, cursor);
    }

    @Implementation
    public final void startInsert(int token, Object cookie, Uri uri,
                                  ContentValues initialValues) {
        Uri insertUri = shadowOf(RuntimeEnvironment.application.getContentResolver())
                .insert(uri, initialValues);
        realObject.onInsertComplete(token, cookie, insertUri);
    }

    @Implementation
    public final void startDelete(int token, Object cookie, Uri uri,
                                  String selection, String[] selectionArgs) {
        int result = shadowOf(RuntimeEnvironment.application.getContentResolver())
                .delete(uri, selection, selectionArgs);
        realObject.onDeleteComplete(token, cookie, result);
    }
}
