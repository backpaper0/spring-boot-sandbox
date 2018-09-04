# Sprint Boot Sandbox








git grep -l 2.0.3|xargs sed -i -e 's/2\.0\.3/2.0.4/g'



for i in `git st -s|grep "^M"|cut -c 4-`; do mvn -f $i clean package -DskipTests; done

## License

Licensed under [The MIT License](https://opensource.org/licenses/MIT)
