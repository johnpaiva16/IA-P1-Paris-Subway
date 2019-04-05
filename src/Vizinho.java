

public class Vizinho {

	private Estacao estacao;
	private double custo;
	
	public Vizinho() {}
	
	public Vizinho(Estacao estacao, double custo) {
		this.estacao = estacao;
		this.custo = custo;
	}
	
	public Estacao getEstacao() {
		return estacao;
	}
	public void setEstacao(Estacao estacao) {
		this.estacao = estacao;
	}
	public double getCusto() {
		return custo;
	}
	public void setCusto(double custo) {
		this.custo = custo;
	}
	
	
}
