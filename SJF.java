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
        while(!execucao.isEmpty()){
            for (Processo processo : execucao) {
                if (processo.getTempoChegada() == clock) { // se o processo chega no clock atual, ele vai pro escalonamento
                    if (!comparacao.isEmpty()) {
                        for (Processo subprocesso : comparacao) {
                            if(subprocesso.getTempoRestante() == subprocesso.getTempoExecucao()){
                                if (subprocesso.getTempoRestante() > processo.getTempoRestante()) {
                                    comparacao.add(comparacao.indexOf(subprocesso), processo);
                                    break;
                                }
                            } else {
                                comparacao.add(processo);
                                break;
                            }
                        }
                    } else {
                        comparacao.add(processo);
                    }
                    //comparacao.add(processo);
                }
            }
            if (!comparacao.isEmpty()) {
                Processo processoMaisAntigo = comparacao.getFirst();
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
        return executados;
    }

}

