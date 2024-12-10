import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RR {
    ArrayList<Processo> executados;
    ArrayList<Processo> execucao;
    Queue<Processo> comparacao;
    int quantumSize;
    int quantum;
    
    public RR(ArrayList<Processo> processes, int quantumSize){
        this.execucao = processes;
        executados = new ArrayList<Processo>();
        comparacao = new LinkedList<>();
        this.quantumSize = quantumSize;
        this.quantum = quantumSize;
    }

    public int updateQuantum(){
        if (quantum == 0) {
            quantum = quantumSize;
        } else {
            quantum--;
        }
        return quantum;
    }

    public ArrayList<Processo> linhaDeExecucao(){
        int clock = 0;
        while (!execucao.isEmpty()) {
            for (Processo processo : execucao) {
                if (processo.getTempoChegada() == clock) {
                    comparacao.add(processo);
                }
            }
            if (!comparacao.isEmpty()) {
                for (Processo processo : comparacao) {
                    if (processo == comparacao.peek()) {
                        processo.decreaseRestante();
                    } else {
                        processo.increaseEspera();
                        if (processo.getTempoRestante() == processo.getTempoExecucao()) {
                            processo.increaseResposta();
                        }
                    }
                }
                if (comparacao.peek().getTempoRestante() == 0) {
                    executados.add(comparacao.peek());
                    execucao.remove(comparacao.peek());
                    comparacao.remove();
                    quantum = quantumSize;
                } else if (updateQuantum() == 0) {
                    Processo aux = comparacao.remove();
                    comparacao.add(aux);
                }
            }
            clock++;
        }
        return executados;
    }
}