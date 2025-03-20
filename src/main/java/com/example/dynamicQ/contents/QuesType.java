package com.example.dynamicQ.contents;

public enum QuesType {

	SINGLE("單選題"), MULTI("多選題"), TEXT("文本問題");

	private String type;

	private QuesType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static boolean checkType(String type) {
		for (QuesType item : QuesType.values()) {
			if (type.equalsIgnoreCase(item.getType())) {
				return true;
			}
		}
		return false;
	}

}
