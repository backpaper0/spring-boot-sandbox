package main

import (
	"encoding/xml"
	"io"
	"io/fs"
	"net/http"
	"os"
	"strings"
	"text/template"
)

type Metadata struct {
	LatestVersion string `xml:"versioning>latest"`
}

func getLatestVersion(name string) string {
	resp, err := http.Get("https://repo.maven.apache.org/maven2/" + name + "/maven-metadata.xml")
	if err != nil {
		panic(err)
	}
	bs, err := io.ReadAll(resp.Body)
	if err != nil {
		panic(err)
	}
	md := &Metadata{LatestVersion: ""}
	err = xml.Unmarshal(bs, &md)
	if err != nil {
		panic(err)
	}
	return md.LatestVersion
}

type Data struct {
	SpringBootVersion     string
	DomaVersion           string
	DomaSpringBootVersion string
	TestcontainersVersion string
	Dirs                  []string
}

func main() {

	dirs := make([]string, 0)
	fs.WalkDir(os.DirFS("."), ".", func(path string, d fs.DirEntry, err error) error {
		if err != nil {
			panic(err)
		}
		if !d.IsDir() && strings.HasSuffix(path, "/pom.xml") && !strings.Contains(path, "/target/classes/META-INF/maven/") {
			dir, _, _ := strings.Cut(path, "/pom.xml")
			dirs = append(dirs, dir)
		}
		return nil
	})

	data := &Data{
		SpringBootVersion:     getLatestVersion("org/springframework/boot/spring-boot"),
		DomaVersion:           getLatestVersion("org/seasar/doma/doma-core"),
		DomaSpringBootVersion: getLatestVersion("org/seasar/doma/boot/doma-spring-boot-starter"),
		TestcontainersVersion: getLatestVersion("org/testcontainers/testcontainers-bom"),
		Dirs:                  dirs,
	}

	t := `<?xml version='1.0' encoding='UTF-8'?>
<project xsi:schemaLocation='http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd' xmlns='http://maven.apache.org/POM/4.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>{{.SpringBootVersion}}</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>spring-boot-sandbox-parent</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>
	<properties>
		<java.version>21</java.version>
		<spring-cloud.version>2022.0.4</spring-cloud.version>
		<spring-cloud-aws.version>3.0.2</spring-cloud-aws.version>
		<doma.version>{{.DomaVersion}}</doma.version>
		<doma.boot.version>{{.DomaSpringBootVersion}}</doma.boot.version>
		<testcontainers.version>{{.TestcontainersVersion}}</testcontainers.version>
		<database-rider.version>1.41.0</database-rider.version>
		<springdoc.version>2.2.0</springdoc.version>
		<mybatis-spring-boot-starter.version>3.0.3</mybatis-spring-boot-starter.version>
		<mybatis-generator-maven-plugin.version>1.4.2</mybatis-generator-maven-plugin.version>
		<greenmail.version>2.1.0-alpha-2</greenmail.version>
		<argLine>-Duser.language=ja -Duser.country=JP -Duser.timezone=Asia/Tokyo</argLine>
	</properties>
	<modules>{{range .Dirs}}
		<module>{{.}}</module>{{end}}
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
			<dependency>
				<groupId>org.testcontainers</groupId>
				<artifactId>testcontainers-bom</artifactId>
				<version>${testcontainers.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			<dependency>
				<groupId>io.awspring.cloud</groupId>
				<artifactId>spring-cloud-aws-dependencies</artifactId>
				<version>${spring-cloud-aws.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			<dependency>
				<groupId>com.github.database-rider</groupId>
				<artifactId>rider-junit5</artifactId>
				<version>${database-rider.version}</version>
			</dependency>
			<dependency>
				<groupId>org.springdoc</groupId>
				<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
				<version>${springdoc.version}</version>
			</dependency>
			<dependency>
				<groupId>org.mybatis.spring.boot</groupId>
				<artifactId>mybatis-spring-boot-starter</artifactId>
				<version>${mybatis-spring-boot-starter.version}</version>
			</dependency>
			<dependency>
				<groupId>com.icegreen</groupId>
				<artifactId>greenmail-junit5</artifactId>
				<version>${greenmail.version}</version>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<build>
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>org.mybatis.generator</groupId>
					<artifactId>mybatis-generator-maven-plugin</artifactId>
					<version>${mybatis-generator-maven-plugin.version}</version>
				</plugin>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-compiler-plugin</artifactId>
					<configuration>
						<compilerArgs>
							<arg>-Xlint:deprecation</arg>
						</compilerArgs>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>
	<repositories>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>maven-snapshots</id>
			<name>Maven Snapshots</name>
			<url>https://oss.sonatype.org/content/repositories/snapshots</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
	<pluginRepositories>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>
</project>`
	tt := template.Must(template.New("pom").Parse(t))
	tt.Execute(os.Stdout, data)
}
