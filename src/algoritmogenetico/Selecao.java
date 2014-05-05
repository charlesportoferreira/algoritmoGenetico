package algoritmogenetico;

import fitness.Fitness;
import java.util.ArrayList;
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

        //pegar o primeiro fitness como menor temporariamente
        double menorFitness = Math.round(1 / (new Fitness(cromossomos.get(0)).getSphereEvaluator()) * 100.0) / 100.0;
        for (int i = 0; i < dimensao; i++) {
            double fitnessAtual = Math.round(1 / (new Fitness(cromossomos.get(i)).getSphereEvaluator()) * 100.0) / 100.0;
            if (fitnessAtual < menorFitness) {
                menorFitness = fitnessAtual;
            }
            fitness[i] = fitnessAtual;
        }

        //faz o escalonamento
        for (int i = 0; i < dimensao; i++) {
            fitness[i] = fitness[i] + menorFitness;
            fitnessTotal += fitness[i];
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
        double fit1 = 1 / new Fitness(cromossomos.get(rand1)).getSphereEvaluator();
        fit1 = Math.round(fit1 * 100.0) / 100.0;
        double fit2 = 1 / new Fitness(cromossomos.get(rand2)).getSphereEvaluator();
        fit2 = Math.round(fit2 * 100.0) / 100.0;
        if (fit1 > fit2) {
            // System.out.println(fit1 + " : " + fit2);
            return cromossomos.get(rand1);
        }
        return cromossomos.get(rand2);
    }

    public List<double[]> Elitismo(int nElementos) {
        List<double[]> cromo = new ArrayList<>(cromossomos.subList(0, cromossomos.size()));
        cromossomos.clear();
        double fitnessAtual;
        double bestFitness;
        int posicaoMelhorFitness = 0;
        for (int j = 0; j < nElementos; j++) {
            //iniciliaza o bestFitness com o primeiro fitness temporariamente
            //bestFitness = 1 / new Fitness(cromo.get(0)).getSphereEvaluator();
            bestFitness = 1 / new Fitness(cromo.get(0)).getGriewankEvaluator();
            posicaoMelhorFitness = 0;
            int i = 0;
            while (i < cromo.size()) {
                //fitnessAtual = 1 / new Fitness(cromo.get(i)).getSphereEvaluator();
                fitnessAtual = 1 / new Fitness(cromo.get(i)).getGriewankEvaluator();
                if (fitnessAtual > bestFitness) {
                    bestFitness = fitnessAtual;
                    posicaoMelhorFitness = i;
                }
                i++;
            }
            cromossomos.add(cromo.get(posicaoMelhorFitness));
            cromo.remove(posicaoMelhorFitness);
        }
        return cromossomos;
    }

    private double min(double[] vetor) {
        double min = vetor[0];
        for (double el : vetor) {
            if (el < min) {
                min = el;
            }
        }
        return min;

    }
}
