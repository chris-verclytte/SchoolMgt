package src.protocole;

import java.io.Serializable;

public class Token implements Serializable {
	private static final long serialVersionUID = 7726173849036078140L;
	
	public String login;
	public String pwd;
	
	@Override
	public String toString() {
		return "Token [login=" + login + ", pwd=" + pwd + "]";
	}
}
