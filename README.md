
## Running in the background: Services
Joscha Behrmann &lt;*behrmann@hm.edu*&gt;, Salman Alhadziev &lt;*salman.alhadziev0@hm.edu*&gt;

Services are widely used in almost every popular application. Services are typically applied to perform any sort of operation in the background, that might possibly block the flow of execution. This includes common I/O-operations such as operating on a file or network-communications with a backend server. Services might also be used to offer functionality that is frequently required by different applications as services can be exported. That way they are accessible not only by the originating app but every other application.

### Example 1
A really simple example that makes use of a unbound service to do some logging by implementing the services lifecycle-callbacks.

### Example 2
Demonstrates the use of a bound service. A server backend provides the current time which is polled by the service. If you want to run this example, make sure to run `main.go` to provide the backend. Also change the `apiBaseUrl` in `MyExampleService.kt` to reflect the actual location of the backend (e.g. its hostname or IP-address).

### Example 3
Shows the comparison between running work in a thread and running it using a foreground service. The idea is to allow the execution even after the application is destroyed. For that a simple thread is used, which logs a
message every second in a loop. First the thread is started regularly by creating a new instance of the thread
and running it using the start() method. The threads starts logging a message. However, when the application is closed (destroyed), the logging stops. To change that and allow the thread to run after the app is closed, a
foreground service is used, which creates the same thread and starts it. It is noticeable, that when the app is closed, the thread continues to log. A notification in the notification bar shows, that the service is running.
Only after starting the app again, the service can be stopped, which terminates the logging thread as well.
