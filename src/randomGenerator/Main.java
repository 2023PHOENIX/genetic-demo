package randomGenerator;

import javax.sound.midi.Soundbank;
import java.util.*;

class FitnessScore {
    public
    ArrayList<Integer> chromosome;
    int matches;

    FitnessScore(ArrayList<Integer> chromosome, int matches) {
        this.chromosome = chromosome;
        this.matches = matches;
    }
}

class SortByMatches implements Comparator<FitnessScore> {

    public int compare(FitnessScore o1, FitnessScore o2) {
        return o2.matches - o1.matches;
    }
}

public class Main {

    static int PASSCODE = 10;
    static int PASSCODE_LOWER_BOUND = 0;
    static int PASSCODE_UPPER_BOUND = 9;
    static int POPULATION_SIZE = 10;
    static int NO_OF_PARENT = 5;
    static int ELITE_SIZE = 2;
//    static int secret_password = 2438;


    public static void main(String[] args) {
//        System.out.println("HELLO");

        ArrayList<Integer> secret_password = SecretPasswordGenerator();
//        System.out.println(secret_password);
        ArrayList<ArrayList<Integer>> population = PopulationGenerator();

//       for(int i = 0; i<population.size();i++){
//           System.out.println(population.get(i));
//       }
        // TODO: FitnessScore

        // TODO : parentList
//        for(FitnessScore f : parentList){
//            System.out.println(f.chromosome +" ->" +  f.matches);
//        }
//         FitnessScore p1 = parentList.get(0);
//         FitnessScore p2 = parentList.get(1);
//        System.out.println("parent "+ p1.chromosome);
//        System.out.println("parent :" + p2.chromosome);
//        ArrayList<Integer> child = MultiPointCrossOver(p1,p2);   TODO: crossover
//        System.out.println("bacha : " + child);


//        ArrayList<ArrayList<Integer>> childrens = CreateChildren(parentList);  TODO:CHILD

//        for(ArrayList<Integer> a : childrens){
//
//            System.out.println(a);
//        }

//        ArrayList<ArrayList<Integer>> m = Mutation(childrens);    TODO: MUTATION


        ArrayList<ArrayList<Integer>> success = new ArrayList<>();

        int generation = 0;
//        int T0 = (int) System.nanoTime();
        int iteration = 0;

        while (true) {
            ArrayList<FitnessScore> ft = FitnessScore(population, secret_password);


            for (int i = 0; i < 10; i++) {
                success.add(ft.get(i).chromosome);


            }

            for (int i = 0; i < 10; i++) {
                if (ft.get(i).matches == 10) {
                    System.out.println("discovered password " + ft.get(i).chromosome);
                    System.out.println("cracked generation " + generation);
                }
            }

            ArrayList<FitnessScore> parentList = SelectParent(ft); // TODO: best top 5 parent who have best fitness score

//            ======>
            FitnessScore p1 = parentList.get(0);
            FitnessScore p2 = parentList.get(1);

            ArrayList<Integer> child = MultiPointCrossOver(p1, p2);


            ArrayList<ArrayList<Integer>> childrens = CreateChildren(parentList);  // TODO:CHILD
            ArrayList<ArrayList<Integer>> m = Mutation(childrens);  //TODO: MUTATION
            if (generation >= 1000) {
                break;
            }
            generation++;
        }


    }

    static ArrayList<ArrayList<Integer>> Mutation(ArrayList<ArrayList<Integer>> childrens) {

        for (ArrayList<Integer> c : childrens) {
            System.out.println(c);

        }

        System.out.println("-----------------");
        for (int i = 0; i < childrens.size(); i++) {
            int x = (Math.random() > 0.1) ? 1 : 0;   // TODO: mutation will happen only 9 out 10 time
            if (x == 1) {
                int mutation_pos = (int) (Math.random() * PASSCODE);
                Random rand = new Random();
                int xy = rand.nextInt(9);
                int y = rand.nextInt(9);
                int z = (xy + y) / 2;

                childrens.get(i).set(mutation_pos, z);
            }
        }

//        for(ArrayList<Integer> c : childrens){
//            System.out.println(c);
//
//        }
        return childrens;
    }

    static ArrayList<ArrayList<Integer>> CreateChildren(ArrayList<FitnessScore> parentList) {

        ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();

        int no_of_newchild = 8;  // TODO: change the number of parent size

        for (int i = 0; i < ELITE_SIZE; i++) {
            children.add(parentList.get(i).chromosome);
        }


        for (int i = 0; i < no_of_newchild; i++) {
            FitnessScore p1 = parentList.get((int) (Math.random() * parentList.size()));
            FitnessScore p2 = parentList.get((int) (Math.random() * parentList.size()));


            ArrayList<Integer> c = MultiPointCrossOver(p1, p2);

//            System.out.println(p1.chromosome + "\t" + p2.chromosome +"\t" +c);
            children.add(c);
        }
        return children;
    }

    static ArrayList<Integer> MultiPointCrossOver(FitnessScore p1, FitnessScore p2) {
        ArrayList<Integer> child = new ArrayList<Integer>();
        int a = (int) (Math.random() * 2);
        int b = (int) (Math.random() * 8);

        int L_Limit = Math.min(a, b);
        int R_Limit = Math.max(a, b);

        for (int i = 0; i < p1.chromosome.size(); i++) {
            if (L_Limit <= i && i <= R_Limit) {
                child.add(p1.chromosome.get(i));
            } else {
                child.add(p2.chromosome.get(i));
            }
        }
        return child;
    }

    static ArrayList<Integer> SecretPasswordGenerator() {
        ArrayList<Integer> secret_password = new ArrayList<Integer>();

        for (int i = 0; i < POPULATION_SIZE; i++) {

            int x = (int) (Math.random() * 10);
            secret_password.add(x);
        }

        return secret_password;

    }


    static ArrayList<ArrayList<Integer>> PopulationGenerator() {
        ArrayList<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            ArrayList<Integer> Chromosome = new ArrayList<>();
            for (int j = 0; j < PASSCODE; j++) {
                int rnd = (int) (Math.random() * 10);
                Chromosome.add(rnd);
            }

            population.add(Chromosome);
        }
        return population;
    }

    static ArrayList<FitnessScore> FitnessScore(ArrayList<ArrayList<Integer>> population, ArrayList<Integer> secret_password) {

        ArrayList<FitnessScore> ft = new ArrayList<FitnessScore>();


        for (int i = 0; i < population.size(); i++) {
            ArrayList<Integer> chromo = population.get(i);

            int Matches = 0;
            for (int j = 0; j < PASSCODE; j++) {
                if (secret_password.get(j) == chromo.get(j)) {
                    Matches = Matches + 1;
                }
            }
            FitnessScore f = new FitnessScore(chromo, Matches);


            ft.add(f);
        }

        return ft;
    }


    static ArrayList<FitnessScore> SelectParent(ArrayList<FitnessScore> ft) {
        Collections.sort(ft, new SortByMatches());

        ArrayList<FitnessScore> parentList = new ArrayList<>();

        for (int i = 0; i < NO_OF_PARENT; i++) {
            parentList.add(ft.get(i));
        }

        return parentList;
    }


}
