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
        float tempoResposta = 0, tempoEspera = 0, turnaround = 0;

        for (File arquivo : arquivos) {
            String name = arquivo.getName();
            System.out.println(arquivo.getName());
            leitorTxt = new LeitorTxt(arquivo.getAbsolutePath());
            String[] linhaFatiada = null;
            String[] quantum = leitorTxt.proximaLinha();

            while ((linhaFatiada = leitorTxt.proximaLinha()) != null) {
                Processo fifo = new Processo(Integer.parseInt(linhaFatiada[0]), Integer.parseInt(linhaFatiada[1]));
                Processo sjf = new Processo(Integer.parseInt(linhaFatiada[0]), Integer.parseInt(linhaFatiada[1]));
                //processos.add(aux);
                processosFIFO.add(fifo);

                processosSJF.add(sjf);
            }
            ArrayList<ArrayList<Processo>> algoritmos = new ArrayList<>();
            //FIFO fifo = new FIFO(processosFIFO);
            //algoritmos.add(fifo.linhaDeExecucao());
            SJF sjf = new SJF(processosSJF);
            algoritmos.add(sjf.linhaDeExecucao());
            // SRT
            // RR
            try {
                for (ArrayList<Processo> listaProcessos : algoritmos) {
                    for (Processo processo : listaProcessos) {
                        tempoResposta += processo.getTempoResposta();
                        tempoEspera += processo.getTempoEspera();
                        turnaround += processo.getTempoExecucao() + processo.getTempoEspera();
                    }
                    int totalProcessos = listaProcessos.size();
                    tempoResposta /= totalProcessos;
                    tempoEspera /= totalProcessos;
                    turnaround /= totalProcessos;
                    System.out.printf("%.3f %.3f %.3f", tempoResposta, tempoEspera, turnaround);
                    System.out.println();
                }
                    
            }catch(NullPointerException e){
                System.out.println(-1);
            }
            //processos.clear();
        }
    }
}
