require "rexml/document"

pjs = `git ls-files -- **/pom.xml`.split("\n").map { |pom| pom.split("/")[0] }

pjs.each do |pj|
  puts pj

  doc = REXML::Document.new(File.new("#{pj}/pom.xml"))
  ver_elem = doc.elements["project/properties/java.version"]
  ver_elem = doc.elements["project/properties/maven.compiler.target"] unless ver_elem
  ver = ver_elem.get_text

  File.open(".github/workflows/#{pj}.yml", "w") do |out|
    out.puts <<_EOF_
name: #{pj}

on:
  push:
    paths:
    - '#{pj}/**'

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v1
    - name: Set up JDK #{ver}
      uses: actions/setup-java@v1
      with:
        java-version: #{ver}
    - name: Build with Maven
      run: mvn -B test --file #{pj}/pom.xml
_EOF_

  end
end
