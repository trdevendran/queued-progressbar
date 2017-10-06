# QueuedProgressBar
QueuedProgressBar is a progress bar for android. It displays animated running balls as algorithm of Queue(first in first out).
  - Drawn completely using android canvas

## Demo

 <img src="https://github.com/trdevendran/queued-progressbar/blob/master/app/src/main/assets/running-animated-ball-demo.gif" alt="" data-canonical-src="https://gyazo.com/eb5c5741b6a9a16c692170a41a49c858.png" width="575" height="805" />

## Integration
Integrating QueuedProgressBar in your project is quite easy.
### Step 1:
Add this dependency in your project's build.gradle file which is in your app folder
```groovy
compile 'com.github.trdevendran:queued-progressbar:1.0.0'
```
add this to your dependencies.
## Step 2:
Now place the QueuedProgressBar in your layout.
```xml
<com.printfthoughts.queuedprogressbar.QueuedProgressBar
            android:id="@+id/test_queued_progressbar"
            android:layout_width="match_parent"
            android:layout_height="40dp"
            app:ballColor="@color/red"
            app:ballCount="7"
            app:interval="5"
            app:ballRadius="4" />

```
## Step 3:
### Initialize your view
```java
QueuedProgressBar queuedProgressbar = (QueuedProgressBar) findViewById(R.id.queued_progressbar);
```
| Params  | Description |
| ------------- | ------------- |
| `app:ballColor`  | To set the color of the ball's color.  |
| `app:ballCount`  | To set the total no of balls.  |
| `app:interval`  | To set the interval speed of running animation  |
| `app:ballRadius`  | To set the radius value of the ball.  |

Or, you can change colors on runtime also
```java
queuedProgressbar.setBallColor(int color);
queuedProgressbar.setTotalBalls(int ballCount);
queuedProgressbar.setBallRadius(int radius);
queuedProgressbar.setIntervalValue(int interval);
```

All colors should have be provided as color int.

*Example*
```java
queuedProgressbar.setBallColor(Color.RED);
queuedProgressbar.setBallColor(Color.parseColor("#303F9F"));
queuedProgressbar.setBallColor(ContextCompat.getColor(context, R.color.your_color));
```
#### Default properties
Ball properties will be applied by default for colorAccent as ballColor, 3 as interval, 5 as ballCount and 5 as ballRadius