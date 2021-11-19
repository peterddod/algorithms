main : Main.java
	javac Main.java

instance :
	java -jar generator.jar param.txt instance.txt

rununstable : main
	java Main ExampleInstance.txt MatchingUnstable.txt

runstable : main
	java Main ExampleInstance.txt MatchingStable.txt

runalg : main
	java Main ExampleInstance.txt

runalg_newi : main instance
	java Main instance.txt

runalg_i : main
	java Main instance.txt