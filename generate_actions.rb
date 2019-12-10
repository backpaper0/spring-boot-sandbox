require "rexml/document"

pjs = `git ls-files | grep /pom\.xml$`.split("\n").map { |pom| pom.split("/")[0] }

pjs.each do |pj|
  puts pj

  version = '11'

  File.open(".github/workflows/#{pj}.yml", "w") do |out|
    out.puts <<_EOF_
name: #{pj}

on:
  push:
    paths:
    - '#{pj}/**'
    - 'pom.xml'

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v1
    - uses: actions/cache@v1
      with:
        path: ~/.m2/repository
        key: ${{ runner.os }}-maven-${{ hashFiles('#{pj}/pom.xml') }}
        restore-keys: |
          ${{ runner.os }}-maven-
    - name: Set up JDK #{version}
      uses: actions/setup-java@v1
      with:
        java-version: #{version}
    - name: Build with Maven
      run: mvn -B test --file #{pj}/pom.xml
_EOF_

  end
end

File.open("workflow-status.md", "w") do |out|
  out.puts "# workflow-status"
  out.puts ""
  pjs.each do |pj|
    badge_url = "https://github.com/backpaper0/spring-boot-sandbox/workflows/#{pj}/badge.svg"
    workflow_url = "https://github.com/backpaper0/spring-boot-sandbox/actions?query=workflow:#{pj}"
    out.puts "- [![](#{badge_url})](#{workflow_url})"
  end
end

