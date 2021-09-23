import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {




    //文章读取
    public static ArrayList<String> readWords(String words) throws IOException {
        File file = new File(words);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader fR = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(fR);
        ArrayList<String> strs = new ArrayList<>();
        String str;
        while ((str = br.readLine()) != null){
            strs.add(str);
        }
        br.close();
        fR.close();
        return strs;
    }

    public static ArrayList<String> readWordsBad(String words) throws IOException {
        File file = new File(words);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader fR = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(fR);
        ArrayList<String> strs = new ArrayList<>();
        String str;
        while ((str = br.readLine()) != null){
            strs.add(str);
            strs.add(PinyinUtils.toPinYin(str,""));
            strs.add(PinyinUtils.getFirstSpell(str));
            strs.add(PinyinUtils.OnlyFirstChar_Upper(str));
            strs.add(PinyinUtils.toPinYinFirstUpper(str,""));
        }
        br.close();
        fR.close();
        return strs;
    }

    //向文件写内容
    public static void writeAns(String desc,ArrayList<String> ans) throws IOException {
        File file=new File(desc);
        FileOutputStream fos=new FileOutputStream(file);
        OutputStreamWriter ow =new OutputStreamWriter(fos,StandardCharsets.UTF_8);
        BufferedWriter bw=new BufferedWriter(ow);
        bw.write("Total: "+ans.size());
        bw.newLine();
        for (String str : ans) {
            bw.write(str);
            bw.newLine();
        }
        bw.close();
        ow.close();
    }


}
