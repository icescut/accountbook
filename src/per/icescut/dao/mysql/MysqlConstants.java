package per.icescut.dao.mysql;

public final class MysqlConstants {
    // sqls
    public static final String SQL_QUERY_ACCOUNT = "SELECT id, name, amount FROM ab_account";
    public static final String SQL_UPDATE_ACCOUNT = "UPDATE ab_account SET amount = ? where id = ?";
    public static final String SQL_QUERY_RECORD = "SELECT id, type_value, owner, category_lv1, category_lv2, value_date, amount, amount, account, transfer_out, transfer_in, remark FROM ab_record ORDER BY id DESC LIMIT ?, ?";
    public static final String SQL_QUERY_RECORD_COUNT = "SELECT COUNT(id) FROM ab_record";
    public static final String SQL_INSERT_RECORD = "INSERT INTO ab_record(type_value, owner, category_lv1, category_lv2, value_date, amount, account, transfer_out, transfer_in, remark) VALUES(?,?,?,?,?,?,?,?,?,?)";

    // fields
    public static final String TABLE_AB_ACCOUNT_ID = "id";
    public static final String TABLE_AB_ACCOUNT_NAME = "name";
    public static final String TABLE_AB_ACCOUNT_AMOUNT = "amount";
    public static final String TABLE_AB_RECORD_TYPE = "type_value";
    public static final String TABLE_AB_RECORD_OWNER = "owner";
    public static final String TABLE_AB_RECORD_CATEGORYLV1 = "category_lv1";
    public static final String TABLE_AB_RECORD_CATEGORYLV2 = "category_lv2";
    public static final String TABLE_AB_RECORD_TRANSFEROUT = "transfer_out";
    public static final String TABLE_AB_RECORD_TRANSFERIN = "transfer_in";
    public static final String TABLE_AB_RECORD_ACCOUNT = "account";
    public static final String TABLE_AB_RECORD_DATE = "value_date";
    public static final String TABLE_AB_RECORD_AMOUNT = "amount";
    public static final String TABLE_AB_RECORD_REMARK = "remark";
}
