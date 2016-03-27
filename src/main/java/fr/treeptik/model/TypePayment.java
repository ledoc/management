/**
 * 
 */
package fr.treeptik.model;

/**
 * @author herve
 *
 */
public enum TypePayment {

	SALAIRE("salaire"), REMBOURSEMENT("remboursement"),PRELEVEMENT("prélèvement"), CHEQUE("chèque"), LIQUIDE("liquide"), CARTEBANCAIRE("carte bancaire");

	private String description;

	private TypePayment(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
