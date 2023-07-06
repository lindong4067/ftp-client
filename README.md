# ftp-client
Java FTP client that can be executed directly

## Dependency
```xml
    <dependency>
        <groupId>commons-net</groupId>
        <artifactId>commons-net</artifactId>
        <version>${commons-net.version}</version>
    </dependency>
```
### Build
```shell
mvn clean install
```
### Usage
```shell
java -jar ftp-client-3.0.jar -s ftp.example.com -P 21 -u your-username -p your-password -l /path/to/local/file.zip -r /path/to/remote/file.zip -a
# -s: ftp server address
# -P: ftp server port
# -u: ftp username
# -p: ftp password
# -l: local file path
# -r: remote file path
# -a: active mode(optional, default passive mode)
```