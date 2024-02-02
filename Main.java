package metrohomework;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        DirectedGraph graph = new DirectedGraph<>();
        Scanner scanner = new Scanner(new FileReader("C:/parismetro.txt"));
        String line;
        ArrayList<String> stations = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> tempdirection = new ArrayList<>();
        ArrayList<Object> linenumber = new ArrayList<>();
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            String [] parts = line.split(",");
            String stopid = parts[0];
            String stopname = parts[1];
            int weight = Integer.parseInt(parts[2]);
            int stopsequence = Integer.parseInt(parts[3]);
            int directionid = Integer.parseInt(parts[4]);
            Object whichline = parts[5];
            String routelongname = parts[6];
            String routetype = parts[7];
            if (stations.size() == 0) {
                graph.addVertex(stopname);
                stations.add(stopname);
                times.add(weight);
                tempdirection.add(directionid);
                linenumber.add(whichline);
            }
            else {
                // Hem hattın hem yönün değiştiği noktalara dikkat et.
                Object x =  linenumber.get(linenumber.size() - 1);
                if (x == (Object) whichline) { //aynı hattaysa

                    if (directionid == tempdirection.get(tempdirection.size() - 1)) { //aynı yöndeyse
                        if (!stations.contains(stopname)) { // aynı düğüm yoksa
                        graph.addVertex(stopname);
                        int edgeweight = (weight - times.get(times.size() - 1));
                        graph.addEdge(stopname , stations.get(stations.size() - 1) , edgeweight );
                        stations.add(stopname);
                        times.add(weight);
                        tempdirection.add(directionid);
                        linenumber.add(whichline);
                        }
                        // dönüşte aynı vertexler olduğu için if'in içine girmiyor else açıp vertexi eklemeden tam tersi edge eklemek gerekiyor.
                        else if (stations.contains(stopname)) {
                            int edgeweight2 = (times.get(times.size() - 1) - weight);
                            String x2 = (stations.get(stations.size() - 1));
                            graph.addEdge(stopname , x2 , edgeweight2);
                            stations.add(stopname);
                            times.add(weight);
                            tempdirection.add(directionid);
                            linenumber.add(whichline);
                        }
                    // içeriyorsa addvertex yapma , addEdge kısmına ortak vertex ve okuduğun linedaki stopname i ekle.
                    }
                    else if (directionid != tempdirection.get(tempdirection.size() - 1)) { //Aynı yönde değilse
                        int edgeweight2 = (weight - times.get(times.size() - 2));
                        String stat = stations.get(stations.size() - 1);
                         graph.addEdge(stat , stopname , edgeweight2);
                         tempdirection.add(directionid);
                         linenumber.add(whichline);
                         times.add(weight);

                    }
                }
                else if (x != whichline) {
                    boolean flag = false;
                    for (int i = 0; i < stations.size(); i++) {
                        if (stations.get(i).equals(stopname)) {
                            stations.add(stopname);
                            times.add(weight);
                            tempdirection.add(directionid);
                            linenumber.add(whichline);
                            flag = true;
                            break;
                        }

                        }
                        if (flag == false) {
                            graph.addVertex(stopname);
                            stations.add(stopname);
                            times.add(weight);
                            tempdirection.add(directionid);
                            linenumber.add(whichline);

                    }

                }
            }



        } }

    }
