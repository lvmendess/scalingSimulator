import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class Main{
    public static void main(String[] args) throws FileNotFoundException, IOException, CloneNotSupportedException{
        File diretorio = new File("testes\\");
        File[] arquivos = diretorio.listFiles();
        LeitorTxt leitorTxt;
        EscritorTxt escritor = new EscritorTxt();
        ArrayList<Processo> processosFIFO = new ArrayList<>();
        ArrayList<Processo> processosSJF = new ArrayList<>();
        ArrayList<Processo> processosSRT = new ArrayList<>();
        ArrayList<Processo> processosRR = new ArrayList<>();
        ArrayList<String> resultados = new ArrayList<>();
        
        for (File arquivo : arquivos) {
            String name = arquivo.getName();
            System.out.println(arquivo.getName());
            leitorTxt = new LeitorTxt(arquivo.getAbsolutePath());
            String[] linhaFatiada = null;
            String[] quantum = leitorTxt.proximaLinha();

            while ((linhaFatiada = leitorTxt.proximaLinha()) != null) {
                processosFIFO.add(new Processo(Integer.parseInt(linhaFatiada[0]), Integer.parseInt(linhaFatiada[1])));
                processosSJF.add(new Processo(Integer.parseInt(linhaFatiada[0]), Integer.parseInt(linhaFatiada[1])));
                processosSRT.add(new Processo(Integer.parseInt(linhaFatiada[0]), Integer.parseInt(linhaFatiada[1])));
                processosRR.add(new Processo(Integer.parseInt(linhaFatiada[0]), Integer.parseInt(linhaFatiada[1])));
            }
            ArrayList<ArrayList<Processo>> algoritmos = new ArrayList<>();
            FIFO fifo = new FIFO(processosFIFO);
            algoritmos.add(fifo.linhaDeExecucao());
            SJF sjf = new SJF(processosSJF);
            algoritmos.add(sjf.linhaDeExecucao());
            SRT srt = new SRT(processosSRT);
            algoritmos.add(srt.linhaDeExecucao());
            RR rr = new RR(processosRR, Integer.parseInt(quantum[0]));
            algoritmos.add(rr.linhaDeExecucao());
            try {
                for (ArrayList<Processo> listaProcessos : algoritmos) {
                    float tempoResposta = 0, tempoEspera = 0, turnaround = 0;
                    for (Processo processo : listaProcessos) {
                        tempoResposta += processo.getTempoResposta();
                        tempoEspera += processo.getTempoEspera();
                        turnaround += processo.getTempoExecucao() + processo.getTempoEspera();
                    }
                    int totalProcessos = listaProcessos.size();
                    tempoResposta /= totalProcessos;
                    tempoEspera /= totalProcessos;
                    turnaround /= totalProcessos;
                    resultados.add(String.format("%.3f %.3f %.3f", tempoResposta, tempoEspera, turnaround));
                }
                escritor.write(resultados, name);
            }catch(NullPointerException e){
                System.out.println(-1);
            }
            resultados.clear();
        }
    }
}
