
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JOptionPane;

public class Programa {

	static final double TEMPO_TROCA_LINHA = 4;

	static double custoReal[][] = criaMatrizCusto();
	static double heuristica[][] = criaMatrizHeuristica();
	static List<Estacao> estacoes = criaEstacoes();

	public static void main(String[] args) {
		vinculaEstacoesELinhas();
		addVizinhos();
		
		int idOrigem = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a Estação de origem: "));
		int idDestino = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a Estação de destino: "));
		
		aStar(estacoes.get(idOrigem - 1), estacoes.get(idDestino - 1));
	}

	public static List<Estacao> returnPath(Estacao target) {
		List<Estacao> path = new ArrayList<Estacao>();

		for (Estacao node = target; node != null; node = node.getPai()) {
			path.add(node);
		}
		Collections.reverse(path);
		return path;
	}

	public static void aStar(Estacao origem, Estacao destino) {
		if (origem.getIdEstacao() == destino.getIdEstacao()) {
			JOptionPane.showMessageDialog(null, "Você já se encontra no destino informado.");
			//System.out.println("Você já se encontra no destino informado.");
		} else {

			adicionaCustoHeuristica(destino);
			Set<Estacao> explored = new HashSet<>();
			PriorityQueue<Estacao> queue = new PriorityQueue<>(14, new Comparator<Estacao>() {

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

			CorLinha linhaAtual = null;
			CorLinha linhaAnterior = null;
			boolean found = false;
			while ((!queue.isEmpty()) && (!found)) {
				Estacao current = queue.poll();
				linhaAnterior = current.getLinhaAtual();
				explored.add(current);

				if (current.getIdEstacao() == destino.getIdEstacao()) {
					found = true;
					break;
				}

				for (Vizinho e : current.getVizinhos()) {
					Estacao child = e.getEstacao();
					double cost = e.getCusto();

					double temp_g_scores = current.getCustoG() + cost;
					double temp_f_scores = temp_g_scores + child.getCustoH();

					if ((explored.contains(child)) && (temp_f_scores >= child.getCustoF())) {
						continue;
					} else if ((!queue.contains(child)) || (temp_f_scores < child.getCustoF())) {
						if (child.getPai() == null) {
							child.setPai(current);
							
							for (CorLinha linha : child.getLinhas()) {
								if (current.getLinhas().contains(linha)) {
									linhaAtual = linha;
									current.setLinhaAtual(linha);
									child.setLinhaAtual(linha);
									if (current.getIdEstacao() == origem.getIdEstacao()) {
										linhaAnterior = linha;
									}
									break;
								}
							}

							if (!linhaAtual.equals(linhaAnterior)) {
								temp_g_scores += TEMPO_TROCA_LINHA;
								temp_f_scores += TEMPO_TROCA_LINHA;
							}
							child.setCustoG(temp_g_scores);
							child.setCustoF(temp_f_scores);
						}
						if (queue.contains(child)) {
							queue.remove(child);
						}
						queue.add(child);

					}
				}


			}
			
			JOptionPane.showMessageDialog(null, "Rota: " + returnPath(destino) + "\nCusto total: " + new BigDecimal(destino.getCustoF()).setScale(1, RoundingMode.HALF_EVEN)+" minutos");
			//System.out.println("Rota: " + returnPath(destino) + "\nCusto total (minutos): " + new BigDecimal(destino.getCustoF()).setScale(1, RoundingMode.HALF_EVEN));
		}

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

	public static void vinculaEstacoesELinhas() {
		Map<CorLinha, Linha> linhas = new Hashtable<CorLinha, Linha>();
		for (CorLinha cor : CorLinha.values()) {
			linhas.put(cor, new Linha());
		}

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

	}

	public static double[][] criaMatrizCusto() {
		double t2[][] = /* E1 */ { { 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E1
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
		double t1[][] = { { 0.0, 10.0, 18.5, 24.8, 36.4, 38.8, 35.8, 25.4, 17.6, 9.1, 16.7, 27.3, 27.6, 29.8 }, // E1
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