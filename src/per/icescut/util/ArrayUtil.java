package per.icescut.util;

/**
 * ������ĳ��ò���
 * @author Alan Liang
 *
 */
public final class ArrayUtil {
    public static int IndexOfArray(String[] array, String source) {
	for (int i = 0; i < array.length; i++) {
	    if(array[i].equals(source)) return i;
	}
	return -1;
    }
}
