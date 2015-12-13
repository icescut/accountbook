package per.icescut.util;

import java.text.SimpleDateFormat;

public class Constants {
    public static final String[] OWNERS = { "老公", "老婆" };
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    public static final String[] CATEGORY_LV1_PAY = { "饮食", "生活用品", "居家物业", "行车交通", "交流通讯", "休闲请客", "学习进修", "医疗保健",
	    "金融保险", "其他" };
    public static final String[][] CATEGORY_LV2_PAY = {
	    {
		"工作餐",
		"买菜",
		"零食"
	    },
	    {
		"日常用品",
		"衣服",
		"家具电器"
	    },
	    {
		"水电煤",
		"物业费"
	    },
	    {
		"公共交通",
		"私家车"
	    },
	    {
		"手机费",
		"网费"
	    },
	    {
		"玩乐",
		"送礼",
		"捐助"
	    },
	    {
		"书籍",
		"装备",
		"培训"
	    },
	    {
		"医药费"
	    },
	    {
		"保险费",
		"投资亏损",
		"按揭还款"
	    },
	    {
		"其他"
	    }
    };
    
    public static final String[] CATEGORY_LV1_INCOME = { "收入" };
    public static final String[][] CATEGORY_LV2_INCOME = { {"工资","奖金","意外","投资收益"} };
    
    // mysql参数
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/account_book?useUnicode=true&characterEncoding=GBK";
    public static final String USERNAME = "ab_user";
    public static final String PASSWORD = "abP@ssw0rd";
    public static final int GUI_TABLE_ROW = 10;
    public static final int GUI_TABLE_COLUMN = 3;
}
