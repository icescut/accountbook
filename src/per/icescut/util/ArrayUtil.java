package per.icescut.util;

/**
 * 对数组的常用操作
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
