require "rexml/document"
require "net/https"

def get_latest_version(name)
  http = Net::HTTP.new("repo.maven.apache.org", 443)
  http.use_ssl = true
  response = http.get("/maven2/#{name}/maven-metadata.xml")
  REXML::Document.new(response.body).elements["metadata/versioning/latest"].get_text
end

spring_boot_version = get_latest_version("org/springframework/boot/spring-boot")
doma_version = get_latest_version("org/seasar/doma/doma-core")
doma_spring_boot_version = get_latest_version("org/seasar/doma/boot/doma-spring-boot-starter")

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
		<spring-cloud.version>2020.0.3-SNAPSHOT</spring-cloud.version>
		<doma.version>#{doma_version}</doma.version>
		<doma.boot.version>#{doma_spring_boot_version}</doma.boot.version>
	</properties>

	<modules>
_EOS_

  poms = `git ls-files | grep /pom\.xml$`.split("\n")
  poms.each do |pom|
    mod = pom[0, pom.size - "/pom.xml".size]
    out.puts "		<module>#{mod}</module>"
  end

  out.puts <<_EOS_
	</modules>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<releases>
				<enabled>false</enabled>
			</releases>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

</project>
_EOS_
}

