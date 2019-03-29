package es.deusto.server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.server.jdo.Article;
import es.deusto.server.jdo.User;

public interface IServer extends Remote {
	
	
	void registerUser(String login, String password) throws RemoteException;
	User logIn() throws RemoteException;
	void logOut() throws RemoteException;
	User signIn()  throws RemoteException;
	Article readArticle() throws RemoteException;
	Boolean createArticle() throws RemoteException;
	ArrayList<Article> searchArticleTitle() throws RemoteException;
	ArrayList<Article> searchArticleCategory() throws RemoteException;
	Boolean deleteArticle() throws RemoteException;
	Boolean editArticle() throws RemoteException;
	ArrayList<Article> viewTopArticle() throws RemoteException;
	

} 
