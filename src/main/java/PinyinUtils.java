
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 *
 * Created by zengxm on 2014/12/4.
 */
public class PinyinUtils {
    HanyuPinyinOutputFormat format = null;
    public static enum Type {
        UPPERCASE,              //全部大写
        LOWERCASE,              //全部小写
        FIRSTUPPER,              //首字母大写
        ONLYFIRSTCHAR    //只有首字母--大写
    }
    static PinyinUtils record = new PinyinUtils();
    public PinyinUtils(){
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }
    //首字母大写    明天--Ming tian
    public static String toPinYinFirstUpper(String str,String spera) {
        String pinYin;
        try {
            pinYin = record.toPinYin(str, spera, Type.FIRSTUPPER);
            if("0123456789".contains(pinYin)) {
                pinYin = numBerToPinyin(pinYin);
            }
            return pinYin;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            return "";
        }
    }
    //全部大写        明天--MINGTIAN
    public static String toPinYin(String str,String spera){
        String pinYin;
        try {
            pinYin = record.toPinYin(str, spera, Type.UPPERCASE);
            return pinYin;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            return "";
        }
    }
    //只包含首字母，大写    明天 --MT
    public static String OnlyFirstChar_Upper(String str){
        String pinYin;
        try {
            pinYin = record.toPinYin(str, " ", Type.FIRSTUPPER);
            if(pinYin.length() == 0) {
                return "";
            }
            String[] array = pinYin.split(" ");
            String firstchar = "";
            for(String ste : array) {
                if(ste.length() >0) {
                    firstchar  +=  ste.substring(0,1).toUpperCase();
                }
            }
            return firstchar;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            return "";
        }
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
     * 如： 明天 转换成 MINGTIAN
     * @param str
     * @param spera
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public  String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
        if(str == null || str.trim().length()==0) {
            return "";
        }
        if(type == Type.UPPERCASE) {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }else {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        String py = "";
        String temp = "";
        String[] t;
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if((int)c <= 128) {
                py += c;
            }else{
                t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if(t == null) {
                    py += c;
                }else{
                    temp = t[0];
                    if(type == Type.FIRSTUPPER) {
                        temp = t[0].toUpperCase().charAt(0)+temp.substring(1);
                    }
                    py += temp+(i==str.length()-1?"":spera);
                }
            }
        }

        return py.trim();
    }

    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    //数字转换成对应拼音
    public static String numBerToPinyin(String str) {
        String pinyin = null;
        switch (str) {
            case "0":
                pinyin = "Z";
                break;
            case "1":
                pinyin = "Y";
                break;
            case "2":
                pinyin = "E";
                break;
            case "3":
                pinyin = "S";
                break;
            case "4":
                pinyin = "S";
                break;
            case "5":
                pinyin = "W";
                break;
            case "6":
                pinyin = "L";
                break;
            case "7":
                pinyin = "Q";
                break;
            case "8":
                pinyin = "B";
                break;
            case "9":
                pinyin = "J";
                break;
        }
        return pinyin;

    }
}