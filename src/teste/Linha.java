package teste;

import java.util.ArrayList;
import java.util.List;

public class Linha {
	
	private String linha;
	private int numero;
	
	List<Node> estacoes=new ArrayList<>();

	public Linha(int i, String string) {
		this.numero=i;
		this.linha=string;
				
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<Node> getEstacoes() {
		return estacoes;
	}

	public void setEstacoes(List<Node> estacoes) {
		this.estacoes = estacoes;
	}
	
	

}
