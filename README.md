# Telegram-bot

Telegram bot to download songs from Spotify and YouTube url.

A spring boot Java app, which can easily be deployed to Heroku.

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/jitnegii/Telegram-bot.git
$ cd Telegram-bot
$ mvn clean install -Dbot.token=<your-bot-token> -Dbot.name=<your-bot-name> -Dserver.port=<server-port>
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

## Deploying to Heroku

```sh
$ heroku create <app-name>
$ heroku config:set TOKEN=<your-bot-token> NAME=<your-bot-name>
$ git push heroku master
$ heroku open
```

Note : Make sure you set the TOKEN and NAME correctly.

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
