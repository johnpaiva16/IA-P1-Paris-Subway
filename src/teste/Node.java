package teste;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private int numeroEstacao;
	
	private Linha linhaAtual;
	
	private List<Linha>baldeacao=new ArrayList<>();

	private double g_scores;

	private Node parent;

	private List<Edge> adjacencies= new ArrayList<>();;

	private  double h_scores;

	private double f_scores;

	
	public Node(int numeroEstacao) {
		this.numeroEstacao = numeroEstacao;
	}

	public int getNumeroEstacao() {
		return numeroEstacao;
	}

	public void setNumeroEstacao(int numeroEstacao) {
		this.numeroEstacao = numeroEstacao;
	}

	public Linha getLinhaAtual() {
		return linhaAtual;
	}

	public void setLinhaAtual(Linha linhaAtual) {
		this.linhaAtual = linhaAtual;
	}

	public List<Linha> getBaldeacao() {
		return baldeacao;
	}

	public void setBaldeacao(List<Linha> baldeacao) {
		this.baldeacao = baldeacao;
	}

	public double getG_scores() {
		return g_scores;
	}

	public void setG_scores(double g_scores) {
		this.g_scores = g_scores;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Edge> getAdjacencies() {
		return adjacencies;
	}

	public void setAdjacencies(List<Edge> adjacencies) {
		this.adjacencies = adjacencies;
	}

	public double getH_scores() {
		return h_scores;
	}

	public void setH_scores(double h_scores) {
		this.h_scores = h_scores;
	}

	public double getF_scores() {
		return f_scores;
	}

	public void setF_scores(double f_scores) {
		this.f_scores = f_scores;
	}
	
	
	
	
	
	
	
	
	

}
