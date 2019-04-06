package es.deusto.server;

import java.rmi.Naming;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import es.deusto.server.jdo.Admin;
import es.deusto.server.jdo.Article;
import es.deusto.server.jdo.User;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	protected Server() throws RemoteException {
		super();
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	protected void finalize() throws Throwable {
		if (tx.isActive()) {
			tx.rollback();
		}
		// pm.close();
	}

	public Boolean registerUser(String login, String password) {

		tx.begin();
        System.out.println("Checking whether the user already exits or not: '" + login +"'");
		User user = null;
		try {
			user = pm.getObjectById(User.class, login);
		} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
			System.out.println("Exception launched: " + jonfe.getMessage());
		
		System.out.println("User: " + user);
		if (user != null) {
			System.out.println("Setting password user: " + user);
			user.setPassword(password);
			System.out.println("Password set user: " + user);
		} else {
			System.out.println("Creating user: " + user);
			user = new User(login, password);
			pm.makePersistent(user);					 
			System.out.println("User created: " + user);
		}
		tx.commit();
    }
    finally
    {
        if (tx.isActive())
        {
            tx.rollback();
        }

    }

    return true;
	}

	@Override
	public User logIn(String user, String pass) throws RemoteException {
		// TODO Auto-generated method stub
		User user1 = null;
		try {
			System.out.println("Log in user: "+ user);
			tx.begin();
			System.out.println("SELECT FROM " +User.class.getName()+" WHERE username == '"+user+ "'"+ " AND password == '"+ pass +"'");
			Query<?> query = pm.newQuery("SELECT FROM " +User.class.getName()+" WHERE username == '"+user+ "'"+ " && password == '"+ pass +"'");
			query.setUnique(true);
			user1 = (User)query.execute(); 
			tx.commit();
		} catch (Exception e) {
			System.out.println("Exception thrown during retrieval of Extent : " + e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		System.out.println("Correctly login");
		return user1;
	}


	@Override
	public Article readArticle() throws RemoteException {
		// TODO Auto-generated method stub
		 
		return null;
	}

	@Override
	public Boolean createArticle(String title, String body, int visits, String category, Admin autho) throws RemoteException {
		// TODO Auto-generated method stub
		try {
		tx.begin();
		//Use the SearchArticleTitle method here to check if the title is already in use.
        System.out.println("Checking whether the article title is already in use: ''");
        //Whether it's in use or not is reflected in the titleUsed boolean
		boolean titleUsed = false;
		if (titleUsed == true) {
			System.out.println("The title: " + title + " it's already in use, the transaction wasn't successful");
		} else {
			System.out.println("Creating a new article with title: " + title);
			System.out.println("Body: " + body);
			System.out.println("Number of visits: " + visits);
			System.out.println("Categorized as: " + category);
			Article article = new Article(title, body, visits, category, autho);
			pm.makePersistent(article);	
			System.out.println("New article with title: " + title + " created successfully");
		}
		tx.commit();
    }
    finally
    {
        if (tx.isActive())
        {
            tx.rollback();
        }

    }

    return true;
	}
	
	@Override
		public Boolean editArticle(Article art, String newTitle, boolean changeTitle, String newBody, boolean changeBody) throws RemoteException {
		// It's made so you can only change the articles title and body, we can make it more complex later.
		
		//Need to find the article with the method SearchArticleTitle(art)
		//Needs to be changed once SerachArticleTitle method is ready
			
		try {
		tx.begin();
		if (changeTitle == true) {
			art.setTitle(newTitle);
		}
		if (changeBody == true) {
			art.setBody(newBody);
		}
		tx.commit();
		} finally {
			if(tx.isActive()) {
				tx.rollback();
			}
		}
		 return true;
	}

	@Override
	public Boolean deleteArticle(Article art) throws RemoteException {
		try{
			tx.begin();
			pm.deletePersistent(art);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return true;
	}
	
	@Override
	public ArrayList<Article> searchArticleTitle() throws RemoteException {
		
		try{
			tx.begin();
			
			System.out.println("SELECT FROM " + Article.class.getTitle() + " WHERE title == \"" + title);
			Query<Article> q = pm.newQuery("SELECT FROM " + Article.class.getTitle() + " WHERE title == \"" + title);
			q.setUnique(true);
			ArrayList<Article> art = q.executeResultList(Article.class);
			//ArrayList<Article> art = (ArrayList<Article>)q.execute();
			
			System.out.println("Article: " + art);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}

	@Override
	public ArrayList<Article> searchArticleCategory() throws RemoteException {
		
		try{
			tx.begin();
		
			System.out.println("SELECT FROM " + Article.class.getCategory() + " WHERE category == \"" + category);
			Query<Article> q = pm.newQuery("SELECT FROM " + Article.class.getCategory() + " WHERE category == \"" + category);
			q.setUnique(true);
			q.executeResultList(Article.class)
			ArrayList<Article> arts = q.executeResultList(Article.class);
			//ArrayList<Article> arts = (ArrayList<Article>)q.execute();
			
			System.out.println("Articles: " + arts);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
	
	public ArrayList<Article> searchArticleAuthor() throws RemoteException {
			
			try{
				tx.begin();
			
				System.out.println("SELECT FROM " + Admin.class.getOwnArticles() + " WHERE author == \"" + Admin.class.getLogin() );
				Query<Article> q = pm.newQuery("SELECT FROM " + Admin.class.getOwnArticles() + " WHERE author == \"" + Admin.class.getLogin());
				q.setUnique(true);
				q.executeResultList(Admin.class.getOwnArticles())
				ArrayList<Article> ownArticles = q.executeResultList(Admin.class.getOwnArticles());
				//ArrayList<Article> arts = (ArrayList<Article>)q.execute();
				System.out.println("Articles: " + ownArticles);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
			}
		}
	}
	
	@Override
	public ArrayList<Article> viewTopArticle() throws RemoteException {
		try{
			tx.begin();
		
			System.out.println("SELECT FROM " + Article.class.getTitle() + " WHERE visits <= 1000\"");
			Query<Article> q = pm.newQuery("SELECT FROM " + Article.class.getCategory() + " WHERE visits <= 1000\"");
			q.setUnique(true);
			q.setOrdering("Visits ascending");
			ArrayList<Article> arts = q.executeResultList(Article.class);
			//ArrayList<Article> arts = (ArrayList<Article>)q.execute();
			
			System.out.println("Articles: " + arts);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
		return null;
	}

	@Override
	public Boolean SayHello() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		return true;
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IServer objServer = new Server();
			Naming.rebind(name, objServer);
			System.out.println("Server '" + name + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(System.in);
			java.io.BufferedReader stdin = new java.io.BufferedReader(inputStreamReader);
			@SuppressWarnings("unused")
			String line = stdin.readLine();
		} catch (Exception e) {
			System.err.println("Hello exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
