package com.example.spense.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spense.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "spending.db", null, 1);
    }

    //创建数据库的方法，只有项目第一次运行时会用到

    @Override
    public void onCreate(SQLiteDatabase db) {
    //创建表时类型的表
        String sql = "create table typetb(id integer primary key autoincrement, typename varchar(10), imageId integer, sImageId integer, kind integer)";
        db.execSQL(sql);
        insertType(db);

        //创建记账表
        sql = "create table accounttb(id integer primary key autoincrement, typename varchar(10), sImageId integer, note varchar(10), money float," +
                "time varchar(50), year integer, month integer, day integer, kind integer)";

        db.execSQL(sql);


    }

    private void insertType(SQLiteDatabase db) {
        String sql = "insert into typetb(typename,imageId, sImageId, kind) values(?,?,?,?)";
        db.execSQL(sql, new Object[]{"Others", R.mipmap.expense_others,R.mipmap.expense_others_slected, 0});
        db.execSQL(sql, new Object[]{"Eating Out", R.mipmap.expense_eating_out,R.mipmap.expense_eating_out_selected, 0});
        db.execSQL(sql, new Object[]{"Commute", R.mipmap.expense_commute,R.mipmap.expense_commute_selected, 0});
        db.execSQL(sql, new Object[]{"Shopping", R.mipmap.expense_shopping,R.mipmap.expense_shopping_selected, 0});
        db.execSQL(sql, new Object[]{"Car", R.mipmap.expense_car,R.mipmap.expense_car_selected, 0});
        db.execSQL(sql, new Object[]{"Grocery", R.mipmap.expense_grocery,R.mipmap.expense_grocery_selected, 0});
        db.execSQL(sql, new Object[]{"Entertainment", R.mipmap.expense_entertainment,R.mipmap.expense_entertainment_selected, 0});
        db.execSQL(sql, new Object[]{"Snack", R.mipmap.expense_snack,R.mipmap.expense_snack_selected, 0});
        db.execSQL(sql, new Object[]{"Travel", R.mipmap.expense_travel,R.mipmap.expense_travel_selected, 0});
        db.execSQL(sql, new Object[]{"Education", R.mipmap.expense_study,R.mipmap.expense_study_selected, 0});
        db.execSQL(sql, new Object[]{"Medical", R.mipmap.expense_medical,R.mipmap.expense_medical_selected, 0});
        db.execSQL(sql, new Object[]{"Mortgage/Rent", R.mipmap.expense_house,R.mipmap.expense_house_selected, 0});
        db.execSQL(sql, new Object[]{"Utilities", R.mipmap.expense_utilities,R.mipmap.expense_utilities_selected, 0});
        db.execSQL(sql, new Object[]{"Phone", R.mipmap.expense_phone,R.mipmap.expense_phone_selected, 0});
        db.execSQL(sql, new Object[]{"Gift", R.mipmap.expense_gift,R.mipmap.expense_gift_selected, 0});

        db.execSQL(sql, new Object[]{"Others", R.mipmap.income_others,R.mipmap.income_others_selected, 1});
        db.execSQL(sql, new Object[]{"Salary", R.mipmap.income_salary,R.mipmap.income_salary_selected, 1});
        db.execSQL(sql, new Object[]{"Bonus", R.mipmap.income_bonus,R.mipmap.income_bonus_selected, 1});
        db.execSQL(sql, new Object[]{"Loan", R.mipmap.income_borrow,R.mipmap.income_borrow_selected, 1});
        db.execSQL(sql, new Object[]{"Used-Sell", R.mipmap.income_sell,R.mipmap.income_sell_selected, 1});
        db.execSQL(sql, new Object[]{"Investment", R.mipmap.income_investment,R.mipmap.income_investment_selected, 1});
        db.execSQL(sql, new Object[]{"Stock", R.mipmap.income_stock,R.mipmap.income_stock_selected, 1});
        db.execSQL(sql, new Object[]{"Lucky$$", R.mipmap.income_luckymoney,R.mipmap.income_luckymoney_selected, 1});



    }

    //数据库版本在更新时发生改变，调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
