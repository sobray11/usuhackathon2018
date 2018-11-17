package com.company;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.List;

public class Main {



    public static class Worker extends Thread {
        String search;
        int count = 0;

        public Worker(String search){
            this.search = search;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount(){
            count++;
        }

        @Override
        public void run() {

            streamFeed(search, this);

        }

    }

    public static void streamFeed(String search, Worker w) {

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice arg) {
            }
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            }
            @Override
            public void onStallWarning(StallWarning warning) {
            }
            @Override
            public void onStatus(Status status) {
                w.incrementCount();
                System.out.println(w.getCount() + " - " + search);
            }
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        twitterStream.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
        AccessToken accessToken = new AccessToken(accessTokenStr,
                accessTokenSecretStr);

        twitterStream.setOAuthAccessToken(accessToken);

        twitterStream.addListener(listener);

        twitterStream.filter(search);
    }


    public static void main(String[] args) {


        Worker w1 = new Worker("nba");
        Worker w2 = new Worker("nfl");
        w1.start();
        w2.start();

//        Twitter twitter = new TwitterFactory().getInstance();
//
//        twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
//        AccessToken accessToken = new AccessToken(accessTokenStr,
//                accessTokenSecretStr);
//
//        twitter.setOAuthAccessToken(accessToken);
//
//        try {
//            Query query = new Query("trump");
//            QueryResult result;
//            do {
//                result = twitter.search(query);
//                List<Status> tweets = result.getTweets();
//                for (Status tweet : tweets) {
//                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
//                    System.out.println(tweet.getCreatedAt());
//                }
//            } while ((query = result.nextQuery()) != null);
//            System.exit(0);
//        } catch (TwitterException te) {
//            te.printStackTrace();
//            System.out.println("Failed to search tweets: " + te.getMessage());
//            System.exit(-1);
//        }
    }

}