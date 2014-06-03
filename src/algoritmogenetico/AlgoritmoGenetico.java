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
    double beta = 0.9;
    double gama = 0.9;
    int indiceConvergencia = 0;
    double fitnessAtual;
    boolean enable;
    double media;
    double alpha = 0.1;

    public AlgoritmoGenetico(int dimensao, double min, double max, int funcaoFitness) {
        this.dimensao = dimensao;
        tamanhoPopulacao = 50;
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
        getMediaCromossomo();
        double probMin = getMenorGene();
        double probMax = getMaxGene();
        if (probMin == probMax) {
            probMin = min;
            probMax = max;

        }

        if (beta < 0.6 && gama >= 0.7) {
            beta = beta + 0.1;
            gama = 0.1;
            indiceConvergencia = 0;
        }
        if (indiceConvergencia >= 25 && gama >= 0.7)  {
            gama = gama + 0.1;
            //if(gama >= 0.3){
              //  gama = 0.1;
            //}
            indiceConvergencia = 0;
        }

        if (beta >= 0.6 ) {
            beta = 0.3;
        }
        int qtde = (int) (beta * (tamanhoPopulacao));
        // System.out.println("!!!!!!!!!!!!!!!!!" + qtde + "!!!!!!!!!!!!!!!!!!!!!!!");

        if (count %500 == 0 && gama <= 7.0) {
            gama = gama + 0.1;
        }
        for (int i = 0; i < gama * qtde; i++) {
            int probMutacao = 0 + (int) (Math.random() * (100 - 0));
            if (probMutacao > 25) {
                int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
                new Mutacao(cromossomos.get(rand1).getGenes()).insereRuido(probMin, probMax);
            }
        }
        if (beta == 0.4 && gama <= 0.5) {
            enable = true;
            if (alpha == 0.1) {
                alpha = alpha +0.2;
                
            }
        } else {
            enable = false;
            alpha = 0.1;
        }
        if (enable && dimensao > 10) {

            for (int i = 0; i < 0.1 * qtde; i++) {
                int probMutacao = 0 + (int) (Math.random() * (100 - 0));
                if (probMutacao > 25) {
                    int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
                    new Mutacao(cromossomos.get(rand1).getGenes()).insereRuidoGeral(media - 0.5, media + 0.5);
                }
            }
        }

        for (int i = 0; i < 0.1 * qtde; i++) {
            int probMutacao = 0 + (int) (Math.random() * (100 - 0));
            if (probMutacao > 25) {
                int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
                new Mutacao(cromossomos.get(rand1).getGenes()).insereRuido(min, max);
            }
        }

//        if (cromossomoEscolhido != null) {
//            alpha = 1 / cromossomoEscolhido.getFitness();
//        }
//        for (int i = 0; i < 0.1*qtde; i++) {
//            int probMutacao = 0 + (int) (Math.random() * (100 - 0));
//            if (probMutacao > 25) {
//                int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
//                new Mutacao(cromossomos.get(rand1).getGenes()).mutacaoUnidimensional(0.1);
//            }
//        }
        if (cromossomoEscolhido != null) {
            for (int i = 0; i < alpha * qtde; i++) {
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
        if (cromossomoEscolhido.getFitness() != fitnessAtual) {
            fitnessAtual = cromossomoEscolhido.getFitness();
        } else {
            indiceConvergencia++;
        }
        count++;
        //System.out.println("Melhor solucao ate agora: " + melhorSolucao );
        System.out.println(count + " " + beta + " " + gama + " Solucao: " + cromossomoEscolhido.getFitness() + " " + Arrays.toString(cromossomoEscolhido.getGenes()));
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

    public double getMediaCromossomo() {
        if(count == 0){
            return 0;
        }
        media = 0;
        for (double gene : cromossomoEscolhido.getGenes()) {
            media += gene;
        }
        return media / cromossomoEscolhido.getGenes().length;
    }
}
