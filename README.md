# Web of Needs - BookPileBot

The bot reacts to user book needs, crawls search results from https://buechereien.wien.gv.at/ related to the need and generates atoms for each of them.

The bot can be interacted with in three ways:
* generate an atom "Find a Book" with the search query as the Title
* generate an atom "Look for something" and relate it to books by adding a tag that cointains the word "book", "buch" or "bücher"
  search query as Title or ISBN (if ISBN is filled in this is used for the search query no matter the title)
* connect with the bots atom and send him a message containing the book title or isbn you are looking for

The BookPileBot is a [Spring Boot Application](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html).

## The team: 
[bookpilebot](https://github.com/orgs/WoN-Hackathon-2019/teams/bookpilebot)

Members:
* https://github.com/sammybaer
* https://github.com/legarou

## Cooperations
The [BookMatchBot](https://github.com/orgs/WoN-Hackathon-2019/teams/bookmatchbot)
uses our generated atoms to match with user generated atoms searching for books.

Further more, we used their BookAtomModelWrapper and extended it.

## Running the bot

### Prerequisites

- [Openjdk 8](https://adoptopenjdk.net/index.html) - the method described here does **not work** with the Oracle 8 JDK!
- Maven framework set up

### On the command line

```
cd won-bookpilebot
export WON_NODE_URI="https://hackathonnode.matchat.org/won"
mvn clean package
java -jar target/bot.jar
```
Now go to [What's new](https://hackathon.matchat.org/owner/#!/overview) to find your bot, connect and [create an atom](https://hackathon.matchat.org/owner/#!/create) or message your bot to see the bot in action.

### In Intellij Idea
1. Create a run configuration for the class `won.bot.bookpile.BookPileBot`
2. Add the environment variables

  * `WON_NODE_URI` pointing to your node uri (e.g. `https://hackathonnode.matchat.org/won` without quotes)
  
  to your run configuration.
  
3. Run your configuration

If you get a message indicating your keysize is restricted on startup (`JCE unlimited strength encryption policy is not enabled, WoN applications will not work. Please consult the setup guide.`), refer to [Enabling Unlimited Strength Jurisdiction Policy](https://github.com/open-eid/cdoc4j/wiki/Enabling-Unlimited-Strength-Jurisdiction-Policy) to increase the allowed key size.

##### Optional Parameters for both Run Configurations:
- `WON_KEYSTORE_DIR` path to folder where `bot-keys.jks` and `owner-trusted-certs.jks` are stored (needs write access and folder must exist) 


