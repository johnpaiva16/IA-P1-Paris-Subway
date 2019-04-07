package teste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Principal {

	private static final double distanciasDiretas[][] = {

			{ 0.0, 10.0, 18.5, 24.8, 36.4, 38.8, 35.8, 25.4, 17.6, 9.1, 16.7, 27.3, 27.6, 29.8 },
			{ 0.0, 0.0, 8.5, 14.8, 26.6, 29.1, 26.1, 17.3, 10, 3.5, 15.5, 20.9, 19.1, 21.8 },
			{ 0.0, 0.0, 0.0, 6.3, 18.2, 20.6, 17.6, 13.6, 9.4, 10.3, 19.5, 19.1, 12.1, 16.6 },
			{ 0.0, 0.0, 0.0, 0.0, 12, 14.4, 11.5, 12.4, 12.6, 16.7, 23.6, 18.6, 10.6, 15.4 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 3.0, 2.4, 19.4, 23.3, 28.2, 34.2, 24.8, 14.5, 17.9 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.3, 22.3, 25.7, 30.3, 36.7, 27.6, 15.2, 18.2 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 23.0, 27.3, 34.2, 25.7, 12.4, 15.6 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 8.2, 20.3, 16.1, 6.4, 22.7, 27.6 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 13.5, 11.2, 10.9, 21.2, 26.6 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 17.6, 24.2, 18.7, 21.2 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 14.2, 31.5, 35.5 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.8, 33.6 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.1 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }

	};

	private static final double distanciasReais[][] = {

			{ 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 8.5, 0.0, 0.0, 0.0, 0.0, 0.0, 10, 3.5, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 6.3, 0.0, 0.0, 0.0, 0.0, 9.4, 0.0, 0.0, 0.0, 18.7, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 13, 0.0, 0.0, 15.3, 0.0, 0.0, 0.0, 0.0, 12.8, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 3.0, 2.4, 30.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 9.6, 0.0, 0.0, 6.4, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 12.2, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.1 },
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } };

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		Linha azul = new Linha(1, "azul");
		Linha amarela = new Linha(2, "amarela");
		Linha verde = new Linha(3, "verde");
		Linha vermelha = new Linha(4, "vermelha");

		List<Node> nodes = inicializarNos();
