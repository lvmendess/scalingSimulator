import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class EscritorTxt {
    public void write(ArrayList<String> result, String filename/*int n*/){
        try {
            filename = filename.replace(".txt", "");
            File file = new File("testes\\" + filename + "-RESULTADO.txt");
            
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            StringBuilder str = new StringBuilder();
            for (String string : result) {
                str.append(string+"\n");
            }
            String w = str.toString();
            bufferedWriter.write(w, 0, w.length() - 1);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
