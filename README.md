# MomentoVelocityUtils

### Current Features
* Ability to define words in the config that will be checked against a players kick reason in order to decide whether to behave normally (kick them to hub) or kick them fully off of the network. Useful for kicking/banning as velocity's default behavior is to kick to hub.
* Ability to enable slashserver so users can use /<servername> to connect to a server instead of having to do /server <servername>. You can use this feature to setup a /hub or /lobby command by calling one of your hubs that.

#### Commands:
* /ismyserverdead => Shows you how many unique players are currently logged in.
* /ismyserverdead debug => Debug message that shows you your current ip.

#### Permissions:
* `mvu.ismyserverdead` => Access to /ismyserverdead and /ismyserverdead debug

#### TODO:
* add slash server functionality for example /skyblock
