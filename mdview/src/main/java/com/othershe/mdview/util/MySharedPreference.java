package com.othershe.mdview.util;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 模仿 SP 写保存数据
 * TAG 不能以数字开头 不然会有异常
 *
 */

public class MySharedPreference {


    private static final String SHARED_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/.shared/";

    private File shareFile;

    public MySharedPreference() throws IOException {
        this("default.xml");
    }

    public MySharedPreference(String file) throws IOException {

        File sharedPath = new File(SHARED_PATH);
        if (!sharedPath.exists())
            sharedPath.mkdirs();

        if (!sharedPath.exists())
            sharedPath.mkdirs();

        if (!file.endsWith(".xml")) {
            file += ".xml";
        }

        File shareFile = new File(sharedPath, file);
        if (!shareFile.exists())
            shareFile.createNewFile();

        this.shareFile = shareFile;

    }

    public boolean put(String key, String value) {
        putXmlIntoFile(shareFile, key, value);
        return true;
    }

    public String get(String key, String defValue) {
        return getXmlFromFile(shareFile, key, defValue);
    }

    public int getInt(String key, int defValue) {
        String value = getXmlFromFile(shareFile, key, String.valueOf(defValue));
        return Integer.valueOf(value);
    }


    private void putXmlIntoFile(File file, String key, String value) {
        try {
            XmlSerializer serializer = Xml.newSerializer();
            FileOutputStream outputStream = new FileOutputStream(file);
            serializer.setOutput(outputStream, "utf-8");
            serializer.startDocument("utf-8", true);
            serializer.text("\n");
            serializer.startTag(null, "maps");
            serializer.text("\n\t");

            serializer.startTag(null, "int");
            serializer.attribute(null, "key", key);
            serializer.attribute(null, "value", value);
            serializer.endTag(null, "int");

            serializer.text("\n");
            serializer.endTag(null, "maps");
            serializer.text("\n");

            serializer.endDocument();

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getXmlFromFile(File file, String key, String defValue) {
        String res = null;
        try {
            XmlPullParser pullParser = Xml.newPullParser();
            FileInputStream inputStream = new FileInputStream(file);
            pullParser.setInput(inputStream, "utf-8");
            int event = pullParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                if (event == XmlPullParser.START_DOCUMENT) {

                } else if (event == XmlPullParser.START_TAG) {
                    Log.e("Andy", "tag name = " + pullParser.getName());

                    int attrs = pullParser.getAttributeCount();
                    if (attrs == 2) {
                        String myKey = pullParser.getAttributeValue(0);
                        Log.e("Andy", " tag attrs = " + myKey);
                        if (myKey.equals(key)) {
                            String value = pullParser.getAttributeValue(1);
                            res = value;
                            Log.e("Andy", " tag attrs = " + value);
                            break;
                        }
                    }

                } else if (event == XmlPullParser.END_TAG) {

                }

                event = pullParser.next();
            }
            inputStream.close();

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            res = defValue;
        }

        return  res;
    }





}
