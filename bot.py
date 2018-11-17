from tweepy import Stream
from tweepy import OAuthHandler
from tweepy.streaming import StreamListener
from credentials import *
import json

cat1List = []
cat2List = []

# Access and authorize our Twitter credentials from credentials.py
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

def process(tweet):
    if 'nba' in tweet.lower():
        cat1List.append(0)
    elif 'nfl' in tweet.lower():
        cat2List.append(1)
    print("NBA:", len(cat1List))
    print("NFL:", len(cat2List))


class listener(StreamListener):

    def on_data(self, data):
        all_data = json.loads(data)
        tweet = all_data["text"]
        process(tweet)
        # print(tweet)
        #
        # print("NBA:" , len(cat1List))
        # print("NFL:" , len(cat2List))
        return(True)

    def on_error(self, status):
        print(status)

cat1 = 'nba'
cat2 = 'nfl'

twitterStream = Stream(auth, listener())
twitterStream.filter(track=[cat1, cat2])






# api = tweepy.API(auth)
#
# line = "Hello World!!"
#
# api.update_status(line)
