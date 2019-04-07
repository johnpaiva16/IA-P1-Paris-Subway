package teste;


public class Edge {
	private  double cost;
	private  Node target;
	private  boolean baldeacao;

	public Edge(Node targetNode, double costVal, boolean b) {
		target = targetNode;
		cost = costVal;
		baldeacao = b;
	}

	public Edge(Node targetNode, double costVal) {
		target = targetNode;
		cost = costVal;
		baldeacao = false;
	}
	
	public Edge(Node targetNode) {
		target = targetNode;
		cost = 0;
		baldeacao = false;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public boolean isBaldeacao() {
		return baldeacao;
	}

	public void setBaldeacao(boolean baldeacao) {
		this.baldeacao = baldeacao;
	}
	
	
}
