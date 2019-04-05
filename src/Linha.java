
import java.util.ArrayList;
import java.util.List;

public class Linha {

	List<Estacao> estacoes = new ArrayList<>();
	CorLinha corLinha;

	
	public Linha() {}
	
	public Linha(CorLinha corLinha) {
		this.corLinha = corLinha;
	}

	
	public List<Estacao> getEstacoes() {
		return estacoes;
	}

	public void setEstacoes(List<Estacao> estacoes) {
		this.estacoes = estacoes;
	}

	public CorLinha getCorLinha() {
		return corLinha;
	}

	public void setCorLinha(CorLinha corLinha) {
		this.corLinha = corLinha;
	}
	
	
	
	
}
