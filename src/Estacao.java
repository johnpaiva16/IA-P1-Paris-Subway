import java.util.ArrayList;
import java.util.List;

public class Estacao {

	private int idEstacao;
	List <Estacao> vizinhos = new ArrayList<>();
	
	public Estacao(int idEstacao) {
		this.idEstacao = idEstacao;
	}

	
	public int getIdEstacao() {
		return idEstacao;
	}

	public void setIdEstacao(int idEstacao) {
		this.idEstacao = idEstacao;
	}


	public List<Estacao> getVizinhos() {
		return vizinhos;
	}


	public void setVizinhos(List<Estacao> vizinhos) {
		this.vizinhos = vizinhos;
	}
	
	
	
}
