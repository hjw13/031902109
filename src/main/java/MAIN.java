import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MAIN {
    public static void main(String[] args) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        //从命令行读取参数
        String wordsTxt = args[0];
        String orgTxt = args[1];
        String ansTxt = args[2];

        //读入敏感词文件
        ArrayList<String> word = IOUtils.readWordsBad(wordsTxt);
        //初始化AC自动机
        acMachine ac = new acMachine();
        ac.acMachineBuilder(word);


        //读入待检测文件
        ArrayList<String> txt = IOUtils.readWords(orgTxt);
        int line=1;
        //生成答案集合result
        for (String eachTxt : txt) {
            ArrayList<String> ans = new ArrayList<>();
            ans = ac.findAll(eachTxt);
            System.out.println(ans.size());
            for (String eachAns : ans) {
                StringBuilder sb = new StringBuilder();
                sb.append("Line").append(line).append(": <").append(eachAns).append(">").append(eachAns);
                result.add(sb.toString());
            }
            line++;

        }


        //输出文件
        IOUtils.writeAns(ansTxt, result);


    }
}
