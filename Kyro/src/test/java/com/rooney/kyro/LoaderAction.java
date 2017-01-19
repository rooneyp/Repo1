package com.rooney.kyro;

public enum LoaderAction {
	INSERT("insert"), CHANGE("change"), DELETE("endDate");

	private String label;

	private LoaderAction(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}