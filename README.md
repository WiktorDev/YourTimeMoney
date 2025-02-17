# YourTimeMoney
YourTimeMoney is a Minecraft server plugin that allows players to earn virtual currency based on their playtime. This system encourages player engagement and rewards active participation on the server.

# Commands
### Admin commands:
- **/ytc-admin give <player> <amount>** - give coins to user
- **/ytc-admin take <player> <amount>** - take coins from user
- **/ytc-admin info <player>** - information about user
- **/ytc-admin reload** - reload plugin configs
### Player commands:
Player commands can be modified in configuration.yml
- **/ytimecoins** - return amount of player coins
- **/ytimecoins shop** - open shop menu

# Placeholders
This feature requires the PlaceholderAPI plugin to be installed.
- %ytimecoins_coins% - player coins
- %ytimecoins_spent_coins% - coins spent by the player
- %ytimecoins_first_login_datetime% - player first login date (HH:mm:ss dd.MM.yyyy)
- %ytimecoins_first_login_date% - player first login date (dd.MM.yyyy)