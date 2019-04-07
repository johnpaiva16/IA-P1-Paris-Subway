
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Programa {

	static final double TEMPO_TROCA_LINHA = 4;

	static double custoReal[][] = criaMatrizCusto();
	static double heuristica[][] = criaMatrizHeuristica();
	static List<Estacao> estacoes = criaEstacoes();
	static Map<CorLinha, Linha> linhas = criaLinhas(estacoes);

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("Estação de origem: ");
		int idOrigem = in.nextInt();
		System.out.print("Estação de destino: ");
		int idDestino = in.nextInt();

		addVizinhos();
		aStar(estacoes.get(idOrigem - 1), estacoes.get(idDestino - 1));

		in.close();
	}

	public static List<Estacao> returnPath(Estacao target) {
		List<Estacao> path = new ArrayList<Estacao>();

		for (Estacao node = target; node != null; node = node.getPai()) {
			path.add(node);

		}
		Collections.reverse(path);
		for (Estacao node : path) {
			System.out.println("Estação: E" + node.getIdEstacao());
			System.out.println("Valor F: " + node.getCustoF());
			System.out.println("Valor G: " + node.getCustoG());
			System.out.println("Valor H: " + node.getCustoH());
			System.out.println("Pai :" + node.getPai());
			System.out.println("----------------------------------");

		}
		return path;
	}

	public static void aStar(Estacao origem, Estacao destino) {
		adicionaCustoHeuristica(destino);
		Set<Estacao> explored = new HashSet<>();
		PriorityQueue<Estacao> queue = new PriorityQueue<>(20, new Comparator<Estacao>() {

			public int compare(Estacao i, Estacao j) {
				if (i.getCustoF() > j.getCustoF()) {
					return 1;
				} else if (i.getCustoF() < j.getCustoF()) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		queue.add(origem);

		boolean found = false;
		while ((!queue.isEmpty()) && (!found)) {
			Estacao current = queue.poll();
			explored.add(current);

			if (current.getIdEstacao() == destino.getIdEstacao()) {
				found = true;
			}

			for (Vizinho e : current.getVizinhos()) {
				Estacao child = e.getEstacao();
				double cost = e.getCusto();
				
				double temp_g_scores = current.getCustoG() + cost;
				double temp_f_scores = temp_g_scores + child.getCustoH();

				if ((explored.contains(child)) && (temp_f_scores >= child.getCustoF())) {
					continue;
				}

				else if ((!queue.contains(child)) || (temp_f_scores < child.getCustoF())) {
					child.setPai(current);

					boolean trocaDeLinha = true;
					for (CorLinha linha : child.getLinhas()) {
						if (current.getLinhas().contains(linha)) {
							trocaDeLinha = false;
							break;
						}
					}

					if (trocaDeLinha) {
						System.out.println("----------------------- O usuário deve troca de linha nas estações");
						temp_g_scores += TEMPO_TROCA_LINHA;
						temp_f_scores += TEMPO_TROCA_LINHA;
					}

					child.setCustoG(temp_g_scores);
					child.setCustoF(temp_f_scores);

					if (!queue.contains(child)) {
						// queue.remove(child);
						queue.add(child);
					}
				}
			}
		}
		System.out.println("Rota: " + returnPath(destino) + "\nCusto total (minutos): " + destino.getCustoF());
	}

	public static void adicionaCustoHeuristica(Estacao destino) {
		for (Estacao e : estacoes) {
			e.setCustoH(calculaFuncaoH(e, destino));
		}
	}

	public static void addVizinhos() {
		for (Estacao e : estacoes) {
			for (int i = 0; i < custoReal.length; i++) {
				if (custoReal[e.getIdEstacao() - 1][i] > 0 || custoReal[i][e.getIdEstacao() - 1] > 0) {
					Estacao estacaoVizinho = estacoes.get(i);
					e.getVizinhos().add(new Vizinho(estacaoVizinho, calculaFuncaoG(e, estacaoVizinho)));
				}
			}
		}
	}

	public static double converteCustoParaMinutos(double custoDistancia) {
		final int VELOCIDADE = 30;
		return (custoDistancia / VELOCIDADE) * 60;
	}

	public static double calculaFuncaoG(Estacao origem, Estacao estacaoAtual) {
		if (converteCustoParaMinutos(custoReal[origem.getIdEstacao() - 1][estacaoAtual.getIdEstacao() - 1]) != 0)
			return converteCustoParaMinutos(custoReal[origem.getIdEstacao() - 1][estacaoAtual.getIdEstacao() - 1]);

		return converteCustoParaMinutos(custoReal[estacaoAtual.getIdEstacao() - 1][origem.getIdEstacao() - 1]);
	}

	public static double calculaFuncaoH(Estacao estacaoAtual, Estacao destino) {
		if (converteCustoParaMinutos(heuristica[estacaoAtual.getIdEstacao() - 1][destino.getIdEstacao() - 1]) != 0)
			return converteCustoParaMinutos(heuristica[estacaoAtual.getIdEstacao() - 1][destino.getIdEstacao() - 1]);

		return converteCustoParaMinutos(heuristica[destino.getIdEstacao() - 1][estacaoAtual.getIdEstacao() - 1]);
	}

	public static List<Estacao> criaEstacoes() {
		List<Estacao> estacoes = new ArrayList<>();
		for (int i = 1; i <= 14; i++) {
			estacoes.add(new Estacao(i));
		}
		return estacoes;
	}

	public static Map<CorLinha, Linha> criaLinhas(List<Estacao> estacoes) {
		Map<CorLinha, Linha> linhas = new Hashtable<CorLinha, Linha>();
		for (CorLinha cor : CorLinha.values()) {
			linhas.put(cor, new Linha());
		}

		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(0));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(1));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(2));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(3));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(4));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(5));

		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(3));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(7));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(11));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(12));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(13));

		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(2));
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(8));
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(10));
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(12));

		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(1));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(4));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(6));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(7));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(8));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(9));

		estacoes.get(0).getLinhas().add(CorLinha.AZUL);
		estacoes.get(1).getLinhas().add(CorLinha.AZUL);
		estacoes.get(2).getLinhas().add(CorLinha.AZUL);
		estacoes.get(3).getLinhas().add(CorLinha.AZUL);
		estacoes.get(4).getLinhas().add(CorLinha.AZUL);
		estacoes.get(5).getLinhas().add(CorLinha.AZUL);

		estacoes.get(3).getLinhas().add(CorLinha.VERDE);
		estacoes.get(7).getLinhas().add(CorLinha.VERDE);
		estacoes.get(11).getLinhas().add(CorLinha.VERDE);
		estacoes.get(12).getLinhas().add(CorLinha.VERDE);
		estacoes.get(13).getLinhas().add(CorLinha.VERDE);

		estacoes.get(2).getLinhas().add(CorLinha.VERMELHO);
		estacoes.get(8).getLinhas().add(CorLinha.VERMELHO);
		estacoes.get(10).getLinhas().add(CorLinha.VERMELHO);
		estacoes.get(12).getLinhas().add(CorLinha.VERMELHO);

		estacoes.get(1).getLinhas().add(CorLinha.AMARELO);
		estacoes.get(4).getLinhas().add(CorLinha.AMARELO);
		estacoes.get(6).getLinhas().add(CorLinha.AMARELO);
		estacoes.get(7).getLinhas().add(CorLinha.AMARELO);
		estacoes.get(8).getLinhas().add(CorLinha.AMARELO);
		estacoes.get(9).getLinhas().add(CorLinha.AMARELO);

		return linhas;
	}

	public static double[][] criaMatrizCusto() {
		double t2[][] =
				// E1 - E2 - E3 - E4 - E5 - E6 - E7 - E8 - E9 - E10 - E11 - E12 - E13 - E14
				/* E1 */ { { 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E1
						/* E2 */ { 0.0, 0.0, 8.5, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 3.5, 0.0, 0.0, 0.0, 0.0 }, // E2
						/* E3 */ { 0.0, 0.0, 0.0, 6.3, 0.0, 0.0, 0.0, 0.0, 9.4, 0.0, 0.0, 0.0, 18.7, 0.0 }, // E3
						/* E4 */ { 0.0, 0.0, 0.0, 0.0, 13.0, 0.0, 0.0, 15.3, 0.0, 0.0, 0.0, 0.0, 12.8, 0.0 }, // E4
						/* E5 */ { 0.0, 0.0, 0.0, 0.0, 0.0, 3.0, 2.4, 30.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E5
						/* E6 */ { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E6
						/* E7 */ { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E7
						/* E8 */ { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 9.6, 0.0, 0.0, 6.4, 0.0, 0.0 }, // E8
						/* E9 */ { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 12.2, 0.0, 0.0, 0.0 }, // E9
						/* E10 */{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E10
						/* E11 */{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E11
						/* E12 */{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E12
						/* E13 */{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.1 }, // E13
						/* E14 */{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } }; // E14

		return t2;
	}

	public static double[][] criaMatrizHeuristica() {
		double t1[][] =
				// E1 - E2 - E3 - E4 - E5 - E6 - E7 - E8 - E9 - E10 - E11 - E12 - E13 - E14
				{ { 0.0, 10.0, 18.5, 24.8, 36.4, 38.8, 35.8, 25.4, 17.6, 9.1, 16.7, 27.3, 27.6, 29.8 }, // E1
						{ 0.0, 0.0, 8.5, 14.8, 26.6, 29.1, 26.1, 17.3, 10.0, 3.5, 15.5, 20.9, 19.1, 28.1 }, // E2
						{ 0.0, 0.0, 0.0, 6.3, 18.2, 20.6, 17.6, 13.6, 9.4, 10.3, 19.5, 19.1, 12.1, 16.6 }, // E3
						{ 0.0, 0.0, 0.0, 0.0, 12.0, 14.4, 11.5, 12.4, 12.6, 16.7, 23.6, 18.6, 10.6, 15.4 }, // E4
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 3.0, 2.4, 19.4, 23.3, 28.2, 34.2, 24.8, 14.5, 17.9 }, // E5
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.3, 22.3, 25.7, 30.3, 36.7, 27.6, 15.2, 18.2 }, // E6
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 23.0, 27.3, 34.2, 25.7, 12.4, 15.6 }, // E7
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 8.2, 20.3, 16.1, 6.4, 22.7, 27.6 }, // E8
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 13.5, 11.2, 10.9, 21.2, 26.6 }, // E9
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 17.6, 24.2, 18.7, 21.2 }, // E10
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 14.2, 31.5, 35.5 }, // E11
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.8, 33.6 }, // E12
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.1 }, // E13
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } }; // E14

		return t1;
	}
}
