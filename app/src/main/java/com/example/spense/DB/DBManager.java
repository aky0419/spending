package com.example.spense.DB;

//负责管理数据库的类，主要对于表当中的内容进行操作，增删改查

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String TAG = "DBManager";

    private static SQLiteDatabase database;


    public static void initDB(Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
    }

    /**读取数据库当中数据，写入内存集合
     * kind：表示收入或者支出
     **/

    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean> list = new ArrayList<>();
        String sql = "select * from typetb where kind = " + kind;
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id,typename,imageId,sImageId,kind1);
            list.add(typeBean);
        }

        return list;
    }

    //
//    int id;
//    String typename;
//    int sImageId;
//    String node;
//    float money;
//    String time; //时间字符串
//    int year;
//    int month;
//    int day;
//    int kind; //类型 收入 1 支出 0

    public static void insertItemToAccountTb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("sImageId",bean.getsImageId());
        values.put("note",bean.getNote());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());

        database.insert("accounttb", null, values);
    }


    public static List<AccountBean> getAccountListForToday(int year, int month, int day){
        List<AccountBean> res = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, note, money, time, year, month, day, kind);
            res.add(accountBean);

        }
        return res;
    }

    public static float getDailySummary(int year, int month, int day, int kind){
        float sum = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            sum = money;
        }
        return sum;
    }

    public static float getMonthlySummary(int year, int month, int kind){
        float sum = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(cursor.getColumnIndex("sum(money)"));

        }
        return sum;
    }

    /**
     * 统计某月份支出收入情况 有多少条
     * 收入 1 支持 0
     * @param year
     * @param month
     * @param kind
     * @return
     */

    public static int getMonthlyCount(int year, int month, int kind){
        int count = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            int c = cursor.getInt(cursor.getColumnIndex("count(money)"));
            count = c;
        }
        return count;
    }


    public static float getYearlySummary(int year, int kind){
        float sum = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            sum = money;
        }
        return sum;
    }

    /**
     * 根据传入的id，删除accounttb中的对应数据
     */

    public static int deleteRecordFromAccounttb(int current_id){
        int i = database.delete("accounttb", "id=?", new String[]{current_id + ""});
        return i;

    }

    /**
     * 根据备注搜索收入或者支出的情况列表
     */

    public static List<AccountBean> getAccountListByNote(String input_note){
        List<AccountBean> res = new ArrayList<>();
        String sql = "select * from accounttb where note like '%" + input_note + "%'";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, note, money, time, year, month, day, kind);
            res.add(accountBean);
        }
        return res;
    }

    public static List<AccountBean> getMonthlyAccountList(int year, int month){
        List<AccountBean> res = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + ""});
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, note, money, time, year, month, day, kind);
            res.add(accountBean);

        }
        return res;
    }

    /**
     * 查询记账表中 有几个年份信息
     */

    public static List<Integer> getYearInfoFromAccounttb(){
        List<Integer> yearList = new ArrayList<>();
        String sql = "select distinct(year) from accounttb order by year asc";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            yearList.add(year);
        }
        return yearList;
    }

    public static void deleteAccounttbAllInfo(){
        String sql = "delete from accounttb";
        database.execSQL(sql);
    }

    /**
     * 查询指定年月的收入和支出每一种类型的总钱数
     */

    public static List<GraphItemBean> getGraphItemBeam(int year, int month, int kind){
        List<GraphItemBean> itemBeanList = new ArrayList<>();
        float monthlySummary = getMonthlySummary(year, month, kind); //求出支出或者收入总钱数
        String sql = "select typename, sImageId, sum(money)as total from accounttb where year =? and month=? and kind=? group by typename " +
                "order by total desc";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            float total = cursor.getFloat(cursor.getColumnIndex("total"));

            //计算所占百分比
            float percentage = calculation(total, monthlySummary);
            GraphItemBean graphItemBean = new GraphItemBean(sImageId, typename, percentage, total);
            itemBeanList.add(graphItemBean);


        }

        return itemBeanList;
    }

    /**
     * 除法运算 保留四位小数点
     */

    public static float calculation(float divisor, float dividend){
        float ans = divisor/dividend;
        BigDecimal bigDecimal = new BigDecimal(ans);
        float res = bigDecimal.setScale(4, 4).floatValue();
        return res;
    }

    /**
     * 获取本月收入或支出最大值
     */

    public static float getDailyMaxForMonth(int year, int month, int kind){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            float res = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            return res;
        }
        return 0;


    }

    /**
     * 知道年月和种类 获取每天的总数
     */

    public static List<BarChartItemBean> getDailyBarCharItemBean(int year, int month, int kind){
        List<BarChartItemBean> list = new ArrayList<>();
        String sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day";
        Cursor cursor = database.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int sumMoney = cursor.getInt(cursor.getColumnIndex("sum(money)"));
            BarChartItemBean barChartItemBean = new BarChartItemBean(year, month, day, sumMoney);
            list.add(barChartItemBean);
        }
        return list;
    }



}
