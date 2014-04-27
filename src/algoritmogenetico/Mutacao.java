package algoritmogenetico;

public class Mutacao {

    private final double cromossomo[];

    public Mutacao(double[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    public void trocaPosicao() {
        int dimensao = this.cromossomo.length;
        int rand1 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        int rand2 = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        double aux = cromossomo[rand1];
        cromossomo[rand1] = cromossomo[rand2];
        cromossomo[rand2] = aux;
        System.out.println(rand1 + " : " + rand2);
    }

    public void insereRuido(double min, double max) {
        int dimensao = this.cromossomo.length;
        int rand = 0 + (int) (Math.random() * (((dimensao - 1) - 0) + 1));
        double ruido = min + (Math.random() * ((max - (min))));
        cromossomo[rand] = Math.round(ruido * 100.0) / 100.0;
    }

}
