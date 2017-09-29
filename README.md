# presleyBN

PresleyBN is a Server-Client application for testing and remote diagnosing multiple systems at a time.

**Command List**
- `execute`: Execute a command on client systems. This command has special uses described below. Example: `> execute echo Hello there!` will run `echo Hello there!` on clients.
- `status`: Displays how many clients are currently connected (work in progress).
- `stop`: Closes all client connections and stops the server. This command asks for confirmation when called.
- `execute ddos <ip>`: Executes a DDoS attack using client systems against a given IP. **Only use this on systems and networks you own!**
- `execute halt ddos`: Attempts to tell Client's to stop the running DDoS attack. **This may not always work as some clients may force their local networks to shutdown, allowing the DDoS to continue and you may have no control over the system.**
- The execute command is very useful in the fact that all (except DDoS) commands are run through the client command line interface. So, for example, no specific command is programmed for remote shutdown since you can use `execute shutdown now` to shutdown all client systems.

**Known Issues**
- The `execute halt ddos` command may not always work, as some clients may shutdown their local networks during the attack.
- Commands for Windows and Unix systems are not always the same, so keep this in mind when running commands on multiple OS's.
- As of right now, there is no way to run the program using the shipped JRE. Clients must have Java installed for this to run (this is being worked on).

The files in the `Builds` folder are pre-compiled binaries made by me. I strongly suggest changing the code to your needs (such as the server IP in the Client), and compiling it afterwards. The other files in the folder are for "installing" the program to a computer. It extracts the JRE to a folder in the C drive, as well as the start scripts and the Jar file. If you change the code, make sure to name the Jar file `Client.jar` so the start scripts can find it.

**Do NOT use this program against any systems or servers that you don't own! I am NOT responsible for anything that comes of this!**