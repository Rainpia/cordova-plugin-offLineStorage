package org.apache.cordova.offLineStorage;

/**
 * Created by ryp on 1/26/16.
 */
import java.util.ArrayList;
import java.util.Date;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONObject;

public class DBUtil
{
    public static SQLiteDatabase sld;
    private static final String   DATABASE_POSITION    = "/data/data/io.cordova.hellocordova/";
    private static final String   DATABASE_NAME    = "offLineDB";
    private static final String   TABLE_NAME       = "off_line_storage";

    public DBUtil(){
        createOrOpenDatabase();
    }

    //创建或打开数据库的方法
    public static void createOrOpenDatabase()
    {
        try
        {
            sld=SQLiteDatabase.openDatabase
                    (
                            DATABASE_POSITION+DATABASE_NAME, //数据库所在路径
                            null,                                 //CursorFactory
                            SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //读写、若不存在则创建
                    );

            //本地存储信息
            String sql="create table if not exists "+TABLE_NAME +
                    "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "request text," +
                    "response text" +
                    ")";
            sld.execSQL(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //插入方法
        public boolean insert(String request,String response)
        {
            int s= (int) new Date().getTime();
            try
            {
                String sql="insert into "+TABLE_NAME+" values('"+s+"','"+request+"','"+response+"');";
                sld.execSQL(sql);
                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }

    //查询的方法
    public  ArrayList<JSONObject> query(String requestP)
    {
        ArrayList<JSONObject> resData=new ArrayList<JSONObject>();
        try
        {
            String sql="select * from "+TABLE_NAME+" where request=?";
            Cursor cur=sld.rawQuery(sql, new String[]{requestP});
            while(cur.moveToNext())
            {
                String id=cur.getString(cur.getColumnIndex("id"));
                String request=cur.getString(cur.getColumnIndex("request"));
                String response=cur.getString(cur.getColumnIndex("response"));

                JSONObject requestJson=new JSONObject(request);
                JSONObject responseJson=new JSONObject(response);


                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",id);
                jsonObject.put("request",requestJson);
                jsonObject.put("response",responseJson);

                resData.add(jsonObject);
            }
            cur.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resData;
    }

    //类别的删除信息
    public  void delete()
    {
        try
        {
            String sql="delete from "+TABLE_NAME;
            sld.execSQL(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //关闭数据库的方法
    public  void closeDatabase()
    {
        try
        {
            sld.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}