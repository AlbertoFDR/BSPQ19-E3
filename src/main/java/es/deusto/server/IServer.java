package es.deusto.server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.server.jdo.Admin;
import es.deusto.server.jdo.Article;
import es.deusto.server.jdo.User;

public interface IServer extends Remote {
	
	
	Boolean registerUser(String login, String password) throws RemoteException;
	User logIn(String user, String pass) throws RemoteException;
	Article readArticle() throws RemoteException;
	Boolean createArticle(String title, String body, int visits, String category, Admin autho) throws RemoteException;
	ArrayList<Article> searchArticleTitle() throws RemoteException;
	ArrayList<Article> searchArticleCategory() throws RemoteException;
	ArrayList<Article> searchArticleAuthor() throws RemoteException;
	Boolean deleteArticle(Article art) throws RemoteException;
	Boolean editArticle(Article e, String newTitle, boolean changeTitle, String newBody, boolean changeBody) throws RemoteException;
	ArrayList<Article> viewTopArticle() throws RemoteException;
	Boolean SayHello() throws RemoteException;

} 
