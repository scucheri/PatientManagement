package plugin.android.ss.com.patientmanagementfordoctor;

import android.content.Context;
import android.text.TextUtils;
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
    private static final String TAG = "CsvUtil";
    private static String folderName = "/病人信息/";


    public static void save(Context context, String patientName, String infoNumber, ArrayList<PatientOptionData> dataList) {
        if (dataList == null || context == null || TextUtils.isEmpty(patientName)) return;

        String baseDir = context.getExternalFilesDir(null).getAbsolutePath();
        String csvFileName = patientName + "_" + infoNumber + ".csv";
        String textFileName = patientName + "_" + infoNumber + ".txt";

        String folderPath = baseDir + folderName;
        String csvFilePath = baseDir + folderName + csvFileName;
        String textFilePath = baseDir + folderName + textFileName;

        try {
            File folderFile = new File(folderPath);
            if (!folderFile.exists()) {
                Log.i(TAG, "write: --------2");
                folderFile.mkdirs();
            }
            File csvFile = new File(csvFilePath);
            File txtFile = new File(textFilePath);

            saveDataToFile(csvFile, dataList);
            saveDataToFile(txtFile, dataList);

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private static void saveDataToFile(File file, ArrayList<PatientOptionData> dataList) {
        boolean isFirstFoRow1 = true;
        boolean isFirstFoRow2 = true;
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }

            FileOutputStream outStream = new FileOutputStream(file, true);

            for (int index = 0; index < dataList.size(); index++) {
                try {
                    PatientOptionData data = dataList.get(index);
                    if (data.getType() == OptionType.TITLE) continue;
                    if (!isFirstFoRow1) {
                        outStream.write(",".getBytes());
                    }
                    isFirstFoRow1 = false;
                    outStream.write(data.getOptionName().getBytes());
                } catch (FileNotFoundException e) {
                    Log.d(TAG, e.toString());
                    return;
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }

            outStream.write("\r\n".getBytes());

            for (int index = 0; index < dataList.size(); index++) {
                try {
                    PatientOptionData data = dataList.get(index);
                    if (data.getType() == OptionType.TITLE) continue;
                    if (!isFirstFoRow2) {
                        outStream.write(",".getBytes());
                    }
                    isFirstFoRow2 = false;
                    outStream.write(data.getOptionResult().getBytes());
                } catch (FileNotFoundException e) {
                    Log.d(TAG, e.toString());
                    return;
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }
            outStream.close();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
