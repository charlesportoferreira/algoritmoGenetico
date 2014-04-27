package algoritmogenetico;

import fitness.Fitness;
import java.util.List;

public class Selecao {

    List<double[]> cromossomos;

    public Selecao(List<double[]> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public double[] roleta() {
        int dimensao = cromossomos.size();
        double fitnessTotal = 0;
        double[] fitness = new double[dimensao];

        for (int i = 0; i < dimensao; i++) {
            fitnessTotal += 1 / (new Fitness(cromossomos.get(i), 5).getSphereEvaluator());
            fitness[i] = fitnessTotal;
        }

        double rand = 0 + (Math.random() * ((fitnessTotal - 0)));
        for (int i = 0; i < dimensao; i++) {
            if (rand <= fitness[i]) {
                return cromossomos.get(i);
            }
        }

        throw new RuntimeException("Nao foi achado o fitness na roleta");

    }

    public double[] Torneio() {
        int dimensao = cromossomos.size();
        int rand1 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        int rand2 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        double fit1 = 1 / new Fitness(cromossomos.get(rand1), 5).getSphereEvaluator();
        fit1  = Math.round(fit1  * 100.0) / 100.0;
        double fit2 = 1 / new Fitness(cromossomos.get(rand2), 5).getSphereEvaluator();
        fit2  = Math.round(fit2  * 100.0) / 100.0;
        if (fit1 > fit2) {
           // System.out.println(fit1 + " : " + fit2);
            return cromossomos.get(rand1);
        }
        return cromossomos.get(rand2);
    }

    public double[] Elitismo() {
        double bestFitness = 0;
        double fitnessAtual;
        int posicaoMelhorFitness = 0;
        for (int i = 0; i < cromossomos.size(); i++) {
            fitnessAtual = 1 / new Fitness(cromossomos.get(i), 5).getSphereEvaluator();
            if (fitnessAtual > bestFitness) {
                bestFitness = fitnessAtual;
                posicaoMelhorFitness = i;
            }
        }
        return cromossomos.get(posicaoMelhorFitness);
    }

}
