

import java.util.ArrayList;
import java.util.List;

public class Estacao {

	private int idEstacao;
	private List<Vizinho> vizinhos = new ArrayList<>();
	private double custoF;
	private double custoG;
	private double custoH;
	private Estacao pai;
	private List<CorLinha> linhas = new ArrayList<>();
	
	public Estacao(int idEstacao) {
		this.idEstacao = idEstacao;
	}

	public Estacao() {}
	
	
	
	public int getIdEstacao() {
		return idEstacao;
	}

	public void setIdEstacao(int idEstacao) {
		this.idEstacao = idEstacao;
	}


	public List<Vizinho> getVizinhos() {
		return vizinhos;
	}


	public void setVizinhos(List<Vizinho> vizinhos) {
		this.vizinhos = vizinhos;
	}

	public double getCustoF() {
		return custoF;
	}

	public void setCustoF(double custoF) {
		this.custoF = custoF;
	}

	public double getCustoG() {
		return custoG;
	}

	public void setCustoG(double custoG) {
		this.custoG = custoG;
	}

	public double getCustoH() {
		return custoH;
	}

	public void setCustoH(double custoH) {
		this.custoH = custoH;
	}

	public Estacao getPai() {
		return pai;
	}

	public void setPai(Estacao pai) {
		this.pai = pai;
	}
	
	 public List<CorLinha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<CorLinha> linhas) {
		this.linhas = linhas;
	}

	public String toString(){
         return String.valueOf(idEstacao) ;
 }
	 
	
}
