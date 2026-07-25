package uz.talim.markaz.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    /**
     * Tanlangan faylni (kitob yoki media) ilovaning ichki xotirasiga nusxalaydi
     * va yangi faylning to'liq manzilini qaytaradi.
     */
    public static String copyFileToInternalStorage(Context context, Uri sourceUri, String folderName) {
        try {
            File folder = new File(context.getFilesDir(), folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String originalName = getFileName(context, sourceUri);
            String fileName = System.currentTimeMillis() + "_" + originalName;
            File destFile = new File(folder, fileName);

            InputStream inputStream = context.getContentResolver().openInputStream(sourceUri);
            OutputStream outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[4096];
            int length;
            if (inputStream != null) {
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
            }
            outputStream.close();

            return destFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result != null ? result.lastIndexOf('/') : -1;
            if (cut != -1 && result != null) {
                result = result.substring(cut + 1);
            }
        }
        return result != null ? result : "fayl";
    }

    public static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, "uz.talim.markaz.fileprovider", file);
    }
}
