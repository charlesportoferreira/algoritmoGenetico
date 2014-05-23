/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

import java.util.Arrays;

public class Cruzamento {

    private final double pai1[];
    private final double pai2[];
    private final double cromossomos[][];

    public Cruzamento(double[] pai1, double[] pai2) {
        cromossomos = new double[2][pai1.length];
        this.pai1 = pai1;
        this.pai2 = pai2;
    }

    public static double[][] mediaPonderada(double genesPai1[], double genesPai2[], double fatorPonderacao) {
        double cromo[][] = new double[2][];
        double filho1[] = new double[genesPai1.length];
        double filho2[] = new double[genesPai2.length];
        for (int i = 0; i < genesPai1.length; i++) {

            filho1[i] = fatorPonderacao * genesPai1[i] + (1 - fatorPonderacao) * genesPai2[i];
            filho1[i] = Math.round(filho1[i] * 10000.0) / 10000.0;
            filho2[i] = (1 - fatorPonderacao) * genesPai1[i] + fatorPonderacao * genesPai2[i];
            filho2[i] = Math.round(filho2[i] * 10000.0) / 10000.0;
        }
        cromo[0] = filho1;
        cromo[1] = filho2;
        return cromo;
    }

    public double[][] CX(int posicaoCorte, int tamanho) {
        if (posicaoCorte + tamanho > pai1.length) {
            throw new RuntimeException("limites de corte errados: " + posicaoCorte + ":" + tamanho + " " + pai1.length);
        }
        double filho1[] = new double[pai1.length];
        double filho2[] = new double[pai2.length];
        System.arraycopy(pai1, posicaoCorte, filho1, 0, tamanho);
        System.arraycopy(pai2, 0, filho1, tamanho, posicaoCorte);
        System.arraycopy(pai2, posicaoCorte + tamanho, filho1, posicaoCorte + tamanho, pai2.length - (posicaoCorte + tamanho));

        System.arraycopy(pai2, posicaoCorte, filho2, 0, tamanho);
        System.arraycopy(pai1, 0, filho2, tamanho, posicaoCorte);
        System.arraycopy(pai1, posicaoCorte + tamanho, filho2, posicaoCorte + tamanho, pai1.length - (posicaoCorte + tamanho));
        cromossomos[0] = filho1;
        cromossomos[1] = filho2;
        return cromossomos;
    }

    public double[][] PMX(int posicaoCorte1, int posicaoCorte2) {
        if (posicaoCorte1 > pai1.length || posicaoCorte2 > pai2.length || posicaoCorte1 > posicaoCorte2) {
            throw new RuntimeException("limites de corte errados: " + posicaoCorte1 + ":" + posicaoCorte2);
        }
        double filho1[] = new double[pai1.length];
        double filho2[] = new double[pai2.length];
        System.arraycopy(pai1, posicaoCorte1, filho1, posicaoCorte1, posicaoCorte1 + posicaoCorte2);
        System.arraycopy(pai2, 0, filho1, 0, posicaoCorte1);
        System.arraycopy(pai2, posicaoCorte2, filho1, posicaoCorte2, pai1.length - posicaoCorte2);

        System.arraycopy(pai2, posicaoCorte1, filho2, posicaoCorte1, posicaoCorte1 + posicaoCorte2);
        System.arraycopy(pai1, 0, filho2, 0, posicaoCorte1);
        System.arraycopy(pai1, posicaoCorte2, filho2, posicaoCorte2, pai2.length - posicaoCorte2);
        cromossomos[0] = filho1;
        cromossomos[1] = filho2;
        return cromossomos;
    }

    public static double[][] corte(double []p1, double []p2,int posicaoCorte, int tamanho) {
//        if (posicaoCorte + tamanho > p1.length) {
//            throw new RuntimeException("limites de corte errados: " + posicaoCorte + ":" + tamanho + " " + p1.length);
//        }
        double cromo[][] = new double[2][];
        int metade = (int) Math.floor(tamanho / 2);
        double filho1[] = new double[p1.length];
        double filho2[] = new double[p2.length];
        System.arraycopy(p1, 0, filho1, 0, posicaoCorte);
        System.arraycopy(p2, posicaoCorte, filho1, posicaoCorte, tamanho - posicaoCorte);

        System.arraycopy(p2, 0, filho2, 0, posicaoCorte);
        System.arraycopy(p1, posicaoCorte, filho2, posicaoCorte, tamanho - posicaoCorte);
        cromo[0] = filho1;
        cromo[1] = filho2;
//        System.out.println("pai1: " + Arrays.toString(pai1));
//        System.out.println("pai2: " + Arrays.toString(pai2));
//        System.out.println("filho1: " + Arrays.toString(filho1));
//        System.out.println("filho2: " + Arrays.toString(filho2) + "\n");

        return cromo;
    }

}
