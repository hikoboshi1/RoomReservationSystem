package jp.co.dcf.rrs.domain.constants;
import java.time.format.DateTimeFormatter;
public final class FormatConstants {
    private FormatConstants() {}
    
    public static final DateTimeFormatter JS_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter JS_TIME = DateTimeFormatter.ofPattern("hh:mm");
}