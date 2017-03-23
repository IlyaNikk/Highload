all: rm_httpd jar cp

jar:
	@mvn  clean package

cp:
	@cp ./target/httpd.jar ./httpd

clean: rm_httpd  mvn_clean

mvn_clean:
	@mvn clean

rm_httpd:
	@rm -f ./httpd
