import java.util.ArrayList;

public class SJF {
    ArrayList<Processo> executados;
    ArrayList<Processo> execucao;
    ArrayList<Processo> comparacao;

    public SJF(ArrayList<Processo> processes){
        this.execucao = processes;
        executados = new ArrayList<Processo>();
        comparacao = new ArrayList<Processo>();
    }

    public ArrayList<Processo> linhaDeExecucao(){
        int clock = 0;
        System.out.print("SJF: ");
        while(!execucao.isEmpty()){
            for (Processo processo : execucao) {
                if (processo.getTempoChegada() == clock) { // se o processo chega no clock atual, ele vai pro escalonamento
                    comparacao.add(processo);
                    if (comparacao.size()>1) {
                        for (int i = comparacao.size() - 1; i > 0; i--) { // insertion sort
                            Processo subprocesso = comparacao.get(i - 1);
                            if (subprocesso.getTempoRestante() == subprocesso.getTempoExecucao()) {
                                if (subprocesso.getTempoRestante() > processo.getTempoRestante()) {
                                    comparacao.remove(processo);
                                    comparacao.add(i - 1, processo);
                                }
                            }
                        }                        
                    }
                }
            }
            if (!comparacao.isEmpty()) {
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