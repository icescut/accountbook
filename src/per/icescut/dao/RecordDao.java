package per.icescut.dao;

import per.icescut.entry.Record;

public interface RecordDao {
    boolean insertRecord(Record record);
    String[][] queryByLimit(int start, int len);
    int countRecord();
}
