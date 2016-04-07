name := "links"

version := "0.0.1-SNAPSHOT"

organization := "com.mamoreno.links"

scalaVersion := "2.11.7"


scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings"
)

libraryDependencies ++= Seq(
  "com.typesafe.slick"  %% 	"slick"           	% "3.1.0",
  "com.h2database"      % 	"h2"              	% "1.4.185",
  "ch.qos.logback"      % 	"logback-classic" 	% "1.1.2",
  "org.scalatest" 		%% 	"scalatest" 		% "2.2.4" 		% "test"
)

