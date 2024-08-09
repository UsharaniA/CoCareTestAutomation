/**
 * 
 */
package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author usharani A
 *
 */
public class DateTimeUtils {

	public static String getTimeStamp(){
		return  new SimpleDateFormat("MM-dd-yyyy_HHmmss").format(new Date());
	}
	public static String getTimeStampMiliSec(){
		return  new SimpleDateFormat("HHmmss").format(new Date());
	}
	public static String getDate(){
		return  new SimpleDateFormat("MM-dd-yyyy").format(new Date());
	}
}
