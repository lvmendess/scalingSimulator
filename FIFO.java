import java.util.ArrayList;

public class FIFO {
    ArrayList<Processo> executados;
    ArrayList<Processo> execucao;
    ArrayList<Processo> comparacao;

    public FIFO(ArrayList<Processo> processes){
        this.execucao = processes;
        executados = new ArrayList<Processo>();
        comparacao = new ArrayList<Processo>();
    }

    public ArrayList<Processo> linhaDeExecucao(){
        int clock = 0;
        System.out.print("FIFO: ");
        while(!execucao.isEmpty()){
            for (Processo processo : execucao) {
                if (processo.getTempoChegada() == clock) { // se o processo chega no clock atual, ele vai pro escalonamento
                    comparacao.add(processo);
                }
            }
            if (!comparacao.isEmpty()){
                Processo processoMaisAntigo = comparacao.get(0);
                for (Processo processo : comparacao) {
                    if (processo == processoMaisAntigo) {
                        processo.decreaseRestante();
                    } else {
                        processo.increaseEspera();
                        processo.increaseResposta();
                    }
                }
                if (processoMaisAntigo.getTempoRestante() == 0) {
                    executados.add(processoMaisAntigo);
                    comparacao.remove(processoMaisAntigo);
                    execucao.remove(processoMaisAntigo);
                }
            }
           clock++;
        }
        System.out.println(clock);
        return executados;
    }

}
