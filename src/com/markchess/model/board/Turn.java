package com.markchess.model.board;

public enum Turn {
	WHITE, BLACK;

	@Override
	public String toString() {
		String result = "";
		switch (this) {
		case WHITE:
			result = "w";
			break;
		case BLACK:
			result = "b";
			break;
		}
		return result;
	}
}
