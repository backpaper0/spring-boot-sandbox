require "rexml/document"
require "net/http"

http = Net::HTTP.new("repo.maven.apache.org")

response = http.get("http://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot/maven-metadata.xml")
spring_boot_version = REXML::Document.new(response.body).elements["metadata/versioning/latest"].get_text

poms = `git ls-files -- **/pom.xml`.split("\n")

poms.each do |pom|
  puts pom

  doc = REXML::Document.new(File.new("#{pom}"))
  parent_group = doc.elements["project/parent/groupId"]
  if parent_group && parent_group.get_text == "org.springframework.boot" then
    version = doc.elements["project/parent/version"]
    version.text = spring_boot_version
    doc.write(File.new("#{pom}", "w"))
  end
end
