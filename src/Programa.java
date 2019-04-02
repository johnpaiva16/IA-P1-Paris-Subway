import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Programa {

	static double custoReal [][] = criaMatrizCusto();
	static double heuristica [][] = criaMatrizHeuristica();
	static List<Estacao> estacoes = criaEstacoes();
	static Map<CorLinha, Linha> linhas = criaLinhas(estacoes);
	
	public static void main(String[] args) {
		
		addVizinhos();
		for (Estacao e: estacoes.get(2).getVizinhos()) {
			System.out.println(e.getIdEstacao());
		}
		
		//Linha linhaAzul = new Linha(CorLinha.AZUL);
		//Linha linhaVermelha = new Linha(CorLinha.VERMELHO);
		//Linha linhaVerde = new Linha(CorLinha.VERDE);
		//Linha linhaAmarela = new Linha(CorLinha.AMARELO);

		/*
		 * int l1 [] = {1,2,3,4,5,6}; int l2 [] = {4,8,12,13,14}; int l3 [] =
		 * {3,9,11,14}; int l4 [] = {2,5,7,8,9,10};
		 * 
		 * Linha linhaAzul = new Linha(l1, CorLinha.AZUL); 
		 * Linha linhaVerde = new Linha(l2, CorLinha.VERDE);
		 * Linha linhaVermelha = new Linha(l3, CorLinha.VERMELHO);
		 * Linha linhaAmarela = new Linha(l4, CorLinha.AMARELO);
		*/ 
		//List<Linha> linhas = new ArrayList<Linha>();
		//linhas.add(linhaAzul);
		//linhas.add(linhaVerde);
		//linhas.add(linhaVermelha);
		//linhas.add(linhaAmarela);
		 	
	}
	public static void addVizinhos() {
		for (Estacao e: estacoes) {
			for (int i=0; i < custoReal.length; i++) {
				if (custoReal[e.getIdEstacao() - 1][i] > 0) {
					e.getVizinhos().add(estacoes.get(i));
				}
			}
		}
	}

	public static double converteCustoParaMinutos(double custoDistancia) {
		final int VELOCIDADE = 30;
		return (custoDistancia / VELOCIDADE) * 60;
	}

	public static double calculaFuncao(Estacao estacaoInicial, Estacao estacaoDestino) {
		return calculaFuncaoG() + calculaFuncaoH();
	}

	private static double calculaFuncaoG() {
		return 0;
	}

	private static double calculaFuncaoH() {
		return 0;
	}

	public static Map<CorLinha, Linha> criaLinhas(List<Estacao> estacoes) {
		/*List<Linha> linhas = new ArrayList<Linha>();
		for(CorLinha cor : CorLinha.values()) {
			linhas.add(new Linha(cor));
		}
		*/
		
		Map<CorLinha, Linha> linhas = new Hashtable<CorLinha, Linha>();
		for (CorLinha cor: CorLinha.values()) {
			linhas.put(cor, new Linha());
		}
		
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(0));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(1));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(2));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(3));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(4));
		linhas.get(CorLinha.AZUL).getEstacoes().add(estacoes.get(6));

		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(3));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(7));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(11));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(12));
		linhas.get(CorLinha.VERDE).getEstacoes().add(estacoes.get(13));
		
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(2));
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(8));
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(10));
		linhas.get(CorLinha.VERMELHO).getEstacoes().add(estacoes.get(13));
		
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(1));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(4));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(6));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(7));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(8));
		linhas.get(CorLinha.AMARELO).getEstacoes().add(estacoes.get(9));

		return linhas;
	}
	public static List<Estacao> criaEstacoes() {
		List<Estacao> estacoes = new ArrayList<>();
		for (int i = 1; i <= 14; i++) {
			estacoes.add(new Estacao(i));
		}
		return estacoes;
	}
	public static double[][] criaMatrizCusto() {
		double t2[][] =
				// E1 - E2 - E3 - E4 - E5 - E6 - E7 - E8 - E9 - E10 - E11 - E12 - E13 - E14
				{ { 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E1
				{ 0.0, 0.0, 17.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 7.0, 0.0, 0.0, 0.0, 0.0 }, // E2
				{ 0.0, 0.0, 0.0, 12.6, 0.0, 0.0, 0.0, 0.0, 18.8, 0.0, 0.0, 0.0, 37.4, 0.0 }, // E3
				{ 0.0, 0.0, 0.0, 0.0, 26.0, 0.0, 0.0, 30.6, 0.0, 0.0, 0.0, 0.0, 25.6, 0.0 }, // E4
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 6.0, 4.8, 60.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E5
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E6
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E7
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 19.2, 0.0, 0.0, 12.8, 0.0, 0.0 }, // E8
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 22.4, 0.0, 0.0, 0.0 }, // E9
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E10
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E11
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // E12
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.2 }, // E13
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } }; // E14

		return t2;
	}

	public static double[][] criaMatrizHeuristica() {
		double t1[][] =
				// E1 - E2 - E3 - E4 - E5 - E6 - E7 - E8 - E9 - E10 - E11 - E12 - E13 - E14
				{ { 0.0, 20.0, 37.0, 49.6, 72.8, 77.6, 71.6, 50.8, 35.2, 18.2, 33.4, 54.6, 55.2, 59.6 }, // E1
						{ 0.0, 0.0, 17.0, 29.6, 53.2, 58.2, 52.2, 34.6, 20.0, 7.0, 31.0, 41.8, 38.2, 43.6 }, // E2
						{ 0.0, 0.0, 0.0, 12.6, 36.4, 41.2, 35.2, 27.2, 18.8, 20.6, 39.0, 38.2, 24.2, 33.2 }, // E3
						{ 0.0, 0.0, 0.0, 0.0, 24.0, 28.8, 23.0, 24.8, 25.2, 33.4, 47.2, 37.2, 21.2, 30.8 }, // E4
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 6.0, 4.8, 38.8, 46.6, 56.4, 68.4, 49.6, 29.0, 35.8 }, // E5
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 6.6, 44.6, 51.4, 60.6, 73.4, 55.2, 30.4, 36.4 }, // E6
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 46.0, 54.6, 68.4, 51.4, 24.8, 31.2 }, // E7
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 16.4, 40.6, 32.2, 12.8, 45.4, 55.2 }, // E8
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 27.0, 22.4, 21.8, 42.4, 53.2 }, // E9
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 35.2, 48.4, 37.4, 42.4 }, // E10
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.4, 63.0, 71.0 }, // E11
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 57.6, 67.2 }, // E12
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.2 }, // E13
						{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } }; // E14

		return t1;
	}
}
