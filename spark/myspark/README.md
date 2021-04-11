SETUP
* Versions
  * https://spark.apache.org/docs/latest/  3.1.1
  
* Java
  * read https://github.com/AdoptOpenJDK/homebrew-openjdk
  * brew tap AdoptOpenJDK/openjdk
  * brew install --cask adoptopenjdk11
  * add jdk() to ~/.zshrc
  * jdk 11
  * intellij, add jdk, set src level to 11
  * Maven jdk 11: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html#java-9-or-later
  
* Scala
  * https://www.scala-lang.org/download/2.12.10.html
  * brew install scala@2.12
    * it installed openjdk 15
    * For the system Java wrappers to find this JDK, symlink it with
      sudo ln -sfn /usr/local/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
      <p>
      openjdk is keg-only, which means it was not symlinked into /usr/local,
      because macOS provides similar software and installing this software in
      parallel can cause all kinds of trouble.
      <p>
      If you need to have openjdk first in your PATH, run:
      echo 'export PATH="/usr/local/opt/openjdk/bin:$PATH"' >> ~/.zshrc
      <p>
      For compilers to find openjdk you may need to set:
      export CPPFLAGS="-I/usr/local/opt/openjdk/include"
    * Scala 12 caveats
      To use with IntelliJ, set the Scala home to:
      /usr/local/opt/scala@2.12/idea
      <p>
      scala@2.12 is keg-only, which means it was not symlinked into /usr/local,
      because this is an alternate version of another formula.
      <p>
      If you need to have scala@2.12 first in your PATH, run:
      echo 'export PATH="/usr/local/opt/scala@2.12/bin:$PATH"' >> ~/.zshrc
  
* Hello World
  * https://spark.apache.org/docs/latest/quick-start.html
  
* ISSUES
  * Logging. Can't cleanly swap in logback-classic and exclude log4j* as something refs org/apache/log4j/spi/Filter
  * Java 11 warning See https://issues.apache.org/jira/browse/SPARK-27981
    * WARNING: Illegal reflective access by org.apache.spark.unsafe.Platform (file:/Users/paul/.m2/repository/org/apache/spark/spark-unsafe_2.12/3.1.1/spark-unsafe_2.12-3.1.1.jar) to constructor java.nio.DirectByteBuffer(long,int)
  * 