
JAVAC = javac
JFLAGS = -g

SOURCES = golfGame/BallStash.java golfGame/Bollie.java golfGame/DrivingRangeApp.java golfGame/golfBall.java golfGame/Golfer.java golfGame/Range.java 

.SUFIXES: .java .class

.java.class:
	$(JAVAC) $(JFLAGS) $<

golfGame/DrivingRangeApp.class: golfGame/BallStash.class golfGame/Bollie.class golfGame/golfBall.class golfGame/Golfer.class golfGame/Range.class
	$(JAVAC)  $(JFLAGS)  golfGame/DrivingRangeApp.java
golfGame/Golfer.class : golfGame/golfBall.class golfGame/BallStash.class golfGame/Range.class
	$(JAVAC)  $(JFLAGS)  golfGame/Golfer.java
golfGame/Range.class : golfGame/golfBall.class 
	$(JAVAC)  $(JFLAGS)  golfGame/Range.java
golfGame/BallStash.class : golfGame/golfBall.class
	$(JAVAC)  $(JFLAGS)  golfGame/BallStash.java
golfGame/Bollie.class : golfGame/BallStash.class golfGame/Range.class golfGame/golfBall.class
	$(JAVAC)  $(JFLAGS)  golfGame/Bollie.java
golfGame/golfBall.class :
	$(JAVAC)  $(JFLAGS)  golfGame/golfBall.java

		

clean:
	@rm -f $(SOURCES:.java=.class)
run: 
	java golfGame/DrivingRangeApp ${a} ${b} ${c}