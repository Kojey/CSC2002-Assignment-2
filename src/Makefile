
JAVAC = javac
JFLAGS = -g

SOURCES = golfGame/BallStash.java golfGame/Bollie.java golfGame/DrivingRangeApp.java golfGame/golfBall.java golfGame/Golfer.java golfGame/Range.java 

.SUFIXES: .java .class

.java.class:
	$(JAVAC) $(JFLAGS) $<

golfGame/DrivingRangeApp.class: golfGame/BallStash.java golfGame/Bollie.java golfGame/golfBall.java golfGame/Golfer.java golfGame/Range.java
	$(JAVAC)  $(JFLAGS)  golfGame/DrivingRangeApp.java
golfGame/Golfer.class : golfGame/golfBall.java golfGame/BallStash.java golfGame/Range.java
	$(JAVAC)  $(JFLAGS)  golfGame/Golfer.java
golfGame/Range.class : golfGame/golfBall.java 
	$(JAVAC)  $(JFLAGS)  golfGame/Range.java
golfGame/BallStash.class : golfGame/golfBall.java
	$(JAVAC)  $(JFLAGS)  golfGame/BallStash.java
golfGame/Bollie.class : golfGame/BallStash.java golfGame/Range.java golfGame/golfBall.java
	$(JAVAC)  $(JFLAGS)  golfGame/Bollie.java
golfGame/golfBall.class :
	$(JAVAC)  $(JFLAGS)  golfGame/golfBall.java

		

clean:
	@rm -f $(SOURCES:.java=.class)
run: 
	java golfGame/DrivingRangeApp ${a} ${b} ${c} ${d}