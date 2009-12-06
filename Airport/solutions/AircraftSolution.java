package solutions;

import jade.util.leap.Serializable;

public class AircraftSolution implements Serializable {

	public String getDescricao() {
		return descricao;
	}

	public int getVoo_afetado() {
		return voo_afetado;
	}

	public int getCusto() {
		return custo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setVoo_afetado(int vooAfetado) {
		voo_afetado = vooAfetado;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}

	@Override
	public String toString() {
		return "AircraftSolution [custo=" + custo + ", descricao=" + descricao
				+ ", voo_afetado=" + voo_afetado + "]";
	}

	String descricao;
	int voo_afetado;
	int custo;
	public AircraftSolution()
	{
	descricao = "";
	voo_afetado = -1;
	custo = -1;
	}
	
	public AircraftSolution(String descricao, int voo_afectado, int custo)
	{
		this.descricao = descricao;
		this.voo_afetado = voo_afectado;
		this.custo = custo;
	}
}
