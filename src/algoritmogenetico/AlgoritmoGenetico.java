package algoritmogenetico;

import fitness.Fitness;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGenetico {

    private final int dimensao;
    private List<double[]> cromossomos;
    private final double min;
    private final double max;
    private final int tamanhoPopulacao;
    private double melhorSolucao;
    private double[] cromossomoEscolhido;

    public AlgoritmoGenetico(int dimensao, double min, double max) {
        this.dimensao = dimensao;
        tamanhoPopulacao = 5;
        cromossomos = new ArrayList<>(tamanhoPopulacao);
        this.min = min;
        this.max = max;
        melhorSolucao = 100000.0;
    }

    public static void abc(String[] args) {
        double valor = .885650224;
        double finalValue = Math.round(valor * 100.0) / 100.0;
        System.out.println(finalValue);
        // System.exit(0);

        double solucao = 2000000;
        AlgoritmoGenetico ag = new AlgoritmoGenetico(5, -5.12, 5.12);
        ag.inicializaCromossomo();
        for (int i = 0; i < 100; i++) {
            ag.executaCrossover();
            ag.executaMutacao();
            ag.executaSelecao();
            solucao = ag.avaliaSolucao();
            if (solucao == 0.0) {
                System.out.println("NAo achei a melhor solucao: " + solucao);
                System.exit(0);
                break;
            }

        }
        System.out.println("NAo achei a melhor solucao: " + solucao);
        System.exit(0);

        // TODO code application logic here
        int rand2 = 1 + (int) (Math.random() * ((3 - 1) + 1));
        double rand = 0 + (Math.random() * ((1 - 0)));
        double ruido = -5.12 + (Math.random() * ((5.12 - (-5.12))));
        System.out.println(ruido);
        System.exit(0);
        double cromossomo1[] = new double[5];
        cromossomo1[0] = 5.5;
        cromossomo1[1] = 6.2;
        cromossomo1[2] = 3.1;
        cromossomo1[3] = 1.7;
        cromossomo1[4] = 9.8;
        new Mutacao(cromossomo1).trocaPosicao();
        System.out.println(Arrays.toString(cromossomo1));

        double cromossomo2[] = new double[5];
        cromossomo2[0] = 3.1;
        cromossomo2[1] = 2.7;
        cromossomo2[2] = 4.2;
        cromossomo2[3] = 1.7;
        cromossomo2[4] = 8.5;
        new Mutacao(cromossomo2).trocaPosicao();
        System.out.println(Arrays.toString(cromossomo2));

        double cromossomo3[] = new double[5];
        cromossomo3[0] = 1.0;
        cromossomo3[1] = 2.0;
        cromossomo3[2] = 1.0;
        cromossomo3[3] = 1.0;
        cromossomo3[4] = 1.0;
        new Mutacao(cromossomo3).trocaPosicao();
        System.out.println(Arrays.toString(cromossomo3));

        List<double[]> crs = new ArrayList<>();
        crs.add(cromossomo1);
        crs.add(cromossomo2);
        crs.add(cromossomo3);

        System.out.println(Arrays.toString(new Selecao(crs).roleta()));
        System.out.println(Arrays.toString(new Selecao(crs).Torneio()));
        // System.out.println(Arrays.toString(new Selecao(crs).Elitismo(10)));
        System.exit(0);

        //double cromossomos[][] = new Cruzamento(cromossomo1,cromossomo2).mediaPonderada(0.3);
        //double cromossomos[][] = new Cruzamento(cromossomo1,cromossomo2).CX(0, 5);
        double cromossomos[][] = new Cruzamento(cromossomo1, cromossomo2).PMX(0, 5);
        for (double[] cr : cromossomos) {
            System.out.println(Arrays.toString(cr));
        }

        System.out.println(new Fitness(cromossomo1).getSphereEvaluator());
        System.out.println(new Fitness(cromossomo2).getSphereEvaluator());

    }

    public void inicializaCromossomo() {

        for (int i = 0; i < tamanhoPopulacao; i++) {
            double[] cr = new double[dimensao];
            for (int j = 0; j < dimensao; j++) {
                double random = min + (Math.random() * ((max - (min))));
                cr[j] = Math.round(random * 100.0) / 100.0;

            }
            cromossomos.add(cr);
        }


    }

    public void executaCrossover() {
        ArrayList<double[]> novaPopulacao = new ArrayList<>(tamanhoPopulacao);
        for (int i = 0; i < tamanhoPopulacao * 2 - 1; i = i + 2) {
            int pai1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            int pai2 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            int corte1 = 0 + (int) (Math.random() * (2 - 0));
            int corte2 = corte1 + (int) (Math.random() * (dimensao - corte1));
            double[][] filhos = new Cruzamento(cromossomos.get(pai1), cromossomos.get(pai2)).CX(2, dimensao / 2);
            novaPopulacao.add(filhos[0]);
            novaPopulacao.add(filhos[1]);
        }
        if (tamanhoPopulacao % 2 != 0) {
            int individuoAleatorio = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
            novaPopulacao.add(cromossomos.get(individuoAleatorio));
        }
        cromossomos = novaPopulacao;
    }

    public void executaMutacao() {
        int rand1 = 0 + (int) (Math.random() * (tamanhoPopulacao - 0));
        new Mutacao(cromossomos.get(rand1)).insereRuido(min, max);

    }

    public void executaSelecao() {
        ArrayList<double[]> popSelecionada = new ArrayList<>();
        
        popSelecionada.addAll(new Selecao(cromossomos).Elitismo(tamanhoPopulacao));
        
        cromossomos = popSelecionada;
    }

    public double avaliaSolucao() {
        double solucaoAtual;
        for (double[] cr : cromossomos) {
            //solucaoAtual = new Fitness(cr).getSphereEvaluator();
            solucaoAtual = new Fitness(cr).getGriewankEvaluator();
            if (solucaoAtual < melhorSolucao) {
                melhorSolucao = solucaoAtual;
                cromossomoEscolhido = cr;
            }
        }
        //System.out.println("Melhor solucao ate agora: " + melhorSolucao );
        System.out.println("Melhor solucao ate agora: " + melhorSolucao + " " + Arrays.toString(cromossomoEscolhido));
        return melhorSolucao;
    }

    public double[] getMelhorCromossomo() {
        return cromossomoEscolhido;
    }

}
