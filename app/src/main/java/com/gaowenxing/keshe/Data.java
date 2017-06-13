package com.gaowenxing.keshe;

import java.util.HashMap;

/**
 * Created by lx on 2017/6/3.
 */

public class Data {
    /**
     *Student表
     */
    public static String Sno = "Sno CHAR(10)";
    public static String Sname = "Sname CHAR(10)";
    public static String Ssex = "Ssex  CHAR(4)";
    public static String Sdate = "Sdate  CHAR(10) ";
    public static String Sdept = "Sdept CHAR(20)";
    public static String INSERT_STUDENT=" (Sno,Sname,Ssex,Sdate,Sdept)";

    /**
     * Course表
     */
    public static String Cno = "Cno CHAR(10)";
    public static String Cname = "Cname CHAR(40)";
    public static String Cpno = "Cpno CHAR(10) ";
    public static String Ccredit = "Ccredit SMALLINT";
    public static String INSERT_COURSE=" (Cno,Cname,Cpno,Ccredit)";

    /**
     * SC表
     */
    public static String Grade = "Grade SMALLINT";
    public static String INSERT_SC=" (Sno,Cno,Tno,Grade)";

    /**
     * Teacher表
     */
    public static String Tno ="Tno CHAR(10)";
    public static String Tname = "Tname CHAR(10)";
    public static String Tsex = "Tsex  CHAR(4)";
    public static String Ttel = "Ttel  CHAR(11)";
    public static String INSERT_TEACHER = " (Tno,Tname,Tsex,Ttel)";


    /**
     * TC表
     */
    public static String INSERT_TC = " (Tno,Cno)";


    /**
     * User表
     */
    public static String Uname = "Uname CHAR(10)";
    public static String Uclass = "UClass CHAR(10)";
    public static String Upassword = "Upassword CHAR(15)";
    public static String INSERT_USER=" (Uname,UClass,Upassword)";




    /**
     * 更新数据时传递的参数
     */
    public static String INTENT = "intent";
    public static String CHANGE_STUDENT_INFO =  "Sno";
    public static String CHANGE_teacher_INFO =  "Tno";
    public static String CHANGE_COURSE_INFO =  "Cno";
    public static String CHANGE_SC_INFO =  "Sno,Cno,Tno";
    public static String CHANGE_USER_INFO =  "Uname";

}
