package de.hse.blogstream.twitter;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;

public class DataAccessRepos {

    private Twitter twitter;
    private ConnectionRepository repository;

    private static DataAccessRepos repos;

    public DataAccessRepos(Twitter twitter, ConnectionRepository repository){
        this.twitter = twitter;
        this.repository = repository;
    }

    public ConnectionRepository getRepository() {
        return repository;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public static void setRepos(DataAccessRepos repos) {
        DataAccessRepos.repos = repos;
    }

    public static DataAccessRepos getRepos() {
        return repos;
    }
}
