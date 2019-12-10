require "rexml/document"
require "net/http"

http = Net::HTTP.new("repo.maven.apache.org")

response = http.get("http://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot/maven-metadata.xml")
spring_boot_version = REXML::Document.new(response.body).elements["metadata/versioning/latest"].get_text

File.open("pom.xml", "w") { |out|
  out.puts <<_EOS_
<?xml version='1.0' encoding='UTF-8'?>
<project xsi:schemaLocation='http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd' xmlns='http://maven.apache.org/POM/4.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>#{spring_boot_version}</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>spring-boot-sandbox-parent</artifactId>
	<version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>

	<properties>
		<java.version>11</java.version>
	</properties>

    <modules>
_EOS_

  poms = `git ls-files | grep /pom\.xml$`.split("\n")
  poms.each do |pom|
    mod = pom[0, pom.size - "/pom.xml".size]
    out.puts "        <module>#{mod}</module>"
  end

  out.puts <<_EOS_
    </modules>

</project>
_EOS_
}

