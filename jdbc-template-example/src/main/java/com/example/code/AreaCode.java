package com.example.code;

public enum AreaCode implements Code {

	SHIGA("S", "滋賀"),
	KYOTO("K", "京都"),
	NARA("N", "奈良"),
	WAKAYAMA("W", "和歌山"),
	OSAKA("O", "大阪"),
	HYOGO("H", "兵庫");

	private String code;
	private String label;

	private AreaCode(final String code, final String label) {
		this.code = code;
		this.label = label;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