//
//		for (Node node : nodes) {
//			System.out.print("Nó "+node.getNumeroEstacao() + "\n  Vizinhos: ");
//			for (Edge vizinho : node.getAdjacencies()) {
//				System.out.print(vizinho.getTarget().getNumeroEstacao() + " ");
//			}
//			System.out.println("\n");
//		}

		azul.getEstacoes().add(nodes.get(1 - 1));
		azul.getEstacoes().add(nodes.get(2 - 1));
		azul.getEstacoes().add(nodes.get(3 - 1));
		azul.getEstacoes().add(nodes.get(4 - 1));
		azul.getEstacoes().add(nodes.get(5 - 1));
		azul.getEstacoes().add(nodes.get(6 - 1));

		amarela.getEstacoes().add(nodes.get(7 - 1));
		amarela.getEstacoes().add(nodes.get(8 - 1));
		amarela.getEstacoes().add(nodes.get(9 - 1));
		amarela.getEstacoes().add(nodes.get(10 - 1));

		vermelha.getEstacoes().add(nodes.get(11 - 1));
		vermelha.getEstacoes().add(nodes.get(9 - 1));
		vermelha.getEstacoes().add(nodes.get(3 - 1));
		vermelha.getEstacoes().add(nodes.get(13 - 1));

		verde.getEstacoes().add(nodes.get(12 - 1));
		verde.getEstacoes().add(nodes.get(8 - 1));
		verde.getEstacoes().add(nodes.get(4 - 1));
		verde.getEstacoes().add(nodes.get(13 - 1));
		verde.getEstacoes().add(nodes.get(14 - 1));

		List<Linha> linhas = new ArrayList<>();
		linhas.add(vermelha);
		linhas.add(azul);
		linhas.add(verde);
		linhas.add(amarela);

		System.out.println("Escola a Estação de partida");

		int inicio = input.nextInt();
		Node noInicial = nodes.get(inicio - 1);

		System.out.println("Escola a Estação de destino");

		int fim = input.nextInt();
		Node noFinal = nodes.get(fim - 1);

		List<Node> path = AStar(noInicial, noFinal, linhas);
		double cost = 0;
		for (Node node : path) {
			System.out.print("| " + node.getNumeroEstacao() + " |");
			cost = node.getF_scores();
		}
		System.out.println("\nCusto : " +cost+ " min");
	}

	public static List<Node> AStar(Node start, Node goal, List<Linha> linhas) {
		Set<Node> explored = new HashSet<Node>();

		PriorityQueue<Node> queue = new PriorityQueue<Node>(Comparator.comparing(Node::getF_scores));

		// cost from start
		start.setG_scores(0);

		queue.add(start);

		boolean found = false;

		while ((!queue.isEmpty()) && (!found)) {

			// the node in having the lowest f_score value
			Node current = queue.poll();

			explored.add(current);

			// goal found
			if (current.equals(goal)) {
				found = true;
				return printPath(goal);
			}
			// System.out.println("Nó atual "+current.getNumeroEstacao());
			// System.out.print("f "+current.getF_scores()+"\ng "+current.getG_scores()+"\nh
			// "+current.getH_scores()+"\n Linha Atual "+current.getLinhaAtual());

			// check every child of current node
			for (Edge e : current.getAdjacencies()) {

				Node child = e.getTarget();
				double cost = e.getCost();

				for (Linha linha : linhas) {
					if (!linha.getEstacoes().contains(current) && linha.getEstacoes().contains(e.getTarget())) {
						cost += 4;
					}
				}

				double temp_g_scores = current.getG_scores() + cost;
				double temp_f_scores = temp_g_scores + child.getH_scores();

				/*
				 * if child node has been evaluated and the newer f_score is higher, skip
				 */

				if ((explored.contains(child)) && (temp_f_scores >= child.getF_scores())) {
					continue;
				}

				/*
				 * else if child node is not in queue or newer f_score is lower
				 */

				else if ((!queue.contains(child)) || (temp_f_scores < child.getF_scores())) {

					child.setParent(current);
					child.setG_scores(temp_g_scores);
					child.setF_scores(temp_f_scores);

					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child);

				}
			}
		}
		return null;

	}

	public static List<Node> printPath(Node target) {
		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.getParent()) {
			path.add(node);
		}

		Collections.reverse(path);

		return path;
	}

	private static double h(Node start, Node goal) {
		double distanciaDireta = distanciasDiretas[start.getNumeroEstacao() - 1][goal.getNumeroEstacao() - 1];
		if (distanciaDireta == 0) {
			distanciaDireta = distanciasDiretas[goal.getNumeroEstacao() - 1][start.getNumeroEstacao() - 1];
		}
		return converterHous2Minutes(distanciaDireta);
	}

	public static double g(Node neighbor, Node goal) {
		double distanciaReal = distanciasReais[neighbor.getNumeroEstacao() - 1][goal.getNumeroEstacao() - 1];
		if (distanciaReal == 0) {
			distanciaReal = distanciasDiretas[goal.getNumeroEstacao() - 1][neighbor.getNumeroEstacao() - 1];
		}
		return converterHous2Minutes(distanciaReal);

	}

	public static double f(Node start, Node goal) {
		return g(start, goal) + h(start, goal);
	}

	public static double converterHous2Minutes(double distancia) {
		return (distancia / 30.0) * 60.0;
	}

	public static List<Node> inicializarNos() {
		List<Node> estacoes = new ArrayList<>();
		for (int i = 1; i <= 14; i++) {
			estacoes.add(new Node(i));
		}
		return addVizinhos(estacoes);
	}

	public static List<Node> addVizinhos(List<Node> nos) {
		for (Node e : nos) {
			for (int i = 0; i < distanciasReais.length; i++) {
				if (distanciasReais[e.getNumeroEstacao() - 1][i] > 0.0
						|| distanciasReais[i][e.getNumeroEstacao() - 1] > 0.0) {
					// System.out.println("Nó "+ e.getNumeroEstacao()+" E"+e.getNumeroEstacao()+"-->
					// E"+(i+1));
					e.getAdjacencies().add(new Edge(nos.get(i), f(e, nos.get(i))));
				}
			}
		}
		return nos;
	}

}
