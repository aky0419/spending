package com.example.spense.DB;

/**
 * 用于描述绘制柱状图是每一个柱子表示的对象
 */

public class BarChartItemBean {
    int year;
    int month;
    int day;
    int sumMoney;

    public BarChartItemBean() {
    }

    public BarChartItemBean(int year, int month, int day, int sumMoney) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.sumMoney = sumMoney;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }
}
