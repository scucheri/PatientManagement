package plugin.android.ss.com.patientmanagementfordoctor;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xiaoxiaoyu on 2020-11-21.
 */
public class CsvUtil {
    private static String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String TAG = "CsvUtil";
    private static String folderName = "病人信息/";


    public static void save(String fileName, ArrayList<PatientOptionData> dataList) {
        if (dataList == null) return;
        String folderPath = baseDir + folderName;
        String filePath = baseDir + folderName + fileName;
        try {
            File folderFile = new File(folderPath);
            if (!folderFile.exists() || !folderFile.isDirectory()) {
                Log.i(TAG, "write: --------2");
                folderFile.mkdirs();
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }else {
                file.delete();
                file.createNewFile();
            }
            for (PatientOptionData data : dataList) {
                try {
                    FileOutputStream outStream = new FileOutputStream(file, true);
                    outStream.write((data.getOptionName() + "," + data.getOptionResult() + "\r\n").getBytes());
                    outStream.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, e.toString());
                    return;
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}
