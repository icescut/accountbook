package per.icescut.util;

import java.text.SimpleDateFormat;

public class Constants {
    public static final String[] OWNERS = { "�Ϲ�", "����" };
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    public static final String[] CATEGORY_LV1_PAY = { "��ʳ", "������Ʒ", "�Ӽ���ҵ", "�г���ͨ", "����ͨѶ", "�������", "ѧϰ����", "ҽ�Ʊ���",
	    "���ڱ���", "����" };
    public static final String[][] CATEGORY_LV2_PAY = {
	    {
		"������",
		"���",
		"��ʳ"
	    },
	    {
		"�ճ���Ʒ",
		"�·�",
		"�Ҿߵ���"
	    },
	    {
		"ˮ��ú",
		"��ҵ��"
	    },
	    {
		"������ͨ",
		"˽�ҳ�"
	    },
	    {
		"�ֻ���",
		"����"
	    },
	    {
		"����",
		"����",
		"����"
	    },
	    {
		"�鼮",
		"װ��",
		"��ѵ"
	    },
	    {
		"ҽҩ��"
	    },
	    {
		"���շ�",
		"Ͷ�ʿ���",
		"���һ���"
	    },
	    {
		"����"
	    }
    };
    
    public static final String[] CATEGORY_LV1_INCOME = { "����" };
    public static final String[][] CATEGORY_LV2_INCOME = { {"����","����","����","Ͷ������"} };
    
    // mysql����
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/account_book?useUnicode=true&characterEncoding=GBK";
    public static final String USERNAME = "ab_user";
    public static final String PASSWORD = "abP@ssw0rd";
    public static final int GUI_TABLE_ROW = 10;
    public static final int GUI_TABLE_COLUMN = 3;
}
