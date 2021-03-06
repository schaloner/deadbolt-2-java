name := "deadbolt-java"

lazy val root = (project in file(".")).enablePlugins(PlayWeb).disablePlugins(PlayFilters, PlayLogback, PlayAkkaHttpServer)

scalaVersion := "2.13.1"

crossScalaVersions := Seq("2.12.10", "2.13.1")

organization := "be.objectify"

libraryDependencies := libraryDependencies.value.filterNot(m => m.name == "twirl-api" || m.name == "play-server") ++ Seq(
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "org.awaitility" % "awaitility" % "3.0.0" % "test",
  playCore % "provided",
  javaCore % "test",
)

releasePublishArtifactsAction := PgpKeys.publishSigned.value

javacOptions += "-Xlint:deprecation"
scalacOptions += "-deprecation"

resolvers += Resolver.sonatypeRepo("snapshots")

TwirlKeys.templateImports ++= Seq(
  "java.util.Optional"
)
Test / TwirlKeys.templateImports ++= Seq(
  "java.util.List",
  "play.core.j.PlayMagicForJava._",
)