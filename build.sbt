ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.1"

lazy val root = (project in file("."))
  .settings(
    name := "AkkaTemplate"
  )

lazy val circeVersion = "0.14.5"

libraryDependencies ++= Seq(
  // Akka
  "com.typesafe.akka"           %% "akka-actor"                 % "2.8.2",
  "com.typesafe.akka"           %% "akka-stream"                % "2.8.2",


// Postgres DB
  "org.postgresql"              % "postgresql"                  % "42.5.4",

  // Slick
  "com.typesafe.slick"          %% "slick"                      % "3.4.1",

  // Slick Connection Pool Library
  "com.typesafe.slick"          %% "slick-hikaricp"             % "3.4.1",
  "com.github.tminglei"         %% "slick-pg"                   % "0.21.1",
  "com.github.tminglei"         %% "slick-pg_play-json"         % "0.21.1",

  // logging & H2
  "org.slf4j"                   % "slf4j-nop"                   % "2.0.5",
  "com.h2database"              % "h2"                          % "2.1.214",

  // JSON
  "io.circe"                    %% "circe-core"                 % circeVersion,
  "io.circe"                    %% "circe-generic"              % circeVersion,
  "io.circe"                    %% "circe-parser"               % circeVersion,

  // Testing
  "org.scalatest"               %% "scalatest"                  % "3.2.15"      % Test,
  "org.scalatest"               %% "scalatest-funspec"          % "3.2.15"      % Test,
  "org.scalatestplus"           %% "scalacheck-1-17"            % "3.2.16.0"    % Test,
  "org.scalamock"               %% "scalamock"                  % "5.2.0"       % Test,
  "org.scalatestplus.play"      %% "scalatestplus-play"         % "5.1.0"       % Test,
  "com.typesafe.akka"           %% "akka-testkit"               % "2.8.2"       % Test
)