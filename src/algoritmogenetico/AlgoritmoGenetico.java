package algoritmogenetico;

import fitness.Fitness;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGenetico {

    private final int dimensao;
    //private List<double[]> cromossomos;
    private final List<Cromossomo> cromossomos;
    private final double min;
    private final double max;
    private final int tamanhoPopulacao;
    private double melhorSolucao;
    private Cromossomo cromossomoEscolhido;
    private final int funcaoFitness;
    private int count = 0;

    public AlgoritmoGenetico(int dimensao, double min, double max, int funcaoFitness) {
        this.dimensao = dimensao;
        tamanhoPopulacao = 200;
        cromossomos = new ArrayList<>(tamanhoPopulacao);
        this.min = min;
        this.max = max;
        melhorSolucao = 12345.12345;
        this.funcaoFitness = funcaoFitness;
    }

    public static void abc(String[] args) {
    }

    public void inicializaCromossomo() {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            Cromossomo c = new Cromossomo(dimensao, funcaoFitness);
            c.inicializarGenes(min, max);
            cromossomos.add(c);
        }

    }

    public void executaCrossover() {
        ArrayList<Cromossomo> novaPopulacao = new ArrayList<>(tamanhoPopulacao);
        int probCruzamento = 0 + (int) (Math.random() * (100 - 0));
        for (int i = 0; i < tamanhoPopulacao - 1; i = i + 2) {
            int pai1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            int pai2 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            Cromossomo filho1 = new Cromossomo(dimensao, funcaoFitness);
            Cromossomo filho2 = new Cromossomo(dimensao, funcaoFitness);
            if (probCruzamento > 25) {
                double[][] genesFilhos = Cruzamento.mediaPonderada(cromossomos.get(pai1).getGenes(), cromossomos.get(pai2).getGenes(), 0.3);
                filho1.setGenes(genesFilhos[0]);
                filho2.setGenes(genesFilhos[1]);
            } else {
                filho1.setGenes(cromossomos.get(pai1).getGenes());
                filho2.setGenes(cromossomos.get(pai2).getGenes());
            }
            novaPopulacao.add(filho1);
            novaPopulacao.add(filho2);
        }
        if (tamanhoPopulacao % 2 != 0) {
            int individuoAleatorio = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            novaPopulacao.add(cromossomos.get(individuoAleatorio));
        }
        for (Cromossomo novoCromossomo : novaPopulacao) {
            cromossomos.add(novoCromossomo);
        }
        //}
    }

    public void executaMutacao() {
        double probMin = getMenorGene();
        double probMax = getMaxGene();
        if (probMin == probMax) {
            probMin = min;
            probMax = max;

        }
        int qtde = (int) (0.3 * (tamanhoPopulacao));
        // System.out.println("!!!!!!!!!!!!!!!!!" + qtde + "!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < 0.8 * qtde; i++) {
            int probMutacao = 0 + (int) (Math.random() * (100 - 0));
            if (probMutacao > 25) {
                int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
                new Mutacao(cromossomos.get(rand1).getGenes()).insereRuido(probMin, probMax);
            }
        }

        for (int i = 0; i < 0.2*qtde; i++) {
            int probMutacao = 0 + (int) (Math.random() * (100 - 0));
            if (probMutacao > 25) {
                int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
                new Mutacao(cromossomos.get(rand1).getGenes()).insereRuido(min, max);
            }
        }
        double alpha = 0.1;
        if (cromossomoEscolhido != null) {
            alpha = 1 / cromossomoEscolhido.getFitness();
        }

//        for (int i = 0; i < 0.1*qtde; i++) {
//            int probMutacao = 0 + (int) (Math.random() * (100 - 0));
//            if (probMutacao > 25) {
//                int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
//                new Mutacao(cromossomos.get(rand1).getGenes()).mutacaoUnidimensional(0.1);
//            }
//        }
        if (cromossomoEscolhido != null) {
            for (int i = 0; i <  0.1*qtde; i++) {
                int probMutacao = 0 + (int) (Math.random() * (100 - 0));
                if (probMutacao > 25) {
                    int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
                    new Mutacao(cromossomos.get(rand1).getGenes()).mutacaoUnidimensional(0.01, cromossomoEscolhido.getGenes());
                }
            }
        }
    }

    public void executaSelecao() {
        ArrayList<Cromossomo> popSelecionada = new ArrayList<>();
        popSelecionada.addAll(Selecao.Elitismo(cromossomos, tamanhoPopulacao));
        //popSelecionada.addAll(Selecao.roleta(cromossomos, tamanhoPopulacao));
        cromossomos.clear();
        cromossomos.addAll(popSelecionada);
    }

    public double avaliaSolucao() {
        double solucaoAtual;
        double bestFit = -888888;
//        for (Cromossomo cromossomo : cromossomos) {
//            solucaoAtual = 1 / cromossomo.getFitness();
//            if (solucaoAtual > bestFit) {
//                bestFit = solucaoAtual;
//                cromossomoEscolhido = cromossomo;
//            }
//        }
        cromossomoEscolhido = cromossomos.get(0);
        count++;
        //System.out.println("Melhor solucao ate agora: " + melhorSolucao );
        System.out.println(count + " Melhor solucao ate agora: " + cromossomoEscolhido.getFitness() + " " + Arrays.toString(cromossomoEscolhido.getGenes()));
        return cromossomoEscolhido.getFitness();
    }

    public double[] getMelhorCromossomo() {
        return cromossomoEscolhido.getGenes();
    }

    private double getMenorGene() {
        if (cromossomoEscolhido == null) {
            return min;
        }
        double menor = 10000;
        for (double gene : cromossomoEscolhido.getGenes()) {
            if (menor > gene) {
                menor = gene;
            }
        }
        return menor;
    }

    private double getMaxGene() {
        if (cromossomoEscolhido == null) {
            return max;
        }
        double maior = -11111;
        for (double gene : cromossomoEscolhido.getGenes()) {
            if (maior < gene) {
                maior = gene;
            }
        }
        return maior;
    }
}
