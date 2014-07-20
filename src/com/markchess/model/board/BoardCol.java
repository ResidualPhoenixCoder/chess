package com.markchess.model.board;

public enum BoardCol {
	a, b, c, d, e, f, g, h;

	@Override
	public String toString() {
		String result = "";
		switch (this) {
			case a:
				result = "a";
				break;
			case b:
				result = "b";
				break;
			case c:
				result = "c";
				break;
			case d:
				result = "d";
				break;
			case e:
				result = "e";
				break;
			case f:
				result = "f";
				break;
			case g:
				result = "g";
				break;
			case h:
				result = "h";
				break;
		}
		return result;
	}
}
