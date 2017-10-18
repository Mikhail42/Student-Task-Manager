/**
 * Created by mikhail on 10/10/17.
 */
public class M {

    public static void main(String... args) {
        int[] vacancy = {1695, 1358, 1243};
        System.out.println("\\hline");
        double[] noExp = {91, 71, 82};
        double[] exists = {455, 388, 719};
        for (int i=0; i<3; i++) {
            System.out.printf("%d ", Math.round(exists[i]*100/vacancy[i]));
        }
        System.out.println();
        for (int i=0; i<3; i++) {
            System.out.printf("%.1f ", noExp[i]*100/vacancy[i]);
        }
        System.out.println();

        int[][] from = {
                {80, 75, 65},
                {145, 130, 105},
                {205, 185, 150},
                {270, 240, 190},
                {335, 295, 235}
        };
        double[][] salary = {
                {407, 364, 624 },
                {283, 260, 384},
                {112, 99, 236},
                {29, 32, 80},
                {15, 13, 26}
        };

        System.out.println("\\hline");
        for (int i=0; i<5; i++) {
            for (int j = 0; j < 3; j++) {
                salary[i][j] /= exists[j];
                String s = String.format("& (>%d) %.1f\\%% ",
                        from[i][j],
                        salary[i][j]*100);
                System.out.print(s);
            }
            System.out.print("\\\\ \n");
            System.out.print("\\hline \n");
        }

    }
}
